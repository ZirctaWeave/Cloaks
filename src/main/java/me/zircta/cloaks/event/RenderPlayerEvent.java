package me.zircta.cloaks.event;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.weavemc.loader.api.event.Event;

public class RenderPlayerEvent extends Event {
    final public AbstractClientPlayer player;
    final public RenderPlayer renderer;
    final public float partialTicks;

    public RenderPlayerEvent(AbstractClientPlayer player, RenderPlayer renderer, float partialTicks) {
        this.player = player;
        this.renderer = renderer;
        this.partialTicks = partialTicks;
    }
}
