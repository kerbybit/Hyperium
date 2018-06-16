package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandResetCS implements ICommand
{
    private final List aliases;
    
    public CommandResetCS() {
        (this.aliases = new ArrayList()).add("resetcs");
    }
    
    public String getCommandName() {
        return "resetcs";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "/resetcs";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        ClickTracker.instance().reset();
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
