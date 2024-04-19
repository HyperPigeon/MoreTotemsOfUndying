package net.hyper_pigeon.moretotems.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SummonedBeeEntity extends Bee {

    private Entity summoner;

    public SummonedBeeEntity(EntityType<? extends Bee> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createTotemBeeAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 15.0D).add(Attributes.FLYING_SPEED, 2.5D).add(Attributes.MOVEMENT_SPEED, 2.5D).add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    public void setSummoner(Entity player) {
        summoner = player;
    }



    protected void customServerAiStep() {

        if(summoner instanceof Player) {

           if(((Player) summoner).getLastHurtByMob() != null) {


               this.setBeeAttacker(((Player) summoner).getLastHurtByMob());

           }

            if(((Player) summoner).getLastHurtMob() != null) {

                this.setBeeAttacker(((Player) summoner).getLastHurtMob());

            }


        }

        super.customServerAiStep();

    }

    private boolean setBeeAttacker(LivingEntity attacker) {
        if(attacker.equals(summoner)) {
            return false;
        }
        setLastHurtByMob(attacker);
        return true;

    }





    public boolean doHurtTarget(Entity target) {

        if(target.equals(summoner)) {
            return false;
        }
        else if (this.hasStung()){

            return false;

        }
        else {

            return super.doHurtTarget(target);

        }

    }





}
