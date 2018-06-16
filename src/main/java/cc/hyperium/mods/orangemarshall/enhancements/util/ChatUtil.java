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
            ChatUtil.mc.field_71439_g.func_145747_a(showtag ? getTag().func_150257_a(c) : c);
        }
    }
    
    public static void errorMessage(final String str) {
        final IChatComponent msg = (IChatComponent)new CCT(str, new EnumChatFormatting[0]).red();
        if (canSend()) {
            ChatUtil.mc.field_71439_g.func_145747_a(msg);
        }
    }
    
    public static void addMessage(final String s) {
        addMessage(s, false);
    }
    
    public static void sendCommandServer(final String cmd) {
        if (canSend()) {
            ChatUtil.mc.field_71439_g.func_71165_d(((cmd.charAt(0) == '/') ? "" : "/") + cmd);
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
        return ChatUtil.tag.func_150259_f();
    }
    
    public static void setTag(final IChatComponent c) {
        ChatUtil.tag = c;
    }
    
    public static void addMessage(final IChatComponent c, final boolean showtag) {
        if (canSend()) {
            ChatUtil.mc.field_71439_g.func_145747_a(showtag ? getTag().func_150257_a(c) : c);
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
                    ChatUtil.mc.field_71439_g.func_145747_a((IChatComponent)(showtag ? ChatUtil.getTag().func_150257_a((IChatComponent)c) : c));
                }
            }
        }, ms);
    }
    
    public static void sendMessage(final String s) {
        if (canSend()) {
            ChatUtil.mc.field_71439_g.func_71165_d(s);
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
        return Minecraft.func_71410_x() != null && Minecraft.func_71410_x().field_71439_g != null;
    }
    
    static {
        ChatUtil.tag = (IChatComponent)new CCT("[E]: ", new EnumChatFormatting[0]).red();
        ChatUtil.mc = Minecraft.func_71410_x();
    }
}
