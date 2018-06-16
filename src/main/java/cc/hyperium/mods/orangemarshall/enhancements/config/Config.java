package cc.hyperium.mods.orangemarshall.enhancements.config;

import net.minecraft.client.settings.*;
import java.io.*;

public class Config extends ConfigHelper
{
    private static final String VERSION = "0.1";
    private static Config instance;
    @ConfigOpt(ignore = false, category = "Misc", name = "Change the chat length (Vanilla: 100)")
    public int longerChatLines;
    @ConfigOpt(ignore = false, category = "Misc", name = "Show ping of other players in tab")
    public boolean showPingInTab;
    @ConfigOpt(ignore = false, category = "Misc", name = "Mute portal sounds")
    public boolean mutePortalSounds;
    @ConfigOpt(ignore = false, category = "Misc", name = "Mute wither sounds")
    public boolean muteWitherSounds;
    @ConfigOpt(ignore = false, category = "Misc", name = "Mute slime sounds")
    public boolean muteSlimeSounds;
    @ConfigOpt(ignore = false, category = "Misc", name = "Mute thunder sounds")
    public boolean muteThunderSounds;
    @ConfigOpt(ignore = false, category = "Misc", name = "Enable chat timestamps")
    public boolean showTimestamps;
    @ConfigOpt(ignore = false, category = "Misc", name = "The amount of players that will show in tab (Vanilla: 80)")
    public int tabSize;
    @ConfigOpt(ignore = false, category = "Misc", name = "Players per coloumn in tab (Vanilla: 20)")
    public int tabColoumnLength;
    @ConfigOpt(ignore = false, category = "Misc", name = "Make tab transparent")
    public boolean tabTransparency;
    @ConfigOpt(ignore = false, category = "Misc", name = "Disable slow mouse movements on Optifine Zoom")
    public boolean disableOptifineZoomSmooth;
    @ConfigOpt(ignore = false, category = "Misc", name = "Make tab list toggleable")
    public boolean enableTabToggle;
    @ConfigOpt(ignore = false, category = "Misc", name = "Mojang Status Notifier")
    public boolean enableMojangStatusNotifier;
    @ConfigOpt(ignore = false, category = "Misc", name = "Hide boss bar")
    public boolean hideBossBar;
    @ConfigOpt(ignore = false, category = "Misc", name = "Enable custom chat tabs")
    public boolean enableCustomChatTabs;
    @ConfigOpt(ignore = false, category = "Misc", name = "Render custom chat tabs when chat is not open")
    public boolean renderCustomChatTabsWhenChatIsNotOpen;
    @ConfigOpt(ignore = false, category = "Hitboxes", name = "Do not render hitboxes from entites that are close to you")
    public boolean enableHitboxDistance;
    @ConfigOpt(ignore = false, category = "Hitboxes", name = "Hide every hitbox closer than this distance")
    public int hitboxDistance;
    @ConfigOpt(ignore = false, category = "Hitboxes", name = "Hide blue eyesight line when showing hitboxes")
    public boolean disableHitboxEyesight;
    @ConfigOpt(ignore = false, category = "Hitboxes", name = "Hide red eye height line when showing hitboxes")
    public boolean disableHitboxEyeHeight;
    @ConfigOpt(ignore = false, category = "Hitboxes", name = "Render hitbox red if you're aiming at the entity")
    public boolean enableRedHitboxOnMouseover;
    @ConfigOpt(ignore = false, category = "HUD", name = "Show a warning once your memory reaches 90%")
    public boolean showMemoryWarning;
    @ConfigOpt(ignore = false, category = "HUD", name = "Show a warning if your armor has low durability")
    public boolean showArmorWarning;
    @ConfigOpt(ignore = false, category = "HUD", name = "Show the amount of the currently held item you have in your whole inventory")
    public boolean enableArrowCounter;
    @ConfigOpt(ignore = false, category = "HUD", name = "Show distance to the block your crosshair is pointing at in F3")
    public boolean showCrosshairDistanceInF3;
    @ConfigOpt(ignore = false, category = "HUD", name = "Show enchantemnts of currently held item above hotbar")
    public boolean showEnchantsAboveHotbar;
    @ConfigOpt(ignore = false, category = "HUD", name = "Show attack damage of currently held item above hotbar")
    public boolean showAttackDamageAboveHotbar;
    @ConfigOpt(ignore = false, category = "HUD", name = "Enable clear chat (improves fps)")
    public boolean disabledChatBackground;
    @ConfigOpt(ignore = false, category = "HUDF4", name = "Enable by default")
    public boolean showF4OnStart;
    @ConfigOpt(ignore = false, category = "HUDF4", name = "Show distance to the block your crosshair is pointing at")
    public boolean showCrosshairDistanceInF4;
    @ConfigOpt(ignore = false, category = "HUDF4", name = "Show info about the current server")
    public boolean showServerInfoInF4;
    @ConfigOpt(ignore = false, category = "HUDF4", name = "Draw a rectangle behind the shown text")
    public boolean showBackgroundInF4;
    @ConfigOpt(ignore = false, category = "Blood", name = "Spawn blood particles when you click on an entity")
    public boolean enableBloodEffect;
    @ConfigOpt(ignore = false, category = "Blood", name = "Play a sound when you click on an entity")
    public boolean enableBloodSound;
    @ConfigOpt(ignore = false, category = "FovModifier", name = "Sprint FOV modifier")
    public double sprintFovFactor;
    @ConfigOpt(ignore = false, category = "FovModifier", name = "Speed FOV modifier")
    public double speedFovFactor;
    @ConfigOpt(ignore = false, category = "FovModifier", name = "Slowness FOV modifier")
    public double slownessFovFactor;
    @ConfigOpt(ignore = false, category = "FovModifier", name = "Bow FOV modifier")
    public double bowFovFactor;
    @ConfigOpt(ignore = false, category = "Profiles", name = "FOV Profile #1")
    public int fovProfile1;
    @ConfigOpt(ignore = false, category = "Profiles", name = "FOV Profile #2")
    public int fovProfile2;
    @ConfigOpt(ignore = false, category = "Profiles", name = "FOV Profile #3")
    public int fovProfile3;
    @ConfigOpt(ignore = false, category = "Profiles", name = "Sensitivity Profile #1")
    public int sensProfile1;
    @ConfigOpt(ignore = false, category = "Profiles", name = "Sensitivity Profile #2")
    public int sensProfile2;
    @ConfigOpt(ignore = false, category = "Profiles", name = "Sensitivity Profile #3")
    public int sensProfile3;
    @ConfigOpt(ignore = false, category = "Profiles", name = "Volume Profile #1 \"master-music-noteblocks-weather-block-hostile-friendly-players-ambient\"")
    public String volumeProfile1;
    @ConfigOpt(ignore = false, category = "Profiles", name = "Volume Profile #2")
    public String volumeProfile2;
    @ConfigOpt(ignore = false, category = "Profiles", name = "Volume Profile #3")
    public String volumeProfile3;
    @ConfigOpt(ignore = false, category = "Inventory", name = "Do not move inventory when getting applied a potion / status effect")
    public boolean fixInventory;
    @ConfigOpt(ignore = false, category = "Inventory", name = "Show current protection when opening Inventory")
    public boolean showProtectionInInventory;
    @ConfigOpt(ignore = false, category = "Inventory", name = "Show current projectile protection when opening Inventory")
    public boolean showProjProtectionInInventory;
    @ConfigOpt(ignore = true)
    public boolean chatHooked;
    @ConfigOpt(ignore = true)
    public boolean showF4Screen;
    @ConfigOpt(ignore = true)
    public KeyBinding keybindZoom;
    
