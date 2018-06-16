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
    
    public String getCommandName() {
        return "enconfig";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "enconfig";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        new DelayedTask(2, () -> Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiConfigList()));
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
