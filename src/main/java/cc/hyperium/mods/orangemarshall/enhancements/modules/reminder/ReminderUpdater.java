package cc.hyperium.mods.orangemarshall.enhancements.modules.reminder;

import java.util.*;
import net.minecraft.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;

public class ReminderUpdater implements Runnable
{
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;
    private final String sign = "�";
    
    @Override
    public void run() {
        this.updateTime();
        final List<String> list = Reminders.INSTANCE.getReminders();
        for (int i = 0; i < list.size(); ++i) {
            if (this.processEntry(list.get(i))) {
                list.remove(i);
                Reminders.INSTANCE.getFileHelper().deleteLines(i + 1);
            }
        }
    }
    
    private void updateTime() {
        final Calendar cal = Calendar.getInstance();
        this.minute = cal.get(12);
        this.hour = cal.get(11);
        this.day = cal.get(5);
        this.month = cal.get(2) + 1;
        this.year = cal.get(1);
    }
    
    private boolean processEntry(final String entry) {
        try {
            if (entry.isEmpty()) {
                return true;
            }
            final String[] split = entry.split(" at ", 2);
            final String date = split[0];
            final String time = split[1].split(" ")[0];
            final String descr = entry.split(" - ", 2)[1];
            final int minute_test = Integer.valueOf(time.split(":")[1]);
            final int hour_test = Integer.valueOf(time.split(":")[0]);
            final int day_test = Integer.valueOf(date.split("/")[0]);
            final int month_test = Integer.valueOf(date.split("/")[1]);
            final int year_test = Integer.valueOf(date.split("/")[2]);
            if (this.hasPassed(minute_test, hour_test, day_test, month_test, year_test)) {
                ChatUtil.addMessageWithoutTag(String.format(EnumChatFormatting.GOLD + ">> %skMN%sr%s6%sl Reminder: %sr%s6%skMN%sr%s6 <<", "�", "�", "�", "�", "�", "�", "�", "�", "�"));
                ChatUtil.addMessageWithoutTag(" > " + descr);
                return true;
            }
        }
        catch (Exception e) {
            ChatUtil.addMessage("Something went wrong, please check the logs. Most likely a malformed entry.\n" + entry);
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean hasPassed(final int minute, final int hour, final int day, final int month, final int year) {
        return this.year > year || (this.year >= year && (this.month > month || (this.month >= month && (this.day > day || (this.day >= day && (this.hour > hour || (this.hour >= hour && (this.minute > minute || this.minute >= minute))))))));
    }
}
