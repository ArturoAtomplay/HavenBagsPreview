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

import java.util.List;

public class BackpackContent {
    public static ItemStack createItemStack(BackpackData.ItemData item) {
        ItemStack itemStack = BuiltInRegistries.ITEM.getOptional(new ResourceLocation(String.format("minecraft:%s", item.getItemName().toLowerCase())))
            .map(i -> new ItemStack(i, item.getCount()))
            .orElse(ItemStack.EMPTY);

        itemStack.setDamageValue(item.getDamage());

        if (item.isEnchanted()) {
            itemStack.enchant(BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation("minecraft:flame")), 1);
        }

        if (item.getModelData() != 0) {
            itemStack.applyComponentsAndValidate(DataComponentPatch.builder().set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(item.getModelData())).build());
        }

        return itemStack;
    }
}

