package cc.hyperium.mods.orangemarshall.enhancements;

import cc.hyperium.event.EventBus;
import cc.hyperium.event.GuiOpenEvent;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.ServerJoinEvent;
import cc.hyperium.mods.orangemarshall.enhancements.config.Config;
import cc.hyperium.mods.orangemarshall.enhancements.custom.CustomHitboxRenderManager;
import cc.hyperium.mods.orangemarshall.enhancements.custom.FixedInventory;
import cc.hyperium.mods.orangemarshall.enhancements.modules.chat.BetterChat;
import cc.hyperium.mods.orangemarshall.enhancements.modules.chat.tab.BetterChatWithTabs;
import cc.hyperium.mods.orangemarshall.enhancements.modules.particle.EntitySlashBloodFX;
import cc.hyperium.mods.orangemarshall.enhancements.modules.tab.CustomGuiPlayerTabOverlay;
import cc.hyperium.mods.orangemarshall.enhancements.util.FieldWrapper;
import cc.hyperium.mods.orangemarshall.enhancements.util.ReflectionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

import java.io.File;

public class Hooker
{
    private Minecraft mc;
    private GuiNewChat chat;

    public Hooker() {
        this.mc = Minecraft.getMinecraft();
        this.chat = null;
        EventBus.INSTANCE.register(this);
    }
    
    public void clientCommandHandler() {
        final ClientCommandHandler ccch = ClientCommandHandler.instance;
        ReflectionUtil.setFinal("instance", new CaseInsensitiveCommandHandler(), ClientCommandHandler.class, ClientCommandHandler.instance);
        ccch.getCommands().values().forEach(ClientCommandHandler.instance::registerCommand);
    }
    
    public void renderManager() {
        final CustomHitboxRenderManager manager = new CustomHitboxRenderManager(this.mc.renderEngine, this.mc.getRenderItem());
        manager.initialize();
        ReflectionUtil.set("renderManager", "renderManager", manager, Minecraft.class, this.mc);
    }
    
    @InvokeEvent
    public void guiChat(ServerJoinEvent event) {
        if (this.chat != null) {
            Hooker.chatField.set(this.mc.ingameGUI, this.chat);
            this.chat = null;
        }
    }
    
    @SubscribeEvent
    public void guiTabOverlay(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Hooker.overlay.setFinal(this.mc.ingameGUI, new CustomGuiPlayerTabOverlay(this.mc, this.mc.ingameGUI));
    }
    
    public void particles() {
        this.mc.effectRenderer.registerParticle(77, (IParticleFactory)new EntitySlashBloodFX.Factory());
    }
    
    public void config(final File file) {
        final Config config = new Config(file);
        config.load();
    }
    
    public void loadChatInstance() {
        this.chat = (Config.instance().enableCustomChatTabs ? new BetterChatWithTabs(this.mc) : new BetterChat(this.mc));
    }
    
    public void zoomKeybind() {
        final KeyBinding[] k = Minecraft.getMinecraft().gameSettings.keyBindings;
        for (int i = 0; i < k.length; ++i) {
            if (k[i].getKeyDescription().equalsIgnoreCase("Zoom") || k[i].getKeyDescription().equalsIgnoreCase("of.key.zoom")) {
                Config.instance().keybindZoom = k[i];
                return;
            }
        }
    }
    

    

}
