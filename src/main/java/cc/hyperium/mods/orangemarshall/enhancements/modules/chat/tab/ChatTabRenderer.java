package cc.hyperium.mods.orangemarshall.enhancements.modules.chat.tab;

import java.util.function.*;
import net.minecraft.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class ChatTabRenderer
{
    private static final int STRING_SELECTED = 16777216;
    private static final int STRING = -1140850689;
    private static final int BOX_SELECTED = 1728053247;
    private static final int BOX = 1996488704;
    private boolean isMouseDownOnThis;
    private int width;
    private int height;
    private int x;
    private Supplier<Integer> getY;
    protected Minecraft mc;
    private final String name;
    private ChatTab tab;
    
    public ChatTabRenderer(final String name, final int x, final Supplier<Integer> getY, final int width, final int height, final ChatTab tab) {
        this.mc = Minecraft.getMinecraft();
        this.width = width;
        this.height = height;
        this.name = name;
        this.x = x;
        this.getY = getY;
        this.tab = tab;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onChatShow(final RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            if (this.mc.currentScreen instanceof GuiChat) {
                this.render();
                this.updateMouseState();
            }
            else {
                this.resetMouseState();
            }
            if (this.mc.currentScreen == null && Config.instance().renderCustomChatTabsWhenChatIsNotOpen) {
                this.render();
            }
        }
    }
    
    private void updateMouseState() {
        if (Mouse.getEventButton() == 0) {
            final boolean eventState = Mouse.getEventButtonState();
            if (eventState) {
                if (this.isMouseOver()) {
                    this.isMouseDownOnThis = true;
                }
            }
            else {
                if (this.isMouseDownOnThis) {
                    this.onClick();
                }
                this.isMouseDownOnThis = false;
            }
        }
    }
    
    private void resetMouseState() {
        this.isMouseDownOnThis = false;
    }
    
    protected boolean isMouseOver() {
        if (!(this.mc.currentScreen instanceof GuiChat)) {
            return false;
        }
        final ScaledResolution res = new ScaledResolution(this.mc);
        final int screenWidth = res.getScaledWidth();
        final int screenHeight = res.getScaledHeight();
        final int mouseX = Mouse.getX() * screenWidth / this.mc.displayWidth;
        final int mouseY = screenHeight - Mouse.getY() * screenHeight / this.mc.displayHeight - 1;
        final int y = this.getY.get();
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > y && mouseY < y + this.height;
    }
    
    protected void render() {
        final boolean flag = this.isMouseOver() || this.tab.isSelected();
        final int box_color = flag ? 1728053247 : 1996488704;
        final int text_color = flag ? 16777216 : -1140850689;
        final int y = this.getY.get();
        Gui.drawRect(this.x, y, this.x + this.width, y + this.height, box_color);
        final String name = this.tab.hasUnreadMessage() ? (EnumChatFormatting.BOLD.toString() + EnumChatFormatting.UNDERLINE + this.name) : this.name;
        final int boxMiddleX = 1 + this.x + (this.width - this.mc.fontRendererObj.getStringWidth(name) >> 1);
        final int boxMiddleY = 2 + y + (this.height - this.mc.fontRendererObj.FONT_HEIGHT >> 1);
        this.mc.fontRendererObj.drawString(name, boxMiddleX, boxMiddleY, text_color);
    }
    
    protected void onClick() {
        this.tab.select();
    }
}
