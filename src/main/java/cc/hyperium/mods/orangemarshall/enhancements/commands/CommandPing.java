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
    
    public String func_71517_b() {
        return "ping";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/ping or /ping <name>";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        final String name = (args.length == 1) ? args[0] : PlayerDetails.getOwnName();
        NetworkInfo.getInstance().printPing(name);
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public List func_180525_a(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return TabCompletionUtil.getListOfStringsMatchingLastWord(args, TabCompletionUtil.getTabUsernames());
    }
    
    public boolean func_82358_a(final String[] args, final int index) {
        return false;
    }
    
    public int compareTo(final ICommand arg0) {
        return 0;
    }
}
