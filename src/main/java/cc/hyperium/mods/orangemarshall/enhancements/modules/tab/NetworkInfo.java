package cc.hyperium.mods.orangemarshall.enhancements.modules.tab;

import net.minecraft.client.multiplayer.*;
import net.minecraft.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;

public class NetworkInfo
{
    private static NetworkInfo instance;
    private ServerAddress currentServerAddress;
    private Minecraft mc;
    
    public NetworkInfo() {
        this.mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register((Object)this);
        NetworkInfo.instance = this;
    }
    
    @SubscribeEvent
    public void onServerJoin(final FMLNetworkEvent.ClientConnectedToServerEvent e) {
        if (e.isLocal) {
            this.currentServerAddress = null;
        }
        else {
            this.currentServerAddress = ServerAddress.fromString(this.mc.getCurrentServerData().serverIP);
        }
    }
    
    @SubscribeEvent
    public void onServerLeave(final FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        this.currentServerAddress = null;
    }
    
    public ServerAddress getCurrentServerAddress() {
        return this.currentServerAddress;
    }
    
    public static NetworkInfo getInstance() {
        return NetworkInfo.instance;
    }
    
    private NetworkPlayerInfo getPlayerInfo(final String ign) {
        final Collection<NetworkPlayerInfo> map = (Collection<NetworkPlayerInfo>)this.mc.getNetHandler().getPlayerInfoMap();
        for (final NetworkPlayerInfo networkplayerinfo : map) {
            if (networkplayerinfo.getGameProfile().getName().equalsIgnoreCase(ign)) {
                return networkplayerinfo;
            }
        }
        return null;
    }
    
    public int getPing(final String ign) {
        final NetworkPlayerInfo networkInfo = this.getPlayerInfo(ign);
        if (networkInfo == null) {
            return -1;
        }
        return networkInfo.getResponseTime();
    }
    
    public void printPing(final String name) {
        final NetworkPlayerInfo info = this.getPlayerInfo(name);
        if (info == null || info.getResponseTime() < 0) {
            ChatUtil.addMessage(EnumChatFormatting.RED + "No info about " + name);
        }
        else {
            ChatUtil.addMessage(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.BOLD + info.getGameProfile().getName() + EnumChatFormatting.WHITE + ": " + info.getResponseTime() + "ms");
        }
    }
}
