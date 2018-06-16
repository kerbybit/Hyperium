package cc.hyperium.mods.orangemarshall.enhancements.modules;

import java.util.concurrent.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;

public class Watermark
{
    private static int cooldown;
    private static ScheduledExecutorService service;
    private static ScheduledFuture<?> future;
    private static int multiplier;
    private static int alpha;
    private static Runnable run;
    
    private static void start() {
        Watermark.service = Executors.newSingleThreadScheduledExecutor();
        Watermark.future = Watermark.service.scheduleAtFixedRate(Watermark.run, 10L, Watermark.cooldown, TimeUnit.SECONDS);
    }
    
    private static void stop() {
        Watermark.future.cancel(true);
        Watermark.future = null;
    }
    
    @SubscribeEvent
    public void onTickReset(final TickEvent.ClientTickEvent e) {
        if (Minecraft.getMinecraft().thePlayer == null && Watermark.future != null) {
            stop();
        }
        else if (Watermark.future == null) {
            start();
        }
    }
    
    @SubscribeEvent
    public void drawWatermark(final RenderGameOverlayEvent.Text e) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().thePlayer == null || Watermark.alpha >= Watermark.multiplier * 255 || Watermark.future == null) {
            ++Watermark.alpha;
            return;
        }
        final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        final String text = Minecraft.getMinecraft().getSession().getUsername().equals("OrangeMarshall") ? "" : "Vanilla Enhancements Mod by OrangeMarshall";
        Minecraft.getMinecraft().fontRendererObj.drawString(text, res.getScaledWidth() - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text), res.getScaledHeight(), 0xFFFFFF | Watermark.alpha / Watermark.multiplier << 24);
        ++Watermark.alpha;
    }
    
    static {
        Watermark.cooldown = 300;
        Watermark.multiplier = 4;
        Watermark.alpha = Watermark.multiplier * 255;
        Watermark.run = new Runnable() {
            @Override
            public void run() {
                Watermark.alpha = 24;
            }
        };
    }
}
