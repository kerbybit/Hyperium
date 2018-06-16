package cc.hyperium.mods.orangemarshall.enhancements.config;

import net.minecraftforge.common.config.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraftforge.fml.common.*;
import java.io.*;
import java.lang.reflect.*;
import org.apache.http.*;
import java.lang.annotation.*;

public class ConfigHelper
{
    private transient File configFile;
    private transient Configuration config;
    private transient String configVersion;
    
    protected ConfigHelper(final File configFile) {
        this.configFile = configFile;
        this.configVersion = null;
    }
    
    protected ConfigHelper(final File configFile, final String configVersion) {
        this.configFile = configFile;
        this.configVersion = configVersion;
    }
    
    public final ArrayList<FieldContainer> getFields() {
        final ArrayList<FieldContainer> list = new ArrayList<FieldContainer>();
        for (final Field field : this.getClass().getFields()) {
            try {
                final ConfigOpt ann = field.getAnnotation(ConfigOpt.class);
                if (ann != null && !ann.ignore()) {
                    list.add(new FieldContainer(field, ann));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(list, (f1, f2) -> f1.category().toLowerCase().compareTo(f2.category().toLowerCase()));
        return list;
    }
    
    public final void load() {
        try {
            this.config = new Configuration(this.configFile, this.configVersion);
            for (final Field field : this.getClass().getFields()) {
                this.loadField(field, this.getFieldName(field), this.config);
            }
            this.saveConfig(this.config);
        }
        catch (Exception e) {
            FMLLog.log(Level.ERROR, (Throwable)e, "Error To load the file configuration!", new Object[0]);
        }
    }
    
    public final void save() {
        try {
            final Configuration config = this.config;
            for (final Field field : this.getClass().getFields()) {
                this.saveField(field, this.getFieldName(field), config);
            }
            this.saveConfig(config);
        }
        catch (Exception e) {
            FMLLog.log(Level.ERROR, (Throwable)e, "Error To save the file configuration!", new Object[0]);
        }
    }
    
    private final String getFieldName(final Field field) {
        final ConfigOpt options = field.getAnnotation(ConfigOpt.class);
        if (options == null) {
            return field.getName();
        }
        final String name = options.name();
        if (name.equals("")) {
            return field.getName();
        }
        return name;
    }
    
    private final String getCategoryName(final Field field) {
        final ConfigOpt options = field.getAnnotation(ConfigOpt.class);
        if (options == null) {
            return "client";
        }
        return options.category();
    }
    
    private final boolean ignoreField(final Field field) {
        final ConfigOpt options = field.getAnnotation(ConfigOpt.class);
        return options != null && options.ignore();
    }
    
    private final String getComment(final Field field) {
        final ConfigOpt options = field.getAnnotation(ConfigOpt.class);
        if (options == null) {
            return null;
        }
        if (options.comment() == "") {
            return null;
        }
        return options.comment();
    }
    
    private final void saveConfig(final Configuration config) throws IOException {
        config.save();
    }
    
    private final void loadField(final Field field, final String name, final Configuration config) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException, InstantiationException {
        if (Modifier.isTransient(field.getModifiers()) || this.ignoreField(field)) {
            return;
        }
        Object obj = null;
        if (field.getType().isAssignableFrom(Integer.TYPE)) {
            obj = config.get(this.getCategoryName(field), name, field.getInt(this), this.getComment(field)).getInt();
        }
        else if (field.getType().isAssignableFrom(String.class)) {
            obj = config.get(this.getCategoryName(field), name, (String)field.get(this), this.getComment(field)).getString();
        }
        else if (field.getType().isAssignableFrom(Boolean.TYPE)) {
            obj = config.get(this.getCategoryName(field), name, field.getBoolean(this), this.getComment(field)).getBoolean();
        }
        else if (field.getType().isAssignableFrom(Double.TYPE)) {
            obj = config.get(this.getCategoryName(field), name, field.getDouble(this), this.getComment(field)).getDouble();
        }
        if (obj != null) {
            field.set(this, obj);
        }
    }
    
    private final void saveField(final Field field, final String name, final Configuration config) throws IllegalAccessException {
        if (Modifier.isTransient(field.getModifiers()) || this.ignoreField(field)) {
            return;
        }
        if (field.getType().isAssignableFrom(Integer.TYPE)) {
            config.get(this.getCategoryName(field), name, field.getInt(this), this.getComment(field)).set(field.getInt(this));
        }
        else if (field.getType().isAssignableFrom(String.class)) {
            config.get(this.getCategoryName(field), name, (String)field.get(this), this.getComment(field)).set((String)field.get(this));
        }
        else if (field.getType().isAssignableFrom(Boolean.TYPE)) {
            config.get(this.getCategoryName(field), name, field.getBoolean(this), this.getComment(field)).set(field.getBoolean(this));
        }
        else if (field.getType().isAssignableFrom(Double.TYPE)) {
            config.get(this.getCategoryName(field), name, field.getDouble(this), this.getComment(field)).set(field.getDouble(this));
        }
    }
    
    public final File getConfigFile() {
        return this.configFile;
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD })
    public @interface ConfigOpt {
        String name() default "";
        
        String category() default "client";
        
        String comment() default "";
        
        boolean ignore() default false;
    }
}
