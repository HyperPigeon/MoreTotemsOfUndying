package net.hyper_pigeon.moretotems.entity;

import net.hyper_pigeon.moretotems.goals.FollowZombieSummonerGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class SummonedZombieEntity extends Zombie {


    protected static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> SUMMONER_REF;


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
        syncedDataBuilder.define(SUMMONER_REF, Optional.empty());
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


    private void setSummonerRef(@Nullable EntityReference<LivingEntity> summoner) {
        this.entityData.set(SUMMONER_REF, Optional.ofNullable(summoner));
    }

    public Optional<EntityReference<LivingEntity>> getSummonerRef() {
        return this.entityData.get(SUMMONER_REF);
    }

    public void setSummoner(LivingEntity livingEntity) {
        this.entityData.set(SUMMONER_REF,Optional.ofNullable(livingEntity).map(EntityReference::of));
    }

    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        Optional<EntityReference<@NotNull LivingEntity>> entityreference = this.getSummonerRef();
        entityreference.ifPresent(ref -> {
            EntityReference.store(ref, tag, "Summoner");
        });

    }

    public void readAdditionalSaveData(@NotNull ValueInput tag) {
        super.readAdditionalSaveData(tag);
        EntityReference<@NotNull LivingEntity> entityreference = EntityReference.readWithOldOwnerConversion(tag, "Summoner", this.level());
        if (entityreference != null) {
            this.entityData.set(SUMMONER_REF, Optional.of(entityreference));
        } else {
            this.entityData.set(SUMMONER_REF, Optional.empty());
        }
    }


    @Override
    public void setLastHurtByMob(LivingEntity attacker) {
        if (attacker != getSummoner()) {
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
        }
        super.aiStep();
    }


    public LivingEntity getSummoner() {
        try {
            if(this.getSummonerRef().isPresent()) {
                Optional<UUID> uUID = Optional.of(this.getSummonerRef().get().getUUID());
                return (LivingEntity) uUID.map(value -> this.level().getEntity(value)).orElse(null);
            }
            else {
                return null;
            }
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    static {
        SUMMONER_REF = SynchedEntityData.defineId(SummonedZombieEntity.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    }


}