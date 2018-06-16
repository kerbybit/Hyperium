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
        this.mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayText(final RenderGameOverlayEvent.Post e) {
        if (e.type != RenderGameOverlayEvent.ElementType.TEXT || !Config.instance().showEnchantsAboveHotbar) {
            return;
        }
        final ItemStack heldItemStack = this.mc.thePlayer.inventory.getCurrentItem();
        if (heldItemStack != null) {
            String toDraw = "";
            if (heldItemStack.getItem() instanceof ItemPotion) {
                toDraw = this.getPotionEffectString(heldItemStack);
            }
            else {
                toDraw = this.getEnchantmentString(heldItemStack);
            }
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            final ScaledResolution res = e.resolution;
            int y = res.getScaledHeight() - 59;
            y += (this.mc.playerController.shouldDrawHUD() ? -2 : 14);
            y = y + this.mc.fontRendererObj.FONT_HEIGHT << 0;
            y <<= 1;
            final int x = res.getScaledWidth() - (this.mc.fontRendererObj.getStringWidth(toDraw) >> 1);
            this.mc.fontRendererObj.drawString(toDraw, x, y, 13421772);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glPopMatrix();
        }
    }
    
    private String getPotionEffectString(final ItemStack heldItemStack) {
        final ItemPotion potion = (ItemPotion)heldItemStack.getItem();
        final List<PotionEffect> effects = (List<PotionEffect>)potion.getEffects(heldItemStack);
        if (effects == null) {
            return "";
        }
        final StringBuilder potionBuilder = new StringBuilder();
        for (final PotionEffect entry : effects) {
            final int duration = entry.getDuration() / 20;
            potionBuilder.append(EnumChatFormatting.BOLD.toString());
            potionBuilder.append(StatCollector.translateToLocal(entry.getEffectName()));
            potionBuilder.append(" ");
            potionBuilder.append(entry.getAmplifier() + 1);
            potionBuilder.append(" ");
            potionBuilder.append("(");
            potionBuilder.append(duration / 60 + String.format(":%02d", duration % 60));
            potionBuilder.append(") ");
        }
        return potionBuilder.toString().trim();
    }
    
    private String getEnchantmentString(final ItemStack heldItemStack) {
        final StringBuilder enchantBuilder = new StringBuilder();
        final Map<Integer, Integer> en = (Map<Integer, Integer>)EnchantmentHelper.getEnchantments(heldItemStack);
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
