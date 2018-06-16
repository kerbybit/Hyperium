package cc.hyperium.mods.orangemarshall.enhancements.modules.chat.tab;

import java.io.*;
import cc.hyperium.mods.orangemarshall.enhancements.other.*;

public class ChatTabParser extends Parser
{
    private ChatTabConditionMap map;
    
    public ChatTabParser(final InputStream inputStream) {
        super(inputStream);
        this.map = new ChatTabConditionMap();
    }
    
    @Override
    protected void parseLine(final String line) {
        if (line.startsWith("//") || line.isEmpty()) {
            return;
        }
        final String[] split = line.split(" : ", 2);
        this.parseConditions(split[0], split[1]);
    }
    
    private void parseConditions(final String name, final String line) {
        if (line.trim().isEmpty()) {
            throw new ParseException();
        }
        final Conditions.ConditionBuilder builder = Conditions.ConditionBuilder.newBuilder();
        final String[] split2;
        final String[] split = split2 = line.split(" : ");
        for (final String cond : split2) {
            builder.add(this.getCondition(cond));
        }
        this.map.addCondition(name, builder.build());
    }
    
    private ConditionDefinition getCondition(final String condition) {
        return ConditionDefinition.of(condition);
    }
    
    public ChatTabConditionMap getMap() {
        return this.map;
    }
}
