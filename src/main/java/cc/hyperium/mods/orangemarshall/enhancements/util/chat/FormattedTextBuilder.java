package cc.hyperium.mods.orangemarshall.enhancements.util.chat;

import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;

public class FormattedTextBuilder
{
    private final List<ColoredEntry> entries;
    
    public FormattedTextBuilder() {
        this.entries = (List<ColoredEntry>)Lists.newArrayList();
    }
    
    public void addEntry(final String text, final List<EnumChatFormatting> formattingList) {
        final EnumChatFormatting[] formatting = formattingList.toArray(new EnumChatFormatting[formattingList.size()]);
        this.entries.add(new ColoredEntry(text, formatting));
    }
    
    public CCT createCCT() {
        final CCT cct = CCT.newComponent();
        for (final ColoredEntry entry : this.entries) {
            final CCT append = CCT.newComponent();
            append.func_150258_a(entry.getText());
            append.format(entry.getFormatting());
            cct.func_150257_a((IChatComponent)append);
        }
        return cct;
    }
    
    public static class ColoredEntry
    {
        private EnumChatFormatting[] formatting;
        private String text;
        
        public ColoredEntry(final String text, final EnumChatFormatting... formatting) {
            this.text = text;
            this.formatting = formatting;
        }
        
        public String getText() {
            return this.text;
        }
        
        public EnumChatFormatting[] getFormatting() {
            return this.formatting;
        }
    }
}
