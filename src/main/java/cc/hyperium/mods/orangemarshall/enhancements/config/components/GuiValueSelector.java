package cc.hyperium.mods.orangemarshall.enhancements.config.components;

import net.minecraft.client.gui.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraft.client.*;

public abstract class GuiValueSelector extends Gui
{
    protected static final int COMPONENT_ID = 1337;
    protected static final FontRenderer RENDERER;
    
    public abstract String getValue();
    
    public static GuiValueSelector getFromField(final FieldContainer field, final int left, final int top, final int maxWidth) {
        if (field == null) {
            return null;
        }
        final Class<?> value = field.getField().getType();
        GuiValueSelector selector = null;
        if (value.isAssignableFrom(Integer.TYPE)) {
            selector = new GuiNumberSelector(1337, GuiValueSelector.RENDERER, left, top, maxWidth, 20, false);
        }
        else if (value.isAssignableFrom(Double.TYPE)) {
            selector = new GuiNumberSelector(1337, GuiValueSelector.RENDERER, left, top, maxWidth, 20, true);
        }
        else if (value.isAssignableFrom(Boolean.TYPE)) {
            final int x = left + (maxWidth - 20) / 2;
            selector = new GuiBooleanSelector(1337, x, top);
        }
        else if (value.isAssignableFrom(String.class)) {
            selector = new GuiTextSelector(1337, GuiValueSelector.RENDERER, left, top, maxWidth, 20);
        }
        if (selector == null) {
            throw new IllegalArgumentException("Could not find Selector for " + field.getClass());
        }
        selector.setValue(field.getValue());
        return selector;
    }
    
    public abstract void draw();
    
    public abstract void setValue(final Object p0);
    
    public abstract void keyTyped(final char p0, final int p1);
    
    public abstract void mouseClicked(final int p0, final int p1, final int p2);
    
    static {
        RENDERER = Minecraft.func_71410_x().field_71466_p;
    }
}
