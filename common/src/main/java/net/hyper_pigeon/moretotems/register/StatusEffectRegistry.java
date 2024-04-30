package net.hyper_pigeon.moretotems.register;

import net.hyper_pigeon.moretotems.Constants;
import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.hyper_pigeon.moretotems.effects.Cephalopod;
import net.hyper_pigeon.moretotems.effects.Necrosis;
import net.hyper_pigeon.moretotems.effects.Sniper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.function.Supplier;

public class StatusEffectRegistry {
    
    public static final Holder<MobEffect> SNIPER = registerMobEffect("sniper", new Sniper(MobEffectCategory.BENEFICIAL, 13420603));
    public static final  Holder<MobEffect> CEPHALOPOD = registerMobEffect("cephalopod",new Cephalopod(MobEffectCategory.BENEFICIAL, 23245245));
    public static final Holder<MobEffect> NECROSIS = registerMobEffect("necrosis",new Necrosis(MobEffectCategory.BENEFICIAL, 23245245));

    public static void init() {}

    private static Holder<MobEffect> registerMobEffect(String name, MobEffect mobEffect) {
        return MoreTotemsMod.COMMON_PLATFORM.registerMobEffect(name,mobEffect);
    }

}
