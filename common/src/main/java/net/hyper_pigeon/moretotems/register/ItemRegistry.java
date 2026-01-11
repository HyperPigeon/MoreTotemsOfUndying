package net.hyper_pigeon.moretotems.register;

import net.hyper_pigeon.moretotems.Constants;
import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.hyper_pigeon.moretotems.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public final class ItemRegistry {


    public static final ResourceLocation EXPLOSIVE_TOTEM_RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "explosive_totem_of_undying");
    private static final ResourceKey<Item> EXPLOSIVE_TOTEM_KEY = ResourceKey.create(Registries.ITEM, EXPLOSIVE_TOTEM_RESOURCE_LOCATION);
    public static final Supplier<Item> EXPLOSIVE_TOTEM_OF_UNDYING = registerItem("explosive_totem_of_undying", () ->  new ExplosiveTotemOfUndying(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).setId(EXPLOSIVE_TOTEM_KEY)));
    public static final ResourceLocation STINGING_TOTEM_RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "stinging_totem_of_undying");
    private static final ResourceKey<Item> STINGING_TOTEM_KEY = ResourceKey.create(Registries.ITEM, STINGING_TOTEM_RESOURCE_LOCATION);
    public static final Supplier<Item> STINGING_TOTEM_OF_UNDYING = registerItem("stinging_totem_of_undying", () ->  new StingingTotemOfUndying(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).setId(STINGING_TOTEM_KEY)));
    public static final ResourceLocation TELEPORTING_TOTEM_RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "teleporting_totem_of_undying");
    private static final ResourceKey<Item> TELEPORTING_TOTEM_KEY = ResourceKey.create(Registries.ITEM, TELEPORTING_TOTEM_RESOURCE_LOCATION);
    public static final Supplier<Item> TELEPORTING_TOTEM_OF_UNDYING = registerItem("teleporting_totem_of_undying", () -> new TeleportingTotemOfUndying(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).setId(TELEPORTING_TOTEM_KEY)));
    public static final ResourceLocation GHASTLY_TOTEM_RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ghastly_totem_of_undying");
    private static final ResourceKey<Item> GHASTLY_TOTEM_KEY = ResourceKey.create(Registries.ITEM, GHASTLY_TOTEM_RESOURCE_LOCATION);
    public static final Supplier<Item> GHASTLY_TOTEM_OF_UNDYING = registerItem("ghastly_totem_of_undying", () -> new GhastlyTotemOfUndying
            (new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).setId(GHASTLY_TOTEM_KEY)));
    public static final ResourceLocation SKELETAL_TOTEM_RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "skeletal_totem_of_undying");
    private static final ResourceKey<Item> SKELETAL_TOTEM_KEY = ResourceKey.create(Registries.ITEM, SKELETAL_TOTEM_RESOURCE_LOCATION);
    public static final Supplier<Item> SKELETAL_TOTEM_OF_UNDYING = registerItem("skeletal_totem_of_undying", () ->  new SkeletalTotemOfUndying (new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).setId(SKELETAL_TOTEM_KEY)));
    public static final ResourceLocation TENTACLED_TOTEM_RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "tentacled_totem_of_undying");
    private static final ResourceKey<Item> TENTACLED_TOTEM_KEY = ResourceKey.create(Registries.ITEM, TENTACLED_TOTEM_RESOURCE_LOCATION);
    public static final Supplier<Item> TENTACLED_TOTEM_OF_UNDYING = registerItem("tentacled_totem_of_undying", () ->  new TentacledTotemOfUndying(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).setId(TENTACLED_TOTEM_KEY)));
    public static final ResourceLocation ROTTING_TOTEM_RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rotting_totem_of_undying");
    private static final ResourceKey<Item> ROTTING_TOTEM_KEY = ResourceKey.create(Registries.ITEM, ROTTING_TOTEM_RESOURCE_LOCATION);
    public static final Supplier<Item> ROTTING_TOTEM_OF_UNDYING = registerItem("rotting_totem_of_undying", () -> new RottingTotemOfUndying(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).setId(ROTTING_TOTEM_KEY)));

    public static final Supplier<CreativeModeTab> MORETOTEMS_TAB = MoreTotemsMod.COMMON_PLATFORM.registerCreativeModeTab("moretotems_items", () -> MoreTotemsMod.COMMON_PLATFORM.newCreativeTabBuilder()
            .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".moretotems_items"))
            .icon(() -> new ItemStack(ItemRegistry.EXPLOSIVE_TOTEM_OF_UNDYING.get()))
            .displayItems((enabledFeatures, entries) -> {
                entries.accept(ItemRegistry.EXPLOSIVE_TOTEM_OF_UNDYING.get());
                entries.accept(ItemRegistry.STINGING_TOTEM_OF_UNDYING.get());
                entries.accept(ItemRegistry.TELEPORTING_TOTEM_OF_UNDYING.get());
                entries.accept(ItemRegistry.GHASTLY_TOTEM_OF_UNDYING.get());
                entries.accept(ItemRegistry.SKELETAL_TOTEM_OF_UNDYING.get());
                entries.accept(ItemRegistry.TENTACLED_TOTEM_OF_UNDYING.get());
                entries.accept(ItemRegistry.ROTTING_TOTEM_OF_UNDYING.get());
            })
            .build());

    public static void init(){}

    private static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return MoreTotemsMod.COMMON_PLATFORM.registerItem(id, item);
    }
}