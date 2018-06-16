package cc.hyperium.mods.orangemarshall.enhancements.modules.f4;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import org.apache.commons.io.*;
import java.io.*;
import java.util.stream.*;
import com.google.common.collect.*;
import cc.hyperium.mods.orangemarshall.enhancements.other.*;

public class F4Parser
{
    private final String NAME_LEFT = "enhancements_f4screen_left.txt";
    private final String NAME_RIGHT = "enhancements_f4screen_right.txt";
    private final File FILE_LEFT;
    private final File FILE_RIGHT;
    private final String COMMENT_PREFIX = "//";
    private final List<String> left;
    private final List<String> right;
    
    public F4Parser() {
        this.FILE_LEFT = new File(Config.instance().getConfigFile().getParent(), "enhancements_f4screen_left.txt");
        this.FILE_RIGHT = new File(Config.instance().getConfigFile().getParent(), "enhancements_f4screen_right.txt");
        this.copyIfNotExistant(this.FILE_LEFT, "enhancements_f4screen_left.txt");
        this.copyIfNotExistant(this.FILE_RIGHT, "enhancements_f4screen_right.txt");
        this.left = this.readFromFile(this.FILE_LEFT);
        this.right = this.readFromFile(this.FILE_RIGHT);
    }
    
    private void copyIfNotExistant(final File file, final String resourcename) {
        try {
            if (!file.exists()) {
                FileUtils.copyURLToFile(F4Parser.class.getClassLoader().getResource(resourcename), file);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private List<String> readFromFile(final File file) {
        try {
            final List<String> lines = (List<String>)FileUtils.readLines(file);
            return lines.stream().filter(line -> !line.startsWith("//")).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
            final List<String> lines2 = (List<String>)Lists.newArrayList();
            lines2.add("Error parsing file " + file.getName());
            return lines2;
        }
    }
    
    private List<String> getTranslated(final Translator translator, final List<String> input) {
        final List<String> translated = (List<String>)Lists.newArrayList((Iterable)input);
        translator.translate(translated);
        return translated;
    }
    
    public List<String> getRightTranslated(final Translator translator) {
        return this.getTranslated(translator, this.right);
    }
    
    public List<String> getLeftTranslated(final Translator translator) {
        return this.getTranslated(translator, this.left);
    }
}
