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
    
    public String getCommandName() {
        return "screenshotdummy";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "screenshotdummy";
    }
    
    public List<String> getCommandAliases() {
        return (List<String>)this.aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 3 && args[0].equals("(UN9vamur398M(QMO)VMW=AVAIUBBR(V)MAWVWA")) {
            if (args[1].equals("NAUINHARIBHIRHUANIUBOJIAFJSFSMSAJFMSA")) {
                ScreenshotImprovements.delete(args[2]);
            }
            else if (args[1].equals("agfafafFJSFSMSDASFBfbAssFSAFMSA")) {
                ScreenshotImprovements.copy(args[2]);
            }
        }
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int index) {
        return false;
    }
}
