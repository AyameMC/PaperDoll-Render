/*
 *     Highly configurable paper doll mod, well integrated with Ayame.
 *     Copyright (C) 2024  LucunJi(Original author), HappyRespawnanchor, pertaz(Port to Architectury)
 *
 *     This file is part of PaperDoll Render.
 *
 *     PaperDoll Render is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     PaperDoll Render is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with PaperDoll Render.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.paperdollrender.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import org.ayamemc.paperdollrender.CommonInterfaceInstances;
import org.ayamemc.paperdollrender.PaperDollRender;
import org.ayamemc.paperdollrender.config.model.ConfigOption;
import org.ayamemc.paperdollrender.config.view.ListWidget;
import org.ayamemc.paperdollrender.config.view.Tab;
import org.ayamemc.paperdollrender.hud.ExtraPlayerHud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ConfigScreen extends Screen {
    private static final int TAB_BUTTON_HEIGHT = 24;
    private static final int ENTRY_HEIGHT = 30;
    private static final int LIST_WIDTH_OFFSET = -50;

    private final Screen parent;

    private final ExtraPlayerHud previewHud;
    private final TabManager tabManager;
    private final List<ListWidget> listWidgets;
    private final List<? extends ConfigOption<?>> options;
    private Tab[] tabs;
    private TabNavigationBar tabNav;

    public ConfigScreen(Screen parent, List<? extends ConfigOption<?>> options) {
        super(Component.nullToEmpty("Config Screen"));
        this.parent = parent;
        this.previewHud = new ExtraPlayerHud(Minecraft.getInstance());
        this.tabManager = new TabManager(this::addRenderableWidget, this::removeWidget);
        this.options = options;
        this.listWidgets = new ArrayList<>();
    }

    @Override
    protected void init() {
        this.tabs = generateTabs();
        this.addRenderableWidget(this.tabNav = TabNavigationBar
                .builder(this.tabManager, this.width)
                .addTabs(tabs).build());

        int tabIdx = PaperDollRender.CONFIGS.lastConfigTabIdx.getValue();
        if (tabIdx < 0 || tabIdx >= tabs.length)
            PaperDollRender.CONFIGS.lastConfigTabIdx.setValue(tabIdx = 0); // will be saved when screen closes
        this.tabNav.selectTab(tabIdx, false);
        this.repositionElements();
    }

    private Tab[] generateTabs() {
        var tabs = new ArrayList<Tab>();
        var categoryLists = new HashMap<ResourceLocation, ListWidget>();
        for (var option : options) {
            if (option.getCategory().equals(Configs.HIDDEN_CATEGORY)) continue;
            var configEntryOptioal = ConfigWidgetRegistry.DEFAULT.getConfigEntry(option);
            if (configEntryOptioal.isEmpty()) {
                PaperDollRender.LOGGER.error("Could not find widget for option {}", option.getId());
                continue;
            }
            //noinspection DataFlowIssue
            var label = new StringWidget(option.getName(), this.minecraft.font);
            label.setTooltip(Tooltip.create(option.getDescription()));

            var category = option.getCategory();
            if (!categoryLists.containsKey(category)) {
                var tab = new Tab(Component.translatable("config.%s.category.%s".formatted(category.getNamespace(), category.getPath())));
                var list = new ListWidget(this.width, this.height - TAB_BUTTON_HEIGHT, TAB_BUTTON_HEIGHT, ENTRY_HEIGHT);
                this.listWidgets.add(list);
                tab.addChild(list);
                tabs.add(tab);
                categoryLists.put(category, list);
            }
            categoryLists.get(category).addEntry(configEntryOptioal.get());

            if (option.getId().equals(PaperDollRender.withPaperDollRenderNamespace("enabled"))) {
                categoryLists.get(category).addEntry(this.getPresetsConfigEntry());
            }
        }

        if (tabs.isEmpty()) tabs.add(new Tab(Component.nullToEmpty("")));
        return tabs.toArray(Tab[]::new);
    }

    @Override
    protected void repositionElements() {
        if (this.tabNav == null) return;

        this.tabNav.setWidth(this.width);
        this.tabNav.arrangeElements();
        for (var listWidget : this.listWidgets) {
            listWidget.setSize(this.width, this.height - TAB_BUTTON_HEIGHT);
            listWidget.setRowWidth(this.width + LIST_WIDTH_OFFSET);
        }
    }

    @Override
    public void onClose() {
        // -1 will become 0 after validation
        PaperDollRender.CONFIGS.lastConfigTabIdx.setValue(ArrayUtils.indexOf(tabs, tabManager.getCurrentTab()));
        //noinspection DataFlowIssue
        this.minecraft.setScreen(this.parent);
        PaperDollRender.CONFIG_PERSISTENCE.save(PaperDollRender.CONFIGS.getOptions());
    }

    @Override
    protected void renderBlurredBackground(float delta) {
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        // only render when the screen is opened in game
        //noinspection DataFlowIssue
        if (this.minecraft.level != null) {
            this.previewHud.render(this.minecraft.getTimer().getGameTimeDeltaPartialTick(true));
            // put behind GUI
            context.pose().translate(0, 0, 200);
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == CommonInterfaceInstances.keyHelper.getBoundKeyOf(PaperDollRender.CONFIG_KEY).getValue() && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private ListWidget.ListEntry getPresetsConfigEntry() {
        final int buttonWidth = 70, gap = 10, buttonHeight = 20, labelYOffset = 7;
        @SuppressWarnings("DataFlowIssue")
        var presetLabel = new StringWidget(Component.translatable("config.%s.option.presets".formatted(PaperDollRender.MOD_ID)), this.minecraft.font);
        presetLabel.setTooltip(Tooltip.create(Component.translatable("config.%s.option.presets.desc".formatted(PaperDollRender.MOD_ID))));
        var topLeft = new ConfigWidgetRegistry.ConfigButton(buttonWidth, buttonHeight, getPresetText("top_left"), getPresetPressAction(PaperDollRender.CONFIGS.topLeft));
        var topRight = new ConfigWidgetRegistry.ConfigButton(buttonWidth, buttonHeight, getPresetText("top_right"), getPresetPressAction(PaperDollRender.CONFIGS.topRight));
        var bottomLeft = new ConfigWidgetRegistry.ConfigButton(buttonWidth, buttonHeight, getPresetText("bottom_left"), getPresetPressAction(PaperDollRender.CONFIGS.bottomLeft));
        var bottomRight = new ConfigWidgetRegistry.ConfigButton(buttonWidth, buttonHeight, getPresetText("bottom_right"), getPresetPressAction(PaperDollRender.CONFIGS.bottomRight));
        var children = List.of(presetLabel, topLeft, topRight, bottomLeft, bottomRight);
        return new ListWidget.ListEntry() {
            @Override
            public List<? extends NarratableEntry> narratables() {
                return children;
            }

            @Override
            public List<? extends GuiEventListener> children() {
                return children;
            }

            @Override
            public void render(GuiGraphics context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                presetLabel.setPosition(x, y + labelYOffset);
                topLeft.setPosition(x + entryWidth - buttonWidth * 4 - gap * 3, y);
                topRight.setPosition(x + entryWidth - buttonWidth * 3 - gap * 2, y);
                bottomLeft.setPosition(x + entryWidth - buttonWidth * 2 - gap, y);
                bottomRight.setPosition(x + entryWidth - buttonWidth, y);

                for (AbstractWidget child : children) child.render(context, mouseX, mouseY, tickDelta);
            }
        };
    }

    private Component getPresetText(String id) {
        return Component.translatable("config.%s.presets.%s".formatted(PaperDollRender.MOD_ID, id));
    }

    private Button.OnPress getPresetPressAction(Configs.Presets presets) {
        return ignored -> {
            presets.load();
            // clear everything and re-init, AVOID MEMORY LEAK
            int tabIdx = ArrayUtils.indexOf(tabs, tabManager.getCurrentTab());
            this.clearWidgets();
            this.listWidgets.clear();
            this.init();
            this.tabNav.selectTab(tabIdx, false);
        };
    }
}
