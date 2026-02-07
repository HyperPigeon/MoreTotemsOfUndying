package net.hyper_pigeon.moretotems.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class Necrosis extends MobEffect {

    public Necrosis(MobEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if(entity.hasEffect(MobEffects.POISON)) {
            entity.heal(0.3F);
        }
        return true;
    }

    public boolean shouldApplyEffectTickThisTick(int $$0, int $$1) {
        return true;
    }


}