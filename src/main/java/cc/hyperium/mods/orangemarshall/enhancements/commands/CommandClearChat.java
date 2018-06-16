package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandClearChat implements ICommand
{
    private final List aliases;
    
    public CommandClearChat() {
        (this.aliases = new ArrayList()).add("clearchat");
        this.aliases.add("chatclear");
    }
    
    public String func_71517_b() {
        return "clearchat";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "clearchat";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146231_a();
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
