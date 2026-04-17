package de.leonkoth.blockparty.audio;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CentralHubAudioProvider implements AudioProvider {
    private static final String BROADCAST_AUDIO_EVENT_MUTATION =
            "mutation BroadcastAudioEvent($input: BroadcastAudioEventInput!) { " +
                    "broadcastAudioEvent(input: $input) { status action topic errors { code message field } } }";

    private final BlockParty blockParty;

    private HttpClient httpClient;
    private String apiBaseUrl;
    private String frontendBaseUrl;
    private String serverKey;
    private String installationFingerprint;

    public CentralHubAudioProvider(BlockParty blockParty) {
        this.blockParty = blockParty;
    }

    @Override
    public String getName() {
        return "Central Hub";
    }

    @Override
    public void initialize() {
        FileConfiguration config = blockParty.getConfig().getConfig();
        this.apiBaseUrl = sanitizeBaseUrl(config.getString("Audio.CentralHub.ApiBaseUrl"));
        this.frontendBaseUrl = sanitizeBaseUrl(config.getString("Audio.CentralHub.FrontendBaseUrl"));
        this.serverKey = ensureServerKey(config);
        this.installationFingerprint = ensureInstallationFingerprint(config);
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .executor(blockParty.getExecutorService())
                .build();
    }

    @Override
    public void shutdown() {
        this.httpClient = null;
    }

    @Override
    public void playTrackForPlayer(Player player, Arena arena, String trackIdentifier) {
        publishAudioEvent(arena, "play", trackIdentifier, player);
    }

    @Override
    public void stopForPlayer(Player player, Arena arena) {
        publishAudioEvent(arena, "stop", null, player);
    }

    @Override
    public void playTrackForArena(Arena arena, String trackIdentifier) {
        publishAudioEvent(arena, "play", trackIdentifier, null);
    }

    @Override
    public void pauseArena(Arena arena) {
        publishAudioEvent(arena, "pause", null, null);
    }

    @Override
    public void resumeArena(Arena arena) {
        publishAudioEvent(arena, "resume", null, null);
    }

    @Override
    public void stopArena(Arena arena) {
        publishAudioEvent(arena, "stop", null, null);
    }

    @Override
    public void handlePlayerJoin(Player player, Arena arena) {
        if (player == null || arena == null) {
            return;
        }

        String url = buildFrontendUrl(arena);
        TextComponent message = new TextComponent("§6[BlockParty] §eOpen the audio player for arena §f" + arena.getName() + "§e.");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        player.spigot().sendMessage(message);
    }

    @Override
    public void handlePlayerLeave(Player player, Arena arena) {
        stopForPlayer(player, arena);
    }

    @Override
    public String getConnectUrl(Arena arena) {
        if (arena == null) {
            return null;
        }

        return buildFrontendUrl(arena);
    }

    private void publishAudioEvent(Arena arena, String action, String trackIdentifier, Player player) {
        if (httpClient == null || arena == null) {
            return;
        }

        String payload = buildPayload(arena, action, trackIdentifier, player);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiBaseUrl + "graphql"))
                .timeout(Duration.ofSeconds(5))
                .header("Content-Type", "application/json")
                .header("x-server-key", serverKey)
                .header("x-installation-fingerprint", installationFingerprint)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(this::handlePublishResponse)
                .exceptionally(throwable -> {
                    blockParty.getPlugin().getLogger().warning("Failed to publish audio event to Central Hub: " + throwable.getMessage());
                    return null;
                });
    }

    private void handlePublishResponse(HttpResponse<String> response) {
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            blockParty.getPlugin().getLogger().warning("Failed to publish audio event to Central Hub: HTTP " + response.statusCode());
            return;
        }

        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        if (hasTopLevelErrors(root)) {
            blockParty.getPlugin().getLogger().warning("Failed to publish audio event to Central Hub: " + extractGraphQlMessages(root.getAsJsonArray("errors")));
            return;
        }

        JsonObject data = root.has("data") && root.get("data").isJsonObject()
                ? root.getAsJsonObject("data")
                : null;
        JsonObject payload = data != null && data.has("broadcastAudioEvent") && data.get("broadcastAudioEvent").isJsonObject()
                ? data.getAsJsonObject("broadcastAudioEvent")
                : null;

        if (payload == null) {
            blockParty.getPlugin().getLogger().warning("Failed to publish audio event to Central Hub: missing broadcastAudioEvent payload.");
            return;
        }

        JsonArray payloadErrors = payload.has("errors") && payload.get("errors").isJsonArray()
                ? payload.getAsJsonArray("errors")
                : new JsonArray();

        if (!payloadErrors.isEmpty()) {
            blockParty.getPlugin().getLogger().warning("Failed to publish audio event to Central Hub: " + extractGraphQlMessages(payloadErrors));
        }
    }

    private String buildPayload(Arena arena, String action, String trackIdentifier, Player player) {
        StringBuilder inputBuilder = new StringBuilder("{")
                .append("\"arenaId\":\"").append(escapeJson(arena.getName())).append("\",")
                .append("\"action\":\"").append(escapeJson(action)).append("\"");

        if (trackIdentifier != null && !trackIdentifier.isBlank()) {
            inputBuilder.append(",\"trackId\":\"").append(escapeJson(trackIdentifier)).append("\"");

            TrackCatalogService trackCatalogService = blockParty.getAudioManager() != null
                    ? blockParty.getAudioManager().getTrackCatalogService()
                    : null;
            String trackName = trackCatalogService != null
                    ? trackCatalogService.getDisplayName(trackIdentifier)
                    : trackIdentifier;

            inputBuilder.append(",\"trackName\":\"").append(escapeJson(trackName)).append("\"");
        }

        if (player != null) {
            inputBuilder.append(",\"playerUuid\":\"").append(player.getUniqueId()).append("\"");
        }

        inputBuilder.append("}");

        return new StringBuilder("{")
                .append("\"query\":\"").append(escapeJson(BROADCAST_AUDIO_EVENT_MUTATION)).append("\",")
                .append("\"variables\":{\"input\":").append(inputBuilder).append("}}")
                .toString();
    }

    private String ensureServerKey(FileConfiguration config) {
        String configuredKey = config.getString("Audio.CentralHub.ServerKey");
        if (configuredKey != null && !configuredKey.isBlank()) {
            return configuredKey;
        }

        String generatedKey = UUID.randomUUID().toString();
        config.set("Audio.CentralHub.ServerKey", generatedKey);
        blockParty.getConfig().save();
        return generatedKey;
    }

    private String ensureInstallationFingerprint(FileConfiguration config) {
        String configuredFingerprint = config.getString("Audio.CentralHub.InstallationFingerprint");
        if (configuredFingerprint != null && !configuredFingerprint.isBlank()) {
            return configuredFingerprint;
        }

        String generatedFingerprint = UUID.randomUUID().toString();
        config.set("Audio.CentralHub.InstallationFingerprint", generatedFingerprint);
        blockParty.getConfig().save();
        return generatedFingerprint;
    }

    private String buildFrontendUrl(Arena arena) {
        return frontendBaseUrl + "?serverKey="
                + encode(serverKey)
                + "&arena="
                + encode(arena.getName());
    }

    private String sanitizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Central Hub audio requires all configured base URLs.");
        }

        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private boolean hasTopLevelErrors(JsonObject root) {
        return root.has("errors") && root.get("errors").isJsonArray() && !root.getAsJsonArray("errors").isEmpty();
    }

    private String extractGraphQlMessages(JsonArray errors) {
        List<String> messages = new ArrayList<>();

        for (JsonElement element : errors) {
            if (!element.isJsonObject()) {
                continue;
            }

            JsonObject object = element.getAsJsonObject();
            if (object.has("message") && !object.get("message").isJsonNull()) {
                messages.add(object.get("message").getAsString());
            }
        }

        return messages.isEmpty() ? "unknown GraphQL error" : String.join("; ", messages);
    }

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

}
