package de.leonkoth.blockparty.audio;

public record TrackCatalogEntry(
        String id,
        String title,
        boolean enabled,
        Long durationMillis
) {

    public String getDisplayName() {
        if (title != null && !title.isBlank()) {
            return title;
        }

        return id;
    }

}
