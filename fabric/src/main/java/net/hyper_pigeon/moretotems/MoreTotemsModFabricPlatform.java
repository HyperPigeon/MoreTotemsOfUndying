package net.hyper_pigeon.moretotems;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.hyper_pigeon.moretotems.platform.MoreTotemsModPlatform;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class MoreTotemsModFabricPlatform implements MoreTotemsModPlatform {
    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String id, Supplier<BlockEntityType<T>> blockEntityType) {
        ResourceLocation resourceLocation =  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id);
        ResourceKey registryKey = Registries.BLOCK_ENTITY_TYPE;
        ResourceKey<BlockEntityType<T>> resourceKey = ResourceKey.create(registryKey, resourceLocation);
        return registerSupplier(resourceKey, BuiltInRegistries.BLOCK_ENTITY_TYPE, blockEntityType);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        ResourceLocation resourceLocation =  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id);
        ResourceKey registryKey = Registries.BLOCK;
        ResourceKey<T> resourceKey = ResourceKey.create(registryKey, resourceLocation);
        return registerSupplier(resourceKey, BuiltInRegistries.BLOCK, block);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entity) {
        ResourceLocation resourceLocation =  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id);
        ResourceKey registryKey = Registries.ENTITY_TYPE;
        ResourceKey<EntityType<T>> resourceKey = ResourceKey.create(registryKey, resourceLocation);
        return registerSupplier(resourceKey, BuiltInRegistries.ENTITY_TYPE, entity);
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        ResourceLocation resourceLocation =  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id);
        ResourceKey registryKey = Registries.ITEM;
        ResourceKey<T> resourceKey = ResourceKey.create(registryKey, resourceLocation);
        return registerSupplier(resourceKey,BuiltInRegistries.ITEM, item);
    }

    @Override
    public Holder<MobEffect> registerMobEffect(String name, MobEffect mobEffect) {
        ResourceLocation resourceLocation =  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,  resourceLocation, mobEffect);
    }

    @Override
    public <T extends SoundEvent> Supplier<T> registerSound(String id, Supplier<T> sound) {
        ResourceLocation resourceLocation =  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id);
        ResourceKey registryKey = Registries.SOUND_EVENT;
        ResourceKey<T> resourceKey = ResourceKey.create(registryKey, resourceLocation);
        return registerSupplier(resourceKey, BuiltInRegistries.SOUND_EVENT, sound);
    }

    @Override
    public <T extends CreativeModeTab> Supplier<T> registerCreativeModeTab(String id, Supplier<T> tab) {
        ResourceLocation resourceLocation =  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id);
        ResourceKey registryKey = Registries.SOUND_EVENT;
        ResourceKey<T> resourceKey = ResourceKey.create(registryKey, resourceLocation);
        return registerSupplier(resourceKey, BuiltInRegistries.CREATIVE_MODE_TAB, tab);
    }

    @Override
    public <E extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(Supplier<EntityType<E>> entityType, int primaryEggColour, int secondaryEggColour, Item.Properties itemProperties) {
        return () -> new SpawnEggItem(entityType.get(), itemProperties);
    }

    @Override
    public CreativeModeTab.Builder newCreativeTabBuilder() {
        return FabricItemGroup.builder();
    }

    /**
     * Quick wrapper to make the individual registration lines cleaner but still return the multiloader-compatible supplier
     */
    private static <T, R extends Registry<? super T>> Supplier<T> registerSupplier(ResourceKey<T> resourceKey, R registry, Supplier<T> object) {
        final T registeredObject = Registry.register((Registry<T>)registry, resourceKey, object.get());
        return () -> registeredObject;
    }
}