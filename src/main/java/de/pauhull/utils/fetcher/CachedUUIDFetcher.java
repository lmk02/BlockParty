package de.pauhull.utils.fetcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.pauhull.utils.scheduler.Scheduler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class CachedUUIDFetcher implements UUIDFetcher {

    private static final String UUID_API_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String NAME_HISTORY_API_URL = "https://api.mojang.com/user/profiles/%s/names";

    @Getter
    protected static UUIDCache cache = new UUIDCache();

    @Setter
    @Getter
    protected ExecutorService executor;

    /**
     * Create CachedUUIDFetcher with given ExecutorService. ExecutorService needs to be shut down after use!
     *
     * @param executor ExecutorService to use ({@link Scheduler#createExecutorService()})
     */
    public CachedUUIDFetcher(ExecutorService executor) {
        this.executor = executor;
    }

    /**
     * Create CachedUUIDFetcher without ExecutorService. Can only be used for sync tasks.
     *
     * @deprecated Use {@link #CachedUUIDFetcher(ExecutorService)} instead
     */
    @Deprecated
    public CachedUUIDFetcher() {
        this(null);
    }

    /**
     * Not recommended. Halts the whole thread while fetching the profile.
     *
     * @param playerName Player to get UUID from
     * @return Player's profile
     * @deprecated Use {@link #fetchProfileAsync(String, Consumer)} instead
     */
    @Override
    @Deprecated
    @Nullable
    public Profile fetchProfileSync(@Nonnull String playerName) {

        UUID uuid = cache.getUUID(playerName);
        if (uuid != null) {
            return new Profile(playerName, uuid);
        }

        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            cache.save(player.getUniqueId(), player.getName());
            return new Profile(player.getName(), player.getUniqueId());
        }

        try {
            // Get response from Mojang API
            URL url = new URL(String.format(UUID_API_URL, playerName));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != 200) {
                return null;
            }

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // Parse JSON response and get UUID
            JsonElement element = new JsonParser().parse(bufferedReader);
            JsonObject object = element.getAsJsonObject();
            String uuidAsString = object.get("id").getAsString();
            String retrievedName = object.get("name").getAsString();

            inputStream.close();
            bufferedReader.close();

            // Return UUID
            UUID result = UUIDFetcher.parseUUIDFromString(uuidAsString);
            cache.save(result, retrievedName);
            return new Profile(retrievedName, result);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fetches profile asynchronously
     *
     * @param playerName Player to get profile from
     * @param consumer   Consumer of player profile
     * @throws NullPointerException Is thrown when the fetcher has no ExecutorService.
     */
    @Override
    public void fetchProfileAsync(@Nonnull String playerName, @Nonnull Consumer<Profile> consumer)
            throws NullPointerException {

        if (executor == null) {
            throw new NullPointerException("ExecutorService is null");
        }

        executor.execute(() -> consumer.accept(fetchProfileSync(playerName)));
    }

    /**
     * Fetches profile synchronously
     *
     * @param uuid Player to get profile from
     * @return Player profile
     * @deprecated Use {@link #fetchProfileAsync(UUID, Consumer)}
     */
    @Override
    @Deprecated
    @Nullable
    public Profile fetchProfileSync(@Nonnull UUID uuid) {

        String playerName = cache.getName(uuid);
        if (playerName != null) {
            return new Profile(playerName, uuid);
        }

        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            cache.save(player.getUniqueId(), player.getName());
            return new Profile(player.getName(), player.getUniqueId());
        }

        try {
            // Get response from Mojang API
            URL url = new URL(String.format(NAME_HISTORY_API_URL, uuid.toString().replace("-", "")));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != 200) {
                return null;
            }

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // Parse JSON response and return name
            JsonElement element = new JsonParser().parse(bufferedReader);
            JsonArray array = element.getAsJsonArray();
            JsonObject object = array.get(array.size() - 1).getAsJsonObject();

            bufferedReader.close();
            inputStream.close();

            String result = object.get("name").getAsString();
            cache.save(uuid, result);
            return new Profile(result, uuid);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fetches profile asynchronously
     *
     * @param uuid     Player to get profile from
     * @param consumer Consumer of player profile
     * @throws NullPointerException Is thrown when the fetcher has no ExecutorService.
     */
    @Override
    public void fetchProfileAsync(@Nonnull UUID uuid, @Nonnull Consumer<Profile> consumer)
            throws NullPointerException {

        if (executor == null) {
            throw new NullPointerException("ExecutorService is null");
        }

        executor.execute(() -> consumer.accept(fetchProfileSync(uuid)));
    }

    /**
     * Fetches UUID synchronously
     *
     * @param playerName Player to get UUID from
     * @return Player's uuid
     * @deprecated Fetch profile instead
     */
    @Override
    @Deprecated
    @Nullable
    public UUID fetchUUIDSync(String playerName) {
        Profile profile = fetchProfileSync(playerName);
        return profile == null ? null : profile.getUuid();
    }

    /**
     * Fetches UUID asynchronously
     *
     * @param playerName Player to get UUID from
     * @param consumer   Consumer of UUID
     * @throws NullPointerException Is thrown when the fetcher has no ExecutorService.
     * @deprecated Fetch profile instead
     */
    @Deprecated
    @Override
    public void fetchUUIDAsync(String playerName, Consumer<UUID> consumer)
            throws NullPointerException {

        fetchProfileAsync(playerName, profile -> consumer.accept(profile.getUuid()));
    }

    /**
     * Fetches name synchronously
     *
     * @param uuid Player to get name from
     * @return Player's name
     * @deprecated Fetch profile instead
     */
    @Override
    @Deprecated
    @Nullable
    public String fetchNameSync(UUID uuid) {
        Profile profile = fetchProfileSync(uuid);
        return profile == null ? null : profile.getPlayerName();
    }

    /**
     * Fetches name asynchronously
     *
     * @param uuid     Player to get name from
     * @param consumer Consumer of name
     * @throws NullPointerException Is thrown when the fetcher has no ExecutorService.
     * @deprecated Fetch profile instead
     */
    @Override
    @Deprecated
    public void fetchNameAsync(UUID uuid, Consumer<String> consumer) {
        fetchProfileAsync(uuid, profile -> consumer.accept(profile.getPlayerName()));
    }

    /**
     * Gets case-sensitive name from case-insensitive name
     *
     * @param name Case-insensitive name
     * @return Case-sensitive name
     */
    @Override
    @Deprecated
    @Nullable
    public String getNameCaseSensitiveSync(String name) {
        Profile profile = fetchProfileSync(name);
        return profile == null ? null : profile.getPlayerName();
    }

    /**
     * Gets case-sensitive name from case-insensitive name
     *
     * @param name     Case-insensitive name
     * @param consumer Consumer of case-sensitive name
     */
    @Override
    @Deprecated
    public void getNameCaseSensitiveAsync(String name, Consumer<String> consumer) {
        executor.execute(() -> consumer.accept(getNameCaseSensitiveSync(name)));
    }

}