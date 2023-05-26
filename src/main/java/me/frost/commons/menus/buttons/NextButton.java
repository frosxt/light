package me.frost.commons.menus.buttons;

import me.frost.commons.menus.menu.PaginatedMenu;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class NextButton extends Button {
    private final PaginatedMenu menu;

    public NextButton(PaginatedMenu menu, int index, ItemStack itemStack) {
        super(index, itemStack);
        this.menu = menu;
    }

    @Override
    public Function<ClickType, Boolean> getClickAction() {
        return type -> {
            if (menu.getMaxPages() == menu.getPage()) {
                return true;
            }
            menu.setPage(menu.getPage() + 1);
            menu.updateMenu();

            return true;
        };
    }
}