package cc.hyperium.mods.orangemarshall.enhancements;

import cc.hyperium.Hyperium;
import cc.hyperium.event.InitializationEvent;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.PreInitializationEvent;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandClearChat;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandConfig;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandCoords;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandCopy;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandEnconfig;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandFov;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandGamma;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandListPlayers;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandLogs;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandName;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandNotepad;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandPing;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandReminder;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandResetCS;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandScreenshotDummy;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandSens;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandTimeZone;
import cc.hyperium.mods.orangemarshall.enhancements.commands.CommandVolume;
import cc.hyperium.mods.orangemarshall.enhancements.event.EventPoster;
import cc.hyperium.mods.orangemarshall.enhancements.modules.ArmorPotential;
import cc.hyperium.mods.orangemarshall.enhancements.modules.ArrowCount;
import cc.hyperium.mods.orangemarshall.enhancements.modules.BossBarHider;
import cc.hyperium.mods.orangemarshall.enhancements.modules.ClickTracker;
import cc.hyperium.mods.orangemarshall.enhancements.modules.CrosshairDistance;
import cc.hyperium.mods.orangemarshall.enhancements.modules.DropListener;
import cc.hyperium.mods.orangemarshall.enhancements.modules.DurabilityWarning;
import cc.hyperium.mods.orangemarshall.enhancements.modules.FovModifier;
import cc.hyperium.mods.orangemarshall.enhancements.modules.HotbarAttackDamage;
import cc.hyperium.mods.orangemarshall.enhancements.modules.HotbarEnchantments;
import cc.hyperium.mods.orangemarshall.enhancements.modules.MemoryWarning;
import cc.hyperium.mods.orangemarshall.enhancements.modules.ScreenshotImprovements;
import cc.hyperium.mods.orangemarshall.enhancements.modules.SoundFilter;
import cc.hyperium.mods.orangemarshall.enhancements.modules.ZoomSensitivity;
import cc.hyperium.mods.orangemarshall.enhancements.modules.chat.ChatInputExtender;
import cc.hyperium.mods.orangemarshall.enhancements.modules.f4.F4Listener;
import cc.hyperium.mods.orangemarshall.enhancements.modules.mojangstatus.MojangStatus;
import cc.hyperium.mods.orangemarshall.enhancements.modules.particle.BloodParticles;
import cc.hyperium.mods.orangemarshall.enhancements.modules.reminder.Reminders;
import cc.hyperium.mods.orangemarshall.enhancements.modules.tab.NetworkInfo;
import cc.hyperium.mods.orangemarshall.enhancements.modules.tab.TabToggle;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;

import java.io.File;

public class Enhancements
{
    public static boolean isObfuscated;
    private Hooker hooker;
    
    @InvokeEvent
    public void preInit(final PreInitializationEvent e) {
        Enhancements.isObfuscated = isObfuscated();
        (this.hooker = new Hooker()).clientCommandHandler();
        this.hooker.config(new File(Hyperium.folder,"orangemarshall-enhancements.txt"));
    }
    
    @InvokeEvent
    public void init(final InitializationEvent e) {
        this.hooker.loadChatInstance();
        this.loadCommands();
        this.loadModules();
        this.hooker.renderManager();
        this.hooker.particles();
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
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandGamma());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandFov());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandName());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandEnconfig());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandCopy());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandCoords());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandPing());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandResetCS());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandTime());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandSens());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandScreenshotDummy());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandLogs());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandNotepad());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandReminder());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandTimeZone());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandListPlayers());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandClearChat());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandVolume());
       Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new CommandConfig());
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
