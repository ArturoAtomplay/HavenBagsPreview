package me.github.arturoatomplay.havenbagspreview.tooltip;

import com.google.gson.Gson;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import me.github.arturoatomplay.havenbagspreview.BackpackContent;

import java.util.Optional;

public class TooltipManager {
    private static final Gson gson = new Gson();

    public static Optional<TooltipComponent> getCustomTooltip(ItemStack stack) {
        CompoundTag nbt = stack.getTag();

        if (nbt == null || !nbt.contains("bag-uuid")) return Optional.empty();
        if (nbt.getString("bag-uuid").equals("null")) return Optional.empty();
        if (!nbt.contains("bag-preview-content")) return Optional.empty();
        if (!nbt.contains("bag-size")) return Optional.empty();

        int bagSlots = nbt.getInt("bag-size");
        NonNullList<ItemStack> bagInventory = NonNullList.withSize(bagSlots, ItemStack.EMPTY);

        BackpackContent backpackContent = gson.fromJson(nbt.getString("bag-preview-content"), BackpackContent.class);

        for (BackpackContent.ItemData item : backpackContent.items) {
            int slot = item.getSlot();

            if (slot < bagInventory.size()) {
                bagInventory.set(slot, item.getItemStack());
            }
        }

        return Optional.of(new BackpackTooltip(bagInventory));
    }
}
