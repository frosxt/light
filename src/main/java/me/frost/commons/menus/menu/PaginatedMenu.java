package me.frost.commons.menus.menu;

import me.frost.commons.builders.ItemBuilder;
import me.frost.commons.menus.buttons.Button;
import me.frost.commons.menus.buttons.NextButton;
import me.frost.commons.menus.buttons.PreviousButton;
import me.frost.commons.utils.support.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class PaginatedMenu extends Menu {
    private int page = 1;
    private int maxPages;
    private final List<Button> paginationButtons = new ArrayList<>();

    public PaginatedMenu(Player player, String title, int size, int maxPages) {
        this(player, title, size);

        this.maxPages = maxPages;
    }

    public PaginatedMenu(Player player, String title, int size) {
        super(player, title, size + 9);

        final ItemBuilder previousButton = new ItemBuilder(XMaterial.matchXMaterial("BED").get().parseMaterial(), 1)
                .setName("&c&l<- PREVIOUS PAGE")
                .setLore(" ", "&7(( Click to go back to the &fprevious page&7! ))");

        paginationButtons.add(new PreviousButton(PaginatedMenu.this, 0, previousButton.build()));

        final ItemBuilder nextButton = new ItemBuilder(XMaterial.matchXMaterial("BED").get().parseMaterial(), 1)
                .setName("&a&lNEXT PAGE ->")
                .setLore(" ", "&7(( Click to go to the &fnext page&7! ))");

        paginationButtons.add(new NextButton(PaginatedMenu.this, 8, nextButton.build()));
    }

    public void setPaginationButtons(ItemStack previousButton, int pIndex, ItemStack nextButton, int nIndex) {
        paginationButtons.clear();

        paginationButtons.add(new PreviousButton(PaginatedMenu.this, pIndex, previousButton));
        paginationButtons.add(new NextButton(PaginatedMenu.this, nIndex, nextButton));
    }

    @Override
    public void updateMenu() {
        this.updateMenu(getButtonsInRange());
    }

    public List<Button> getButtonsInRange() {
        final List<Button> buttons = getButtons().stream()
                .filter(button -> button.getIndex() >= ((page - 1) * getSize()) && button.getIndex() < (page * getSize()) - 9)
                .peek(button -> button.setIndex(button.getIndex() - ((getSize()) * (page - 1) - 9))).collect(Collectors.toList());
        buttons.addAll(paginationButtons);

        return buttons;
    }

    @Override
    public boolean click(ClickType clickType, int index) {
        final Optional<Button> button = getButtonsInRange().stream()
                .filter(currentButton -> currentButton.getIndex() == index)
                .findFirst();

        if (button.isPresent()) {
            return button.get().getClickAction().apply(clickType);
        }

        return false;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return this.page;
    }

    public int getMaxPages() {
        return this.maxPages;
    }
}
