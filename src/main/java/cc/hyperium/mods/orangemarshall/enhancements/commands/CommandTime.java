package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import java.text.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandTime implements ICommand
{
    private final List aliases;
    
    public CommandTime() {
        (this.aliases = new ArrayList()).add("time");
    }
    
    public String getCommandName() {
        return "time";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "time";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 0) {
            final Calendar cal = Calendar.getInstance();
            final SimpleDateFormat sdf = new SimpleDateFormat("EE, dd/MM/yyyy @ HH:mm:ss");
            final String timeFormatted = sdf.format(cal.getTime());
            ChatUtil.addMessage(timeFormatted);
        }
        else {
            ChatUtil.sendServerCommand("time", args);
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
