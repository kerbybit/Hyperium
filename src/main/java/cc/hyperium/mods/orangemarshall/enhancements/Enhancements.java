package cc.hyperium.mods.orangemarshall.enhancements;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraft.client.*;
import net.minecraftforge.client.*;
import net.minecraft.command.*;
import cc.hyperium.mods.orangemarshall.enhancements.commands.*;
import cc.hyperium.mods.orangemarshall.enhancements.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.particle.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.tab.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.chat.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.f4.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.mojangstatus.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.reminder.*;

@Mod(modid = "enhancements", name = "Vanilla Enhancements", useMetadata = true, acceptedMinecraftVersions = "[1.8.9]")
public class Enhancements
{
    public static boolean isObfuscated;
    private Hooker hooker;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent e) {
        Enhancements.isObfuscated = isObfuscated();
        (this.hooker = new Hooker()).clientCommandHandler();
        this.hooker.config(e.getSuggestedConfigurationFile());
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent e) {
        this.hooker.loadChatInstance();
        this.loadCommands();
        this.loadModules();
        this.hooker.renderManager();
        this.hooker.particles();
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent e) {
        this.hooker.zoomKeybind();
        this.hooker = null;
    }
    
    private static boolean isObfuscated() {
        try {
            Minecraft.class.getDeclaredField("logger");
            return false;
        }
        catch (NoSuchFieldException e1) {
            return true;
        }
    }
    
    private void loadCommands() {
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandGamma());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandFov());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandName());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandEnconfig());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandCopy());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandCoords());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandPing());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandResetCS());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandTime());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandSens());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandScreenshotDummy());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandLogs());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandNotepad());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandReminder());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandTimeZone());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandListPlayers());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandClearChat());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandVolume());
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandConfig());
    }
    
    private void loadModules() {
        new EventPoster();
        new DropListener();
        new ArmorPotential();
        new CrosshairDistance();
        new ScreenshotImprovements();
        new BloodParticles();
        new ArrowCount();
        new ZoomSensitivity();
        new NetworkInfo();
        new SoundFilter();
        new ClickTracker();
        new HotbarEnchantments();
        new TabToggle();
        new FovModifier();
        new ChatInputExtender();
        new F4Listener();
        new DurabilityWarning();
        new MemoryWarning();
        new BossBarHider();
        new HotbarAttackDamage();
        new MojangStatus().startIfEnabled();
        Reminders.INSTANCE.updateFile();
    }
}
