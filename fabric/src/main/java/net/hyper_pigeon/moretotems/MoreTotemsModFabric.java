package net.hyper_pigeon.moretotems;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.hyper_pigeon.moretotems.register.EntityRegistry;

public class MoreTotemsModFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        MoreTotemsMod.init();
        EntityRegistry.registerEntityAttributes(FabricDefaultAttributeRegistry::register);
    }
}
