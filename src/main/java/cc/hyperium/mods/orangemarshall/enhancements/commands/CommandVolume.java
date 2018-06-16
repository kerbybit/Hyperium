package cc.hyperium.mods.orangemarshall.enhancements.commands;

import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.client.*;
import net.minecraft.command.*;
import net.minecraft.client.audio.*;
import java.util.*;
import net.minecraft.util.*;

public class CommandVolume implements ICommand
{
    private final List aliases;
    private FieldWrapper[] profile;
    
    public CommandVolume() {
        this.profile = new FieldWrapper[] { null, new FieldWrapper("volumeProfile1", Config.class), new FieldWrapper("volumeProfile2", Config.class), new FieldWrapper("volumeProfile3", Config.class) };
        (this.aliases = new ArrayList()).add("volume");
    }
    
    public String func_71517_b() {
        return "volume";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "volume <profile>";
    }
    
    public List func_71514_a() {
        return this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 1) {
            try {
                final int profile = Integer.valueOf(args[0]);
                if (profile == 0) {
                    this.setSoundSettings(new Integer[] { 100, 100, 100, 100, 100, 100, 100, 100, 100 });
                }
                else if (profile == 1 || profile == 2 || profile == 3) {
                    final String values = this.profile[profile].get(Config.instance());
                    this.setSoundSettings(values);
                }
            }
            catch (NumberFormatException e) {
                ChatUtil.addMessage("Invalid arguments!");
            }
            Minecraft.func_71410_x().field_71474_y.func_74303_b();
        }
        else {
            ChatUtil.addMessage("Invalid arguments!");
        }
    }
    
    private void setSoundSettings(final Integer[] values) {
        final Minecraft mc = Minecraft.func_71410_x();
        final SoundCategory[] cat = SoundCategory.values();
        for (int i = 0; i < values.length && i < cat.length; ++i) {
            mc.field_71474_y.func_151439_a(cat[i], values[i] / 100.0f);
        }
    }
    
    private void setSoundSettings(final Collection<Integer> values) {
        this.setSoundSettings(values.toArray(new Integer[values.size()]));
    }
    
    private void setSoundSettings(final String values) {
        if (values == null || !values.contains("-")) {
            ChatUtil.addMessage("Invalid argument!");
        }
        final String[] s = values.split("-");
        final ArrayList<Integer> list = new ArrayList<Integer>();
        for (final String string : s) {
            try {
                list.add(Integer.valueOf(string));
            }
            catch (NumberFormatException ex) {}
        }
        this.setSoundSettings(list);
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
