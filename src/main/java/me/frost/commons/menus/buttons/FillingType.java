package me.frost.commons.menus.buttons;

import me.frost.commons.menus.menu.Menu;

public enum FillingType {

    // This method doesn't seem to work properly (at least with paginated menus)
    // TODO: Fix this method
    BORDER {
        @Override
        public Button[] fillMenu(final Menu menu) {
            final Button[] buttons = new Button[menu.getButtons().length];

            for (int i = 0; i < menu.getButtons().length; i++) {
                if (i < 9 || i >= menu.getButtons().length - 9 || i % 9 == 0 || i % 9 == 8) {
                    if (menu.buttons[i] == null) {
                        buttons[i] = new Button(menu.getFillerItem()).setClickAction(event -> event.setCancelled(true));
                    }
                }
            }

            return buttons;
        }
    },

    EMPTY_SLOTS {
        @Override
        public Button[] fillMenu(final Menu menu) {
            final Button[] buttons = new Button[menu.getButtons().length];

            for (int i = 0; i < menu.getButtons().length; i++) {
                if (menu.buttons[i] == null) {
                    buttons[i] = new Button(menu.getFillerItem()).setClickAction(event -> event.setCancelled(true));
                }
            }

            return buttons;
        }
    };

    public abstract Button[] fillMenu(Menu menu);
}