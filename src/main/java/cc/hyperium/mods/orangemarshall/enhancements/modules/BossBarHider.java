package cc.hyperium.mods.orangemarshall.enhancements.modules;

import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class BossBarHider
{
    public BossBarHider() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlay(final RenderGameOverlayEvent e) {
        if (e.type == RenderGameOverlayEvent.ElementType.BOSSHEALTH && Config.instance().hideBossBar) {
            e.setCanceled(true);
        }
    }
}
