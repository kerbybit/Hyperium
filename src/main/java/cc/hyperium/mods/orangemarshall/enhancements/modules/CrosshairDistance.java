package cc.hyperium.mods.orangemarshall.enhancements.modules;

import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

public class CrosshairDistance
{
    private Minecraft mc;
    private Config config;
    
    public CrosshairDistance() {
        this.mc = Minecraft.getMinecraft();
        this.config = Config.instance();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayText(final RenderGameOverlayEvent.Text e) {
        if ((this.config.showF4Screen && this.config.showCrosshairDistanceInF4) || (this.mc.gameSettings.showDebugInfo && this.config.showCrosshairDistanceInF3)) {
            this.renderCrosshairDistance(e.partialTicks, e.resolution);
        }
    }
    
    private void renderCrosshairDistance(final float ticks, final ScaledResolution resolution) {
        final Vec3 vec3 = this.mc.thePlayer.getPositionEyes(ticks);
        final Vec3 vec4 = this.mc.thePlayer.getLook(ticks);
        final Vec3 vec5 = vec3.addVector(vec4.xCoord * 1000.0, vec4.yCoord * 1000.0, vec4.zCoord * 1000.0);
        final MovingObjectPosition tracedPosition = this.mc.theWorld.rayTraceBlocks(vec3, vec5, false);
        double distance = -1.0;
        if (tracedPosition != null && tracedPosition.hitVec != null) {
            distance = tracedPosition.hitVec.distanceTo(vec3);
            distance = (int)(distance * 100.0) / 100.0;
        }
        String distanceString = (distance >= 0.0) ? (distance + "") : "";
        if (!distanceString.isEmpty() && distanceString.split("\\.")[1].length() < 2) {
            distanceString += 0;
        }
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        final int x = resolution.getScaledWidth();
        final int y = resolution.getScaledHeight() + 13;
        if (!distanceString.isEmpty()) {
            this.mc.fontRendererObj.drawString(distanceString.split("\\.")[0], (float)(x - this.mc.fontRendererObj.getStringWidth(distanceString.split("\\.")[0])), (float)y, 16777215, true);
            this.mc.fontRendererObj.drawString("." + distanceString.split("\\.")[1], (float)x, (float)y, 16777215, true);
        }
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
}
