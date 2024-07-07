package me.github.arturoatomplay.havenbagspreview;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;

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
            ItemStack itemStack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.bySeparator(String.format("minecraft:%s", i.toLowerCase()), ':')), c);

            itemStack.setDamageValue(d);

            if (e) {
                // itemStack.enchant(BuiltInRegistries.ENCHANTMENT.get(ResourceLocation.of("minecraft:flame", ':')), 1); //This needs some fixing but otherwise it works
            }

            if (m != 0) {
                itemStack.applyComponentsAndValidate(DataComponentPatch.builder().set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(m)).build());
            }

            return itemStack;
        }
    }
}

