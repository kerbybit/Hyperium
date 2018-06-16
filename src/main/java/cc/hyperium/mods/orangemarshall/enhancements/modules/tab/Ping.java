package cc.hyperium.mods.orangemarshall.enhancements.modules.tab;

import com.google.common.collect.*;
import cc.hyperium.mods.orangemarshall.enhancements.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.network.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import java.util.*;

public class Ping
{
    private static Thread thread;
    public static String lastServerIP;
    public static int lastServerPort;
    private static HashMap<String, Integer> playerPings;
    public static String lastPing;
    private static int update_cooldown;
    private static int update_counter;
    private FieldWrapper<GuiPlayerTabOverlay> overlayPlayerList;
    private FieldWrapper<Ordering<NetworkPlayerInfo>> ordering;
    
    public Ping() {
        this.overlayPlayerList = new FieldWrapper<GuiPlayerTabOverlay>(Enhancements.isObfuscated ? "field_175196_v" : "overlayPlayerList", GuiIngame.class);
        this.ordering = new FieldWrapper<Ordering<NetworkPlayerInfo>>("field_175252_a", GuiPlayerTabOverlay.class);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public static void printPings() {
        if (Ping.lastServerIP.isEmpty() || (Ping.thread != null && Ping.thread.isAlive())) {
            System.out.println("Is empty or alive");
        }
    }
    
    @SubscribeEvent
    public void onServerJoin(final FMLNetworkEvent.ClientConnectedToServerEvent e) {
        if (e.isLocal) {
            System.out.println("Is local");
            Ping.lastServerIP = "";
        }
        else {
            final ServerAddress serveraddress = ServerAddress.func_78860_a(Minecraft.func_71410_x().func_147104_D().field_78845_b);
            Ping.lastServerIP = serveraddress.func_78861_a();
            Ping.lastServerPort = serveraddress.func_78864_b();
        }
    }
    
    @SubscribeEvent
    public void onServerLeave(final FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        Ping.lastServerIP = "";
    }
    
    @SubscribeEvent
    public void updatePingList(final RenderGameOverlayEvent.Text e) {
        ++Ping.update_counter;
        if (Ping.update_counter > Ping.update_cooldown) {
            final Minecraft mc = Minecraft.func_71410_x();
            final ScoreObjective scoreobjective = mc.field_71441_e.func_96441_U().func_96539_a(0);
            final NetHandlerPlayClient handler = mc.field_71439_g.field_71174_a;
            Ping.playerPings.clear();
            Ping.lastPing = "";
            if (!mc.func_71387_A() || handler.func_175106_d().size() > 1 || scoreobjective != null) {
                final NetHandlerPlayClient nethandlerplayclient = mc.field_71439_g.field_71174_a;
                final GuiPlayerTabOverlay overlayPlayerList = this.overlayPlayerList.get(mc.field_71456_v);
                final Ordering<NetworkPlayerInfo> field_175252_a = this.ordering.get(overlayPlayerList);
                final List<NetworkPlayerInfo> list = (List<NetworkPlayerInfo>)field_175252_a.sortedCopy((Iterable)nethandlerplayclient.func_175106_d());
                for (int i = 0; i < list.size(); ++i) {
                    final NetworkPlayerInfo networkplayerinfo1 = list.get(i);
                    if (networkplayerinfo1 != null) {
                        final String player = networkplayerinfo1.func_178845_a().getName();
                        final int responseTime = networkplayerinfo1.func_178853_c();
                        Ping.playerPings.put(player.toLowerCase(), responseTime);
                        if (player.equals(Minecraft.func_71410_x().func_110432_I().func_111285_a())) {
                            Ping.lastPing = responseTime + "ms";
                        }
                    }
                }
            }
            Ping.update_counter = 0;
        }
    }
    
    public static void printPing(final String name) {
        final Integer i = Ping.playerPings.get(name.toLowerCase());
        if (i == null) {
            ChatUtil.addMessage("No info about " + name, true);
        }
        else {
            ChatUtil.addMessage(name + " - current response time: " + Ping.playerPings.get(name.toLowerCase()) + "ms", true);
        }
    }
    
    public static void printAll() {
        final Iterator<Map.Entry<String, Integer>> it = Ping.playerPings.entrySet().iterator();
        String out = "";
        while (it.hasNext()) {
            final Map.Entry pair = it.next();
            if (!pair.getKey().toString().isEmpty()) {
                out = out + pair.getKey() + " (" + pair.getValue() + "), ";
            }
        }
        if (!out.isEmpty()) {
            out = out.substring(0, out.length() - 2);
            ChatUtil.addMessage(out, true);
        }
    }
    
    public static int getPing() {
        return getPing(Minecraft.func_71410_x().func_110432_I().func_111285_a());
    }
    
    public static int getPing(final String name) {
        final Integer i = Ping.playerPings.get(name.toLowerCase());
        if (i == null) {
            return -1;
        }
        return i;
    }
    
    static {
        Ping.lastServerIP = "";
        Ping.playerPings = new HashMap<String, Integer>();
        Ping.lastPing = "";
        Ping.update_cooldown = 30;
        Ping.update_counter = 0;
    }
}
