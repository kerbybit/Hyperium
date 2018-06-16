package cc.hyperium.mods.orangemarshall.enhancements.util;

import java.util.*;

public class PlayerDetailUtil
{
    public static String uuidRequestURL;
    public static String nameRequestURLPre;
    public static String nameRequestURLSuf;
    
    public static String getUUID(final String player) {
        final String answer = MiscUtil.httpRequestAsString(PlayerDetailUtil.uuidRequestURL + player);
        final LinkedList<String[]> json = StringUtil.getBetterJson(answer);
        for (final Object o : json.toArray()) {
            final String[] s = ((String[])o).clone();
            if (s[0].equals("id")) {
                return s[1];
            }
        }
        return "";
    }
    
    public static LinkedList<String[]> getNames(final String player) {
        final String answer = MiscUtil.httpRequestAsString(PlayerDetailUtil.nameRequestURLPre + player + PlayerDetailUtil.nameRequestURLSuf);
        final LinkedList<String[]> json = StringUtil.getBetterJson(answer);
        final LinkedList<String[]> out = new LinkedList<String[]>();
        final Object[] obj = json.toArray();
        if (obj.length < 1) {
            return null;
        }
        out.add(new String[] { ((String[])obj[0])[1] });
        for (int i = 1; i < obj.length / 2 + 1; ++i) {
            final String[] s = ((String[])obj[2 * i - 1]).clone();
            final String[] s2 = ((String[])obj[2 * i]).clone();
            out.add(new String[] { s[1], s2[1] });
        }
        return out;
    }
    
    static {
        PlayerDetailUtil.uuidRequestURL = "https://api.mojang.com/users/profiles/minecraft/";
        PlayerDetailUtil.nameRequestURLPre = "https://api.mojang.com/user/profiles/";
        PlayerDetailUtil.nameRequestURLSuf = "/names";
    }
}
