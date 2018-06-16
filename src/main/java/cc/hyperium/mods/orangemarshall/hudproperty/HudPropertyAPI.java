package cc.hyperium.mods.orangemarshall.hudproperty;

import cc.hyperium.mods.orangemarshall.hudproperty.util.*;
import cc.hyperium.mods.orangemarshall.hudproperty.base.*;
import cc.hyperium.mods.orangemarshall.hudproperty.screen.*;

public final class HudPropertyAPI
{
    public static void open(final IBasicRenderer renderer) {
        new DelayedTask(() -> new BasicPropertyScreen(renderer));
    }
    
    public static void open(final IAdvancedRenderer... renderer) {
        new DelayedTask(() -> new AdvancedPropertyScreen(renderer));
    }
}
