package cc.hyperium.mods.orangemarshall.enhancements.util.chat;

import java.util.regex.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import net.minecraftforge.event.*;
import java.util.*;
import java.io.*;

public class ChatUtil
{
    private static final Pattern REGEX_CHAT_MESSAGE;
    private static final Minecraft mc;
    
    public static void toggleMessage(final String module, final boolean state) {
        addMessage(module + " is now " + (state ? (EnumChatFormatting.GREEN + "ON") : (EnumChatFormatting.RED + "OFF")), true);
    }
    
    private static CCT getCommandListEntry(final String s) {
        final CCT c1 = CCT.newComponent(" - ").gold();
        final CCT c2 = CCT.newComponent(s).yellow().suggestCommand(s.substring(0, (s.indexOf(45) == -1) ? s.length() : s.indexOf(45))).hover("Use command");
        return c1.appendSiblingKeepStyle((IChatComponent)c2);
    }
    
    private static CCT getCommandListEntryWithDescription(final String cmd, final String description) {
        final CCT c1 = CCT.newComponent(" - ").gold();
        final CCT c2 = CCT.newComponent(cmd).yellow().suggestCommand(cmd.substring(0, (cmd.indexOf(45) == -1) ? cmd.length() : cmd.indexOf(45))).hover("Use command");
        final CCT c3 = CCT.newComponent(" - More Information").gray().hover(description).suggestCommand(cmd.substring(0, (cmd.indexOf(45) == -1) ? cmd.length() : cmd.indexOf(45)));
        return c1.appendSiblingKeepStyle((IChatComponent)c2).appendSiblingKeepStyle((IChatComponent)c3);
    }
    
    public static CCT getListEntry(final String s) {
        final CCT c1 = CCT.newComponent(" - ").gold();
        final CCT c2 = CCT.newComponent(s).yellow();
        return c1.appendSiblingKeepStyle((IChatComponent)c2);
    }
    
    public static void printCommandList(final String[] str) {
        printCommandList(str, "");
    }
    
    public static void printCommandList(final String[] strArray, final String header) {
        if (header.isEmpty()) {
            printHeader();
        }
        else {
            printHeader(header);
        }
        for (final String string : strArray) {
            getCommandListEntry(string).print();
        }
        printSuffix();
    }
    
    public static void printList(final String[] strArray, final String header) {
        if (header.isEmpty()) {
            printHeader();
        }
        else {
            printHeader(header);
        }
        for (final String string : strArray) {
            getListEntry(string).print();
        }
        printSuffix();
    }
    
    public static void printList(final String[] strArray) {
        printList(strArray, "");
    }
    
    public static void printCommandListWithDescription(final Map<String, String> mp, final String header) {
        printHeader(header);
        final Map<String, String> map = new HashMap<String, String>(mp);
        map.entrySet().stream().forEach(entry -> getCommandListEntryWithDescription(entry.getKey(), (String)entry.getValue()).print());
        printSuffix();
    }
    
    public static void printList(final Map<String, String> mp, final String header) {
        getLine().print();
        printHeader(header);
        final Map<String, String> map = new HashMap<String, String>(mp);
        map.entrySet().stream().forEach(entry -> getListEntry(entry.getKey(), (String)entry.getValue()).print());
        printSuffix();
    }
    
    private static CCT getListEntry(final String name, final String link) {
        final CCT pre = CCT.newComponent(" - ").gold();
        final CCT linkComponent = CCT.newComponent(name).yellow().hover(link);
        if (link.startsWith("http")) {
            linkComponent.url(link);
        }
        else {
            linkComponent.suggestCommand(link);
        }
        return pre.appendSiblingKeepStyle((IChatComponent)linkComponent);
    }
    
    public static CCT getLine() {
        final StringBuilder line = new StringBuilder(EnumChatFormatting.STRIKETHROUGH.toString() + EnumChatFormatting.BOLD + " ");
        final int maxWidth = ChatUtil.mc.field_71456_v.func_146158_b().func_146228_f();
        while (ChatUtil.mc.field_71466_p.func_78256_a(line.toString()) < maxWidth) {
            line.append(" ");
        }
        return CCT.newComponent(line.substring(0, line.length() - 1)).gold();
    }
    
    public static void printHeader() {
        printHeader("Foundation Guild Mod");
    }
    
    public static void printCentered(final String text) {
        final StringBuilder indentation = new StringBuilder(EnumChatFormatting.BOLD.toString());
        final int halfWidth = ChatUtil.mc.field_71456_v.func_146158_b().func_146228_f() / 2;
        while (ChatUtil.mc.field_71466_p.func_78256_a(text) / 2 + ChatUtil.mc.field_71466_p.func_78256_a(indentation.toString()) < halfWidth) {
            indentation.append(" ");
        }
        CCT.newComponent(indentation.toString() + text).print();
    }
    
