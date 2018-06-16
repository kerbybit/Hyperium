package cc.hyperium.mods.orangemarshall.enhancements.util;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.event.*;

public class CCT extends ChatComponentText
{
    private Minecraft mc;
    
    public CCT(final String msg, final EnumChatFormatting... format) {
        super(msg);
        this.setChatStyle(getDefaultStyle());
        this.format(format);
        this.mc = Minecraft.getMinecraft();
    }
    
    public CCT() {
        this("", new EnumChatFormatting[0]);
    }
    
    public CCT bold() {
        this.setChatStyle(this.getChatStyle().setBold(true));
        return this;
    }
    
    public CCT italic() {
        this.setChatStyle(this.getChatStyle().setItalic(true));
        return this;
    }
    
    public CCT underline() {
        this.setChatStyle(this.getChatStyle().setUnderlined(true));
        return this;
    }
    
    public CCT reset() {
        this.setChatStyle(new ChatStyle());
        return this;
    }
    
    public CCT strikethrough() {
        this.setChatStyle(this.getChatStyle().setStrikethrough(true));
        return this;
    }
    
    public CCT gold() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.GOLD));
        return this;
    }
    
    public CCT black() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.BLACK));
        return this;
    }
    
    public CCT dark_blue() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.DARK_BLUE));
        return this;
    }
    
    public CCT dark_green() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN));
        return this;
    }
    
    public CCT dark_aqua() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.DARK_AQUA));
        return this;
    }
    
    public CCT dark_red() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.DARK_RED));
        return this;
    }
    
    public CCT dark_purple() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE));
        return this;
    }
    
    public CCT gray() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.GRAY));
        return this;
    }
    
    public CCT dark_gray() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.DARK_GRAY));
        return this;
    }
    
    public CCT blue() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.BLUE));
        return this;
    }
    
    public CCT green() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.GREEN));
        return this;
    }
    
    public CCT aqua() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.AQUA));
        return this;
    }
    
    public CCT red() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.RED));
        return this;
    }
    
    public CCT light_purple() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE));
        return this;
    }
    
    public CCT yellow() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.YELLOW));
        return this;
    }
    
    public CCT white() {
        this.setChatStyle(this.getChatStyle().setColor(EnumChatFormatting.WHITE));
        return this;
    }
    
    public void format(final EnumChatFormatting... format) {
        for (int i = 0; i < format.length; ++i) {
            switch (format[i]) {
                case BOLD: {
                    this.bold();
                    break;
                }
                case UNDERLINE: {
                    this.underline();
                    break;
                }
                case ITALIC: {
                    this.italic();
                    break;
                }
                case RESET: {
                    this.reset();
                    break;
                }
                case STRIKETHROUGH: {
                    this.strikethrough();
                    break;
                }
                case BLACK:
                case DARK_BLUE:
                case DARK_GREEN:
                case DARK_PURPLE:
                case DARK_RED:
                case GOLD:
                case AQUA:
                case BLUE:
                case GREEN:
                case GRAY:
                case DARK_GRAY:
                case RED:
                case LIGHT_PURPLE:
                case WHITE: {
                    this.setChatStyle(this.getChatStyle().setColor(format[i]));
                    break;
                }
            }
        }
    }
    
    public CCT add(final IChatComponent ic) {
        super.appendSibling((IChatComponent)new CCT()).appendSibling(ic);
        return this;
    }
    
    public CCT append(final String s) {
        super.appendText(s);
        return this;
    }
    
    public void add() {
        if (this.canSend()) {
            this.mc.thePlayer.addChatMessage((IChatComponent)this);
        }
    }
    
    private boolean canSend() {
        return Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null;
    }
    
    public static CCT getLine() {
        return new CCT("                                               ", new EnumChatFormatting[0]).strikethrough();
    }
    
    public static ChatStyle getDefaultStyle() {
        return new ChatStyle().setBold(false).setItalic(false).setStrikethrough(false).setUnderlined(false).setColor(EnumChatFormatting.WHITE);
    }
    
    public CCT execute(final String cmd) {
        this.setChatStyle(this.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd.startsWith("/") ? cmd : ("/" + cmd))));
        return this;
    }
    
    public CCT suggest(final String text) {
        this.setChatStyle(this.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, text)));
        return this;
    }
    
    public CCT hover(final String text) {
        return this.hover((IChatComponent)new CCT(text, new EnumChatFormatting[0]));
    }
    
    public CCT hover(final IChatComponent c) {
        this.setChatStyle(this.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, c)));
        return this;
    }
}
