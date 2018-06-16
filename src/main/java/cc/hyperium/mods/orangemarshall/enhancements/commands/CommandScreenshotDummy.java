package cc.hyperium.mods.orangemarshall.enhancements.commands;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandScreenshotDummy implements ICommand
{
    private final List aliases;
    
    public CommandScreenshotDummy() {
        (this.aliases = new ArrayList()).add("coords");
        this.aliases.add("pos");
    }
    
    public int compareTo(final ICommand o) {
        return 0;
    }
    
    public String func_71517_b() {
        return "screenshotdummy";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "screenshotdummy";
    }
    
    public List<String> func_71514_a() {
        return (List<String>)this.aliases;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 3 && args[0].equals("(UN9vamur398M(QMO)VMW=AVAIUBBR(V)MAWVWA")) {
            if (args[1].equals("NAUINHARIBHIRHUANIUBOJIAFJSFSMSAJFMSA")) {
                ScreenshotImprovements.delete(args[2]);
            }
            else if (args[1].equals("agfafafFJSFSMSDASFBfbAssFSAFMSA")) {
                ScreenshotImprovements.copy(args[2]);
            }
        }
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public List<String> func_180525_a(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return null;
    }
    
    public boolean func_82358_a(final String[] args, final int index) {
        return false;
    }
}
