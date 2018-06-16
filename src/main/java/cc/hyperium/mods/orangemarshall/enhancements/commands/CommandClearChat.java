package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandClearChat implements ICommand
{
    private final List aliases;
    
    public CommandClearChat() {
        (this.aliases = new ArrayList()).add("clearchat");
        this.aliases.add("chatclear");
    }
    
    public String getCommandName() {
        return "clearchat";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "clearchat";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages();
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
