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
        this.mc = Minecraft.getMinecraft();
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
        final int width = resolution.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(text) - 1;
        final int height = resolution.getScaledHeight() - 3 * this.mc.fontRendererObj.FONT_HEIGHT - 1;
        this.mc.fontRendererObj.drawString(text, (float)width, (float)height, 16724804, true);
    }
    
    private boolean isDurabilityLow() {
        if (this.cooldown.attemptReset()) {
            for (final ItemStack is : this.mc.thePlayer.inventory.armorInventory) {
                if (is != null && (is.getItemDamage() / is.getMaxDamage() > 0.85 || is.getMaxDamage() - is.getItemDamage() < 15)) {
                    final int id = Item.getIdFromItem(is.getItem());
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
