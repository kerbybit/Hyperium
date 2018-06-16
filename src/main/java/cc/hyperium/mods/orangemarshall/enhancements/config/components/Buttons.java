package cc.hyperium.mods.orangemarshall.enhancements.config.components;

import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;

public class Buttons
{
    public static class GuiButtonDone extends GuiButton
    {
        public GuiButtonDone(final int buttonId, final int x, final int y, final int buttonWidth) {
            super(buttonId, x, y, buttonWidth, 20, I18n.func_135052_a("gui.done", new Object[0]));
        }
    }
    
    public static class GuiButtonReset extends GuiButton
    {
        public GuiButtonReset(final int buttonId, final int x, final int y, final int buttonWidth) {
            super(buttonId, x, y, buttonWidth, 20, I18n.func_135052_a("Reset", new Object[0]));
        }
    }
    
    public static class GuiButtonCancel extends GuiButton
    {
        public GuiButtonCancel(final int buttonId, final int x, final int y, final int buttonWidth) {
            super(buttonId, x, y, buttonWidth, 20, I18n.func_135052_a("Cancel", new Object[0]));
        }
    }
}
