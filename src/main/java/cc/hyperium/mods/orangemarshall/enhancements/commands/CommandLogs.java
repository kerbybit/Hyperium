package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import java.awt.*;
import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import java.io.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandLogs implements ICommand
{
    private final List aliases;
    
    public CommandLogs() {
        (this.aliases = new ArrayList()).add("logs");
        this.aliases.add("log");
    }
    
    public String func_71517_b() {
        return "logs";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "logs";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        try {
            Desktop.getDesktop().open(new File(Minecraft.func_71410_x().field_71412_D, "logs"));
            ChatUtil.addMessage("You are probably searching for \"latest.log\" and \"fml-client-latest.log\" :)");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
