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
        this.mc = Minecraft.getMinecraft();
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
            final int width = resolution.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(text) - 1;
            final int height = resolution.getScaledHeight() - this.mc.fontRendererObj.FONT_HEIGHT << 1;
            this.mc.fontRendererObj.drawString(text, (float)width, (float)height, 16724804, true);
        }
    }
}
