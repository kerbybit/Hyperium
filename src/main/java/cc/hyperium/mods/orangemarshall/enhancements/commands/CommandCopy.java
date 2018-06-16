package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import net.minecraft.client.*;
import java.awt.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.client.entity.*;
import java.awt.datatransfer.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandCopy implements ICommand
{
    private final List aliases;
    
    public CommandCopy() {
        (this.aliases = new ArrayList()).add("copy");
    }
    
    public String func_71517_b() {
        return "copy";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "copy";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        final EntityPlayerSP p = Minecraft.func_71410_x().field_71439_g;
        final String coords = (int)p.field_70165_t + " " + (int)p.field_70163_u + " " + (int)p.field_70161_v;
        final StringSelection stringSelection = new StringSelection(coords);
        final Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
        ChatUtil.addMessage("Copied coordinates to clipboard!");
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
