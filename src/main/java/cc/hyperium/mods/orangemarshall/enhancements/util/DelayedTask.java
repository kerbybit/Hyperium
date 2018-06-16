package cc.hyperium.mods.orangemarshall.enhancements.util;

import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class DelayedTask
{
    private int delayTicks;
    private Runnable task;
    
    public DelayedTask(final int delayTicks, final Runnable task) {
        this.delayTicks = delayTicks;
        this.task = task;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase.equals((Object)TickEvent.Phase.START)) {
            return;
        }
        if (--this.delayTicks <= 0) {
            this.task.run();
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }
    }
}
