package cc.hyperium.mods.orangemarshall.enhancements.commands;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import java.util.function.*;
import java.util.stream.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandListPlayers implements ICommand
{
    private final List aliases;
    
    public CommandListPlayers() {
        (this.aliases = new ArrayList()).add("listplayers");
        this.aliases.add("listplayer");
    }
    
    public String getCommandName() {
        return "listplayers";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "listplayers";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        String playerNamesString = EnumChatFormatting.GRAY.toString();
        final List<String> playerNames = (List<String>)Minecraft.getMinecraft().theWorld.playerEntities.stream().map(EntityPlayer::getName).collect(Collectors.toList());
        playerNamesString += StringUtil.join(playerNames, ", ");
        ChatUtil.addMessageWithoutTag(EnumChatFormatting.GOLD + "[" + EnumChatFormatting.GREEN + playerNames.size() + EnumChatFormatting.GOLD + "] " + playerNamesString);
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
