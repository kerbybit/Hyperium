package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import java.text.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandTimeZone implements ICommand
{
    private final List aliases;
    
    public CommandTimeZone() {
        (this.aliases = new ArrayList()).add("timezone");
        this.aliases.add("tz");
        this.aliases.add("timezones");
    }
    
    public String func_71517_b() {
        return "timezone";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "timezone";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 1) {
            final TimeZone zone = TimeZone.getTimeZone(args[0].toUpperCase());
            final TimeZone current_zone = Calendar.getInstance().getTimeZone();
            final long time = System.currentTimeMillis();
            final Date date = new Date(time);
            final long offset = zone.getOffset(date.getTime());
            final long offset_2 = current_zone.getOffset(date.getTime());
            final int diff = (int)((offset - offset_2) / 1000L / 60L / 60L);
            String out = "";
            if (diff == 0) {
                out = zone.getID() + " has the same time as your timezone (" + current_zone.getID() + ").";
                ChatUtil.addMessageWithoutTag(out);
            }
            else {
                out = zone.getID() + " is " + Math.abs(diff) + " hours " + ((diff > 0) ? "ahead of" : "behind") + " your timezone (" + current_zone.getID() + ").";
                ChatUtil.addMessageWithoutTag(out);
                final Calendar cal_1 = Calendar.getInstance();
                final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                final String currenttime = sdf.format(cal_1.getTime());
                cal_1.add(11, diff);
                final String newtime = sdf.format(cal_1.getTime());
                out = "Your time: " + currenttime + ", " + zone.getID() + ": " + newtime;
                ChatUtil.addMessageWithoutTag(out);
            }
        }
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
