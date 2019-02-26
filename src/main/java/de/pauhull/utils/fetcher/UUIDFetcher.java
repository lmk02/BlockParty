package de.pauhull.utils.fetcher;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by Paul
 * on 23.11.2018
 *
 * @author pauhull
 */
public interface UUIDFetcher {

    static UUID parseUUIDFromString(String uuidAsString) {
        String[] parts = {
                "0x" + uuidAsString.substring(0, 8),
                "0x" + uuidAsString.substring(8, 12),
                "0x" + uuidAsString.substring(12, 16),
                "0x" + uuidAsString.substring(16, 20),
                "0x" + uuidAsString.substring(20, 32)
        };

        long mostSigBits = Long.decode(parts[0]);
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(parts[1]);
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(parts[2]);

        long leastSigBits = Long.decode(parts[3]);
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(parts[4]);

        return new UUID(mostSigBits, leastSigBits);
    }

    @Deprecated
    Profile fetchProfileSync(String playerName);

    void fetchProfileAsync(String playerName, Consumer<Profile> consumer);

    @Deprecated
    Profile fetchProfileSync(UUID uuid);

    void fetchProfileAsync(UUID uuid, Consumer<Profile> consumer);

    @Deprecated
    UUID fetchUUIDSync(String playerName);

    @Deprecated
    void fetchUUIDAsync(String playerName, Consumer<UUID> consumer);

    @Deprecated
    String fetchNameSync(UUID uuid);

    @Deprecated
    void fetchNameAsync(UUID uuid, Consumer<String> consumer);

    @Deprecated
    String getNameCaseSensitiveSync(String name);

    @Deprecated
    void getNameCaseSensitiveAsync(String name, Consumer<String> consumer);

}
