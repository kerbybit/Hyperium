package cc.hyperium.mods.orangemarshall.enhancements.modules.f4;

import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraft.client.settings.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.entity.boss.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.tab.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.gameevent.*;

public class F4Listener
{
    private Minecraft mc;
    private F4Screen f4;
    private Config config;
    boolean prevDebug;
    boolean prevF4;
    private ShownScreen status;
    private KeyBinding keybind;
    
    public F4Listener() {
        this.mc = Minecraft.func_71410_x();
        this.f4 = new F4Screen(this.mc);
        this.config = Config.instance();
        this.prevDebug = false;
        this.prevF4 = false;
        this.keybind = KeyBindings.createAndRegister("Better Debug Screen", 62, "key.categories.ui");
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.status = (this.config.showF4OnStart ? ShownScreen.F4 : ShownScreen.NONE);
        this.config.showF4Screen = this.config.showF4OnStart;
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayPost(final RenderGameOverlayEvent.Text e) {
        if (this.config.showF4Screen) {
            final Pair<List<String>, List<String>> sides = this.f4.getLeftAndRight();
            this.printF4Screen(sides.first, sides.second, e.resolution);
        }
    }
    
    private void printF4Screen(final List<String> listL, final List<String> listR, final ScaledResolution resolution) {
        this.printF4Side(listL, Alignment.LEFT, resolution);
        this.printF4Side(listR, Alignment.RIGHT, resolution);
        if (this.config.showServerInfoInF4 && !this.isTabVisible()) {
            this.printServerInfo(resolution);
        }
    }
    
    private void printServerInfo(final ScaledResolution resolution) {
        final ServerAddress curAddress = NetworkInfo.getInstance().getCurrentServerAddress();
        final String ign = PlayerDetails.getOwnName();
        final int ownPing = NetworkInfo.getInstance().getPing(ign);
        String serverInfo = (curAddress == null) ? "" : curAddress.func_78861_a();
        if (ownPing >= 0) {
            serverInfo = serverInfo + " (" + ownPing + "ms)";
        }
        final int left = resolution.func_78326_a() - this.mc.field_71466_p.func_78256_a(serverInfo) >> 1;
        final int top = this.isBossbarVisible() ? 20 : 10;
        this.mc.field_71466_p.func_175065_a(serverInfo, (float)left, (float)top, 16777215, true);
    }
    
    private boolean isBossbarVisible() {
        return BossStatus.field_82827_c != null && BossStatus.field_82826_b > 0 && !this.config.hideBossBar;
    }
    
    private boolean isTabVisible() {
        final ScoreObjective scoreobjective = this.mc.field_71441_e.func_96441_U().func_96539_a(0);
        final NetHandlerPlayClient handler = this.mc.field_71439_g.field_71174_a;
        return (this.mc.field_71474_y.field_74321_H.func_151470_d() || TabToggle.instance().getShallRender()) && (!this.mc.func_71387_A() || handler.func_175106_d().size() > 1 || scoreobjective != null);
    }
    
    private void printF4Side(final List<String> list, final Alignment alignment, final ScaledResolution resolution) {
        int top = 2;
        for (final String entry : list) {
            if (!entry.isEmpty()) {
                if (alignment == Alignment.LEFT) {
                    this.printF4SideTextAt(entry, 2, top);
                }
                else {
                    if (alignment != Alignment.RIGHT) {
                        throw new IllegalArgumentException("Invalid alignment " + alignment);
                    }
                    final int left = resolution.func_78326_a() - 1 - this.mc.field_71466_p.func_78256_a(entry);
                    this.printF4SideTextAt(entry, left, top);
                }
            }
            top += this.mc.field_71466_p.field_78288_b;
        }
    }
    
    private void printF4SideTextAt(final String text, final int left, final int top) {
        int color = 16777215;
        if (Config.instance().showBackgroundInF4) {
            color = 14737632;
            final int textWidth = this.mc.field_71466_p.func_78256_a(text);
            Gui.func_73734_a(left - 1, top - 1, left + textWidth + 1, top + this.mc.field_71466_p.field_78288_b - 1, -1873784752);
        }
        this.mc.field_71466_p.func_175065_a(text, (float)left, (float)top, color, true);
    }
    
    @SubscribeEvent
    public void onTick(final InputEvent.KeyInputEvent e) {
        if (this.keybind.func_151463_i() != 61) {
            if (this.keybind.func_151468_f()) {
                final Config config = this.config;
                final boolean showF4Screen = !this.config.showF4Screen;
                config.showF4Screen = showF4Screen;
                final boolean showF4 = showF4Screen;
                if (showF4) {
                    this.mc.field_71474_y.field_74330_P = false;
                }
            }
            if (this.config.showF4Screen && this.mc.field_71474_y.field_74330_P && !this.prevDebug) {
                this.config.showF4Screen = false;
            }
            if (!this.mc.field_71474_y.field_74330_P && !this.config.showF4Screen) {
                this.status = ShownScreen.NONE;
            }
            if (this.mc.field_71474_y.field_74330_P) {
                this.status = ShownScreen.F3;
            }
            if (this.config.showF4Screen) {
                this.status = ShownScreen.F4;
            }
        }
        else if (this.keybind.func_151468_f()) {
            this.onPressF4();
        }
        this.prevF4 = this.config.showF4Screen;
        this.prevDebug = this.mc.field_71474_y.field_74330_P;
    }
    
    private void onPressF4() {
        if (this.status == null) {
            this.status = (Config.instance().showF4OnStart ? ShownScreen.F4 : ShownScreen.NONE);
        }
        switch (this.status) {
            case NONE: {
                this.mc.field_71474_y.field_74330_P = true;
                this.config.showF4Screen = false;
                this.status = ShownScreen.F3;
                break;
            }
            case F3: {
                this.mc.field_71474_y.field_74330_P = false;
                this.config.showF4Screen = true;
                this.status = ShownScreen.F4;
                break;
            }
            case F4: {
                this.mc.field_71474_y.field_74330_P = false;
                this.config.showF4Screen = false;
                this.status = ShownScreen.NONE;
                break;
            }
            default: {
                throw new IllegalStateException("Could not deal with state " + this.status);
            }
        }
    }
    
    private enum Alignment
    {
        LEFT, 
        RIGHT;
    }
    
    private enum ShownScreen
    {
        F3, 
        F4, 
        NONE;
    }
}
