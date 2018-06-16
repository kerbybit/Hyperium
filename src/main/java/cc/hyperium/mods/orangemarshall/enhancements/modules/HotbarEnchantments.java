package cc.hyperium.mods.orangemarshall.enhancements.modules;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;

public class HotbarEnchantments
{
    private final int COLOR = 13421772;
    private Minecraft mc;
    
    public HotbarEnchantments() {
        this.mc = Minecraft.func_71410_x();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayText(final RenderGameOverlayEvent.Post e) {
        if (e.type != RenderGameOverlayEvent.ElementType.TEXT || !Config.instance().showEnchantsAboveHotbar) {
            return;
        }
        final ItemStack heldItemStack = this.mc.field_71439_g.field_71071_by.func_70448_g();
        if (heldItemStack != null) {
            String toDraw = "";
            if (heldItemStack.func_77973_b() instanceof ItemPotion) {
                toDraw = this.getPotionEffectString(heldItemStack);
            }
            else {
                toDraw = this.getEnchantmentString(heldItemStack);
            }
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            final ScaledResolution res = e.resolution;
            int y = res.func_78328_b() - 59;
            y += (this.mc.field_71442_b.func_78755_b() ? -2 : 14);
            y = y + this.mc.field_71466_p.field_78288_b << 0;
            y <<= 1;
            final int x = res.func_78326_a() - (this.mc.field_71466_p.func_78256_a(toDraw) >> 1);
            this.mc.field_71466_p.func_78276_b(toDraw, x, y, 13421772);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glPopMatrix();
        }
    }
    
    private String getPotionEffectString(final ItemStack heldItemStack) {
        final ItemPotion potion = (ItemPotion)heldItemStack.func_77973_b();
        final List<PotionEffect> effects = (List<PotionEffect>)potion.func_77832_l(heldItemStack);
        if (effects == null) {
            return "";
        }
        final StringBuilder potionBuilder = new StringBuilder();
        for (final PotionEffect entry : effects) {
            final int duration = entry.func_76459_b() / 20;
            potionBuilder.append(EnumChatFormatting.BOLD.toString());
            potionBuilder.append(StatCollector.func_74838_a(entry.func_76453_d()));
            potionBuilder.append(" ");
            potionBuilder.append(entry.func_76458_c() + 1);
            potionBuilder.append(" ");
            potionBuilder.append("(");
            potionBuilder.append(duration / 60 + String.format(":%02d", duration % 60));
            potionBuilder.append(") ");
        }
        return potionBuilder.toString().trim();
    }
    
    private String getEnchantmentString(final ItemStack heldItemStack) {
        final StringBuilder enchantBuilder = new StringBuilder();
        final Map<Integer, Integer> en = (Map<Integer, Integer>)EnchantmentHelper.func_82781_a(heldItemStack);
        for (final Map.Entry<Integer, Integer> entry : en.entrySet()) {
            enchantBuilder.append(EnumChatFormatting.BOLD.toString());
            enchantBuilder.append(Maps.ENCHANTMENT_SHORT_NAME.get(entry.getKey()));
            enchantBuilder.append(" ");
            enchantBuilder.append(entry.getValue());
            enchantBuilder.append(" ");
        }
        return enchantBuilder.toString().trim();
    }
}
