package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.client.entity.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandCoords implements ICommand
{
    private final List aliases;
    
    public CommandCoords() {
        (this.aliases = new ArrayList()).add("coords");
        this.aliases.add("pos");
    }
    
    public String func_71517_b() {
        return "coords";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "coords";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        final EntityPlayerSP p = Minecraft.func_71410_x().field_71439_g;
        final String coords = "X:" + (int)p.field_70165_t + " Y:" + (int)p.field_70163_u + " Z:" + (int)p.field_70161_v;
        ChatUtil.sendMessage(coords);
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
