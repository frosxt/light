package me.frost.commons.menus.buttons;

import me.frost.commons.menus.menu.Menu;

public enum FillingType {

    BORDER {
        @Override
        public Button[] fillMenu(final Menu menu) {
            final Button[] buttons = new Button[menu.getSize()];

            for (int i = 0; i < menu.getSize(); i++) {
                if (i < 9 || i >= menu.getSize() - 9 || i % 9 == 0 || i % 9 == 8 && (menu.buttons[i] == null)) {
                    buttons[i] = new Button(menu.getFillerItem()).setClickAction(event -> event.setCancelled(true));
                }
            }

            return buttons;
        }
    },

    EMPTY_SLOTS {
        @Override
        public Button[] fillMenu(final Menu menu) {
            final Button[] buttons = new Button[menu.getSize()];

            for (int i = 0; i < menu.getSize(); i++) {
                if (menu.buttons[i] == null) {
                    buttons[i] = new Button(menu.getFillerItem()).setClickAction(event -> event.setCancelled(true));
                }
            }

            return buttons;
        }
    };

    public abstract Button[] fillMenu(Menu menu);
}