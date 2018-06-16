package cc.hyperium.mods.orangemarshall.enhancements.modules.chat.tab;

import java.util.function.*;
import org.apache.commons.lang3.*;
import java.util.regex.*;

public abstract class ConditionDefinition implements Function<String, Boolean>
{
    protected String text;
    
    public ConditionDefinition(final String text) {
        this.text = text;
    }
    
    public static ConditionDefinition of(final String input) {
        final String condName = input.substring(0, input.indexOf("(")).toLowerCase();
        final String[] arguments = input.substring(input.indexOf("(") + 1, input.lastIndexOf(")")).split(",");
        final String s = condName;
        switch (s) {
            case "startswith": {
                return new StartsWith(arguments[0]);
            }
            case "contains": {
                if (arguments.length == 1) {
                    return new Contains(arguments[0]);
                }
                return new Contains(arguments[0], Integer.parseInt(arguments[1]));
            }
            case "endswith": {
                return new EndsWith(arguments[0]);
            }
            case "equals": {
                return new Equals(arguments[0]);
            }
            case "equalsignorecase": {
                return new EqualsIgnoreCase(arguments[0]);
            }
            case "charatequals": {
                return new CharAtEquals(Integer.parseInt(arguments[0]), arguments[1].charAt(0));
            }
            case "regex": {
                return new Regex(arguments[0]);
            }
            default: {
                throw new IllegalArgumentException("Could not find Condition for " + input);
            }
        }
    }
    
    public static class Contains extends ConditionDefinition
    {
        private int count;
        
        public Contains(final String text) {
            super(text);
        }
        
        public Contains(final String text, final int count) {
            this(text);
            this.count = Math.max(1, count);
        }
        
        @Override
        public Boolean apply(final String input) {
            return StringUtils.countMatches((CharSequence)input, (CharSequence)this.text) == this.count;
        }
    }
    
    public static class StartsWith extends ConditionDefinition
    {
        public StartsWith(final String text) {
            super(text);
        }
        
        @Override
        public Boolean apply(final String input) {
            return input.startsWith(this.text);
        }
    }
    
    public static class EndsWith extends ConditionDefinition
    {
        public EndsWith(final String text) {
            super(text);
        }
        
        @Override
        public Boolean apply(final String input) {
            return input.endsWith(this.text);
        }
    }
    
    public static class Equals extends ConditionDefinition
    {
        public Equals(final String text) {
            super(text);
        }
        
        @Override
        public Boolean apply(final String input) {
            return input.equals(this.text);
        }
    }
    
    public static class EqualsIgnoreCase extends ConditionDefinition
    {
        public EqualsIgnoreCase(final String text) {
            super(text);
        }
        
        @Override
        public Boolean apply(final String input) {
            return input.equalsIgnoreCase(this.text);
        }
    }
    
    public static class CharAtEquals extends ConditionDefinition
    {
        private int index;
        private char c;
        
        public CharAtEquals(final int index, final char c) {
            super(Character.toString(c));
            this.index = Math.max(0, index);
            this.c = c;
        }
        
        @Override
        public Boolean apply(final String input) {
            return input.charAt(this.index) == this.c;
        }
    }
    
    public static class Regex extends ConditionDefinition
    {
        private Pattern pattern;
        
        public Regex(final String regex) {
            super(regex);
            this.pattern = Pattern.compile(regex);
        }
        
        @Override
        public Boolean apply(final String input) {
            return this.pattern.matcher(input).matches();
        }
    }
}
