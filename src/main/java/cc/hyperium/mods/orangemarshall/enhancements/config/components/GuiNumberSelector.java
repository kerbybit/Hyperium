package cc.hyperium.mods.orangemarshall.enhancements.config.components;

import net.minecraft.client.gui.*;

public class GuiNumberSelector extends GuiTextSelector
{
    private boolean allowDecimals;
    
    public GuiNumberSelector(final int componentId, final FontRenderer fontrendererObj, final int x, final int y, final int width, final int height, final boolean allowDecimals) {
        super(componentId, fontrendererObj, x, y, width, height);
        this.allowDecimals = allowDecimals;
    }
    
    @Override
    public void keyTyped(final char c, final int keyCode) {
        boolean flag = Character.isDigit(c);
        flag |= (this.allowDecimals && c == '.');
        flag |= (c == '\b' || c == '\u007f');
        if (flag) {
            this.textboxKeyTyped(c, keyCode);
        }
    }
}
