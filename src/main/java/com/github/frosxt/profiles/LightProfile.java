package com.github.frosxt.profiles;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Used to store information about a player
 * Can be very useful for neatly retrieving and storing player data
 * Can be extended to store extra information about a player aside from just their UUID
 */
@Getter
public abstract class LightProfile {
    private final UUID uuid;

    /**
     * Creates a new LightProfile object
     * @param uuid The UUID of the player to store
     */
    public LightProfile(@NotNull final UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Used to store a players profile directly from this class
     * @param profileStorage The profile storage instance
     */
    public void storeProfile(final ProfileStorage profileStorage) {
        profileStorage.saveProfile(this);
    }
}