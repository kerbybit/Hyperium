package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.client.settings.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandGamma implements ICommand
{
    private final List aliases;
    
    public CommandGamma() {
        (this.aliases = new ArrayList()).add("gamma");
    }
    
    public String func_71517_b() {
        return "gamma";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "gamma";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        final GameSettings gs = Minecraft.func_71410_x().field_71474_y;
        final float old = gs.field_74333_Y;
        if (gs.field_74333_Y < 1.1) {
            gs.field_74333_Y = 100.0f;
        }
        else {
            gs.field_74333_Y = 1.0f;
        }
        ChatUtil.addMessage("Changed Gamma settings from " + old + " to " + gs.field_74333_Y + "!");
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
