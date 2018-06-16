package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.components.*;
import net.minecraft.client.gui.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandEnconfig implements ICommand
{
    private final List aliases;
    
    public CommandEnconfig() {
        (this.aliases = new ArrayList()).add("enconfig");
        this.aliases.add("veconfig");
        this.aliases.add("econfig");
    }
    
    public String func_71517_b() {
        return "enconfig";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "enconfig";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        new DelayedTask(2, () -> Minecraft.func_71410_x().func_147108_a((GuiScreen)new GuiConfigList()));
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
