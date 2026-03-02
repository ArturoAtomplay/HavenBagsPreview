package me.github.arturoatomplay.havenbagspreview.tooltip;

import com.google.gson.Gson;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import me.github.arturoatomplay.havenbagspreview.BackpackContent;
import net.minecraft.world.item.component.CustomData;

import java.util.Optional;

public class TooltipManager {
    private static final Gson gson = new Gson();

    public static Optional<TooltipComponent> getCustomTooltip(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        boolean nbt = false;
        boolean pdc = false;

        if (tag.contains("bag-preview-content") && tag.contains("bag-size")) {
            nbt = true;
        }

        if (!tag.getCompound("PublicBukkitValues").getString("havenbags:mod").isEmpty()) {
            pdc = true;
        }

        if (nbt && pdc) nbt = false; // If both are present, prioritize PDC data
        if(!nbt && !pdc) return Optional.empty(); // If neither is present, return empty

        Optional<String> content = Optional.empty();
        Optional<Integer> bagSlots = Optional.empty();

        if(nbt) {
            String contentStr = tag.getString("bag-preview-content");
            int bagSizeInt = tag.getInt("bag-size");

            if (!contentStr.isEmpty() && bagSizeInt > 0) {
                content = Optional.of(contentStr);
                bagSlots = Optional.of(bagSizeInt);
            }
        }

        if(pdc) {
            CompoundTag bukkitValues = tag.getCompound("PublicBukkitValues");

            if (bukkitValues != null && !bukkitValues.isEmpty()) {
                // Access havenbags:mod and havenbags:size directly from the compound tag
                String modStr = bukkitValues.getString("havenbags:mod");
                int sizeInt = bukkitValues.getInt("havenbags:size");

                if (!modStr.isEmpty() && sizeInt > 0) {
                    content = Optional.of(modStr);
                    bagSlots = Optional.of(sizeInt);
                }
            }
        }

        if (content.isEmpty() || bagSlots.isEmpty()) {
            return Optional.empty();
        }

        NonNullList<ItemStack> bagInventory = NonNullList.withSize(bagSlots.get(), ItemStack.EMPTY);
        BackpackContent backpackContent = gson.fromJson(content.get(), BackpackContent.class);

        if (backpackContent == null || backpackContent.items == null) {
            return Optional.empty();
        }

        for (BackpackContent.ItemData item : backpackContent.items) {
            int slot = item.getSlot();

            if (slot < bagInventory.size()) {
                bagInventory.set(slot, item.getItemStack());
            }
        }

        return Optional.of(new BackpackTooltip(bagInventory));
    }
}
