package cc.hyperium.mods.orangemarshall.enhancements.util.chat;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.event.*;

public class CCT extends ChatComponentText
{
    CCT(final String msg) {
        super(msg);
    }
    
    public static CCT newComponent(String text) {
        text = ChatUtil.convertColors(text);
        return new TextComponentCreator(text).createCCT();
    }
    
    public static CCT newComponent() {
        return new CCT("");
    }
    
    public CCT url() {
        return this.url(this.getUnformattedText());
    }
    
    public CCT url(final String url) {
        this.setChatStyle(this.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)));
        this.hover((IChatComponent)new CCT("Click to visit ").gray().appendSiblingKeepStyle((IChatComponent)new CCT(this.getUnformattedText()).aqua().underline()));
        return this;
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
    
    public CCT grey() {
        return this.gray();
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
    
    public CCT format(final EnumChatFormatting... formats) {
        for (final EnumChatFormatting format : formats) {
            switch (format) {
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
                case DARK_AQUA:
                case DARK_GREEN:
                case DARK_PURPLE:
                case DARK_RED:
                case DARK_GRAY:
                case GOLD:
                case AQUA:
                case BLUE:
                case GREEN:
                case GRAY:
                case RED:
                case LIGHT_PURPLE:
                case WHITE:
                case YELLOW: {
                    this.setChatStyle(this.getChatStyle().setColor(format));
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Could not process formatting: " + format);
                }
            }
        }
        return this;
    }
    
    public CCT appendSiblingKeepStyle(final IChatComponent ic) {
        this.siblings.add(ic);
        return this;
    }
    
    public CCT print() {
        if (ChatUtil.canSendMessage()) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)this);
        }
        return this;
    }
    
    public CCT execute(final String cmd) {
        this.setChatStyle(this.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd.startsWith("/") ? cmd : ("/" + cmd))));
        return this;
    }
    
    public CCT suggestCommand(final String text) {
        this.setChatStyle(this.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, text)));
        return this;
    }
    
    public CCT hover(final String text) {
        return this.hover((IChatComponent)new CCT(text));
    }
    
    public CCT hover(final IChatComponent c) {
        this.setChatStyle(this.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, c)));
        return this;
    }
}
