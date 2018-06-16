package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import java.awt.*;
import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import java.io.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandLogs implements ICommand
{
    private final List aliases;
    
    public CommandLogs() {
        (this.aliases = new ArrayList()).add("logs");
        this.aliases.add("log");
    }
    
    public String getCommandName() {
        return "logs";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "logs";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        try {
            Desktop.getDesktop().open(new File(Minecraft.getMinecraft().mcDataDir, "logs"));
            ChatUtil.addMessage("You are probably searching for \"latest.log\" and \"fml-client-latest.log\" :)");
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
