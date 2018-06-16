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
        this.mc = Minecraft.func_71410_x();
        this.config = Config.instance();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayText(final RenderGameOverlayEvent.Text e) {
        if ((this.config.showF4Screen && this.config.showCrosshairDistanceInF4) || (this.mc.field_71474_y.field_74330_P && this.config.showCrosshairDistanceInF3)) {
            this.renderCrosshairDistance(e.partialTicks, e.resolution);
        }
    }
    
    private void renderCrosshairDistance(final float ticks, final ScaledResolution resolution) {
        final Vec3 vec3 = this.mc.field_71439_g.func_174824_e(ticks);
        final Vec3 vec4 = this.mc.field_71439_g.func_70676_i(ticks);
        final Vec3 vec5 = vec3.func_72441_c(vec4.field_72450_a * 1000.0, vec4.field_72448_b * 1000.0, vec4.field_72449_c * 1000.0);
        final MovingObjectPosition tracedPosition = this.mc.field_71441_e.func_72901_a(vec3, vec5, false);
        double distance = -1.0;
        if (tracedPosition != null && tracedPosition.field_72307_f != null) {
            distance = tracedPosition.field_72307_f.func_72438_d(vec3);
            distance = (int)(distance * 100.0) / 100.0;
        }
        String distanceString = (distance >= 0.0) ? (distance + "") : "";
        if (!distanceString.isEmpty() && distanceString.split("\\.")[1].length() < 2) {
            distanceString += 0;
        }
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        final int x = resolution.func_78326_a();
        final int y = resolution.func_78328_b() + 13;
        if (!distanceString.isEmpty()) {
            this.mc.field_71466_p.func_175065_a(distanceString.split("\\.")[0], (float)(x - this.mc.field_71466_p.func_78256_a(distanceString.split("\\.")[0])), (float)y, 16777215, true);
            this.mc.field_71466_p.func_175065_a("." + distanceString.split("\\.")[1], (float)x, (float)y, 16777215, true);
        }
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
}
