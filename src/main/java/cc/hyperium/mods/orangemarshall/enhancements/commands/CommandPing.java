package cc.hyperium.mods.orangemarshall.enhancements.commands;

import cc.hyperium.mods.orangemarshall.enhancements.modules.tab.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import java.util.*;

public class CommandPing implements ICommand
{
    private final List aliases;
    
    public CommandPing() {
        (this.aliases = new ArrayList()).add("ping");
    }
    
    public String getCommandName() {
        return "ping";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "/ping or /ping <name>";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        final String name = (args.length == 1) ? args[0] : PlayerDetails.getOwnName();
        NetworkInfo.getInstance().printPing(name);
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return TabCompletionUtil.getListOfStringsMatchingLastWord(args, TabCompletionUtil.getTabUsernames());
    }
    
    public boolean isUsernameIndex(final String[] args, final int index) {
        return false;
    }
    
    public int compareTo(final ICommand arg0) {
        return 0;
    }
}
