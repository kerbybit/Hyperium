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
    
    public String getCommandName() {
        return "coords";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "coords";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        final EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
        final String coords = "X:" + (int)p.posX + " Y:" + (int)p.posY + " Z:" + (int)p.posZ;
        ChatUtil.sendMessage(coords);
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
