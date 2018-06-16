package cc.hyperium.mods.orangemarshall.enhancements.modules.tab;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.network.*;

public class TabToggle
{
    private boolean shallRender;
    private static TabToggle instance;
    private Minecraft mc;
    
    public TabToggle() {
        this.shallRender = false;
        this.mc = Minecraft.getMinecraft();
        TabToggle.instance = this;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public static TabToggle instance() {
        return TabToggle.instance;
    }
    
    public boolean getShallRender() {
        return this.shallRender;
    }
    
    @SubscribeEvent
    public void onKey(final InputEvent.KeyInputEvent event) {
        if (this.mc.gameSettings.keyBindPlayerList.isPressed()) {
            this.shallRender = !this.shallRender;
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (!Config.instance().enableTabToggle) {
            return;
        }
        if (this.shallRender() && this.shallRender) {
            final ScoreObjective scoreobjective = this.mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(0);
            final GuiPlayerTabOverlay gui = this.mc.ingameGUI.getTabList();
            if (gui != null) {
                final int width = event.resolution.getScaledWidth();
                gui.renderPlayerlist(width, this.mc.theWorld.getScoreboard(), scoreobjective);
            }
        }
    }
    
    private boolean shallRender() {
        final ScoreObjective scoreobjective = this.mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(0);
        final NetHandlerPlayClient handler = this.mc.thePlayer.sendQueue;
        return !this.mc.gameSettings.keyBindPlayerList.isKeyDown() && (!this.mc.isIntegratedServerRunning() || handler.getPlayerInfoMap().size() > 1 || scoreobjective != null) && this.mc.currentScreen == null;
    }
}
