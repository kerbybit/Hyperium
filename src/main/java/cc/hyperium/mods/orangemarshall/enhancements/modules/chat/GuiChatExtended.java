package cc.hyperium.mods.orangemarshall.enhancements.modules.chat;

import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import net.minecraftforge.client.*;
import net.minecraft.command.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.io.*;
import cc.hyperium.mods.orangemarshall.enhancements.*;

public class GuiChatExtended extends GuiChat
{
    private String defaultInputTextField;
    private static final FieldWrapper<String> message;
    
    public GuiChatExtended() {
        this.defaultInputTextField = "";
    }
    
    public GuiChatExtended(final String defaultText) {
        this.defaultInputTextField = "";
        this.defaultInputTextField = defaultText;
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146415_a.func_146203_f(256);
        this.field_146415_a.func_146180_a(this.defaultInputTextField);
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 28 || keyCode == 156) {
            final String text = this.field_146415_a.func_146179_b().trim();
            if (text.length() > 0) {
                this.field_146297_k.field_71456_v.func_146158_b().func_146239_a(text);
                if (ClientCommandHandler.instance.func_71556_a((ICommandSender)this.field_146297_k.field_71439_g, text) != 0) {
                    this.field_146297_k.func_147108_a((GuiScreen)null);
                    return;
                }
                final C01PacketChatMessage packet = new C01PacketChatMessage(text);
                GuiChatExtended.message.set(packet, text);
                this.field_146297_k.field_71439_g.field_71174_a.func_147297_a((Packet)packet);
            }
            this.field_146297_k.func_147108_a((GuiScreen)null);
        }
        else {
            super.func_73869_a(typedChar, keyCode);
        }
    }
    
    static {
        message = new FieldWrapper<String>(Enhancements.isObfuscated ? "field_149440_a" : "message", C01PacketChatMessage.class);
    }
}
