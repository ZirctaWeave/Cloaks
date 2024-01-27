package me.zircta.cloaks.mixins;

import me.zircta.cloaks.event.RenderPlayerEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.weavemc.loader.api.event.EventBus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderPlayer.class)
public class RenderPlayerMixin {
    @Inject(method = "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V", at = @At("HEAD"))
    private void doRender(AbstractClientPlayer player, double x, double y, double z, float yaw, float pt, CallbackInfo ci) {
        EventBus.callEvent(new RenderPlayerEvent(player, (RenderPlayer)(Object)this, pt));
    }
}
