package cc.hyperium.mods.orangemarshall.enhancements.util.chat;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;
import java.io.*;

public class TextComponentCreator
{
    private static final String validColorSigns = "0123456789abcdefr";
    private String text;
    
    public TextComponentCreator(final String text) {
        this.text = text;
    }
    
    public CCT createCCT() {
        final FormattedTextBuilder builder = this.splitByValidColorCodes(this.text);
        return builder.createCCT();
    }
    
    private FormattedTextBuilder splitByValidColorCodes(String textToBeProcessed) {
        final FormattedTextBuilder textBuilder = new FormattedTextBuilder();
        StringBuilder nextEntryString = new StringBuilder();
        List<EnumChatFormatting> formattingsForString = (List<EnumChatFormatting>)Lists.newArrayList();
        while (!textToBeProcessed.isEmpty()) {
            if (this.startsWithFormatting(textToBeProcessed)) {
                if (nextEntryString.length() > 0) {
                    textBuilder.addEntry(nextEntryString.toString(), formattingsForString);
                    nextEntryString = new StringBuilder();
                    formattingsForString = (List<EnumChatFormatting>)Lists.newArrayList();
                }
                formattingsForString.add(this.getLeadingFormatting(textToBeProcessed));
                textToBeProcessed = textToBeProcessed.substring(2);
            }
            else {
                nextEntryString.append(textToBeProcessed.charAt(0));
                textToBeProcessed = textToBeProcessed.substring(1);
            }
        }
        if (nextEntryString.length() > 0) {
            textBuilder.addEntry(nextEntryString.toString(), formattingsForString);
        }
        return textBuilder;
    }
    
    private EnumChatFormatting getLeadingFormatting(final String text) {
        final String inputFormat = text.substring(0, 2).toLowerCase();
        for (final EnumChatFormatting format : EnumChatFormatting.values()) {
            if (format.toString().equals(inputFormat)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Could not find leading Formatting for input string: " + text);
    }
    
    private boolean startsWithFormatting(final String text) {
        return text.length() >= 2 && (text.charAt(0) == '�' && "0123456789abcdefr".indexOf(text.toLowerCase().charAt(1)) >= 0);
    }
}
