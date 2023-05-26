package me.frost.commons.menus.buttons;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class ButtonBuilder extends Button {
    public ButtonBuilder(int index, ItemStack itemStack) {
        super(index, itemStack);
    }

    public ButtonBuilder action(Function<ClickType, Boolean> clickAction) {
        super.setClickAction(clickAction);
        return this;
    }
}