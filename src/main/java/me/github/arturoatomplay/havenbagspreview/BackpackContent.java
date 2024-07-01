package me.github.arturoatomplay.havenbagspreview;

import net.minecraft.core.Registry;
import net.minecraft.nbt.IntTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BackpackContent {
    public List<ItemData> items;

    // Inner class to represent item data
    public static class ItemData {
        /** slot */
        private int s;
        /** itemName */
        private String i;
        /** count */
        private int c;
        /** damage */
        private int d;
        /** enchanted */
        private boolean e;
        /** modelData */
        private int m;

        public int getSlot() {
            return s;
        }

        public ItemStack getItemStack() {
            ItemStack itemStack = new ItemStack(Registry.ITEM.get(ResourceLocation.of(String.format("minecraft:%s", i.toLowerCase()), ':')), c);

            itemStack.setDamageValue(d);

            if (e) {
                itemStack.enchant(Registry.ENCHANTMENT.get(ResourceLocation.of("minecraft:flame", ':')), 1);
            }

            if (m != 0) {
                itemStack.addTagElement("CustomModelData", IntTag.valueOf(m));
            }

            return itemStack;
        }
    }
}

