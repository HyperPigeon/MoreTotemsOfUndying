package net.hyper_pigeon.moretotems.register;

import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.hyper_pigeon.moretotems.entity.SummonedBeeEntity;
import net.hyper_pigeon.moretotems.entity.SummonedZombieEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Zombie;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class EntityRegistry {

    public static final Supplier<EntityType<SummonedBeeEntity>> SUMMONED_BEE = registerEntity("summoned_bee", SummonedBeeEntity::new, 1f, 2f, MobCategory.CREATURE);
    public static final Supplier<EntityType<SummonedZombieEntity>> SUMMONED_ZOMBIE = registerEntity("summoned_zombie", SummonedZombieEntity::new, 1f, 2f, MobCategory.MONSTER);

    public static void init(){}

    public static void registerEntityAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> registrar) {
        registrar.accept(EntityRegistry.SUMMONED_BEE.get(), SummonedBeeEntity.createTotemBeeAttributes().build());
        registrar.accept(EntityRegistry.SUMMONED_ZOMBIE.get(), Zombie.createAttributes().build());
    }

    private static <T extends Mob> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> entity, float width, float height, MobCategory mobCategory) {
        return MoreTotemsMod.COMMON_PLATFORM.registerEntity(name, () -> EntityType.Builder.of(entity,mobCategory).sized(width, height).build(name));
    }
}
