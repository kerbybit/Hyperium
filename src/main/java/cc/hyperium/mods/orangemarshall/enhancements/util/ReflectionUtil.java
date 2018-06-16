package cc.hyperium.mods.orangemarshall.enhancements.util;

import java.lang.reflect.*;

public class ReflectionUtil
{
    private static Field modifiersField;
    
    public static <T> void set(final String name, final T value, final Class c, final T obj) {
        try {
            final Field f = c.getDeclaredField(name);
            f.setAccessible(true);
            f.set(c.cast(obj), value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static <T> void set(final String nameObf, final String nameDeObf, final T value, final Class c, final T obj) {
        try {
            final Field f = c.getDeclaredField(nameObf);
            f.setAccessible(true);
            f.set(c.cast(obj), value);
        }
        catch (NoSuchFieldException e) {
            try {
                final Field f2 = c.getDeclaredField(nameDeObf);
                f2.setAccessible(true);
                f2.set(c.cast(obj), value);
            }
            catch (Exception ex) {
                e.printStackTrace();
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public static <T> void setFinal(final String name, final T value, final Class c, final T obj) {
        try {
            final Field f = c.getDeclaredField(name);
            f.setAccessible(true);
            ReflectionUtil.modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
            f.set(c.cast(obj), value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static <T> void setFinal(final String nameObf, final String nameDeObf, final T value, final Class c, final T obj) {
        try {
            final Field f = c.getDeclaredField(nameObf);
            f.setAccessible(true);
            ReflectionUtil.modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
            f.set(c.cast(obj), value);
        }
        catch (NoSuchFieldException e2) {
            try {
                final Field f2 = c.getDeclaredField(nameDeObf);
                f2.setAccessible(true);
                ReflectionUtil.modifiersField.setInt(f2, f2.getModifiers() & 0xFFFFFFEF);
                f2.set(c.cast(obj), value);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static <T, A> A get(final String name, final Class c, final T obj) {
        try {
            final Field f = c.getDeclaredField(name);
            f.setAccessible(true);
            return (A)f.get(obj).getClass().cast(f.get(obj));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static <T, A> A get(final String nameObf, final String nameDeObf, final Class c, final T obj) {
        try {
            final Field f = c.getDeclaredField(nameObf);
            f.setAccessible(true);
            return (A)f.get(obj).getClass().cast(f.get(obj));
        }
        catch (NoSuchFieldException e2) {
            try {
                final Field f2 = c.getDeclaredField(nameDeObf);
                f2.setAccessible(true);
                return (A)f2.get(obj).getClass().cast(f2.get(obj));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    static {
        try {
            (ReflectionUtil.modifiersField = Field.class.getDeclaredField("modifiers")).setAccessible(true);
        }
        catch (NoSuchFieldException | SecurityException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
}
