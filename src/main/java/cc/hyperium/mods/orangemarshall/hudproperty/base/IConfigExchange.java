package cc.hyperium.mods.orangemarshall.hudproperty.base;

import cc.hyperium.mods.orangemarshall.hudproperty.util.*;

public interface IConfigExchange
{
    void save(final ScreenPosition p0);
    
    ScreenPosition load();
}
