package cc.hyperium.mods.orangemarshall.enhancements.modules.chat.tab;

import cc.hyperium.mods.orangemarshall.enhancements.other.*;
import com.google.common.collect.*;
import java.util.*;

public class ChatTabConditionMap
{
    private Map<String, List<Conditions>> map;
    
    public ChatTabConditionMap() {
        this.map = (Map<String, List<Conditions>>)Maps.newHashMap();
    }
    
    public void addCondition(final String name, final Conditions condition) {
        List<Conditions> list = this.map.get(name);
        if (list == null) {
            list = (List<Conditions>)Lists.newArrayList();
        }
        list.add(condition);
        this.map.put(name, list);
    }
    
    public Set<Map.Entry<String, List<Conditions>>> getEntries() {
        return this.map.entrySet();
    }
}
