package cc.hyperium.mods.orangemarshall.enhancements.modules;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class ArrowCount
{
    private Minecraft mc;
    
    public ArrowCount() {
        this.mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayText(final RenderGameOverlayEvent.Text e) {
        if (!Config.instance().enableArrowCounter) {
            return;
        }
        if (this.mc.thePlayer.getCurrentEquippedItem() != null) {
            final boolean isHoldingBow = this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow;
            final int count = this.getHeldItemCount(isHoldingBow);
            if (count > 1 || (isHoldingBow && count > 0)) {
                final int offset = (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE) ? 10 : 0;
                this.mc.fontRendererObj.drawString(count + "", (float)(e.resolution.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(count + "") >> 1), (float)(e.resolution.getScaledHeight() - 46 - offset), 16777215, true);
            }
        }
    }
    
    private int getHeldItemCount(final boolean bow) {
        int id = Item.getIdFromItem(this.mc.thePlayer.getCurrentEquippedItem().getItem());
        int data = this.mc.thePlayer.getCurrentEquippedItem().getItemDamage();
        if (bow) {
            id = Item.getIdFromItem(Items.arrow);
            data = 0;
        }
        int count = 0;
        this.mc = Minecraft.getMinecraft();
        final ItemStack[] inventory = this.mc.thePlayer.inventory.mainInventory;
        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] != null) {
                final Item item = inventory[i].getItem();
                if (Item.getIdFromItem(item) == id && inventory[i].getItemDamage() == data) {
                    count += inventory[i].stackSize;
                }
            }
        }
        return count;
    }
}
