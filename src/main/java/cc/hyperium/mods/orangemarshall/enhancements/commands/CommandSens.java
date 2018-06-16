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
    
    public String getCommandName() {
        return "sens";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "sens <profile:value>";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
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
                Minecraft.getMinecraft().gameSettings.mouseSensitivity = sensf;
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
