package net.hyper_pigeon.moretotems;

import net.hyper_pigeon.moretotems.client.MoreTotemsModClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class MoreTotemsModNeoforgeClient {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        MoreTotemsModClient.registerRenderers(event::registerEntityRenderer, event::registerBlockEntityRenderer);
    }
}
