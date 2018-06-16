package cc.hyperium.mods.orangemarshall.enhancements.modules;

import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.util.*;
import java.awt.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import cc.hyperium.mods.orangemarshall.enhancements.*;
import java.io.*;

public class Notepad
{
    private static FileHelper filehelper;
    private static final int ENTRIES_PER_PAGE = 5;
    private static final String FILENAME = "enhancements_notepad.txt";
    
    public static void newLine(final String... content) {
        if (Notepad.filehelper.append(content)) {
            ChatUtil.addMessage("Successfully added note #" + (Notepad.filehelper.getLines() - 1) + "!");
        }
        else {
            err();
        }
    }
    
    public static void readPage(int page) {
        final int lines = Notepad.filehelper.getLines();
        final int pages = lines / 5 + ((lines % 5 != 0) ? 1 : 0);
        page = Math.max(page, 1);
        page = Math.min(page, pages);
        final String response = Notepad.filehelper.getLines(5 * (page - 1) + 1, 5 * page);
        if (response == null) {
            err();
        }
        else {
            ChatUtil.addMessageWithoutTag("");
            ChatUtil.printCentered(EnumChatFormatting.GOLD + "Notepad (Page " + page + "/" + pages + ")");
            if (response.isEmpty()) {
                ChatUtil.addMessageWithoutTag(EnumChatFormatting.GRAY + " No entries");
            }
            else {
                ChatUtil.addMessageWithoutTag(EnumChatFormatting.GRAY + response);
            }
            ChatUtil.addMessageWithoutTag("");
        }
    }
    
    public static void removeLines(final int... lines) {
        if (Notepad.filehelper.deleteLines(lines)) {
            ChatUtil.addMessage("Successfully deleted note!");
        }
        else {
            err();
        }
    }
    
    public static void printHelp() {
        final String[] info = { EnumChatFormatting.GRAY + " /note <page> " + EnumChatFormatting.DARK_GRAY + "(Shows page)", EnumChatFormatting.GRAY + " /note add <content> " + EnumChatFormatting.DARK_GRAY + "(Adds note)", EnumChatFormatting.GRAY + " /note help " + EnumChatFormatting.DARK_GRAY + "(Shows help)", EnumChatFormatting.GRAY + " /note open " + EnumChatFormatting.DARK_GRAY + "(Opens the file)", EnumChatFormatting.GRAY + " /note remove | delete <line> " + EnumChatFormatting.DARK_GRAY + "(Removes note)", EnumChatFormatting.GRAY + " /note removeall | deleteall | clear | clearall " + EnumChatFormatting.DARK_GRAY + "(Clears all notes)" };
        ChatUtil.printList(info, "- Notepad Usage - ");
    }
    
    public static void openFile() {
        try {
            Desktop.getDesktop().open(Notepad.filehelper.getFile());
            ChatUtil.addMessage("You are searching for \"enhancements_notepad.txt!");
        }
        catch (IOException e) {
            err();
        }
    }
    
    public static void resetFile() {
        if (Notepad.filehelper.resetFile()) {
            ChatUtil.addMessage("Successfully cleared notepad!");
        }
        else {
            err();
        }
    }
    
    protected static void err() {
        ChatUtil.addMessage(EnumChatFormatting.RED + "There was an error, please check the logs!");
        System.out.println("There was an error!");
        new Throwable().printStackTrace();
    }
    
    static {
        String path = Config.instance().getConfigFile().getParentFile().getAbsolutePath();
        if (!Enhancements.isObfuscated) {
            path = path.replace("\\.\\", "\\");
        }
        Notepad.filehelper = new FileHelper(new File(Config.instance().getConfigFile().getParentFile().getAbsolutePath(), "enhancements_notepad.txt"));
    }
}
