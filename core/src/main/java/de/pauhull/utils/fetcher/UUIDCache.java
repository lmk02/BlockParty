package de.pauhull.utils.fetcher;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UUIDCache {

    private Map<UUID, String> nameCache;
    private Map<String, UUID> uuidCache;

    public UUIDCache(TimeUnit unit, long cacheTime) {
        this.nameCache = new TimedHashMap<>(unit, cacheTime);
        this.uuidCache = new TimedHashMap<>(unit, cacheTime);
    }

    public UUIDCache() {
        this(TimeUnit.HOURS, 12);
    }

    public void save(UUID uuid, String name) {
        nameCache.put(uuid, name);
        uuidCache.put(name, uuid);
    }

    public UUID getUUID(String name) {
        return uuidCache.get(name);
    }

    public String getName(UUID uuid) {
        return nameCache.get(uuid);
    }

}
