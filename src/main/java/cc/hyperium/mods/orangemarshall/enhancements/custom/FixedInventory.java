package cc.hyperium.mods.orangemarshall.enhancements.custom;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;

public class FixedInventory extends GuiInventory
{
    public FixedInventory(final EntityPlayer p) {
        super(p);
    }
    
    protected void func_175378_g() {
        super.func_175378_g();
        this.field_147003_i = this.field_146294_l - this.field_146999_f >> 1;
    }
}
