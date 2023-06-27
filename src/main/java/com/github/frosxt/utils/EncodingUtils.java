package com.github.frosxt.utils;

import com.github.frosxt.builders.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class EncodingUtils {

    // Private constructor to prevent instantiation
    private EncodingUtils() {
        throw new UnsupportedOperationException("EncodingUtils is a utility class and cannot be instantiated!");
    }

    public static String toBase64(final ItemStack itemStack) {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream);
            objectOutputStream.writeObject(itemStack);
            objectOutputStream.flush();

            final byte[] serialisedObject = outputStream.toByteArray();
            return new String(Base64.getEncoder().encode(serialisedObject));
        } catch (final IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static ItemBuilder fromBase64(final String decode) {
        try {
            final byte[] serialisedObject = Base64.getDecoder().decode(decode);
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(serialisedObject);
            final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream);

            final ItemStack itemStack = (ItemStack) objectInputStream.readObject();
            return new ItemBuilder(itemStack);
        } catch (final IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}