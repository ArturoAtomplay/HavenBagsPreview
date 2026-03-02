package me.github.arturoatomplay.havenbagspreview.tooltip;

import com.google.gson.Gson;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import me.github.arturoatomplay.havenbagspreview.BackpackContent;
import net.minecraft.world.item.component.CustomData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class TooltipManager {
    private static final Gson gson = new Gson();
    private static final Logger log = LoggerFactory.getLogger(TooltipManager.class);

    public static Optional<TooltipComponent> getCustomTooltip(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        boolean nbt = false;
        boolean pdc = false;

        // Check if NBT data exists
        if (tag.contains("bag-preview-content") && tag.contains("bag-size")) {
            nbt = true;
        }

        if (!tag.getCompound("PublicBukkitValues").get().getString("havenbags:mod").isEmpty()) {
            pdc = true;
        }

        if (nbt && pdc) nbt = false; // If both are present, prioritize PDC data
        if(!nbt && !pdc) return Optional.empty(); // If neither is present, return empty

        Optional<String> content = Optional.empty();
        Optional<Integer> bagSlots = Optional.empty();

        if(nbt) {
            content = tag.getString("bag-preview-content");
            Optional<Integer> bagSizeOpt = tag.getInt("size");

            if (content.isPresent() && bagSizeOpt.isPresent() && bagSizeOpt.get() > 0) {
                bagSlots = bagSizeOpt;
            }
        }

        if(pdc) {
            Optional<CompoundTag> bukkitValuesOpt = tag.getCompound("PublicBukkitValues");

            if (bukkitValuesOpt.isPresent()) {
                CompoundTag bukkitValues = bukkitValuesOpt.get();

                // Access havenbags:mod and havenbags:size directly from the compound tag
                content = bukkitValues.getString("havenbags:mod");
                bagSlots = bukkitValues.getInt("havenbags:size");
            }
        }

        if (content.isEmpty() || bagSlots.isEmpty()) {
            return Optional.empty();
        }

        NonNullList<ItemStack> bagInventory = NonNullList.withSize(bagSlots.get(), ItemStack.EMPTY);
        BackpackContent backpackContent = gson.fromJson(content.get(), BackpackContent.class);

        for (BackpackContent.ItemData item : backpackContent.items) {
            int slot = item.getSlot();

            if (slot < bagInventory.size()) {
                bagInventory.set(slot, item.getItemStack());
            }
        }

        return Optional.of(new BackpackTooltip(bagInventory));
    }
}
