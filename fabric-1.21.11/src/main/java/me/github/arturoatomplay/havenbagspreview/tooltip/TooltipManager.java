package me.github.arturoatomplay.havenbagspreview.tooltip;

import me.github.arturoatomplay.havenbagspreview.BackpackContent;
import me.github.arturoatomplay.havenbagspreview.BackpackData;
import me.github.arturoatomplay.havenbagspreview.BagDataParser;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static me.github.arturoatomplay.havenbagspreview.Constants.*;

public class TooltipManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(TooltipManager.class);

    public static Optional<TooltipComponent> getCustomTooltip(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        BagDataParser.ParseResult result = extractBagData(tag);
        if (!result.valid) return Optional.empty();

        return createTooltip(result);
    }

    private static Optional<TooltipComponent> createTooltip(BagDataParser.ParseResult result) {
        try {
            BackpackData data = BagDataParser.parseJson(result.content);
            if (data == null) return Optional.empty();

            NonNullList<ItemStack> inventory = NonNullList.withSize(result.size, ItemStack.EMPTY);
            for (BackpackData.ItemData item : data.items) {
                int slot = item.getSlot();
                if (slot >= 0 && slot < inventory.size()) {
                    inventory.set(slot, BackpackContent.createItemStack(item));
                }
            }
            return Optional.of(new BackpackTooltip(inventory));
        } catch (Exception e) {
            LOGGER.error("Failed to parse backpack data", e);
            return Optional.empty();
        }
    }

    private static BagDataParser.ParseResult extractBagData(CompoundTag tag) {
        CompoundTag bukkit = tag.getCompound(PDC_BUKKIT_KEY).orElse(null);
        if (bukkit != null) {
            String content = bukkit.getString(PDC_MOD_KEY).orElse(null);
            if (content != null && !content.isEmpty()) {
                int size = bukkit.getInt(PDC_SIZE_KEY).orElse(0);
                return new BagDataParser.ParseResult(content, size);
            }
        }

        if (tag.contains(NBT_CONTENT_KEY) && tag.contains(NBT_SIZE_KEY)) {
            String content = tag.getString(NBT_CONTENT_KEY).orElse(null);
            int size = tag.getInt(NBT_SIZE_KEY).orElse(0);
            return new BagDataParser.ParseResult(content, size);
        }

        return BagDataParser.ParseResult.invalid();
    }
}
