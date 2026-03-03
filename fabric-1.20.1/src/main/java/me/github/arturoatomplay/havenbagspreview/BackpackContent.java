package me.github.arturoatomplay.havenbagspreview;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class BackpackContent {
    public static ItemStack createItemStack(BackpackData.ItemData item) {
        ItemStack stack = BuiltInRegistries.ITEM
            .getOptional(new ResourceLocation("minecraft", item.getItemName().toLowerCase()))
            .map(i -> new ItemStack(i, item.getCount()))
            .orElse(ItemStack.EMPTY);

        stack.setDamageValue(item.getDamage());

        if (item.isEnchanted()) {
            stack.enchant(Enchantments.FLAMING_ARROWS, 1);
        }

        if (item.getModelData() != 0) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putInt("CustomModelData", item.getModelData());
        }

        return stack;
    }
}
