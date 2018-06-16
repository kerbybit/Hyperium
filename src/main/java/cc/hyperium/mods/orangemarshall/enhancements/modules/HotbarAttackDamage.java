package cc.hyperium.mods.orangemarshall.enhancements.modules;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class HotbarAttackDamage
{
    private Minecraft mc;
    
    public HotbarAttackDamage() {
        this.mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayText(final RenderGameOverlayEvent.Post e) {
        if (e.type != RenderGameOverlayEvent.ElementType.TEXT || !Config.instance().showAttackDamageAboveHotbar) {
            return;
        }
        final ItemStack heldItemStack = this.mc.thePlayer.inventory.getCurrentItem();
        if (heldItemStack != null) {
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            final ScaledResolution res = e.resolution;
            final String attackDamage = this.getAttackDamageString(heldItemStack);
            int y = res.getScaledHeight() - 59;
            y += (this.mc.playerController.shouldDrawHUD() ? -1 : 14);
            y = y + this.mc.fontRendererObj.FONT_HEIGHT << 0;
            y <<= 1;
            y += this.mc.fontRendererObj.FONT_HEIGHT;
            final int x = res.getScaledWidth() - (this.mc.fontRendererObj.getStringWidth(attackDamage) >> 1);
            this.mc.fontRendererObj.drawString(attackDamage, x, y, 13421772);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glPopMatrix();
        }
    }
    
    private String getAttackDamageString(final ItemStack stack) {
        for (final String entry : stack.getTooltip((EntityPlayer)this.mc.thePlayer, true)) {
            if (entry.endsWith("Attack Damage")) {
                return entry.split(" ", 2)[0].substring(2);
            }
        }
        return "";
    }
}
