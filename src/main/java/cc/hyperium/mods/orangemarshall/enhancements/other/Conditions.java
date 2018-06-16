package cc.hyperium.mods.orangemarshall.enhancements.other;

import cc.hyperium.mods.orangemarshall.enhancements.modules.chat.tab.*;
import java.util.*;
import com.google.common.collect.*;

public class Conditions
{
    private final List<ConditionDefinition> conditions;
    
    private Conditions(final List<ConditionDefinition> conditions) {
        this.conditions = conditions;
    }
    
    public boolean meetsConditions(final String message) {
        for (final ConditionDefinition condition : this.conditions) {
            if (!condition.apply(message)) {
                return false;
            }
        }
        return true;
    }
    
    public static Conditions alwaysTrue() {
        return ConditionBuilder.newBuilder().build();
    }
    
    public static class ConditionBuilder
    {
        private final List<ConditionDefinition> conditions;
        
        public ConditionBuilder() {
            this.conditions = (List<ConditionDefinition>)Lists.newArrayList();
        }
        
        public static ConditionBuilder newBuilder() {
            return new ConditionBuilder();
        }
        
        public ConditionBuilder add(final ConditionDefinition definition) {
            this.conditions.add(definition);
            return this;
        }
        
        public Conditions build() {
            return new Conditions(this.conditions, null);
        }
    }
}
