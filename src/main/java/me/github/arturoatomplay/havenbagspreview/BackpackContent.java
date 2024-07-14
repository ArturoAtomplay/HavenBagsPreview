package me.github.arturoatomplay.havenbagspreview;

import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

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

            RegistryAccess registryAccess = Minecraft.getInstance().player != null ? Minecraft.getInstance().player.level().registryAccess() : null;
            HolderLookup.RegistryLookup<Enchantment> enchantments = registryAccess != null ? registryAccess.lookup(Registries.ENCHANTMENT).orElse(null) : null;

            if (e && enchantments != null) {
                itemStack.enchant(enchantments.getOrThrow(Enchantments.FLAME), 1);
            }

            if (m != 0) {
                itemStack.applyComponentsAndValidate(DataComponentPatch.builder().set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(m)).build());
            }

            return itemStack;
        }
    }
}

