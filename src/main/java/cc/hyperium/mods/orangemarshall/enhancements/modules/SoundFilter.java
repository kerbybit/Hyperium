package cc.hyperium.mods.orangemarshall.enhancements.modules;

import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.sound.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class SoundFilter
{
    private Config config;
    
    public SoundFilter() {
        this.config = Config.instance();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onSound(final PlaySoundEvent e) {
        if (this.config.mutePortalSounds && e.name.equals("portal.portal")) {
            e.result = null;
        }
        if (this.config.muteWitherSounds && e.name.startsWith("mob.wither")) {
            e.result = null;
        }
        if (this.config.muteSlimeSounds && e.name.startsWith("mob.slime")) {
            e.result = null;
        }
        if (this.config.muteThunderSounds && e.name.startsWith("ambient.weather.thunder")) {
            e.result = null;
        }
    }
}
