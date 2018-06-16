package cc.hyperium.mods.orangemarshall.enhancements.config.components;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class GuiSlotConfigList extends GuiScrollingList
{
    private GuiConfigList parent;
    private ArrayList<FieldContainer> entries;
    private static final int entryHeight = 15;
    
    public GuiSlotConfigList(final GuiConfigList parent, final ArrayList<FieldContainer> entries, final int listWidth) {
        super(Minecraft.getMinecraft(), listWidth, parent.height, 24, parent.height - 35, 10, 15, parent.width, parent.height);
        this.parent = parent;
        this.entries = entries;
    }
    
    public int listWidth() {
        return this.listWidth;
    }
    
    public int right() {
        return this.right;
    }
    
    public int bottom() {
        return this.bottom;
    }
    
    @Override
    protected int getSize() {
        return this.entries.size();
    }
    
    @Override
    protected void elementClicked(final int index, final boolean doubleClick) {
        this.parent.selectModIndex(index);
    }
    
    @Override
    protected boolean isSelected(final int index) {
        return this.parent.fieldIndexSelected(index);
    }
    
    @Override
    protected void drawBackground() {
        this.parent.drawDefaultBackground();
    }
    
    @Override
    protected int getContentHeight() {
        return this.getSize() * 15 + 1;
    }
    
    @Override
    protected void drawSlot(final int idx, final int right, final int top, final int height, final Tessellator tess) {
        final FieldContainer mc = this.entries.get(idx);
        final String name = mc.name();
        final FontRenderer font = this.parent.getFontRenderer();
        font.drawString(font.trimStringToWidth(name, this.listWidth - 10), this.left + 3, top + 2, 16777215);
    }
}
