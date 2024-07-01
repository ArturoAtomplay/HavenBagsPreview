package me.github.arturoatomplay.havenbagspreview.tooltip;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public record BackpackTooltip(NonNullList<ItemStack> items) implements TooltipComponent {
}
