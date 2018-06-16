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
        if (Minecraft.func_71410_x().field_71439_g == null && Watermark.future != null) {
            stop();
        }
        else if (Watermark.future == null) {
            start();
        }
    }
    
    @SubscribeEvent
    public void drawWatermark(final RenderGameOverlayEvent.Text e) {
        if (Minecraft.func_71410_x() == null || Minecraft.func_71410_x().field_71439_g == null || Watermark.alpha >= Watermark.multiplier * 255 || Watermark.future == null) {
            ++Watermark.alpha;
            return;
        }
        final ScaledResolution res = new ScaledResolution(Minecraft.func_71410_x());
        final String text = Minecraft.func_71410_x().func_110432_I().func_111285_a().equals("OrangeMarshall") ? "" : "Vanilla Enhancements Mod by OrangeMarshall";
        Minecraft.func_71410_x().field_71466_p.func_78276_b(text, res.func_78326_a() - Minecraft.func_71410_x().field_71466_p.func_78256_a(text), res.func_78328_b(), 0xFFFFFF | Watermark.alpha / Watermark.multiplier << 24);
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
