package net.hyper_pigeon.moretotems.register;

import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.hyper_pigeon.moretotems.effects.Cephalopod;
import net.hyper_pigeon.moretotems.effects.Necrosis;
import net.hyper_pigeon.moretotems.effects.Sniper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.function.Supplier;

public class StatusEffectRegistry {
//    public static final Sniper SNIPER = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation("moretotems", "sniper"), new Sniper(MobEffectCategory.BENEFICIAL, 13420603));
//    public static final Cephalopod CEPHALOPOD = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation("moretotems", "cephalopod"), new Cephalopod(MobEffectCategory.BENEFICIAL, 23245245));
//    public static final Necrosis NECROSIS = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation("moretotems", "necrosis"), new Necrosis(MobEffectCategory.BENEFICIAL, 23245245));
//
    public static final Supplier<MobEffect> SNIPER = registerMobEffect("sniper", () -> new Sniper(MobEffectCategory.BENEFICIAL, 13420603));
    public static final Supplier<MobEffect> CEPHALOPOD = registerMobEffect("cephalopod",() -> new Cephalopod(MobEffectCategory.BENEFICIAL, 23245245));
    public static final Supplier<MobEffect> NECROSIS = registerMobEffect("necrosis",() -> new Necrosis(MobEffectCategory.BENEFICIAL, 23245245));

    public static void init() {}

    public static <T extends MobEffect> Supplier<T> registerMobEffect(String name, Supplier<T> mobEffect) {
        return MoreTotemsMod.COMMON_PLATFORM.registerMobEffect(name, mobEffect);
    }

}
