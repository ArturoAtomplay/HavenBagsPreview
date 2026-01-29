package me.github.arturoatomplay.havenbagspreview.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import me.github.arturoatomplay.havenbagspreview.HavenBagsPreview;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ClientBackpackTooltip implements ClientTooltipComponent {
    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(HavenBagsPreview.MOD_ID, "textures/gui/bag.png");
    private static final int SLOT_SIZE_X = 18;
    private static final int SLOT_SIZE_Y = 18;
    
    private final NonNullList<ItemStack> items;
    private final int unlockedSize;

    public ClientBackpackTooltip(BackpackTooltip backpackTooltip) {
        this.items = backpackTooltip.items();
        this.unlockedSize = backpackTooltip.items().size();
    }

    @Override
    public int getHeight(Font font) {
        return gridSizeY() * SLOT_SIZE_Y + 2 + 4;
    }

    @Override
    public int getWidth(Font font) {
        return gridSizeX() * SLOT_SIZE_X + 2;
    }

    @Override
    public void renderImage(Font font, int mouseX, int mouseY, int z, int w, GuiGraphics g) {
        int i = this.gridSizeX();
        int j = this.gridSizeY();
        int k = 0;

        for (int l = 0; l < j; ++l) {
            for (int m = 0; m < i; ++m) {
                int n = mouseX + m * SLOT_SIZE_X + 1;
                int o = mouseY + l * SLOT_SIZE_Y + 1;
                this.renderSlot(n, o, k++, font, g);
            }
        }

        this.drawBorder(mouseX, mouseY, i, j, g);
    }

    private void renderSlot(int x, int y, int itemIndex, Font font, GuiGraphics g) {
        if (itemIndex >= this.unlockedSize) {
            this.blit(g, x, y, Texture.BLOCKED_SLOT);
        } else {
            ItemStack itemStack = this.items.get(itemIndex);
            this.blit(g, x, y, Texture.SLOT);
            g.renderItem(itemStack, x + 1, y + 1);
            g.renderItemDecorations(font, itemStack, x + 1, y + 1);
        }
    }

    private void drawBorder(int x, int y, int slotWidth, int slotHeight, GuiGraphics g) {
        this.blit(g, x, y, Texture.BORDER_CORNER_TOP);
        this.blit(g, x + slotWidth * SLOT_SIZE_X + 1, y, Texture.BORDER_CORNER_TOP);

        for (int i = 0; i < slotWidth; ++i) {
            this.blit(g, x + 1 + i * SLOT_SIZE_X, y, Texture.BORDER_HORIZONTAL_TOP);
            this.blit(g, x + 1 + i * SLOT_SIZE_X, y + slotHeight * SLOT_SIZE_Y + 1, Texture.BORDER_HORIZONTAL_BOTTOM);
        }

        for (int i = 0; i < slotHeight; ++i) {
            this.blit(g, x, y + i * SLOT_SIZE_Y + 1, Texture.BORDER_VERTICAL);
            this.blit(g, x + slotWidth * SLOT_SIZE_X + 1, y + i * SLOT_SIZE_Y + 1, Texture.BORDER_VERTICAL);
        }

        this.blit(g, x, y + slotHeight * SLOT_SIZE_Y + 1, Texture.BORDER_CORNER_BOTTOM);
        this.blit(g, x + slotWidth * SLOT_SIZE_X + 1, y + slotHeight * SLOT_SIZE_Y + 1, Texture.BORDER_CORNER_BOTTOM);
    }

    private void blit(GuiGraphics guiGraphics, int x, int y, Texture texture) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE_LOCATION, x, y, (float)texture.x, (float)texture.y, texture.w, texture.h, 128, 128);
    }

    private int gridSizeX() {
        return 9;
    }

    private int gridSizeY() {
        return (int) Math.ceil((double) unlockedSize / 9);
    }

    @Environment(EnvType.CLIENT)
    enum Texture {
        SLOT(0, 0, SLOT_SIZE_X, SLOT_SIZE_Y),
        BLOCKED_SLOT(0, 40, SLOT_SIZE_X, SLOT_SIZE_Y),
        BORDER_VERTICAL(0, SLOT_SIZE_X, 1, SLOT_SIZE_Y),
        BORDER_HORIZONTAL_TOP(0, SLOT_SIZE_Y, SLOT_SIZE_X, 1),
        BORDER_HORIZONTAL_BOTTOM(0, 60, SLOT_SIZE_X, 1),
        BORDER_CORNER_TOP(0, SLOT_SIZE_Y, 1, 1),
        BORDER_CORNER_BOTTOM(0, 60, 1, 1);

        public final int x;
        public final int y;
        public final int w;
        public final int h;

        Texture(int j, int k, int l, int m) {
            this.x = j;
            this.y = k;
            this.w = l;
            this.h = m;
        }
    }
}
