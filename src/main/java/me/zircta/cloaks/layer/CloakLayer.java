package me.zircta.cloaks.layer;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CloakLayer implements LayerRenderer<AbstractClientPlayer> {
    private static ResourceLocation cape;
    private final RenderPlayer playerRenderer;

    public CloakLayer(RenderPlayer playerRenderer) {
        this.playerRenderer = playerRenderer;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float v, float v1, float partialTicks, float v3, float v4, float v5, float scale) {
        if (player.isUser() && player.hasPlayerInfo() && !player.isInvisible()) {
            GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);

            this.playerRenderer.bindTexture(cape);

            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, 0.0D, 0.125D);

            double deltaX = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * (double) partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * (double) partialTicks);
            double deltaY = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * (double) partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * (double) partialTicks);
            double deltaZ = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * (double) partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double) partialTicks);

            double yaw = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;

            double sinYaw = Math.sin(yaw * Math.PI / 180.0D);
            double cosYaw = -Math.cos(yaw * Math.PI / 180.0D);

            double yOffset = deltaY * 10.0D;
            yOffset = MathHelper.clamp_double(yOffset, -6.0D, 32.0D);

            double pitchOffset = (deltaX * sinYaw + deltaZ * cosYaw) * 100.0D;
            double rollOffset = (deltaX * cosYaw - deltaZ * sinYaw) * 100.0D;

            if (pitchOffset > 95.0D) {
                pitchOffset = 95.0D;
            }

            if (pitchOffset < 0.0D) {
                pitchOffset = 0.0D;
            }

            float cameraYaw = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
            yOffset = yOffset + Math.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0D) * 32.0D * cameraYaw;

            if (player.isSneaking()) {
                yOffset += 25.0D;
            }

            GL11.glRotated(6.0D + pitchOffset / 2.0D + yOffset, 1.0D, 0.0D, 0.0D);
            GL11.glRotated(rollOffset / 2.0D, 0.0D, 0.0D, 1.0D);
            GL11.glRotated(-rollOffset / 2.0D, 0.0D, 1.0D, 0.0D);
            GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);

            this.playerRenderer.getMainModel().renderCape(0.0625F);

            GL11.glPopMatrix();
        }
    }

    public static void setCape(String cape) {
        CloakLayer.cape = new ResourceLocation("cloaks/" + cape);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
