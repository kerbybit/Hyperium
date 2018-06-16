package cc.hyperium.mods.orangemarshall.enhancements.commands;

import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import java.util.*;
import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandSens implements ICommand
{
    private final List aliases;
    private FieldWrapper[] profile;
    
    public CommandSens() {
        this.profile = new FieldWrapper[] { null, new FieldWrapper("sensProfile1", Config.class), new FieldWrapper("sensProfile2", Config.class), new FieldWrapper("sensProfile3", Config.class) };
        (this.aliases = new ArrayList()).add("sens");
        this.aliases.add("sensitivity");
    }
    
    public String func_71517_b() {
        return "sens";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "sens <profile:value>";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length > 0) {
            try {
                int sens = Math.max(Integer.valueOf(args[0]), 0);
                float sensf = sens / 200.0f;
                if (sens == 0) {
                    sensf = 0.5f;
                }
                else if (sens == 1 || sens == 2 || sens == 3) {
                    sens = this.profile[sens].get(Config.instance());
                    sensf = sens / 200.0f;
                }
                Minecraft.func_71410_x().field_71474_y.field_74341_c = sensf;
                Minecraft.func_71410_x().field_71474_y.func_74303_b();
            }
            catch (NumberFormatException e) {
                ChatUtil.addMessage("Invalid argument!");
            }
        }
        else {
            ChatUtil.addMessage("Missing argument!");
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
