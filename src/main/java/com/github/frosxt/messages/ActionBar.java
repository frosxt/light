package com.github.frosxt.messages;

import com.github.frosxt.utils.ReflectionUtils;
import com.google.common.base.Strings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;
import java.util.concurrent.Callable;


/**
 * A reflection API for action bars in Minecraft.
 * Fully optimized - Supports 1.8.8+ and above.
 * Requires ReflectionUtils.
 * Messages are not colorized by default.
 * <p>
 * Action bars are text messages that appear above
 * the player's <a href="https://minecraft.gamepedia.com/Heads-up_display">hotbar</a>
 * Note that this is different from the text appeared when switching between items.
 * Those messages show the item's name and are different from action bars.
 * The only natural way of displaying action bars is when mounting.
 * <p>
 * Action bars cannot fade or stay like titles.
 * For static Action bars you'll need to send the packet every
 * 2 seconds (40 ticks) for it to stay on the screen without fading.
 * <p>
 * PacketPlayOutTitle: https://wiki.vg/Protocol#Title
 *
 * @author Crypto Morin
 * @link <a href="https://github.com/CryptoMorin/XSeries/blob/master/src/main/java/com/cryptomorin/xseries/messages/ActionBar.java">...</a>
 * @version 4.0.0
 * @see ReflectionUtils
 */
public final class ActionBar {
    /**
     * If the server is running Spigot which has an official ActionBar API.
     * This should technically be available from 1.9, but TextComponent API
     * has some issues regarding colors from 1.9-1.11, so we're still going
     * to use NMS for anything below 1.12
     * We're not going to support Bukkit.
     */
    private static final boolean USE_SPIGOT_API = ReflectionUtils.supports(12);
    /**
     * ChatComponentText JSON message builder.
     */
    private static final MethodHandle CHAT_COMPONENT_TEXT;
    /**
     * PacketPlayOutChat
     */
    private static final MethodHandle PACKET_PLAY_OUT_CHAT;
    /**
     * GAME_INFO enum constant.
     */
    private static final Object CHAT_MESSAGE_TYPE;

    private static final char TIME_SPECIFIER_START = '^';
    private static final char TIME_SPECIFIER_END = '|';

