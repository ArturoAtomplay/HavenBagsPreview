package me.github.arturoatomplay.havenbagspreview.mixin;

import me.github.arturoatomplay.havenbagspreview.tooltip.BackpackTooltip;
import me.github.arturoatomplay.havenbagspreview.tooltip.ClientBackpackTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientTooltipComponent.class)
public interface ClientTooltipComponentMixin {
    @Inject(
        method = "create(Lnet/minecraft/world/inventory/tooltip/TooltipComponent;)Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipComponent;",
        at = @At("HEAD"),
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    private static void onCreate(TooltipComponent component, CallbackInfoReturnable<ClientTooltipComponent> cir) {
        if (component instanceof BackpackTooltip tooltip) {
            cir.setReturnValue(new ClientBackpackTooltip(tooltip));
        }
    }
}
