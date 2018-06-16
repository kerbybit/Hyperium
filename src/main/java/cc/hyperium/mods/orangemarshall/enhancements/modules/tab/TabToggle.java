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
        this.mc = Minecraft.func_71410_x();
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
        if (this.mc.field_71474_y.field_74321_H.func_151468_f()) {
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
            final ScoreObjective scoreobjective = this.mc.field_71441_e.func_96441_U().func_96539_a(0);
            final GuiPlayerTabOverlay gui = this.mc.field_71456_v.func_175181_h();
            if (gui != null) {
                final int width = event.resolution.func_78326_a();
                gui.func_175249_a(width, this.mc.field_71441_e.func_96441_U(), scoreobjective);
            }
        }
    }
    
    private boolean shallRender() {
        final ScoreObjective scoreobjective = this.mc.field_71441_e.func_96441_U().func_96539_a(0);
        final NetHandlerPlayClient handler = this.mc.field_71439_g.field_71174_a;
        return !this.mc.field_71474_y.field_74321_H.func_151470_d() && (!this.mc.func_71387_A() || handler.func_175106_d().size() > 1 || scoreobjective != null) && this.mc.field_71462_r == null;
    }
}
