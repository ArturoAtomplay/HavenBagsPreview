package me.github.arturoatomplay.havenbagspreview.tooltip;

import me.github.arturoatomplay.havenbagspreview.BackpackContent;
import me.github.arturoatomplay.havenbagspreview.BackpackData;
import me.github.arturoatomplay.havenbagspreview.BagDataParser;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static me.github.arturoatomplay.havenbagspreview.Constants.*;

public class TooltipManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(TooltipManager.class);

    public static Optional<TooltipComponent> getCustomTooltip(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) return Optional.empty();

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
        CompoundTag bukkit = tag.getCompound(PDC_BUKKIT_KEY);
        if (!bukkit.isEmpty()) {
            String content = bukkit.getString(PDC_MOD_KEY);
            if (content != null && !content.isEmpty()) {
                return new BagDataParser.ParseResult(content, bukkit.getInt(PDC_SIZE_KEY));
            }
        }

        if (tag.contains(NBT_CONTENT_KEY) && tag.contains(NBT_SIZE_KEY)) {
            return new BagDataParser.ParseResult(
                tag.getString(NBT_CONTENT_KEY),
                tag.getInt(NBT_SIZE_KEY)
            );
        }

        return BagDataParser.ParseResult.invalid();
    }
}
