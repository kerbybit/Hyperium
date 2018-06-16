package cc.hyperium.mods.orangemarshall.enhancements.modules.mojangstatus;

import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraft.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import java.util.concurrent.*;
import java.net.*;
import javax.net.ssl.*;
import java.io.*;

public class MojangStatus implements Runnable
{
    private static final int REQUEST_INTERVAL_SECONDS = 60;
    private final ScheduledExecutorService repeatableService;
    private boolean wasUpLastTime;
    
    public MojangStatus() {
        this.repeatableService = Executors.newSingleThreadScheduledExecutor();
        this.wasUpLastTime = true;
    }
    
    public void startIfEnabled() {
        if (Config.instance().enableMojangStatusNotifier) {
            this.repeatableService.scheduleWithFixedDelay(this, 3L, 60L, TimeUnit.SECONDS);
        }
    }
    
    @Override
    public void run() {
        try {
            if (Config.instance().enableMojangStatusNotifier) {
                this.printStatus(new FetchMojangStatus().call());
            }
        }
        catch (Exception e) {
            System.out.println("Couldn't retrieve Mojang data " + e.toString());
        }
    }
    
    private void printStatus(final StatusResult result) {
        final StringBuilder message = new StringBuilder("\n");
        if (!result.isLoginUp() || !result.isSessionUp()) {
            message.append(EnumChatFormatting.GOLD.toString() + EnumChatFormatting.BOLD + "Mojang Status: \n");
            message.append(this.getFormattedStatus(" - Login", result.isLoginUp()) + "\n");
            message.append(this.getFormattedStatus(" - Session", result.isSessionUp()) + "\n");
            this.wasUpLastTime = false;
        }
        else if (!this.wasUpLastTime) {
            message.append(EnumChatFormatting.GOLD.toString() + EnumChatFormatting.BOLD + "Mojang Status: \n");
            message.append(EnumChatFormatting.GREEN + "Everything is now UP again!");
            this.wasUpLastTime = true;
        }
        if (message.length() > 1) {
            ChatUtil.addMessageWithoutTag(message.toString());
        }
    }
    
    private String getFormattedStatus(final String name, final boolean status) {
        final String statusString = status ? (EnumChatFormatting.GREEN + "UP") : (EnumChatFormatting.RED + "DOWN");
        return EnumChatFormatting.WHITE.toString() + EnumChatFormatting.BOLD + name + ": " + statusString;
    }
    
    private static class FetchMojangStatus implements Callable<StatusResult>
    {
        private static final String URL_LOGIN = "https://authserver.mojang.com/authenticate";
        private static final String URL_SESSION = "https://sessionserver.mojang.com/";
        private static final String URL_REALMS = "https://mcoapi.minecraft.net/worlds";
        
        @Override
        public StatusResult call() throws Exception {
            final StatusResult.StatusBuilder builder = new StatusResult.StatusBuilder();
            builder.isLoginUp(this.isUp("https://authserver.mojang.com/authenticate"));
            builder.isSessionUp(this.isUp("https://sessionserver.mojang.com/"));
            return builder.build();
        }
        
        public boolean isUp(final String urlString) {
            try {
                final URL url = new URL(urlString);
                final HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
                connection.connect();
                connection.disconnect();
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
