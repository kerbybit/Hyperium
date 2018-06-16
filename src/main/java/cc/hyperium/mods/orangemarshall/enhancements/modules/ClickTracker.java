package cc.hyperium.mods.orangemarshall.enhancements.modules;

import java.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import cc.hyperium.mods.orangemarshall.enhancements.event.*;
import java.util.function.*;

public class ClickTracker
{
    private List<Long> clicks;
    private List<Long> rclicks;
    public int highestCPS;
    public int currentCPS;
    public int highestRCS;
    public int currentRCS;
    private static ClickTracker instance;
    
    public ClickTracker() {
        this.clicks = new ArrayList<Long>();
        this.rclicks = new ArrayList<Long>();
        this.highestCPS = 0;
        this.currentCPS = 0;
        this.highestRCS = 0;
        this.currentRCS = 0;
        MinecraftForge.EVENT_BUS.register((Object)this);
        ClickTracker.instance = this;
    }
    
    public static ClickTracker instance() {
        return ClickTracker.instance;
    }
    
    @SubscribeEvent
    public void tick(final TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            return;
        }
        this.removeOlderThan1Second();
        this.updateHighestCPS();
        this.updateHighestRCS();
    }
    
    @SubscribeEvent
    public void click(final LeftClickEvent e) {
        this.click();
    }
    
    @SubscribeEvent
    public void click(final RightClickEvent e) {
        this.clickR();
    }
    
    public void reset() {
        this.highestCPS = 0;
        this.highestRCS = 0;
    }
    
    private void click() {
        this.clicks.add(System.currentTimeMillis());
    }
    
    private void clickR() {
        this.rclicks.add(System.currentTimeMillis());
    }
    
    private void removeOlderThan1Second() {
        this.clicks.removeIf(new OlderThan1Second());
        this.rclicks.removeIf(new OlderThan1Second());
    }
    
    private void updateHighestCPS() {
        this.highestCPS = ((this.clicks.size() > this.highestCPS) ? this.clicks.size() : this.highestCPS);
        this.currentCPS = this.clicks.size();
    }
    
    private void updateHighestRCS() {
        this.highestRCS = ((this.rclicks.size() > this.highestRCS) ? this.rclicks.size() : this.highestRCS);
        this.currentRCS = this.rclicks.size();
    }
    
    private static class OlderThan1Second implements Predicate<Long>
    {
        private long time;
        
        private OlderThan1Second() {
            this.time = System.currentTimeMillis();
        }
        
        @Override
        public boolean test(final Long entry) {
            return this.time - entry >= 1000L;
        }
    }
}
