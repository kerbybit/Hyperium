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
        this.mc = Minecraft.func_71410_x();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayText(final RenderGameOverlayEvent.Post e) {
        if (e.type != RenderGameOverlayEvent.ElementType.TEXT || !Config.instance().showAttackDamageAboveHotbar) {
            return;
        }
        final ItemStack heldItemStack = this.mc.field_71439_g.field_71071_by.func_70448_g();
        if (heldItemStack != null) {
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            final ScaledResolution res = e.resolution;
            final String attackDamage = this.getAttackDamageString(heldItemStack);
            int y = res.func_78328_b() - 59;
            y += (this.mc.field_71442_b.func_78755_b() ? -1 : 14);
            y = y + this.mc.field_71466_p.field_78288_b << 0;
            y <<= 1;
            y += this.mc.field_71466_p.field_78288_b;
            final int x = res.func_78326_a() - (this.mc.field_71466_p.func_78256_a(attackDamage) >> 1);
            this.mc.field_71466_p.func_78276_b(attackDamage, x, y, 13421772);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glPopMatrix();
        }
    }
    
    private String getAttackDamageString(final ItemStack stack) {
        for (final String entry : stack.func_82840_a((EntityPlayer)this.mc.field_71439_g, true)) {
            if (entry.endsWith("Attack Damage")) {
                return entry.split(" ", 2)[0].substring(2);
            }
        }
        return "";
    }
}
