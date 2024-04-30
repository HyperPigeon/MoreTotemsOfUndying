package net.hyper_pigeon.moretotems.entity;

import net.hyper_pigeon.moretotems.goals.FollowZombieSummonerGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import java.util.Optional;
import java.util.UUID;

public class SummonedZombieEntity extends Zombie {


    protected static final EntityDataAccessor<Optional<UUID>> SUMMONER_UUID;


    public SummonedZombieEntity(EntityType<? extends Zombie> type, Level world) {
        super(type, world);
    }



    @Override
    protected void randomizeReinforcementsChance() {
        super.randomizeReinforcementsChance();
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.5D);
        this.getAttribute(Attributes.ARMOR).setBaseValue(2.8D);
    }

    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder syncedDataBuilder) {
        super.defineSynchedData(syncedDataBuilder);
        syncedDataBuilder.define(SUMMONER_UUID, Optional.empty());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(6, new FollowZombieSummonerGoal(this, this.getSummoner(), this.level(), 1.0, this.getNavigation(), 90.0F, 3.0F, true));
        this.addBehaviourGoals();
    }

    @Override
    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(1, new ZombieAttackGoal(this, 2.0D, true));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers());
    }


    private void setSummonerUuid(UUID uuid) {
        this.entityData.set(SUMMONER_UUID, Optional.ofNullable(uuid));
    }

    public Optional<UUID> getSummonerUuid() {
        return this.entityData.get(SUMMONER_UUID);
    }

    public void setSummoner(Entity player) {
        this.setSummonerUuid(player.getUUID());
    }



    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putUUID("SummonerUUID", getSummonerUuid().get());
    }


    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        UUID id;
        if (tag.contains("SummonerUUID")) {
           id = tag.getUUID("SummonerUUID");
        } else {
            id = tag.getUUID("SummonerUUID");
        }
        if (id != null) {
            this.setSummonerUuid(tag.getUUID("SummonerUUID"));
        }
    }



    @Override
    public void setLastHurtByMob(LivingEntity attacker) {
        if(attacker == getSummoner()) {
        }
        else {
            super.setLastHurtByMob(attacker);
        }
    }


    @Override
    public void aiStep() {
        if (this.isAlive()) {
            if (getSummoner() != null) {
                if (getSummoner().getLastHurtByMob() != null) {
                    this.setTarget(getSummoner().getLastHurtByMob());
                } else if (getSummoner().getLastHurtMob() != null) {
                    this.setTarget(getSummoner().getLastHurtMob());
                }
            }
            else {


            }
        }
        super.aiStep();
    }


    public LivingEntity getSummoner() {
        try {
            Optional<UUID> uUID = this.getSummonerUuid();
            return uUID.map(value -> this.level().getPlayerByUUID(value)).orElse(null);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    static {
        SUMMONER_UUID = SynchedEntityData.defineId(SummonedZombieEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    }




}
