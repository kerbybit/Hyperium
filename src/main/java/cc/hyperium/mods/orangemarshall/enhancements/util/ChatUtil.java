package cc.hyperium.mods.orangemarshall.enhancements.util;

import net.minecraft.client.*;
import net.minecraft.util.*;
import java.util.*;

public class ChatUtil
{
    private static Minecraft mc;
    private static IChatComponent tag;
    
    public static void addMessage(final String s, final boolean showtag) {
        final IChatComponent c = (IChatComponent)new CCT(s, new EnumChatFormatting[0]).gold();
        if (canSend()) {
            ChatUtil.mc.thePlayer.addChatMessage(showtag ? getTag().appendSibling(c) : c);
        }
    }
    
    public static void errorMessage(final String str) {
        final IChatComponent msg = (IChatComponent)new CCT(str, new EnumChatFormatting[0]).red();
        if (canSend()) {
            ChatUtil.mc.thePlayer.addChatMessage(msg);
        }
    }
    
    public static void addMessage(final String s) {
        addMessage(s, false);
    }
    
    public static void sendCommandServer(final String cmd) {
        if (canSend()) {
            ChatUtil.mc.thePlayer.sendChatMessage(((cmd.charAt(0) == '/') ? "" : "/") + cmd);
        }
    }
    
    public static void sendCommandServer(final String cmd, final String[] args) {
        String out = cmd + " ";
        for (int i = 0; i < args.length; ++i) {
            out = out + args[i] + " ";
        }
        sendCommandServer(out);
    }
    
    public static IChatComponent getTag() {
        return ChatUtil.tag.createCopy();
    }
    
    public static void setTag(final IChatComponent c) {
        ChatUtil.tag = c;
    }
    
    public static void addMessage(final IChatComponent c, final boolean showtag) {
        if (canSend()) {
            ChatUtil.mc.thePlayer.addChatMessage(showtag ? getTag().appendSibling(c) : c);
        }
    }
    
    public static void addMessage(final IChatComponent c) {
        addMessage(c, false);
    }
    
    public static void addMessageDelayed(final ChatComponentText c, final int ms, final boolean showtag) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (canSend()) {
                    ChatUtil.mc.thePlayer.addChatMessage((IChatComponent)(showtag ? ChatUtil.getTag().appendSibling((IChatComponent)c) : c));
                }
            }
        }, ms);
    }
    
    public static void sendMessage(final String s) {
        if (canSend()) {
            ChatUtil.mc.thePlayer.sendChatMessage(s);
        }
    }
    
    public static void sendMessageDelayed(final String s, final int ms) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ChatUtil.sendMessage(s);
            }
        }, ms);
    }
    
    private static boolean canSend() {
        return Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null;
    }
    
    static {
        ChatUtil.tag = (IChatComponent)new CCT("[E]: ", new EnumChatFormatting[0]).red();
        ChatUtil.mc = Minecraft.getMinecraft();
    }
}
