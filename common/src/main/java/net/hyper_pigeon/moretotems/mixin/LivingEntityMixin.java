package net.hyper_pigeon.moretotems.mixin;

import net.hyper_pigeon.moretotems.entity.SummonedBeeEntity;
import net.hyper_pigeon.moretotems.entity.SummonedZombieEntity;
import net.hyper_pigeon.moretotems.register.EntityRegistry;
import net.hyper_pigeon.moretotems.register.ItemRegistry;
import net.hyper_pigeon.moretotems.register.StatusEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Optional;

/*The goal of this Mixin class is to give the totems the same ability to save the player from death, along with
some unique custom features*/

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin  extends Entity{


    @Shadow
    public  native ItemStack getItemInHand(InteractionHand hand_1);

    @Shadow
    public native boolean hasEffect(MobEffect effect);

    @Shadow public native void setHealth(float health);

    @Shadow public native boolean removeAllEffects();

    @Shadow public native boolean addEffect(MobEffectInstance statusEffectInstance_1);

    @Shadow public native MobType getMobType();

    @Shadow public abstract boolean addEffect(MobEffectInstance $$0, @Nullable Entity $$1);

    @Shadow public abstract Brain<?> getBrain();

    public EntityType<SummonedBeeEntity> s_bee = EntityRegistry.SUMMONED_BEE.get();

    public MinecraftServer the_server = getServer();

    protected LivingEntityMixin(EntityType<?> entityType_1, Level world_1) {
        super(entityType_1, world_1);
    }



    @Inject(at = @At("HEAD"), method = "checkTotemDeathProtection", cancellable = true)
    public void useExplosiveTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;



        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.OFF_HAND);

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the explosive totem of Undying
        if ((offhand_stack.getItem() == ItemRegistry.EXPLOSIVE_TOTEM_OF_UNDYING.get()) || (mainhand_stack.getItem() == ItemRegistry.EXPLOSIVE_TOTEM_OF_UNDYING.get()) ) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.type().equals(DamageTypes.FELL_OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                /*deletes explosive totem from offhand*/

                if((offhand_stack.getItem() == ItemRegistry.EXPLOSIVE_TOTEM_OF_UNDYING.get())) {
                    offhand_stack.shrink(1);
                }
                else if((mainhand_stack.getItem() == ItemRegistry.EXPLOSIVE_TOTEM_OF_UNDYING.get())){

                    mainhand_stack.shrink(1);

                }



                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.removeAllEffects();
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 125, 2));
                this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 350, 4));
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 2));
                this.level().broadcastEntityEvent(this, (byte)35);

                /*Spawns a tntEntity on the player upon use of Explosive Totem*/

                PrimedTnt tntEntity = EntityType.TNT.create(level());
                tntEntity.setFuse(5);
                tntEntity.moveTo(this.getX() , this.getY() , this.getZ(), 0, 0);
                level().addFreshEntity(tntEntity);

                callback.setReturnValue(true);




            }

        }


    }


    @Inject(at = @At("HEAD"), method = "checkTotemDeathProtection", cancellable = true)
    public void useStingingTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;


        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.OFF_HAND);
        ItemStack offhand_stack_copy;

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the explosive totem of Undying
        if ((offhand_stack.getItem() == ItemRegistry.STINGING_TOTEM_OF_UNDYING.get())
                || (mainhand_stack.getItem() == ItemRegistry.STINGING_TOTEM_OF_UNDYING.get())) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.type().equals(DamageTypes.FELL_OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*deletes explosive totem from offhand*/
                if((offhand_stack.getItem() == ItemRegistry.STINGING_TOTEM_OF_UNDYING.get())) {
                    offhand_stack.shrink(1);
                }
                else if((mainhand_stack.getItem() == ItemRegistry.STINGING_TOTEM_OF_UNDYING.get())){
                    mainhand_stack.shrink(1);
                }

                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.removeAllEffects();
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 650, 1));
                this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1500, 1));
                //this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 500, 2));
                this.level().broadcastEntityEvent(this, (byte)35);

                /*Spawns a SummonedBeeEntity on the player upon use of Stinging Totem*/



                SummonedBeeEntity summonedBeeEntity_1 = s_bee.create(level());
                summonedBeeEntity_1.setSummoner(this);
                summonedBeeEntity_1.moveTo(this.getX(), this.getY() + 1, this.getZ(), 0, 0);
                level().addFreshEntity(summonedBeeEntity_1);


                SummonedBeeEntity summonedBeeEntity_2 = s_bee.create(level());
                summonedBeeEntity_2.setSummoner(this);
                summonedBeeEntity_2.moveTo(this.getX(), this.getY() + 1, this.getZ(), 0, 0);
                level().addFreshEntity(summonedBeeEntity_2);

                SummonedBeeEntity summonedBeeEntity_3 = s_bee.create(level());
                summonedBeeEntity_3.setSummoner(this);
                summonedBeeEntity_3.moveTo(this.getX() + 1, this.getY() + 1, this.getZ(), 0, 0);
                level().addFreshEntity(summonedBeeEntity_3);

                SummonedBeeEntity summonedBeeEntity_4 = s_bee.create(level());
                summonedBeeEntity_4.setSummoner(this);
                summonedBeeEntity_4.moveTo(this.getX(), this.getY() + 1, this.getZ() + 1, 0, 0);
                level().addFreshEntity(summonedBeeEntity_4);

                SummonedBeeEntity summonedBeeEntity_5 = s_bee.create(level());
                summonedBeeEntity_5.setSummoner(this);
                summonedBeeEntity_5.moveTo(this.getX() - 1, this.getY() + 1, this.getZ(), 0, 0);
                level().addFreshEntity(summonedBeeEntity_5);


                SummonedBeeEntity summonedBeeEntity_6 = s_bee.create(level());
                summonedBeeEntity_5.setSummoner(this);
                summonedBeeEntity_5.moveTo(this.getX() , this.getY() + 1, this.getZ()-1, 0, 0);
                level().addFreshEntity(summonedBeeEntity_6);

                callback.setReturnValue(true);

            }
        }


    }

    @Inject(at = @At("HEAD"), method = "checkTotemDeathProtection", cancellable = true)
    public void useTeleportingTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {

        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        LivingEntity entity = (LivingEntity)(Object)this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = entity.getItemInHand(InteractionHand.OFF_HAND);
        ItemStack mainhand_stack = entity.getItemInHand(InteractionHand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the explosive totem of Undying
        if ((offhand_stack.getItem() == ItemRegistry.TELEPORTING_TOTEM_OF_UNDYING.get())
                || (mainhand_stack.getItem() == ItemRegistry.TELEPORTING_TOTEM_OF_UNDYING.get())) {

            /*sets copy to offhand_stack*/

            if((offhand_stack.getItem() == ItemRegistry.TELEPORTING_TOTEM_OF_UNDYING.get())) {
                offhand_stack.shrink(1);
            }
            else {

                mainhand_stack.shrink(1);

            }
            /*totem saves player from an untimely death*/
            this.setHealth(1.0F);
            this.clearFire();
            this.fallDistance = 0f;
            this.removeAllEffects();
            this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 17500, 5));

            if(entity instanceof ServerPlayer && !level().isClientSide()){

                ServerPlayer player = (ServerPlayer) entity;
                ServerLevel dest = Objects.requireNonNullElse(player.getServer().getLevel(player.getRespawnDimension()), player.getServer().overworld());
                Vec3 spawn_pointer = Optional.ofNullable(player.getRespawnPosition())
                        // Get player respawn position
                        .flatMap(pos -> Player.findRespawnPositionAndUseSpawnBlock(dest, pos, player.getRespawnAngle(), player.isRespawnForced(), true))
                        .orElseGet(() -> {
                            // Get world spawn if not possible
                            BlockPos worldSpawn = dest.getSharedSpawnPos();
                            return new Vec3(worldSpawn.getX() + 0.5, worldSpawn.getY() + 0.1, worldSpawn.getZ() + 0.5);
                        });

                TickTask teleport_shift = new TickTask((getServer().getTickCount()) + 1, () -> {
                    // Load chunk for spawning
                    dest.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, new ChunkPos(SectionPos.posToSectionCoord(spawn_pointer.x), SectionPos.posToSectionCoord(spawn_pointer.z)), 1, player.getId());
                    player.teleportTo(dest, spawn_pointer.x(), spawn_pointer.y(), spawn_pointer.z(), 5.0F, 5.0F);
                });
                the_server.tell(teleport_shift);

                this.level().addParticle(ParticleTypes.PORTAL,
                        this.getRandomX(0.5D),
                        this.getRandomY() - 0.25D,
                        this.getRandomZ(0.5D),
                        (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
                        (this.random.nextDouble() - 0.5D) * 2.0D);

                this.level().broadcastEntityEvent(this, (byte) 35);
                callback.setReturnValue(true);
            }

        }
    }



    @Inject(at = @At("HEAD"), method = "checkTotemDeathProtection", cancellable = true)
    public void useGhastlyTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.OFF_HAND);

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if ((offhand_stack.getItem() == ItemRegistry.GHASTLY_TOTEM_OF_UNDYING.get()) || (mainhand_stack.getItem() == ItemRegistry.GHASTLY_TOTEM_OF_UNDYING.get())) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.type().equals(DamageTypes.FELL_OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/

                if((offhand_stack.getItem() == ItemRegistry.GHASTLY_TOTEM_OF_UNDYING.get())) {
                    offhand_stack.shrink(1);
                }
                else if((mainhand_stack.getItem() == ItemRegistry.GHASTLY_TOTEM_OF_UNDYING.get())){

                    mainhand_stack.shrink(1);

                }


                /*if the offhand_stack_copy is not empty, then execute*/


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.removeAllEffects();
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1325, 1));
                this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1525, 2));
                this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 1000, 1));
                this.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 1750, 1));


                this.level().broadcastEntityEvent(this, (byte)35);

                callback.setReturnValue(true);

            }

        }

    }


    @Inject(at = @At("HEAD"), method = "checkTotemDeathProtection", cancellable = true)
    public void useSkeletalTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.OFF_HAND);

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if ((offhand_stack.getItem() == ItemRegistry.SKELETAL_TOTEM_OF_UNDYING.get()) || (mainhand_stack.getItem() == ItemRegistry.SKELETAL_TOTEM_OF_UNDYING.get())) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.type().equals(DamageTypes.FELL_OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                /*deletes  totem from offhand*/
                if((offhand_stack.getItem() == ItemRegistry.SKELETAL_TOTEM_OF_UNDYING.get())) {
                    offhand_stack.shrink(1);
                }
                else if((mainhand_stack.getItem() == ItemRegistry.SKELETAL_TOTEM_OF_UNDYING.get())){

                    mainhand_stack.shrink(1);

                }


                /*if the offhand_stack_copy is not empty, then execute*/


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.removeAllEffects();
                this.addEffect(new MobEffectInstance(StatusEffectRegistry.SNIPER.get(), 2000, 0));
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 0));
                this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 350, 1));
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 250, 0));

                this.level().broadcastEntityEvent(this, (byte)35);

                callback.setReturnValue(true);


            }

        }

    }


    @Inject(at = @At("HEAD"), method = "checkTotemDeathProtection", cancellable = true)
    public void useTentacledTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.OFF_HAND);

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if ((offhand_stack.getItem() == ItemRegistry.TENTACLED_TOTEM_OF_UNDYING.get()) || (mainhand_stack.getItem() == ItemRegistry.TENTACLED_TOTEM_OF_UNDYING.get())) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.type().equals(DamageTypes.FELL_OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                /*deletes  totem from offhand*/
                if((offhand_stack.getItem() == ItemRegistry.TENTACLED_TOTEM_OF_UNDYING.get())) {
                    offhand_stack.shrink(1);
                }
                else if((mainhand_stack.getItem() == ItemRegistry.TENTACLED_TOTEM_OF_UNDYING.get())){

                    mainhand_stack.shrink(1);

                }


                /*if the offhand_stack_copy is not empty, then execute*/


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.removeAllEffects();
                this.addEffect(new MobEffectInstance(StatusEffectRegistry.CEPHALOPOD.get(), 2000, 0));
                this.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 2000, 0));
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 950, 1));
                this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 2));
                this.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 950, 0));



                this.level().broadcastEntityEvent(this, (byte)35);

                callback.setReturnValue(true);




            }

        }

    }



    @Inject(at = @At("RETURN"), method = "hurt", cancellable = true)
    public void applyCephalopodEffect(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {

        Entity entity3 = source.getEntity();

        Entity entity =  this;


        if(entity3 instanceof LivingEntity) {

            if(entity3 != null) {

                if(((LivingEntity) entity3).hasEffect(StatusEffectRegistry.CEPHALOPOD.get()))
                {

                    this.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 450, 1));
                    callback.setReturnValue(true);

                }
                else {

                    if(((LivingEntityMixin) entity).hasEffect(StatusEffectRegistry.CEPHALOPOD.get())) {

                        ((LivingEntity) entity3).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 450, 1));
                        ((LivingEntity) entity3).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 150, 0));

                    }


                }

            }
            else {

                callback.setReturnValue(false);

            }

        }


    }


    @Inject(at = @At("HEAD"), method = "checkTotemDeathProtection", cancellable = true)
    public void useRottingTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.OFF_HAND);

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getItemInHand(InteractionHand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if ((offhand_stack.getItem() == ItemRegistry.ROTTING_TOTEM_OF_UNDYING.get()) || (mainhand_stack.getItem() == ItemRegistry.ROTTING_TOTEM_OF_UNDYING.get())) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.type().equals(DamageTypes.FELL_OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                /*deletes  totem from offhand*/
                if((offhand_stack.getItem() == ItemRegistry.ROTTING_TOTEM_OF_UNDYING.get())) {
                    offhand_stack.shrink(1);
                }
                else if((mainhand_stack.getItem() == ItemRegistry.ROTTING_TOTEM_OF_UNDYING.get())){

                    mainhand_stack.shrink(1);

                }


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.removeAllEffects();
                this.addEffect(new MobEffectInstance(StatusEffectRegistry.NECROSIS.get(),2000,0));
                this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300,2));
                this.addEffect(new MobEffectInstance(MobEffects.POISON, 400, 0));

                SummonedZombieEntity zombie_spawn = EntityRegistry.SUMMONED_ZOMBIE.get().create(level());
                SummonedZombieEntity zombie_spawn_two = EntityRegistry.SUMMONED_ZOMBIE.get().create(level());
                SummonedZombieEntity zombie_spawn_three = EntityRegistry.SUMMONED_ZOMBIE.get().create(level());
                SummonedZombieEntity zombie_spawn_four = EntityRegistry.SUMMONED_ZOMBIE.get().create(level());

                assert zombie_spawn != null;
                zombie_spawn.setSummoner(this);
                assert zombie_spawn_two != null;
                zombie_spawn_two.setSummoner(this);
                assert zombie_spawn_three != null;
                zombie_spawn_three.setSummoner(this);
                assert zombie_spawn_four != null;
                zombie_spawn_four.setSummoner(this);

                zombie_spawn.moveTo(this.getX(), this.getY(), this.getZ()+3, 0, 0);

                zombie_spawn_two.moveTo(this.getX() , this.getY(), this.getZ()-3, 0, 0);

                zombie_spawn_three.moveTo(this.getX() -3, this.getY(), this.getZ(), 0, 0);

                zombie_spawn_four.moveTo(this.getX()+2, this.getY(), this.getZ()+2, 0, 0);

                level().addFreshEntity(zombie_spawn);

                level().addFreshEntity(zombie_spawn_two);

                level().addFreshEntity(zombie_spawn_three);

                level().addFreshEntity(zombie_spawn_four);

                this.level().broadcastEntityEvent(this, (byte)35);

                callback.setReturnValue(true);


            }

        }

    }




    @Inject(at = @At("HEAD"), method = "isInvertedHealAndHarm", cancellable = true)
    public void NecroCheck(CallbackInfoReturnable<Boolean> callback) {

        if (this.hasEffect(StatusEffectRegistry.NECROSIS.get())){
            callback.setReturnValue(true);
        }
        else if(this.getMobType() == MobType.UNDEAD) {
            callback.setReturnValue(true);
        }

    }


}
