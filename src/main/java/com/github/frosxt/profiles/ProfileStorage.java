package com.github.frosxt.profiles;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Used to store and access player profiles
 * Can be extended to provide extra functionality, such as storing to a database
 */
public abstract class ProfileStorage {
    private final Map<UUID, LightProfile> profiles;

    /**
     * Creates a new profile storage instance
     */
    public ProfileStorage() {
        this.profiles = new HashMap<>();
    }

    /**
     * Get a profile by player UUID
     * @param uuid The UUID of the player
     * @return The profile of the player
     */
    public LightProfile getProfile(final UUID uuid) {
        return profiles.getOrDefault(uuid, null);
    }

    /**
     * Get a profile by player
     * @param player The player
     * @return The profile of the player
     */
    public LightProfile getProfile(final Player player) {
        return getProfile(player.getUniqueId());
    }

    /**
     * Gets a map containing profiles of given UUIDs
     * The map will only contain an entry for each provided UUID
     * if there is a corresponding profile in storage
     * @param uuids The UUIDs to get profile for
     * @return A map of UUIDs with profiles
     * @see #getProfile(UUID)
     */
    public Map<UUID, LightProfile> getProfiles(@NotNull final Iterable<UUID> uuids) {
        Objects.requireNonNull(uuids, "uuids should not be null");

        final Map<UUID, LightProfile> returnMap = new HashMap<>();
        for (final UUID uuid : uuids) {
            if (!profiles.containsKey(uuid)) {
                continue;
            }

            returnMap.put(uuid, getProfile(uuid));
        }

        return returnMap;
    }

    /**
     * Stores a profile in the map
     * @param profile The profile to store
     */
    public void saveProfile(final LightProfile profile) {
        profiles.put(profile.getUuid(), profile);
    }
}