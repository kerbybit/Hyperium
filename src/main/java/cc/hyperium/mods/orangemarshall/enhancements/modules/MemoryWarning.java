package cc.hyperium.mods.orangemarshall.enhancements.modules;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;

public class MemoryWarning
{
    private final int RESET_COOLDOWN = 15;
    private long lastTime;
    private Minecraft mc;
    
    public MemoryWarning() {
        this.lastTime = 0L;
        this.mc = Minecraft.func_71410_x();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayText(final RenderGameOverlayEvent.Text e) {
        if (Config.instance().showMemoryWarning) {
            this.printMemoryWarning(e.resolution);
        }
    }
    
    private void printMemoryWarning(final ScaledResolution resolution) {
        final long i = Runtime.getRuntime().maxMemory();
        final long j = Runtime.getRuntime().totalMemory();
        final long k = Runtime.getRuntime().freeMemory();
        final long l = j - k;
        final long currentMem = l * 100L / i;
        if (currentMem > 90L) {
            this.lastTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.lastTime < 15000L) {
            final String text = "High memory!";
            final int width = resolution.func_78326_a() - this.mc.field_71466_p.func_78256_a(text) - 1;
            final int height = resolution.func_78328_b() - this.mc.field_71466_p.field_78288_b << 1;
            this.mc.field_71466_p.func_175065_a(text, (float)width, (float)height, 16724804, true);
        }
    }
}
