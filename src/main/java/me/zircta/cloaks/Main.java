package me.zircta.cloaks;

import me.zircta.cloaks.event.RenderPlayerEvent;
import me.zircta.cloaks.layer.CloakLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.event.EventBus;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.WorldEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main implements ModInitializer {
    public boolean added = false;
    public boolean removed = false;
    private final ArrayList<LayerRenderer<AbstractClientPlayer>> oldCapes = new ArrayList<>();

    @Override
    public void preInit() {
        EventBus.subscribe(this);
    }

    @SubscribeEvent
    public void onWorldEvent(WorldEvent ev) {
        added = false;
        removed = false;
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent ev) {
        if (Minecraft.getMinecraft() != null &&
                Minecraft.getMinecraft().thePlayer != null &&
                Minecraft.getMinecraft().theWorld != null) {
            if (ev.player instanceof EntityPlayerSP) {
                if (!added) {
                    CloakLayer.setCape("mojang.png");
                    ev.renderer.addLayer(new CloakLayer(ev.renderer));

                    added = true;
                } else if (!removed) {
                    final List<LayerRenderer<AbstractClientPlayer>> layerRenderers = ev.renderer.layerRenderers;
                    final Iterator<LayerRenderer<AbstractClientPlayer>> iter = layerRenderers.iterator();
                    while (iter.hasNext()) {
                        final LayerRenderer<AbstractClientPlayer> renderer = iter.next();
                        if (renderer instanceof LayerCape) {
                            this.oldCapes.add(renderer);
                            iter.remove();
                        }
                    }

                    this.oldCapes.forEach(ev.renderer::removeLayer);
                }
            }
        }
    }
}