    public static Config instance() {
        return Config.instance;
    }
    
    public Config(final File configFile) {
        super(configFile, "0.1");
        this.longerChatLines = 500;
        this.showPingInTab = false;
        this.mutePortalSounds = false;
        this.muteWitherSounds = false;
        this.muteSlimeSounds = false;
        this.muteThunderSounds = false;
        this.showTimestamps = false;
        this.tabSize = 80;
        this.tabColoumnLength = 20;
        this.tabTransparency = false;
        this.disableOptifineZoomSmooth = false;
        this.enableTabToggle = false;
        this.enableMojangStatusNotifier = true;
        this.hideBossBar = false;
        this.enableCustomChatTabs = false;
        this.renderCustomChatTabsWhenChatIsNotOpen = false;
        this.enableHitboxDistance = true;
        this.hitboxDistance = 12;
        this.disableHitboxEyesight = true;
        this.disableHitboxEyeHeight = true;
        this.enableRedHitboxOnMouseover = true;
        this.showMemoryWarning = true;
        this.showArmorWarning = true;
        this.enableArrowCounter = true;
        this.showCrosshairDistanceInF3 = true;
        this.showEnchantsAboveHotbar = true;
        this.showAttackDamageAboveHotbar = true;
        this.disabledChatBackground = false;
        this.showF4OnStart = false;
        this.showCrosshairDistanceInF4 = true;
        this.showServerInfoInF4 = true;
        this.showBackgroundInF4 = false;
        this.enableBloodEffect = false;
        this.enableBloodSound = false;
        this.sprintFovFactor = 1.0;
        this.speedFovFactor = 1.0;
        this.slownessFovFactor = 1.0;
        this.bowFovFactor = 1.0;
        this.fovProfile1 = 70;
        this.fovProfile2 = 90;
        this.fovProfile3 = 110;
        this.sensProfile1 = 100;
        this.sensProfile2 = 120;
        this.sensProfile3 = 140;
        this.volumeProfile1 = "100-100-100-100-100-100-100-100-100";
        this.volumeProfile2 = "50-100-100-100-100-100-100-100-100";
        this.volumeProfile3 = "20-0-10-10-100-100-100-100-100";
        this.fixInventory = true;
        this.showProtectionInInventory = true;
        this.showProjProtectionInInventory = true;
        this.chatHooked = false;
        this.showF4Screen = false;
        this.keybindZoom = null;
        Config.instance = this;
    }
}
