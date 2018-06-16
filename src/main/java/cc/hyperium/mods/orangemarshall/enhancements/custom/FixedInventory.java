package cc.hyperium.mods.orangemarshall.enhancements.custom;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;

public class FixedInventory extends GuiInventory
{
    public FixedInventory(final EntityPlayer p) {
        super(p);
    }
    
    protected void updateActivePotionEffects() {
        super.updateActivePotionEffects();
        this.guiLeft = this.width - this.xSize >> 1;
    }
}
