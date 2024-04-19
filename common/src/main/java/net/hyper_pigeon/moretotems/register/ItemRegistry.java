package net.hyper_pigeon.moretotems.register;

import net.hyper_pigeon.moretotems.Constants;
import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.hyper_pigeon.moretotems.item.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public final class ItemRegistry {

    public static final Supplier<Item> EXPLOSIVE_TOTEM_OF_UNDYING = registerItem("explosive_totem_of_undying", () ->  new ExplosiveTotemOfUndying(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> STINGING_TOTEM_OF_UNDYING = registerItem("stinging_totem_of_undying", () ->  new StingingTotemOfUndying(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> TELEPORTING_TOTEM_OF_UNDYING = registerItem("teleporting_totem_of_undying", () ->new TeleportingTotemOfUndying(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> GHASTLY_TOTEM_OF_UNDYING = registerItem("ghastly_totem_of_undying", () -> new GhastlyTotemOfUndying
            (new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> SKELETAL_TOTEM_OF_UNDYING = registerItem("skeletal_totem_of_undying", () ->  new SkeletalTotemOfUndying (new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> TENTACLED_TOTEM_OF_UNDYING = registerItem("tentacled_totem_of_undying", () ->  new TentacledTotemOfUndying(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> ROTTING_TOTEM_OF_UNDYING = registerItem("rotting_totem_of_undying", () -> new RottingTotemOfUndying(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

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
