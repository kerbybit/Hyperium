package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.text.*;
import net.minecraft.command.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.http.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import java.util.*;

public class CommandName implements ICommand
{
    private final List aliases;
    private SimpleDateFormat dateFormatter;
    
    public CommandName() {
        this.dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        (this.aliases = new ArrayList()).add("name");
    }
    
    public String func_71517_b() {
        return "name";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "name <name>";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 1) {
            this.printNames(args[0]);
        }
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public List func_180525_a(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? TabCompletionUtil.getListOfStringsMatchingLastWord(args, TabCompletionUtil.getTabUsernames()) : null;
    }
    
    public boolean func_82358_a(final String[] args, final int index) {
        return false;
    }
    
    public int compareTo(final ICommand arg0) {
        return 0;
    }
    
    private void printNames(final String name) {
        UUID uuid;
        List<Pair<Long, String>> names;
        new Thread(() -> {
            try {
                uuid = new UuidRequest(name).requestUser().getUuid();
                names = new UsernameRequest(uuid).requestNames();
                ChatUtil.getLine().print();
                CCT.newComponent(this.namelistToFormattedString(names)).print();
                ChatUtil.getLine().print();
            }
            catch (Exception e) {
                ChatUtil.addMessage(EnumChatFormatting.RED + "Could not find name " + name + "!");
            }
        }).start();
    }
    
    private String namelistToFormattedString(final List<Pair<Long, String>> names) {
        if (names.size() == 1) {
            return "  " + EnumChatFormatting.GOLD.toString() + EnumChatFormatting.BOLD + names.get(0).second;
        }
        final Pair<Long, String> originalName = names.remove(0);
        final String first = "  " + EnumChatFormatting.GOLD.toString() + EnumChatFormatting.BOLD + originalName.second + EnumChatFormatting.GOLD + " (Original name)\n";
        final StringBuilder builder = new StringBuilder(first);
        while (names.size() > 1) {
            final Pair<Long, String> entry = names.remove(0);
            builder.append("  " + EnumChatFormatting.GOLD + " - " + entry.second + " (" + this.formatTimestamp(entry.first) + ")\n");
        }
        final Pair<Long, String> entry = names.remove(0);
        builder.append("  " + EnumChatFormatting.GOLD + " - " + entry.second + " (" + this.formatTimestamp(entry.first) + ")");
        return builder.toString();
    }
    
    private String formatTimestamp(final long timestamp) {
        final Date resultdate = new Date(timestamp);
        return this.dateFormatter.format(resultdate);
    }
}
