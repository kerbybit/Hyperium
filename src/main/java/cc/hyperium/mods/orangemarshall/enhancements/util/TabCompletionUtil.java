package cc.hyperium.mods.orangemarshall.enhancements.util;

import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.*;
import java.util.stream.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.network.*;

public class TabCompletionUtil
{
    public static List<String> getListOfStringsMatchingLastWord(final String[] p_175762_0_, final Collection<?> p_175762_1_) {
        final String s = p_175762_0_[p_175762_0_.length - 1];
        final List<String> list = (List<String>)Lists.newArrayList();
        if (!p_175762_1_.isEmpty()) {
            for (final String s2 : Iterables.transform((Iterable)p_175762_1_, Functions.toStringFunction())) {
                if (doesStringStartWith(s, s2)) {
                    list.add(s2);
                }
            }
            if (list.isEmpty()) {
                for (final Object object : p_175762_1_) {
                    if (object instanceof ResourceLocation && doesStringStartWith(s, ((ResourceLocation)object).func_110623_a())) {
                        list.add(String.valueOf(object));
                    }
                }
            }
        }
        return list;
    }
    
    public static List<String> getListOfStringsMatchingLastWord(final String[] p_175762_0_, final String[] p_175762_1_) {
        return getListOfStringsMatchingLastWord(p_175762_0_, Arrays.asList(p_175762_1_));
    }
    
    private static boolean doesStringStartWith(final String original, final String region) {
        return region.regionMatches(true, 0, original, 0, original.length());
    }
    
    public static List<String> getTabUsernames() {
        final EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
        final List<String> playerNames = (List<String>)Lists.newArrayList();
        if (player == null) {
            return playerNames;
        }
        return player.field_71174_a.func_175106_d().stream().map(netPlayerInfo -> netPlayerInfo.func_178845_a().getName()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public static List<EntityPlayer> getLoadedPlayers() {
        return (List<EntityPlayer>)Minecraft.func_71410_x().field_71441_e.field_73010_i;
    }
}
