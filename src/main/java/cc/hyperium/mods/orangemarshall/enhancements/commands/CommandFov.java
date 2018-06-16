package cc.hyperium.mods.orangemarshall.enhancements.commands;

import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import java.util.*;
import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandFov implements ICommand
{
    private final List aliases;
    private FieldWrapper[] profile;
    
    public CommandFov() {
        this.profile = new FieldWrapper[] { null, new FieldWrapper("fovProfile1", Config.class), new FieldWrapper("fovProfile2", Config.class), new FieldWrapper("fovProfile3", Config.class) };
        (this.aliases = new ArrayList()).add("fov");
    }
    
    public String func_71517_b() {
        return "fov";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "fov <profile:value>";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length > 0) {
            try {
                int fov = Integer.valueOf(args[0]);
                if (fov == 0) {
                    fov = 70;
                }
                else if (fov == 1 || fov == 2 || fov == 3) {
                    fov = this.profile[fov].get(Config.instance());
                }
                Minecraft.func_71410_x().field_71474_y.field_74334_X = fov;
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
