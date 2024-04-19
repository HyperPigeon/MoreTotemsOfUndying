package net.hyper_pigeon.moretotems.mixin;

import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.hyper_pigeon.moretotems.register.ItemRegistry;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {

    private static boolean isMoreTotem(ItemStack itemStack){
        return itemStack.is(ItemRegistry.EXPLOSIVE_TOTEM_OF_UNDYING.get()) ||
                itemStack.is(ItemRegistry.GHASTLY_TOTEM_OF_UNDYING.get()) ||
                itemStack.is(ItemRegistry.ROTTING_TOTEM_OF_UNDYING.get()) ||
                itemStack.is(ItemRegistry.SKELETAL_TOTEM_OF_UNDYING.get()) ||
                itemStack.is(ItemRegistry.STINGING_TOTEM_OF_UNDYING.get()) ||
                itemStack.is(ItemRegistry.TELEPORTING_TOTEM_OF_UNDYING.get()) ||
                itemStack.is(ItemRegistry.TENTACLED_TOTEM_OF_UNDYING.get());
    }


    @Inject(method = "findTotem", at = @At(value = "RETURN"), cancellable = true)
    private static void getActiveMoreTotemOfUndying(Player player, CallbackInfoReturnable<ItemStack> cir){
        for(InteractionHand hand : InteractionHand.values()) {
            ItemStack itemStack = player.getItemInHand(hand);
            if(isMoreTotem(itemStack)) {
                cir.setReturnValue(itemStack);
            }
        }
    }
}
