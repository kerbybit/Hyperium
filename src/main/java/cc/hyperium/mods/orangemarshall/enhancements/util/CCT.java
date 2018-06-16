package cc.hyperium.mods.orangemarshall.enhancements.util;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.event.*;

public class CCT extends ChatComponentText
{
    private Minecraft mc;
    
    public CCT(final String msg, final EnumChatFormatting... format) {
        super(msg);
        this.func_150255_a(getDefaultStyle());
        this.format(format);
        this.mc = Minecraft.func_71410_x();
    }
    
    public CCT() {
        this("", new EnumChatFormatting[0]);
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
                    this.func_150255_a(this.func_150256_b().func_150238_a(format[i]));
                    break;
                }
            }
        }
    }
    
    public CCT add(final IChatComponent ic) {
        super.func_150257_a((IChatComponent)new CCT()).func_150257_a(ic);
        return this;
    }
    
    public CCT append(final String s) {
        super.func_150258_a(s);
        return this;
    }
    
    public void add() {
        if (this.canSend()) {
            this.mc.field_71439_g.func_145747_a((IChatComponent)this);
        }
    }
    
    private boolean canSend() {
        return Minecraft.func_71410_x() != null && Minecraft.func_71410_x().field_71439_g != null;
    }
    
    public static CCT getLine() {
        return new CCT("                                               ", new EnumChatFormatting[0]).strikethrough();
    }
    
    public static ChatStyle getDefaultStyle() {
        return new ChatStyle().func_150227_a(false).func_150217_b(false).func_150225_c(false).func_150228_d(false).func_150238_a(EnumChatFormatting.WHITE);
    }
    
    public CCT execute(final String cmd) {
        this.func_150255_a(this.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd.startsWith("/") ? cmd : ("/" + cmd))));
        return this;
    }
    
    public CCT suggest(final String text) {
        this.func_150255_a(this.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, text)));
        return this;
    }
    
    public CCT hover(final String text) {
        return this.hover((IChatComponent)new CCT(text, new EnumChatFormatting[0]));
    }
    
    public CCT hover(final IChatComponent c) {
        this.func_150255_a(this.func_150256_b().func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, c)));
        return this;
    }
}
