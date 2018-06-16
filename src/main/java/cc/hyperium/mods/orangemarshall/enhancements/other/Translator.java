package cc.hyperium.mods.orangemarshall.enhancements.other;

import com.google.common.collect.*;
import java.util.function.*;
import java.util.*;

public class Translator
{
    private Set<Entry> translations;
    
    public Translator() {
        this.translations = (Set<Entry>)Sets.newHashSet();
    }
    
    public void addTranslation(final String from, final Supplier<String> to) {
        this.translations.add(new Entry(from, to));
    }
    
    private String translate(final String input, final Entry entry) {
        return input.replace(entry.getFrom(), entry.getTo().get());
    }
    
    public String translate(String input) {
        for (final Entry entry : this.translations) {
            input = this.translate(input, entry);
        }
        return input;
    }
    
    public void translate(final List<String> input) {
        for (int i = 0; i < input.size(); ++i) {
            input.set(i, this.translate(input.get(i)));
        }
    }
    
    private static class Entry
    {
        private String from;
        private Supplier<String> to;
        
        public Entry(final String from, final Supplier<String> to) {
            this.from = from;
            this.to = to;
        }
        
        public String getFrom() {
            return this.from;
        }
        
        public Supplier<String> getTo() {
            return this.to;
        }
    }
}
