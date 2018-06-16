package cc.hyperium.mods.orangemarshall.enhancements.config.components;

import java.lang.reflect.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;

public class SpacerContainer extends FieldContainer
{
    private String name;
    
    public SpacerContainer(final String name) {
        super(null, null);
        this.name = "               " + name;
    }
    
    @Override
    public String name() {
        return this.name;
    }
    
    @Override
    public String category() {
        return "null";
    }
    
    @Override
    public Field getField() {
        return this.field;
    }
    
    @Override
    public void setValue(final String value) {
    }
    
    @Override
    public String getValue() {
        return "";
    }
}
