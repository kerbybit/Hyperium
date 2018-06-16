package cc.hyperium.mods.orangemarshall.enhancements.modules;

import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ZoomSensitivity
{
    private Config config;
    
    public ZoomSensitivity() {
        this.config = Config.instance();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onZoom(final InputEvent.KeyInputEvent event) {
        if (this.config.disableOptifineZoomSmooth && this.config.keybindZoom != null && this.config.keybindZoom.func_151470_d()) {
            Minecraft.func_71410_x().field_71474_y.field_74326_T = false;
        }
    }
}
