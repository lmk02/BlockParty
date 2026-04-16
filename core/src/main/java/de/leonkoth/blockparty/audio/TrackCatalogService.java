package de.leonkoth.blockparty.audio;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.leonkoth.blockparty.BlockParty;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TrackCatalogService {

    private final BlockParty blockParty;
    private final Map<String, TrackCatalogEntry> tracksById = new ConcurrentHashMap<>();

    private HttpClient httpClient;
    private String apiBaseUrl;

    @Getter
    private volatile long lastRefreshMillis;

    @Getter
    private volatile String lastError;

    @Getter
    private volatile boolean catalogAvailable;

    public TrackCatalogService(BlockParty blockParty) {
        this.blockParty = blockParty;
    }

    public void initialize(boolean audioEnabled, AudioProviderType providerType) {
        if (!shouldUseRemoteCatalog(audioEnabled, providerType)) {
            clearState();
            return;
        }

        FileConfiguration config = blockParty.getConfig().getConfig();
        this.apiBaseUrl = sanitizeBaseUrl(config.getString("Audio.CentralHub.ApiBaseUrl"));
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .executor(blockParty.getExecutorService())
                .build();
        refreshAsync();
    }

    public void reload(boolean audioEnabled, AudioProviderType providerType) {
        if (!shouldUseRemoteCatalog(audioEnabled, providerType)) {
            clearState();
            return;
        }

        if (httpClient == null) {
            initialize(true, providerType);
            return;
        }

        FileConfiguration config = blockParty.getConfig().getConfig();
        this.apiBaseUrl = sanitizeBaseUrl(config.getString("Audio.CentralHub.ApiBaseUrl"));
        refreshAsync();
    }

    public void shutdown() {
        this.httpClient = null;
    }

    public boolean refreshAsync() {
        if (httpClient == null || apiBaseUrl == null) {
            return false;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiBaseUrl + "api/v1/tracks"))
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "application/json")
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(this::handleCatalogResponse)
                .exceptionally(throwable -> {
                    lastError = throwable.getMessage();
                    catalogAvailable = !tracksById.isEmpty();
                    blockParty.getPlugin().getLogger().warning("Failed to refresh Aura track catalog: " + throwable.getMessage());
                    return null;
                });
        return true;
    }

    public TrackCatalogEntry getTrack(String trackId) {
        if (trackId == null) {
            return null;
        }

        return tracksById.get(trackId.toLowerCase(Locale.ROOT));
    }

    public boolean hasTrack(String trackId) {
        return getTrack(trackId) != null;
    }

    public Collection<TrackCatalogEntry> getTracks() {
        List<TrackCatalogEntry> tracks = new ArrayList<>(tracksById.values());
        tracks.sort(Comparator.comparing(TrackCatalogEntry::getDisplayName, String.CASE_INSENSITIVE_ORDER));
        return tracks;
    }

    public String getDisplayName(String trackId) {
        TrackCatalogEntry entry = getTrack(trackId);
        return entry != null ? entry.getDisplayName() : trackId;
    }

    private void handleCatalogResponse(HttpResponse<String> response) {
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            lastError = "HTTP " + response.statusCode();
            catalogAvailable = !tracksById.isEmpty();
            blockParty.getPlugin().getLogger().warning("Failed to refresh Aura track catalog: HTTP " + response.statusCode());
            return;
        }

        Map<String, TrackCatalogEntry> refreshedTracks = parseTracks(response.body());
        tracksById.clear();
        tracksById.putAll(refreshedTracks);
        lastRefreshMillis = System.currentTimeMillis();
        lastError = null;
        catalogAvailable = true;
        blockParty.getPlugin().getLogger().info("Loaded " + refreshedTracks.size() + " approved Aura tracks.");
    }

    private Map<String, TrackCatalogEntry> parseTracks(String json) {
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonArray tracks = root.has("tracks") && root.get("tracks").isJsonArray()
                ? root.getAsJsonArray("tracks")
                : new JsonArray();
        Map<String, TrackCatalogEntry> parsedTracks = new ConcurrentHashMap<>();

        for (JsonElement element : tracks) {
            if (!element.isJsonObject()) {
                continue;
            }

            JsonObject object = element.getAsJsonObject();
            String id = getAsString(object, "id");
            if (id == null || id.isBlank()) {
                continue;
            }

            String title = getAsString(object, "title");
            boolean enabled = !object.has("enabled") || object.get("enabled").getAsBoolean();
            Long durationMillis = object.has("duration_ms") && !object.get("duration_ms").isJsonNull()
                    ? object.get("duration_ms").getAsLong()
                    : null;

            parsedTracks.put(id.toLowerCase(Locale.ROOT), new TrackCatalogEntry(id, title, enabled, durationMillis));
        }

        return parsedTracks;
    }

    private String getAsString(JsonObject object, String key) {
        if (!object.has(key) || object.get(key).isJsonNull()) {
            return null;
        }
        return object.get(key).getAsString();
    }

    private boolean shouldUseRemoteCatalog(boolean audioEnabled, AudioProviderType providerType) {
        return audioEnabled && providerType == AudioProviderType.CENTRAL_HUB;
    }

    private void clearState() {
        this.httpClient = null;
        this.apiBaseUrl = null;
        this.lastRefreshMillis = 0L;
        this.lastError = null;
        this.catalogAvailable = false;
        this.tracksById.clear();
    }

    private String sanitizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Audio.CentralHub.ApiBaseUrl must be configured for track catalog sync.");
        }

        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

}
