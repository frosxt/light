/*
 *     SparkCommons
 *     Copyright (C) 2023 frosxt
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License in LICENSE.MD
 *     Please refer to that file for details.
 */

package com.github.frosxt.sparkcommons.menus.buttons;

import com.github.frosxt.sparkcommons.menus.menu.Menu;

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