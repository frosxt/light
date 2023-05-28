package me.frost.commons.menus.buttons;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class ButtonBuilder extends Button {
    public ButtonBuilder(final int index, final ItemStack itemStack) {
        super(index, itemStack);
    }

    public ButtonBuilder action(final Function<ClickType, Boolean> clickAction) {
        super.setClickAction(clickAction);
        return this;
    }
}