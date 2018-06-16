package cc.hyperium.mods.orangemarshall.enhancements.config.components;

import net.minecraft.client.gui.*;

public class GuiBooleanSelector extends GuiValueSelector
{
    private final int id;
    public int xPosition;
    public int yPosition;
    public int width;
    public int height;
    private boolean value;
    
    public GuiBooleanSelector(final int componentId, final int x, final int y) {
        this.id = componentId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = 20;
        this.height = 20;
    }
    
    @Override
    public String getValue() {
        return "" + this.value;
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public void mouseClicked(final int p_146192_1_, final int p_146192_2_, final int p_146192_3_) {
        final boolean flag = p_146192_1_ >= this.xPosition && p_146192_1_ < this.xPosition + this.width && p_146192_2_ >= this.yPosition && p_146192_2_ < this.yPosition + this.height;
        if (flag) {
            this.value = !this.value;
        }
    }
    
    public void drawCheckbox() {
        func_73734_a(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
        func_73734_a(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
        final int padding = 1;
        if (this.value) {
            Gui.func_73734_a(this.xPosition + padding, this.yPosition + padding, this.xPosition + this.width - padding, this.yPosition + this.height - padding, -3092272);
        }
    }
    
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public void draw() {
        this.drawCheckbox();
    }
    
    @Override
    public void setValue(final Object value) {
        this.value = Boolean.valueOf(value.toString());
    }
    
    @Override
    public void keyTyped(final char c, final int keyCode) {
    }
}
