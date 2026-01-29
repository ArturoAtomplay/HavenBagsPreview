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

        Optional<String> uuid = tag.getString("bag-uuid");
        Optional<String> content = tag.getString("bag-preview-content");
        Optional<Integer> bagSlots = tag.getInt("bag-size");

        if (uuid.isEmpty() || content.isEmpty() || bagSlots.isEmpty()) {
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
