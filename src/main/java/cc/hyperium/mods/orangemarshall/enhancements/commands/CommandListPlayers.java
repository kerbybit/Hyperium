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
    
    public String func_71517_b() {
        return "listplayers";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "listplayers";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        String playerNamesString = EnumChatFormatting.GRAY.toString();
        final List<String> playerNames = (List<String>)Minecraft.func_71410_x().field_71441_e.field_73010_i.stream().map(EntityPlayer::func_70005_c_).collect(Collectors.toList());
        playerNamesString += StringUtil.join(playerNames, ", ");
        ChatUtil.addMessageWithoutTag(EnumChatFormatting.GOLD + "[" + EnumChatFormatting.GREEN + playerNames.size() + EnumChatFormatting.GOLD + "] " + playerNamesString);
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
