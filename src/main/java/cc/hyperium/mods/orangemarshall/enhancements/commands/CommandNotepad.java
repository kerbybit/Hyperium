package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;

public class CommandNotepad implements ICommand
{
    private final List aliases;
    
    public CommandNotepad() {
        (this.aliases = new ArrayList()).add("note");
        this.aliases.add("n");
        this.aliases.add("notepad");
    }
    
    public String getCommandName() {
        return "note";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "note";
    }
    
    public List getCommandAliases() {
        return this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 0) {
            Notepad.readPage(1);
        }
        else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("removeall") || args[0].equalsIgnoreCase("deleteall") || args[0].equalsIgnoreCase("clearall") || args[0].equalsIgnoreCase("clear")) {
                Notepad.resetFile();
            }
            else if (args[0].contains("open")) {
                Notepad.openFile();
            }
            else {
                try {
                    final int page = Integer.valueOf(args[0]);
                    Notepad.readPage(page);
                }
                catch (NumberFormatException e) {
                    Notepad.printHelp();
                }
            }
        }
        else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("add")) {
                Notepad.newLine(this.ignoreFirst(args));
            }
            else {
                Label_0195: {
                    if (args.length == 2) {
                        if (!args[0].equalsIgnoreCase("remove")) {
                            if (!args[0].equalsIgnoreCase("delete")) {
                                break Label_0195;
                            }
                        }
                        try {
                            final int page = Integer.valueOf(args[1]);
                            Notepad.removeLines(page);
                        }
                        catch (NumberFormatException e) {
                            Notepad.printHelp();
                        }
                        return;
                    }
                }
                if (args[0].equalsIgnoreCase("page")) {
                    try {
                        final int page = Integer.valueOf(args[1]);
                        Notepad.readPage(page);
                    }
                    catch (NumberFormatException e) {
                        Notepad.printHelp();
                    }
                }
                else {
                    Notepad.printHelp();
                }
            }
        }
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            final String[] s = { "removeall", "deleteall", "clearall", "open", "add", "remove", "delete", "page", "help" };
            return TabCompletionUtil.getListOfStringsMatchingLastWord(args, s);
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int index) {
        return false;
    }
    
    public int compareTo(final ICommand arg0) {
        return 0;
    }
    
    protected String[] ignoreFirst(final String[] str) {
        final String[] out = new String[str.length - 1];
        for (int i = 1; i < str.length; ++i) {
            out[i - 1] = str[i];
        }
        return out;
    }
}
