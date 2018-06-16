package cc.hyperium.mods.orangemarshall.enhancements.other;

import java.util.function.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.*;

public abstract class Parser
{
    private InputStream inputStream;
    
    public Parser(final InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    protected abstract void parseLine(final String p0);
    
    public void parse() {
        final List<String> lines = this.getLines();
        lines.forEach(this::parseLine);
    }
    
    private List<String> getLines() {
        final LinkedList<String> lines = (LinkedList<String>)Lists.newLinkedList();
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    
    public static class ParseException extends RuntimeException
    {
        public ParseException() {
        }
        
        public ParseException(final String message, final Throwable e) {
            super(message, e);
        }
    }
}
