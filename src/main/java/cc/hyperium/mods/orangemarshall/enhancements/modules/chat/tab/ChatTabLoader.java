package cc.hyperium.mods.orangemarshall.enhancements.modules.chat.tab;

import cc.hyperium.mods.orangemarshall.enhancements.modules.f4.*;
import org.apache.commons.io.*;
import java.io.*;
import cc.hyperium.mods.orangemarshall.enhancements.other.*;
import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;

public class ChatTabLoader
{
    private static final String FILE_NAME = "enhancements_chat_tabs.txt";
    private static final File FILE;
    
    private static void copyIfNotExistant(final File file, final String resourcename) {
        try {
            if (!file.exists()) {
                FileUtils.copyURLToFile(F4Parser.class.getClassLoader().getResource(resourcename), file);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void load(final BetterChatWithTabs chat) {
        try {
            final ChatTabParser parser = new ChatTabParser(new FileInputStream(ChatTabLoader.FILE));
            parser.parse();
            loadTabs(parser.getMap(), chat);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static void loadTabs(final ChatTabConditionMap map, final BetterChatWithTabs chat) {
        for (final Map.Entry<String, List<Conditions>> entry : map.getEntries()) {
            chat.createAndAddChatTab(entry.getKey(), entry.getValue());
        }
    }
    
    static {
        copyIfNotExistant(FILE = new File(Config.instance().getConfigFile().getParent(), "enhancements_chat_tabs.txt"), "enhancements_chat_tabs.txt");
    }
}
