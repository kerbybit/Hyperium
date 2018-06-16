package cc.hyperium.mods.orangemarshall.enhancements.util;

import net.minecraft.client.settings.*;
import net.minecraftforge.fml.client.registry.*;

public class KeyBindings
{
    public static KeyBinding createAndRegister(final String name, final int keyCode, final String descr) {
        final KeyBinding keybind = new KeyBinding(name, keyCode, descr);
        ClientRegistry.registerKeyBinding(keybind);
        return keybind;
    }
}
