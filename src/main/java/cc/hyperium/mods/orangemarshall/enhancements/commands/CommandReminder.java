package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.reminder.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import org.apache.commons.lang3.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;

public class CommandReminder implements ICommand
{
    private final List aliases;
    
    public CommandReminder() {
        (this.aliases = new ArrayList()).add("reminder");
        this.aliases.add("remind");
        this.aliases.add("remindme");
    }
    
    public String func_71517_b() {
        return "reminder";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "reminder";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 0) {
            Reminders.INSTANCE.readPage(1);
        }
        else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("removeall") || args[0].equalsIgnoreCase("deleteall") || args[0].equalsIgnoreCase("clearall") || args[0].equalsIgnoreCase("clear")) {
                Reminders.INSTANCE.resetFile();
            }
            else {
                try {
                    final int page = Integer.valueOf(args[0]);
                    Reminders.INSTANCE.readPage(page);
                }
                catch (NumberFormatException e) {
                    new Throwable("Invalid usage").printStackTrace();
                    Reminders.INSTANCE.printHelp();
                    ChatUtil.addMessageWithoutTag("Please provide numbers to choose a page!");
                }
            }
        }
        else if (args[0].equalsIgnoreCase("add") && args.length >= 4) {
            if (StringUtils.countMatches((CharSequence)args[1], (CharSequence)"/") != 2 || StringUtils.countMatches((CharSequence)args[2], (CharSequence)":") != 1) {
                new Throwable("Invalid usage").printStackTrace();
                Reminders.INSTANCE.printHelp();
                ChatUtil.addMessageWithoutTag("Please provide a date with the following format: dd/MM/yy HH:mm");
            }
            else {
                try {
                    final String[] date = args[1].split("/");
                    final String[] time = args[2].split(":");
                    if (date[0].isEmpty() || date[1].isEmpty() || date[2].isEmpty() || time[0].isEmpty() || time[1].isEmpty() || (date[2].length() != 2 && date[2].length() != 4)) {
                        Reminders.INSTANCE.printHelp();
                        ChatUtil.addMessageWithoutTag("Please provide a date with the following format: dd/MM/yy HH:mm");
                        return;
                    }
                    int day = Integer.valueOf(date[0]);
                    day = this.max(day, 31);
                    int month = Integer.valueOf(date[1]);
                    if (month == 0) {
                        month = 1;
                    }
                    month = this.max(month, 12);
                    int year = Integer.valueOf(date[2]);
                    if (date[2].length() == 2) {
                        year += 2000;
                    }
                    int hour = Integer.valueOf(time[0]);
                    hour = this.max(hour, 24);
                    int minute = Integer.valueOf(time[1]);
                    minute %= 60;
                    final String dateStr = String.format("%02d/%02d/%d at %02d:%02d", day, month, year, hour, minute);
                    String descr = "";
                    for (int i = 3; i < args.length; ++i) {
                        descr = descr + args[i] + ((i + 1 < args.length) ? " " : "");
                    }
                    Reminders.INSTANCE.add(dateStr, descr);
                }
                catch (NumberFormatException e) {
                    new Throwable("Invalid usage").printStackTrace();
                    Reminders.INSTANCE.printHelp();
                    ChatUtil.addMessageWithoutTag("Please provide a date with the following format: dd/MM/yy HH:mm");
                }
            }
        }
        else if (args[0].equalsIgnoreCase("in") && args.length >= 3) {
            try {
                final String[] str = this.ignoreFirst(args);
                String descrStr = "";
                boolean descr2 = false;
                int dayOff;
                int minOff;
                int hourOff = minOff = (dayOff = 0);
                for (final String s : str) {
                    if (!descr2 && s.matches("^[0-9]*[d,D,m,M,h,H]$")) {
                        if (s.endsWith("d") || s.endsWith("D")) {
                            dayOff += Integer.valueOf(s.substring(0, s.length() - 1));
                        }
                        else if (s.endsWith("m") || s.endsWith("M")) {
                            minOff += Integer.valueOf(s.substring(0, s.length() - 1));
                        }
                        else if (s.endsWith("h") || s.endsWith("H")) {
                            hourOff += Integer.valueOf(s.substring(0, s.length() - 1));
                        }
                    }
                    else {
                        descr2 = true;
                        descrStr = descrStr + s + " ";
                    }
                }
                if (!descrStr.isEmpty()) {
                    descrStr = descrStr.substring(0, descrStr.length() - 1);
                }
                Reminders.INSTANCE.addIn(minOff, hourOff, dayOff, descrStr);
            }
            catch (NumberFormatException e) {
                new Throwable("Invalid usage").printStackTrace();
                Reminders.INSTANCE.printHelp();
                ChatUtil.addMessageWithoutTag("Please provide numbers to choose a page!");
            }
        }
        else {
            Label_0956: {
                if (args.length == 2) {
                    if (!args[0].equalsIgnoreCase("remove")) {
                        if (!args[0].equalsIgnoreCase("delete")) {
                            break Label_0956;
                        }
                    }
                    try {
                        final int page = Integer.valueOf(args[1]);
                        Reminders.INSTANCE.remove(page);
                    }
                    catch (NumberFormatException e) {
                        new Throwable("Invalid usage").printStackTrace();
                        Reminders.INSTANCE.printHelp();
                        ChatUtil.addMessageWithoutTag("Please provide numbers to choose a page!");
                    }
                    return;
                }
            }
            if (args[0].equalsIgnoreCase("page")) {
                try {
                    final int page = Integer.valueOf(args[1]);
                    Reminders.INSTANCE.readPage(page);
                }
                catch (NumberFormatException e) {
                    new Throwable("Invalid usage").printStackTrace();
                    Reminders.INSTANCE.printHelp();
                    ChatUtil.addMessageWithoutTag("Please provide numbers to choose a page!");
                }
            }
            else {
                Reminders.INSTANCE.printHelp();
            }
        }
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public List func_180525_a(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            return TabCompletionUtil.getListOfStringsMatchingLastWord(args, new String[] { "add", "in", "help", "remove", "delete", "removeall", "deleteall", "clearall" });
        }
        return null;
    }
    
    public boolean func_82358_a(final String[] args, final int index) {
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
    
    protected int max(final int value, final int max) {
        if (value > max) {
            return value % max;
        }
        return value;
    }
}
