package cc.hyperium.mods.orangemarshall.enhancements.config;

import java.lang.reflect.*;
import net.minecraft.client.gui.*;

public class FieldContainer
{
    protected Field field;
    protected ConfigHelper.ConfigOpt annotation;
    protected Gui element;
    
    public FieldContainer(final Field field, final ConfigHelper.ConfigOpt annotation) {
        this.field = field;
        this.annotation = annotation;
    }
    
    public String name() {
        return this.annotation.name();
    }
    
    public String category() {
        return this.annotation.category();
    }
    
    public Field getField() {
        return this.field;
    }
    
    public void setValue(final String value) {
        try {
            if (this.field.getType().isAssignableFrom(Integer.TYPE)) {
                this.field.set(Config.instance(), Integer.valueOf(value));
            }
            else if (this.field.getType().isAssignableFrom(Boolean.TYPE)) {
                if (value.equalsIgnoreCase("true")) {
                    this.field.setBoolean(Config.instance(), true);
                }
                else if (value.equalsIgnoreCase("false")) {
                    this.field.setBoolean(Config.instance(), false);
                }
                else {
                    new Exception("Error updating " + this.annotation.name()).printStackTrace();
                }
            }
            else if (this.field.getType().isAssignableFrom(Double.TYPE)) {
                this.field.setDouble(Config.instance(), Double.parseDouble(value));
            }
            else {
                this.field.set(Config.instance(), value);
            }
            Config.instance().save();
            Config.instance().load();
        }
        catch (Exception e) {
            System.out.println("Error updating " + this.annotation.name());
            e.printStackTrace();
        }
    }
    
    public String getValue() {
        try {
            return this.field.get(Config.instance()) + "";
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return "Error";
    }
}
