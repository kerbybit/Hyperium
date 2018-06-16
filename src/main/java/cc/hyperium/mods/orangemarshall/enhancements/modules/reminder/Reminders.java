package cc.hyperium.mods.orangemarshall.enhancements.modules.reminder;

import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import java.util.concurrent.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.util.*;
import org.apache.commons.io.*;
import java.util.*;
import java.io.*;

public enum Reminders
{
    INSTANCE;
    
    private FileHelper filehelper;
    private final int entries_per_page = 5;
    private final List<String> file_as_lines;
    private final ScheduledExecutorService updateService;
    
    private Reminders() {
        this.updateService = Executors.newSingleThreadScheduledExecutor();
        final String path = Config.instance().getConfigFile().getParentFile().getAbsolutePath().replace("\\.\\", "\\");
        final String filename = "reminders.cfg";
        this.filehelper = new FileHelper(new File(path, filename));
        this.file_as_lines = this.filehelper.getLinesAsList();
        this.updateService.scheduleWithFixedDelay(new ReminderUpdater(), 30L, 30L, TimeUnit.SECONDS);
    }
    
    public void addIn(final int minOff, final int hourOff, final int dayOff, final String description) {
        final Calendar cal = Calendar.getInstance();
        int minute = cal.get(12) + minOff;
        int hour = cal.get(11) + hourOff;
        int day = cal.get(5) + dayOff;
        int month = cal.get(2) + 1;
        final int year = cal.get(1);
        hour += minute / 60;
        minute %= 60;
        if (hour > 24) {
            day += hour / 24;
            hour %= 24;
        }
        if (day > 31) {
            month += day / 31;
            day %= 31;
        }
        if (month > 12) {
            month %= 12;
        }
        final String dateStr = String.format("%02d/%02d/%d at %02d:%02d", day, month, year, hour, minute);
        this.add(dateStr, description);
    }
    
    public void add(final String date, final String description) {
        final String reminder = date + " - " + description;
        if (this.filehelper.append(reminder)) {
            ChatUtil.addMessage("Successfully added reminder \"" + description + "\"!");
        }
        else {
            this.err();
        }
        this.file_as_lines.add(reminder);
    }
    
    public void readPage(int page) {
        final int lines = this.filehelper.getLines();
        final int pages = lines / 5 + ((lines % 5 != 0) ? 1 : 0);
        page = Math.max(page, 1);
        page = Math.min(page, pages);
        final String response = this.filehelper.getLines(5 * (page - 1) + 1, 5 * page);
        if (response == null) {
            this.err();
        }
        else {
            ChatUtil.addMessageWithoutTag("");
            ChatUtil.printCentered(EnumChatFormatting.GOLD + "Reminders (Page " + page + "/" + pages + ")");
            if (response.isEmpty()) {
                ChatUtil.addMessageWithoutTag(EnumChatFormatting.GRAY + " No entries");
            }
            else {
                ChatUtil.addMessageWithoutTag(EnumChatFormatting.GRAY + response);
            }
            ChatUtil.addMessageWithoutTag("");
        }
    }
    
    public void printHelp() {
        final String[] help = { EnumChatFormatting.GRAY + " /remind <page> " + EnumChatFormatting.DARK_GRAY + "(Shows page)", EnumChatFormatting.GRAY + " /remind add <DD:MM:YYYY> <HH:MM> <description> " + EnumChatFormatting.DARK_GRAY + "(Adds reminder)", EnumChatFormatting.GRAY + " /remind in 3d 12h 10m <description> " + EnumChatFormatting.DARK_GRAY + "(Example)", EnumChatFormatting.GRAY + " /remind help " + EnumChatFormatting.DARK_GRAY + "(Shows help)", EnumChatFormatting.GRAY + " /remind remove | delete <line> " + EnumChatFormatting.DARK_GRAY + "(Removes reminder)", EnumChatFormatting.GRAY + " /remind removeall | deleteall | clear | clearall " + EnumChatFormatting.DARK_GRAY + "(Clears all notes)" };
        ChatUtil.printList(help, "Reminder Usage");
    }
    
    public void resetFile() {
        if (this.filehelper.resetFile()) {
            ChatUtil.addMessage("Successfully cleared reminders!");
        }
        else {
            this.err();
        }
        this.file_as_lines.clear();
    }
    
    public void remove(final int line) {
        if (this.filehelper.deleteLines(line)) {
            ChatUtil.addMessage("Successfully deleted reminder!");
        }
        else {
            this.err();
        }
        this.file_as_lines.remove(line);
    }
    
    private void err() {
        ChatUtil.addMessage(EnumChatFormatting.RED + "There was an error, please check the logs!");
        System.out.println("There was an error!");
        new Throwable().printStackTrace();
    }
    
    public List<String> getReminders() {
        return this.file_as_lines;
    }
    
    public FileHelper getFileHelper() {
        return this.filehelper;
    }
    
    public void updateFile() {
        try {
            FileUtils.writeLines(this.getFileHelper().getFile(), (Collection)this.getReminders(), false);
        }
        catch (IOException e) {
            ChatUtil.addMessage("Something went wrong deleting the reminder. Please check the logs.");
            e.printStackTrace();
        }
    }
}
