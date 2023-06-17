package me.frost.commons.menus.menu;

import me.frost.commons.colour.ColouredString;
import me.frost.commons.menus.buttons.Button;
import me.frost.commons.utils.support.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Map;

public abstract class PaginatedMenu extends Menu {
    private int page = 1;
    private final int maxPages;

    private Button previousPageButton = new Button(XMaterial.matchXMaterial("BED").get().parseMaterial())
            .setDisplayName(new ColouredString("&c&l<- PREVIOUS PAGE").toString())
            .setLore(Arrays.asList(" ", new ColouredString("&7(( Click to go back to the &fprevious page&7! ))").toString()).toArray(new String[0]));
    private int previousButtonSlot = getSize() - 6;

    private Button nextPageButton = new Button(XMaterial.matchXMaterial("BED").get().parseMaterial())
            .setDisplayName(new ColouredString("&a&lNEXT PAGE ->").toString())
            .setLore(Arrays.asList(" ", new ColouredString("&7(( Click to go to the &fnext page&7! ))").toString()).toArray(new String[0]));
    private int nextButtonSlot = getSize() - 4;

    private final Button[] stickyButtons = new Button[getSize()];

    public PaginatedMenu(final Player player, final String title, final int size, final int maxPages) {
        super(player, title, size);

        this.maxPages = maxPages;
        this.buttons = new Button[size * maxPages];
    }

    public PaginatedMenu(final Player player, final String title, final int size) {
        this(player, title, size, 16);
    }

    public void navigatePrevious() {
        setPage(Math.max(1, page - 1));
        updateMenu();
    }

    public void navigateNext() {
        page += 1;
        updateMenu();
    }

    @Override
    public void updateMenu() {
        this.updateMenu(getButtonsInRange(getButtons()));
    }

    public Button[] getButtonsInRange(final Button[] buttons) {
        final Button[] returningButtons = new Button[getSize()];

        final int size = getSize();
        final int localPage = getPage();

        final int start = (localPage - 1) * size;
        final int end = (start + size) - 1;

        for (int i = 0; i < buttons.length; i++) {
            final Button button = buttons[i];

            if (button != null && i >= start && i <= end) {
                returningButtons[i - ((size) * (localPage - 1))] = button;
            }
        }

        final Button[] navigationButtons = getNavigationButtons();

        for (int i = 0; i < navigationButtons.length; i++) {
            final Button button = navigationButtons[i];

            if (button != null) {
                returningButtons[i] = button;
            }
        }

        for (int i = 0; i < stickyButtons.length; i++) {
            final Button button = stickyButtons[i];

            if (button != null) {
                returningButtons[i] = button;
            }
        }

        return returningButtons;
    }

    public Button[] getNavigationButtons() {
        final Button[] buttons = new Button[getSize()];
        buttons[previousButtonSlot] = getPreviousPageButton().setClickAction(event -> {
            navigatePrevious();
            event.setCancelled(true);
        });

        buttons[nextButtonSlot] = getNextPageButton().setClickAction(event -> {
            if (getMaxPages() > page) {
                navigateNext();
            }
            event.setCancelled(true);
        });

        return buttons;
    }

    public void setStickyButtons(final Map<Integer, Button> buttonMap) {
        buttonMap.forEach((slot, button) -> stickyButtons[slot] = button);
    }

    public Button[] getStickyButtons() {
        return stickyButtons;
    }

    @Override
    public void click(final InventoryClickEvent event) {
        try {
            final Button[] buttons = this.getButtonsInRange(getButtons());
            final Button button = buttons[event.getSlot()];

            if (button == null) {
                event.setCancelled(true);
                return;
            }

            if (button.getClickAction() != null) {
                button.getClickAction().accept(event);
            }
        } catch (final IndexOutOfBoundsException ignored) {
            // Ignored exception
        }
    }

    public void setButton(final int slot, final int page, final Button button) {
        final int calculateSlot = (page * getSize()) - getSize() + slot;
        buttons[calculateSlot] = button;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public void setPreviousPageButton(final Button button) {
        previousPageButton = button;
    }

    public void setNextPageButton(final Button button) {
        nextPageButton = button;
    }

    public void setPreviousButtonSlot(final int slot) {
        previousButtonSlot = slot;
    }

    public void setNextButtonSlot(final int slot) {
        nextButtonSlot = slot;
    }

    public Button getPreviousPageButton() {
        return previousPageButton;
    }

    public Button getNextPageButton() {
        return nextPageButton;
    }

    public int getPage() {
        return this.page;
    }

    public int getMaxPages() {
        return this.maxPages;
    }
}
