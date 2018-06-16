package cc.hyperium.mods.orangemarshall.enhancements;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import java.util.function.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.tab.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.particle.*;
import net.minecraft.client.particle.*;
import java.io.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.chat.tab.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.chat.*;
import net.minecraft.client.settings.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import cc.hyperium.mods.orangemarshall.enhancements.custom.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;

public class Hooker
{
    private Minecraft mc;
    private GuiNewChat chat;
    private static final FieldWrapper<GuiNewChat> chatField;
    private static final FieldWrapper<GuiPlayerTabOverlay> overlay;
    
    public Hooker() {
        this.mc = Minecraft.func_71410_x();
        this.chat = null;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void clientCommandHandler() {
        final ClientCommandHandler ccch = ClientCommandHandler.instance;
        ReflectionUtil.setFinal("instance", new CaseInsensitiveCommandHandler(), ClientCommandHandler.class, ClientCommandHandler.instance);
        ccch.func_71555_a().values().forEach(ClientCommandHandler.instance::func_71560_a);
    }
    
    public void renderManager() {
        final CustomHitboxRenderManager manager = new CustomHitboxRenderManager(this.mc.field_71446_o, this.mc.func_175599_af());
        manager.initialize();
        ReflectionUtil.set("renderManager", "field_175616_W", manager, Minecraft.class, this.mc);
    }
    
    @SubscribeEvent
    public void guiChat(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (this.chat != null) {
            Hooker.chatField.set(this.mc.field_71456_v, this.chat);
            this.chat = null;
        }
    }
    
    @SubscribeEvent
    public void guiTabOverlay(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Hooker.overlay.setFinal(this.mc.field_71456_v, new CustomGuiPlayerTabOverlay(this.mc, this.mc.field_71456_v));
    }
    
    public void particles() {
        this.mc.field_71452_i.func_178929_a(77, (IParticleFactory)new EntitySlashBloodFX.Factory());
    }
    
    public void config(final File file) {
        final Config config = new Config(file);
        config.load();
    }
    
    public void loadChatInstance() {
        this.chat = (Config.instance().enableCustomChatTabs ? new BetterChatWithTabs(this.mc) : new BetterChat(this.mc));
    }
    
    public void zoomKeybind() {
        final KeyBinding[] k = Minecraft.func_71410_x().field_71474_y.field_74324_K;
        for (int i = 0; i < k.length; ++i) {
            if (k[i].func_151464_g().equalsIgnoreCase("Zoom") || k[i].func_151464_g().equalsIgnoreCase("of.key.zoom")) {
                Config.instance().keybindZoom = k[i];
                return;
            }
        }
    }
    
    @SubscribeEvent
    public void inventory(final GuiOpenEvent event) {
        if (!Config.instance().fixInventory) {
            return;
        }
        if (event.gui != null && event.gui instanceof GuiInventory) {
            event.gui = (GuiScreen)new FixedInventory((EntityPlayer)this.mc.field_71439_g);
        }
    }
    
    static {
        chatField = new FieldWrapper<GuiNewChat>(Enhancements.isObfuscated ? "field_73840_e" : "persistantChatGUI", GuiIngame.class);
        overlay = new FieldWrapper<GuiPlayerTabOverlay>(Enhancements.isObfuscated ? "field_175196_v" : "overlayPlayerList", GuiIngame.class);
    }
}
