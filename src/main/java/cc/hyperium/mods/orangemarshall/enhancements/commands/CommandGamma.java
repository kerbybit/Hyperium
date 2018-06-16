package cc.hyperium.mods.orangemarshall.enhancements.commands;

import cc.hyperium.commands.BaseCommand;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class CommandGamma implements BaseCommand {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/gamma <gamma>";
    }

    @Override
    public void onExecute(String[] args) throws cc.hyperium.commands.CommandException {
        final GameSettings gs = Minecraft.getMinecraft().gameSettings;
        final float old = gs.gammaSetting;
        if (gs.gammaSetting < 1.1) {
            gs.gammaSetting = 100.0f;
        }
        else {
            gs.gammaSetting = 1.0f;
        }
        ChatUtil.addMessage("Changed Gamma settings from " + old + " to " + gs.gammaSetting + "!");
    }
}