    static {
        MethodHandle packet = null;
        MethodHandle chatComp = null;
        Object chatMsgType = null;

        if (!USE_SPIGOT_API) {
            // Supporting 1.12+ is not necessary, the package guards are just for readability.
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final Class<?> packetPlayOutChatClass = ReflectionUtils.getNMSClass("network.protocol.game", "PacketPlayOutChat");
            final Class<?> iChatBaseComponentClass = ReflectionUtils.getNMSClass("network.chat", "IChatBaseComponent");
            final Class<?> ChatSerializerClass = ReflectionUtils.getNMSClass("network.chat", "IChatBaseComponent$ChatSerializer");

            try {
                // JSON Message Builder
                // network.chat.ChatComponentText is for raw messages, we need to support colors.
                chatComp = lookup.findStatic(ChatSerializerClass, "a", MethodType.methodType(iChatBaseComponentClass, String.class));

                // Game Info Message Type
                final Class<?> chatMessageTypeClass = Class.forName(
                        ReflectionUtils.NMS + ReflectionUtils.v(17, "network.chat").orElse("") + "ChatMessageType"
                );

                // Packet Constructor
                final MethodType type = MethodType.methodType(void.class, iChatBaseComponentClass, chatMessageTypeClass);

                for (final Object obj : chatMessageTypeClass.getEnumConstants()) {
                    final String name = obj.toString();
                    if (name.equals("GAME_INFO") || name.equalsIgnoreCase("ACTION_BAR")) {
                        chatMsgType = obj;
                        break;
                    }
                }

                packet = lookup.findConstructor(packetPlayOutChatClass, type);
            } catch (final NoSuchMethodException | IllegalAccessException | ClassNotFoundException ignored) {
                try {
                    // Game Info Message Type
                    chatMsgType = (byte) 2;

                    // Packet Constructor
                    packet = lookup.findConstructor(packetPlayOutChatClass, MethodType.methodType(void.class, iChatBaseComponentClass, byte.class));
                } catch (final NoSuchMethodException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }

        CHAT_MESSAGE_TYPE = chatMsgType;
        CHAT_COMPONENT_TEXT = chatComp;
        PACKET_PLAY_OUT_CHAT = packet;
    }

    // Private constructor to prevent instantiation
    private ActionBar() {
        throw new UnsupportedOperationException("ActionBar is a utility class and cannot be instantiated!");
    }

    /**
     * Sends an action bar to a player.
     * This particular method supports a special prefix for
     * configuring the time of the actionbar.
     * <p>
     * <b>Format: {@code ^number|}</b>
     * <br>
     * where {@code number} is in seconds.
     * <br>
     * E.g. {@code ^7|&2Hello &4World!}
     * will keep the actionbar active for 7 seconds.
     *
     * @param player  the player to send the action bar to.
     * @param message the message to send.
     *
     * @see #sendActionBar(Plugin, Player, String, long)
     * @since 3.2.0
     */
    public static void sendActionBar(@Nonnull final Plugin plugin, @Nonnull final Player player, @Nullable final String message) {
        if (!Strings.isNullOrEmpty(message) && (message.charAt(0) == TIME_SPECIFIER_START)) {
                final int end = message.indexOf(TIME_SPECIFIER_END);
                if (end != -1) {
                    int time = 0;
                    try {
                        time = Integer.parseInt(message.substring(1, end)) * 20;
                    } catch (final NumberFormatException ignored) {
                        // Ignored catch block
                    }
                    if (time >= 0) sendActionBar(plugin, player, message.substring(end + 1), time);
                }
        }

        sendActionBar(player, message);
    }

    /**
     * Sends an action bar to a player.
     *
     * @param player  the player to send the action bar to.
     * @param message the message to send.
     *
     * @see #sendActionBar(Plugin, Player, String, long)
     * @since 1.0.0
     */
    public static void sendActionBar(@Nonnull final Player player, @Nullable final String message) {
        Objects.requireNonNull(player, "Cannot send action bar to null player");
        Objects.requireNonNull(message, "Cannot send null actionbar message");

        if (USE_SPIGOT_API) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            return;
        }

        try {
            // We need to escape both \ and " to avoid all possiblities of breaking JSON syntax and causing an exception.
            final Object component = CHAT_COMPONENT_TEXT.invoke("{\"text\":\"" + message.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
            final Object packet = PACKET_PLAY_OUT_CHAT.invoke(component, CHAT_MESSAGE_TYPE);
            ReflectionUtils.sendPacket(player, packet);
        } catch (final Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    /**
     * Sends an action bar to a player for a specific amount of ticks.
     *
     * @param plugin   the plugin handling the message scheduler.
     * @param player   the player to send the action bar to.
     * @param message  the message to send.
     * @param duration the duration to keep the action bar in ticks.
     *
     * @see #sendActionBarWhile(Plugin, Player, String, Callable)
     * @since 1.0.0
     */
    public static void sendActionBar(@Nonnull final Plugin plugin, @Nonnull final Player player, @Nullable final String message, final long duration) {
        if (duration < 1) return;
        Objects.requireNonNull(plugin, "Cannot send consistent actionbar with null plugin");
        Objects.requireNonNull(player, "Cannot send actionbar to null player");
        Objects.requireNonNull(message, "Cannot send null actionbar message");

        new BukkitRunnable() {
            long repeater = duration;

            @Override
            public void run() {
                sendActionBar(player, message);
                repeater -= 40L;
                if (repeater - 40L < -20L) cancel();
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 40L);
    }

    /**
     * Sends an action bar all the online players.
     *
     * @param message the message to send.
     *
     * @see #sendActionBar(Player, String)
     * @since 1.0.0
     */
    public static void sendPlayersActionBar(@Nullable final String message) {
        for (final Player player : Bukkit.getOnlinePlayers()) sendActionBar(player, message);
    }

    /**
     * Clear the action bar by sending an empty message.
     *
     * @param player the player to send the action bar to.
     *
     * @see #sendActionBar(Player, String)
     * @since 2.1.1
     */
    public static void clearActionBar(@Nonnull final Player player) {
        sendActionBar(player, " ");
    }

    /**
     * Clear the action bar by sending an empty message to all the online players.
     *
     * @see #clearActionBar(Player player)
     * @since 2.1.1
     */
    public static void clearPlayersActionBar() {
        for (final Player player : Bukkit.getOnlinePlayers()) clearActionBar(player);
    }

    /**
     * Sends an action bar to a player for a specific amount of ticks.
     * Plugin instance should be changed in this method for the schedulers.
     * <p>
     * If the caller returns true, the action bar will continue.
     * If the caller returns false, action bar will not be sent anymore.
     *
     * @param plugin   the plugin handling the message scheduler.
     * @param player   the player to send the action bar to.
     * @param message  the message to send. The message will not be updated.
     * @param callable the condition for the action bar to continue.
     *
     * @see #sendActionBar(Plugin, Player, String, long)
     * @since 1.0.0
     */
    public static void sendActionBarWhile(@Nonnull final Plugin plugin, @Nonnull final Player player, @Nullable final String message, @Nonnull final Callable<Boolean> callable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (!callable.call()) {
                        cancel();
                        return;
                    }
                } catch (final Exception ex) {
                    ex.printStackTrace();
                }
                sendActionBar(player, message);
            }
            // Re-sends the messages every 2 seconds so it doesn't go away from the player's screen.
        }.runTaskTimerAsynchronously(plugin, 0L, 40L);
    }

    /**
     * Sends an action bar to a player for a specific amount of ticks.
     * <p>
     * If the caller returns true, the action bar will continue.
     * If the caller returns false, action bar will not be sent anymore.
     *
     * @param plugin   the plugin handling the message scheduler.
     * @param player   the player to send the action bar to.
     * @param message  the message to send. The message will be updated.
     * @param callable the condition for the action bar to continue.
     *
     * @see #sendActionBarWhile(Plugin, Player, String, Callable)
     * @since 1.0.0
     */
    public static void sendActionBarWhile(@Nonnull final Plugin plugin, @Nonnull final Player player, @Nullable final Callable<String> message, @Nonnull final Callable<Boolean> callable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (!callable.call()) {
                        cancel();
                        return;
                    }
                    sendActionBar(player, message.call());
                } catch (final Exception ex) {
                    ex.printStackTrace();
                }
            }
            // Re-sends the messages every 2 seconds so that it doesn't go away from the player's screen.
        }.runTaskTimerAsynchronously(plugin, 0L, 40L);
    }
}