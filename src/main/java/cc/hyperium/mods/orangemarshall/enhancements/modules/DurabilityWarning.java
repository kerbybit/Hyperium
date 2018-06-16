package cc.hyperium.mods.orangemarshall.enhancements.modules;

import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;

public class DurabilityWarning
{
    private Minecraft mc;
    private boolean showWarning;
    private Cooldown cooldown;
    
    public DurabilityWarning() {
        this.mc = Minecraft.func_71410_x();
        this.showWarning = false;
        this.cooldown = Cooldown.getNewCooldownSeconds(1);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayText(final RenderGameOverlayEvent.Text e) {
        if (Config.instance().showArmorWarning && this.isDurabilityLow()) {
            this.printArmorWarning(e.resolution);
        }
    }
    
    private void printArmorWarning(final ScaledResolution resolution) {
        final String text = "Armor durability!";
        final int width = resolution.func_78326_a() - this.mc.field_71466_p.func_78256_a(text) - 1;
        final int height = resolution.func_78328_b() - 3 * this.mc.field_71466_p.field_78288_b - 1;
        this.mc.field_71466_p.func_175065_a(text, (float)width, (float)height, 16724804, true);
    }
    
    private boolean isDurabilityLow() {
        if (this.cooldown.attemptReset()) {
            for (final ItemStack is : this.mc.field_71439_g.field_71071_by.field_70460_b) {
                if (is != null && (is.func_77952_i() / is.func_77958_k() > 0.85 || is.func_77958_k() - is.func_77952_i() < 15)) {
                    final int id = Item.func_150891_b(is.func_77973_b());
                    if (id >= 298 && id <= 317) {
                        return this.showWarning = true;
                    }
                }
            }
            return this.showWarning = false;
        }
        return this.showWarning;
    }
}
