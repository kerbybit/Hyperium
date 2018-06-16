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
    
    public void initGui() {
        super.initGui();
        this.inputField.setMaxStringLength(256);
        this.inputField.setText(this.defaultInputTextField);
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 28 || keyCode == 156) {
            final String text = this.inputField.getText().trim();
            if (text.length() > 0) {
                this.mc.ingameGUI.getChatGUI().addToSentMessages(text);
                if (ClientCommandHandler.instance.executeCommand((ICommandSender)this.mc.thePlayer, text) != 0) {
                    this.mc.displayGuiScreen((GuiScreen)null);
                    return;
                }
                final C01PacketChatMessage packet = new C01PacketChatMessage(text);
                GuiChatExtended.message.set(packet, text);
                this.mc.thePlayer.sendQueue.addToSendQueue((Packet)packet);
            }
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else {
            super.keyTyped(typedChar, keyCode);
        }
    }
    
    static {
        message = new FieldWrapper<String>(Enhancements.isObfuscated ? "message" : "message", C01PacketChatMessage.class);
    }
}
