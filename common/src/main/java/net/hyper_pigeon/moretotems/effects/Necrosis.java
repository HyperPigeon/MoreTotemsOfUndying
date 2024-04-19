package net.hyper_pigeon.moretotems.effects;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class Necrosis extends MobEffect {

    public Necrosis(MobEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    public void applyEffectTick(LivingEntity entity, int i) {

            if(entity.hasEffect(MobEffects.POISON)) {

                if (entity.getHealth() < entity.getMaxHealth()) {
                    entity.heal(0.3F);
                }

            }


    }

    public boolean isDurationEffectTick(int duration, int i) {
        return true;
    }


}
