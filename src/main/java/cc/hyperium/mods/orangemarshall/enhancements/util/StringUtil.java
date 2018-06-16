package cc.hyperium.mods.orangemarshall.enhancements.util;

import java.util.regex.*;
import org.apache.commons.lang3.*;
import java.util.*;

public class StringUtil
{
    public static final String controlSign = "�";
    public static final String bracketOpen = "{";
    public static final String bracketClosed = "}";
    
    public static String removeFormatting(final String inputString) {
        return removeFormatting(inputString, "�");
    }
    
    public static String removeFormatting(String inputString, final String controlSign) {
        while (inputString.contains(controlSign)) {
            final int index = inputString.indexOf(controlSign);
            if (inputString.length() > index) {
                inputString = inputString.replaceFirst(Pattern.quote(controlSign + inputString.charAt(index + 1)), "");
            }
            else {
                inputString = inputString.replaceFirst(controlSign, "");
            }
        }
        return inputString;
    }
    
    public static boolean isNotEmpty(final String inputString) {
        return inputString != null && !inputString.isEmpty();
    }
    
    public static int countOccurences(final String inputString, final String searched) {
        final String replaced = inputString.replaceAll(searched, "");
        return (inputString.length() - replaced.length()) / searched.length();
    }
    
    public static boolean isInteger(final String inputString) {
        try {
            Integer.parseInt(inputString);
        }
        catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }
    
    public static String joinStringWithSplitter(final String[] inputStrings, final String splitter) {
        String joined = "";
        for (int i = 0; i < inputStrings.length; ++i) {
            if (i == 0) {
                joined += inputStrings[i];
            }
            else {
                joined = joined + splitter + inputStrings[i];
            }
        }
        return joined;
    }
    
    public static String join(final Collection<?> collection, final String splitter) {
        return StringUtils.join((Iterable)collection, splitter);
    }
    
    public static String joinStringWithSplitter(final List<String> str, final String splitter) {
        return joinStringWithSplitter(str.toArray(new String[str.size()]), splitter);
    }
    
    public static HashMap<String, String> getJson(String list) {
        final HashMap<String, String> out = new HashMap<String, String>();
        String part = list;
        for (int i = 0; i < containsOpenBracket(list); list = list.substring(0, list.indexOf("{")) + list.substring(list.indexOf("}") + 1, list.length()), ++i) {
            for (part = list.substring(list.indexOf("{") + 1, list.indexOf("}")); containsHowOften(part, ":") >= 1 && containsHowOften(part, "\"") >= 4; part = part.replace("\"" + part.split("\"")[1] + "\"", ""), part = part.replace("\"" + part.split("\"")[1] + "\"", "")) {
                part = part.substring(part.indexOf("\""));
                out.put(part.split("\"")[0], part.split("\"")[2]);
            }
        }
        return out;
    }
    
    public static LinkedList<String[]> getBetterJson(String list) {
        final LinkedList<String[]> out = new LinkedList<String[]>();
        final ArrayList<String> brackets = new ArrayList<String>();
        while (containsOpenBracket(list) >= 1) {
            final String sub = list.substring(list.indexOf("{") + 1, list.indexOf("}"));
            brackets.add(sub);
            list = list.replace(list.substring(list.indexOf("{"), list.indexOf("}") + 1), "");
        }
        for (int i = 0; i < brackets.size() && brackets.get(i).toString().contains(":"); ++i) {
            while (brackets.get(i).toString().contains("\"")) {
                final String[] tmp = brackets.get(i).toString().split(":");
                if (brackets.get(i).toString().contains(",")) {
                    final String sec = tmp[1].substring(0, tmp[1].indexOf(","));
                    out.add(new String[] { tmp[0].replaceAll("\"", ""), sec.replace("\"", "") });
                }
                else {
                    out.add(new String[] { tmp[0].replaceAll("\"", ""), tmp[1].replace("\"", "") });
                }
                if (brackets.get(i).toString().contains(",")) {
                    brackets.set(i, brackets.get(i).toString().substring(brackets.get(i).toString().indexOf(",") + 1));
                }
                else {
                    brackets.set(i, "");
                }
            }
        }
        return out;
    }
    
    private static int containsOpenBracket(final String s) {
        return s.length() - s.replaceAll("\\{", "").length();
    }
    
    private static int containsHowOften(final String s, final String sequence) {
        return (s.length() - s.replaceAll(sequence, "").length()) / sequence.length();
    }
    
    public static String stripUUID(final UUID uuid) {
        return stripUUID(uuid.toString());
    }
    
    public static UUID unstripUuidAsUuid(final String uuid) {
        return UUID.fromString(unstripUuidAsString(uuid));
    }
    
    public static String unstripUuidAsString(final String uuid) {
        if (uuid.length() != 32) {
            return uuid;
        }
        return uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32);
    }
    
    public static String stripUUID(final String uuid) {
        return uuid.replace("-", "");
    }
}
