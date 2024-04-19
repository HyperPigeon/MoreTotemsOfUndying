package net.hyper_pigeon.moretotems;

import net.hyper_pigeon.moretotems.client.MoreTotemsModClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoreTotemsModForgeClient {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        MoreTotemsModClient.registerRenderers(event::registerEntityRenderer, event::registerBlockEntityRenderer);
    }
}
