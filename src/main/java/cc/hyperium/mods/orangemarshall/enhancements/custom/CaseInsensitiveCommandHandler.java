package cc.hyperium.mods.orangemarshall.enhancements.custom;

import net.minecraftforge.client.*;
import net.minecraftforge.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.command.*;
import java.util.*;
import net.minecraft.util.*;

public class CaseInsensitiveCommandHandler extends ClientCommandHandler
{
    public int func_71556_a(final ICommandSender sender, String message) {
        message = message.trim();
        if (!message.startsWith("/")) {
            return 0;
        }
        message = message.substring(1);
        final String[] temp = message.split(" ");
        final String[] args = new String[temp.length - 1];
        final String commandName = temp[0].toLowerCase();
        System.arraycopy(temp, 1, args, 0, args.length);
        final Optional<Map.Entry<String, ICommand>> optional = (Optional<Map.Entry<String, ICommand>>)this.func_71555_a().entrySet().stream().filter(entry -> entry.getKey().equalsIgnoreCase(commandName)).findFirst();
        if (!optional.isPresent()) {
            return 0;
        }
        final ICommand icommand = optional.get().getValue();
        try {
            if (icommand == null) {
                return 0;
            }
            if (icommand.func_71519_b(sender)) {
                final CommandEvent event = new CommandEvent(icommand, sender, args);
                if (!MinecraftForge.EVENT_BUS.post((Event)event)) {
                    icommand.func_71515_b(sender, args);
                    return 1;
                }
                if (event.exception != null) {
                    throw event.exception;
                }
                return 0;
            }
            else {
                sender.func_145747_a((IChatComponent)this.format(EnumChatFormatting.RED, "commands.generic.permission", new Object[0]));
            }
        }
        catch (WrongUsageException wue) {
            sender.func_145747_a((IChatComponent)this.format(EnumChatFormatting.RED, "commands.generic.usage", this.format(EnumChatFormatting.RED, wue.getMessage(), wue.func_74844_a())));
        }
        catch (CommandException ce) {
            sender.func_145747_a((IChatComponent)this.format(EnumChatFormatting.RED, ce.getMessage(), ce.func_74844_a()));
        }
        catch (Throwable t) {
            sender.func_145747_a((IChatComponent)this.format(EnumChatFormatting.RED, "commands.generic.exception", new Object[0]));
            t.printStackTrace();
        }
        return -1;
    }
    
    private ChatComponentTranslation format(final EnumChatFormatting color, final String str, final Object... args) {
        final ChatComponentTranslation ret = new ChatComponentTranslation(str, args);
        ret.func_150256_b().func_150238_a(color);
        return ret;
    }
}
