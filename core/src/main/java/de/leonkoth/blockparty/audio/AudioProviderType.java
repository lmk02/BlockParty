package de.leonkoth.blockparty.audio;

public enum AudioProviderType {

    NONE,
    OPENAUDIOMC,
    CENTRAL_HUB;

    public static AudioProviderType fromConfig(String value) {
        if (value == null || value.isBlank()) {
            return NONE;
        }

        return switch (value.trim().toLowerCase()) {
            case "openaudiomc" -> OPENAUDIOMC;
            case "central_hub", "centralhub", "aura" -> CENTRAL_HUB;
            default -> NONE;
        };
    }

}
