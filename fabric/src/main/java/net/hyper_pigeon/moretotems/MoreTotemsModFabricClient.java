package net.hyper_pigeon.moretotems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.hyper_pigeon.moretotems.client.MoreTotemsModClient;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class MoreTotemsModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MoreTotemsModClient.registerRenderers(EntityRendererRegistry::register, BlockEntityRenderers::register);
    }
}
