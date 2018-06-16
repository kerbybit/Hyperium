package cc.hyperium.mods.orangemarshall.enhancements.util;

import java.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.http.*;
import net.minecraft.client.*;

public class PlayerDetails
{
    private static String name;
    private static UUID uuid;
    
    public static void initialize() {
        PlayerDetails.uuid = new UuidRequest(PlayerDetails.name).requestUser().getUuid();
    }
    
    public static UUID getOwnUUID() {
        return PlayerDetails.uuid;
    }
    
    public static String getOwnName() {
        return PlayerDetails.name;
    }
    
    static {
        PlayerDetails.name = Minecraft.getMinecraft().getSession().getUsername();
    }
}
