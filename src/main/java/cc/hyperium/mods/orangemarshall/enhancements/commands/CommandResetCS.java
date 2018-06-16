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
    
    public String func_71517_b() {
        return "resetcs";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/resetcs";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        ClickTracker.instance().reset();
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public List func_180525_a(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return null;
    }
    
    public boolean func_82358_a(final String[] args, final int index) {
        return false;
    }
    
    public int compareTo(final ICommand arg0) {
        return 0;
    }
}
