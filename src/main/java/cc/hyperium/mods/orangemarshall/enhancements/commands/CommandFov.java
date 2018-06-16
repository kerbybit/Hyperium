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
    
    public String getCommandName() {
        return "fov";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "fov <profile:value>";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length > 0) {
            try {
                int fov = Integer.valueOf(args[0]);
                if (fov == 0) {
                    fov = 70;
                }
                else if (fov == 1 || fov == 2 || fov == 3) {
                    fov = this.profile[fov].get(Config.instance());
                }
                Minecraft.getMinecraft().gameSettings.fovSetting = fov;
                Minecraft.getMinecraft().gameSettings.saveOptions();
            }
            catch (NumberFormatException e) {
                ChatUtil.addMessage("Invalid argument!");
            }
        }
        else {
            ChatUtil.addMessage("Missing argument!");
        }
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int index) {
        return false;
    }
    
    public int compareTo(final ICommand arg0) {
        return 0;
    }
}