    public static String convertColors(String string) {
        for (final EnumChatFormatting format : EnumChatFormatting.values()) {
            final String search = "%" + format.name().toLowerCase();
            string = string.replace(search, format.toString());
        }
        return string;
    }
    
    public static void printHeader(final String name) {
        getLine().print();
        printCentered(EnumChatFormatting.GOLD.toString() + EnumChatFormatting.BOLD + name);
    }
    
    public static void printSuffix() {
        getLine().print();
    }
    
    public static CCT getTag() {
        return CCT.newComponent("[Enhancements] ").red();
    }
    
    public static boolean isGuildChat(final String message) {
        return message.startsWith("Guild > ") || message.startsWith("G > ");
    }
    
    public static boolean isStaffChat(final String message) {
        return message.startsWith("[STAFF]") || message.startsWith("S > ");
    }
    
    public static boolean isPartyChat(final String message) {
        return message.startsWith("Party > ") || message.startsWith("P > ");
    }
    
    public static boolean isPrivateMessage(final String message) {
        return (message.startsWith("From ") || message.startsWith("To ") || message.startsWith("PM << ") || message.startsWith("PM >> ")) && message.contains(": ");
    }
    
    public static String removeRank(String inputString) {
        if (inputString.contains("[") && inputString.contains("]") && inputString.indexOf("[") < inputString.indexOf("]")) {
            inputString = inputString.substring(inputString.indexOf("]") + 1);
        }
        return (inputString.charAt(0) == ' ') ? inputString.substring(1) : inputString;
    }
    
    public static boolean isServerMessage(final String message) {
        return !isGuildChat(message) && !isPartyChat(message) && !isPrivateMessage(message) && !isStaffChat(message) && !isChatMessage(message);
    }
    
    public static boolean isQuestCompletedMessage(String message) {
        message = StringUtil.removeFormatting(message);
        return message.startsWith("\nDaily Quest: ") || message.startsWith("\nWeekly Quest: ");
    }
    
    public static boolean isBoosterQueueMessage(final String message) {
        return isServerMessage(message) && (message.contains("Network Booster Queue") || message.contains(" - Triple Coins from"));
    }
    
    public static boolean isChatMessage(final String message) {
        return message.contains(": ") && ChatUtil.REGEX_CHAT_MESSAGE.matcher(message).matches();
    }
    
    public static void addMessageCallingChatEvent(IChatComponent component) {
        if ((component = ForgeEventFactory.onClientChat((byte)0, component)) != null && canSendMessage()) {
            ChatUtil.mc.field_71439_g.func_146105_b(component);
        }
    }
    
    public static boolean canSendMessage() {
        return ChatUtil.mc.field_71439_g != null;
    }
    
    private static void addMessage(final String message, final boolean showtag) {
        final CCT component = CCT.newComponent(message);
        addMessage((IChatComponent)component, showtag);
    }
    
    private static void addMessage(final IChatComponent component, final boolean showtag) {
        if (canSendMessage()) {
            if (showtag) {
                ChatUtil.mc.field_71439_g.func_145747_a((IChatComponent)getTag().appendSiblingKeepStyle(component));
            }
            else {
                ChatUtil.mc.field_71439_g.func_145747_a(component);
            }
        }
    }
    
    public static void addMessage(final String message) {
        addMessage(message, true);
    }
    
    public static void addMessage(final IChatComponent component) {
        addMessage(component, true);
    }
    
    public static void addMessageWithoutTag(final String message) {
        addMessage(message, false);
    }
    
    public static void addMessageWithoutTag(final IChatComponent component) {
        addMessage(component, false);
    }
    
    public static void sendServerCommand(final String cmd) {
        if (canSendMessage()) {
            ChatUtil.mc.field_71439_g.func_71165_d(((cmd.charAt(0) == '/') ? "" : "/") + cmd);
        }
    }
    
    public static void sendServerCommand(final String cmd, final String[] args) {
        final String fullCommand = cmd + " " + StringUtil.joinStringWithSplitter(args, " ");
        sendServerCommand(fullCommand);
    }
    
    public static void sendMessage(final String message) {
        if (canSendMessage()) {
            ChatUtil.mc.field_71439_g.func_71165_d(message);
        }
    }
    
    public static void sendMessageDelayed(final String message, final int ms) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ChatUtil.sendMessage(message);
            }
        }, ms);
    }
    
    public static synchronized void sendMessageDelayedSync(final String message, final int ms) {
        sendMessageDelayed(message, ms);
    }
    
    static {
        REGEX_CHAT_MESSAGE = Pattern.compile("(\\[[0-9a-zA-Z \\[\\+]+\\] {0,1})*[0-9a-zA-Z_]+: .*");
        mc = Minecraft.func_71410_x();
    }
}
