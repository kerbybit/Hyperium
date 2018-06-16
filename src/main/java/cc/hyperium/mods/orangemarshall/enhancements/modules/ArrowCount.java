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
        this.mc = Minecraft.func_71410_x();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayText(final RenderGameOverlayEvent.Text e) {
        if (!Config.instance().enableArrowCounter) {
            return;
        }
        if (this.mc.field_71439_g.func_71045_bC() != null) {
            final boolean isHoldingBow = this.mc.field_71439_g.func_71045_bC().func_77973_b() instanceof ItemBow;
            final int count = this.getHeldItemCount(isHoldingBow);
            if (count > 1 || (isHoldingBow && count > 0)) {
                final int offset = (this.mc.field_71442_b.func_178889_l() == WorldSettings.GameType.CREATIVE) ? 10 : 0;
                this.mc.field_71466_p.func_175065_a(count + "", (float)(e.resolution.func_78326_a() - this.mc.field_71466_p.func_78256_a(count + "") >> 1), (float)(e.resolution.func_78328_b() - 46 - offset), 16777215, true);
            }
        }
    }
    
    private int getHeldItemCount(final boolean bow) {
        int id = Item.func_150891_b(this.mc.field_71439_g.func_71045_bC().func_77973_b());
        int data = this.mc.field_71439_g.func_71045_bC().func_77952_i();
        if (bow) {
            id = Item.func_150891_b(Items.field_151032_g);
            data = 0;
        }
        int count = 0;
        this.mc = Minecraft.func_71410_x();
        final ItemStack[] inventory = this.mc.field_71439_g.field_71071_by.field_70462_a;
        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] != null) {
                final Item item = inventory[i].func_77973_b();
                if (Item.func_150891_b(item) == id && inventory[i].func_77952_i() == data) {
                    count += inventory[i].field_77994_a;
                }
            }
        }
        return count;
    }
}
