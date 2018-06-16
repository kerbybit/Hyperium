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
        return this.url(this.func_150260_c());
    }
    
    public CCT url(final String url) {
        this.func_150255_a(this.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_URL, url)));
        this.hover((IChatComponent)new CCT("Click to visit ").gray().appendSiblingKeepStyle((IChatComponent)new CCT(this.func_150260_c()).aqua().underline()));
        return this;
    }
    
    public CCT bold() {
        this.func_150255_a(this.func_150256_b().func_150227_a(true));
        return this;
    }
    
    public CCT italic() {
        this.func_150255_a(this.func_150256_b().func_150217_b(true));
        return this;
    }
    
    public CCT underline() {
        this.func_150255_a(this.func_150256_b().func_150228_d(true));
        return this;
    }
    
    public CCT reset() {
        this.func_150255_a(new ChatStyle());
        return this;
    }
    
    public CCT strikethrough() {
        this.func_150255_a(this.func_150256_b().func_150225_c(true));
        return this;
    }
    
    public CCT gold() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.GOLD));
        return this;
    }
    
    public CCT black() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.BLACK));
        return this;
    }
    
    public CCT dark_blue() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.DARK_BLUE));
        return this;
    }
    
    public CCT dark_green() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.DARK_GREEN));
        return this;
    }
    
    public CCT dark_aqua() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.DARK_AQUA));
        return this;
    }
    
    public CCT dark_red() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.DARK_RED));
        return this;
    }
    
    public CCT dark_purple() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.DARK_PURPLE));
        return this;
    }
    
    public CCT gray() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.GRAY));
        return this;
    }
    
    public CCT grey() {
        return this.gray();
    }
    
    public CCT dark_gray() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.DARK_GRAY));
        return this;
    }
    
    public CCT blue() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.BLUE));
        return this;
    }
    
    public CCT green() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.GREEN));
        return this;
    }
    
    public CCT aqua() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.AQUA));
        return this;
    }
    
    public CCT red() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.RED));
        return this;
    }
    
    public CCT light_purple() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.LIGHT_PURPLE));
        return this;
    }
    
    public CCT yellow() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW));
        return this;
    }
    
    public CCT white() {
        this.func_150255_a(this.func_150256_b().func_150238_a(EnumChatFormatting.WHITE));
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
                    this.func_150255_a(this.func_150256_b().func_150238_a(format));
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
        this.field_150264_a.add(ic);
        return this;
    }
    
    public CCT print() {
        if (ChatUtil.canSendMessage()) {
            Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent)this);
        }
        return this;
    }
    
    public CCT execute(final String cmd) {
        this.func_150255_a(this.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd.startsWith("/") ? cmd : ("/" + cmd))));
        return this;
    }
    
    public CCT suggestCommand(final String text) {
        this.func_150255_a(this.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, text)));
        return this;
    }
    
    public CCT hover(final String text) {
        return this.hover((IChatComponent)new CCT(text));
    }
    
    public CCT hover(final IChatComponent c) {
        this.func_150255_a(this.func_150256_b().func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, c)));
        return this;
    }
}
