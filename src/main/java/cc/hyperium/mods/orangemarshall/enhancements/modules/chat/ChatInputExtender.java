package cc.hyperium.mods.orangemarshall.enhancements.modules.chat;

import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import cc.hyperium.mods.orangemarshall.enhancements.*;
import net.minecraft.client.gui.*;

public class ChatInputExtender
{
    private String[] whitelisted;
    private static final FieldWrapper<String> defaultText;
    
    public ChatInputExtender() {
        this.whitelisted = new String[] { "hypixel.net" };
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onOpenChat(final GuiOpenEvent event) {
        if (event.gui instanceof GuiChat && this.doesServerAllow()) {
            final String defaultText = ChatInputExtender.defaultText.get(event.gui);
            event.gui = (GuiScreen)new GuiChatExtended((defaultText != null) ? defaultText : "");
        }
    }
    
    private boolean doesServerAllow() {
        final Minecraft mc = Minecraft.func_71410_x();
        final NetworkManager netManager = mc.func_147114_u().func_147298_b();
        final boolean isLocal = netManager.func_150731_c();
        return isLocal || this.isWhitelisted(mc.func_147104_D().field_78845_b);
    }
    
    private boolean isWhitelisted(final String ip) {
        for (final String server : this.whitelisted) {
            if (ip.endsWith(server)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        defaultText = new FieldWrapper<String>(Enhancements.isObfuscated ? "field_146409_v" : "defaultInputFieldText", GuiChat.class);
    }
}
