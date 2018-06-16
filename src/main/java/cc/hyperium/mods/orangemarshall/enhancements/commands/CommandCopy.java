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
    
    public String getCommandName() {
        return "copy";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "copy";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        final EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
        final String coords = (int)p.posX + " " + (int)p.posY + " " + (int)p.posZ;
        final StringSelection stringSelection = new StringSelection(coords);
        final Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
        ChatUtil.addMessage("Copied coordinates to clipboard!");
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
