package net.hyper_pigeon.moretotems.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class Sniper extends MobEffect {

    public Sniper(MobEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    public boolean isDurationEffectTick(int duration, int i) {
        return true;
    }



}
