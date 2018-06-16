package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import java.awt.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import java.io.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandConfig implements ICommand
{
    private final List aliases;
    
    public CommandConfig() {
        (this.aliases = new ArrayList()).add("config");
    }
    
    public String getCommandName() {
        return "config";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "config";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        try {
            ChatUtil.addMessage("Configuration folder opened!");
            Desktop.getDesktop().open(new File(Config.instance().getConfigFile().getParent()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int index) {
        return false;
    }
    
    public int compareTo(final ICommand arg0) {
        return 0;
    }
}
