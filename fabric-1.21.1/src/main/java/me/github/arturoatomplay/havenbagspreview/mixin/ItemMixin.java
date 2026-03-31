package me.github.arturoatomplay.havenbagspreview.mixin;

import me.github.arturoatomplay.havenbagspreview.tooltip.TooltipManager;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(
        method = "getTooltipImage",
        at = @At("RETURN"),
        cancellable = true
    )
    private void injectCustomTooltip(ItemStack stack, CallbackInfoReturnable<Optional<TooltipComponent>> cir) {
        var result = TooltipManager.getCustomTooltip(stack);
        if (result.isPresent()) {
            cir.setReturnValue(result);
        }
    }
}
