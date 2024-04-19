package net.hyper_pigeon.moretotems.mixin;

import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.hyper_pigeon.moretotems.register.StatusEffectRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(AbstractArrow.class)
public abstract class PersistentProjectileEntityMixin extends Entity {

    @Shadow
    private double baseDamage;

    public PersistentProjectileEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }


    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)V", at = @At("RETURN"))
    private void onInit(EntityType $$0, LivingEntity owner, Level $$2, ItemStack $$3, CallbackInfo ci) {
        if (owner.hasEffect(StatusEffectRegistry.SNIPER.get())) {
            this.baseDamage = baseDamage * 2;
        }
    }
}




