package cc.hyperium.mods.orangemarshall.enhancements.modules.f4;

import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.other.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.tab.*;
import net.minecraft.entity.*;
import java.util.function.*;
import net.minecraft.entity.passive.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import org.lwjgl.opengl.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class F4Screen
{
    private Minecraft mc;
    private final Translator translator;
    private BlockPos blockpos;
    private Entity entity;
    private EnumFacing enumfacing;
    private String[] debugString;
    private F4Parser parser;
    
    public F4Screen(final Minecraft mc) {
        this.translator = new Translator();
        this.parser = new F4Parser();
        this.mc = mc;
        this.loadTranslations();
    }
    
    private void loadTranslations() {
        this.translator.addTranslation("%time%", this::getTime);
        this.translator.addTranslation("%player_coordinates%", this::getPlayerCoords);
        this.translator.addTranslation("%block_coordinates%", this::getBlockCoords);
        this.translator.addTranslation("%world_time%", this::getWorldTime);
        this.translator.addTranslation("%direction%", this::getDirection);
        this.translator.addTranslation("%biome%", this::getBiome);
        this.translator.addTranslation("%shader%", this::getShader);
        this.translator.addTranslation("%max_rcs%", this::getMaxRCS);
        this.translator.addTranslation("%current_rcs%", this::getCurrentRCS);
        this.translator.addTranslation("%max_cps%", this::getMaxCPS);
        this.translator.addTranslation("%current_cps%", this::getCurrentCPS);
        this.translator.addTranslation("%lookingatcoords%", this::getLookingAtCoords);
        this.translator.addTranslation("%lookingatblocktype%", this::getLookingAtBlockType);
        this.translator.addTranslation("%memory%", this::getMemory);
        this.translator.addTranslation("%resolution%", this::getScreenResolution);
        this.translator.addTranslation("%java_version%", this::getJavaVersion);
        this.translator.addTranslation("%chunk_updates%", this::getChunkUpdates);
        this.translator.addTranslation("%fps%", this::getFPS);
        this.translator.addTranslation("%horse_jump%", this::getHorseJumpPower);
        this.translator.addTranslation("%horse_speed%", this::getHorseSpeed);
        this.translator.addTranslation("%armor_value%", this::getArmorValue);
        this.translator.addTranslation("&", () -> "\u00c2�");
        this.translator.addTranslation("%ping%", this::getPing);
        this.translator.addTranslation("%movement_speed%", this::getMovementSpeed);
        this.translator.addTranslation("%movement_speed_ignore_y%", this::getMovementSpeedIgnoreY);
    }
    
    private String getMovementSpeed() {
        if (this.mc.field_71439_g == null) {
            return "";
        }
        final Vec3 oldPos = new Vec3(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v);
        final Vec3 newPos = new Vec3(this.mc.field_71439_g.field_70142_S, this.mc.field_71439_g.field_70137_T, this.mc.field_71439_g.field_70136_U);
        final double perTick = newPos.func_72438_d(oldPos);
        return String.format("%.1f", perTick * 20.0);
    }
    
    private String getMovementSpeedIgnoreY() {
        if (this.mc.field_71439_g == null) {
            return "";
        }
        final Vec3 oldPos = new Vec3(this.mc.field_71439_g.field_70165_t, 0.0, this.mc.field_71439_g.field_70161_v);
        final Vec3 newPos = new Vec3(this.mc.field_71439_g.field_70142_S, 0.0, this.mc.field_71439_g.field_70136_U);
        final double perTick = newPos.func_72438_d(oldPos);
        return String.format("%.1f", perTick * 20.0);
    }
    
    private String getPing() {
        return "" + NetworkInfo.getInstance().getPing(Minecraft.func_71410_x().func_110432_I().func_111285_a());
    }
    
    private String getArmorValue() {
        return ArmorPotential.instance().getAsString();
    }
    
    private String getHorseJumpPower() {
        return this.getHorseStatOrEmpty(horse -> (int)(100.0 * horse.func_110215_cj()) + "");
    }
    
    private String getHorseSpeed() {
        return this.getHorseStatOrEmpty(horse -> (int)(100.0 * horse.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()) + "");
    }
    
    private String getHorseStatOrEmpty(final Function<EntityHorse, String> func) {
        if (this.mc.field_71439_g.field_70154_o instanceof EntityHorse) {
            return func.apply((EntityHorse)this.mc.field_71439_g.field_70154_o);
        }
        return "";
    }
    
    public Pair<List<String>, List<String>> getLeftAndRight() {
        this.updateState();
        return new Pair<List<String>, List<String>>(this.getLeft(), this.getRight());
    }
    
    private List<String> getLeft() {
        this.updateState();
        return this.parser.getLeftTranslated(this.translator);
    }
    
    private List<String> getRight() {
        this.updateState();
        return this.parser.getRightTranslated(this.translator);
    }
    
    private void updateState() {
        this.entity = this.mc.func_175606_aa();
        this.blockpos = new BlockPos(this.mc.func_175606_aa().field_70165_t, this.mc.func_175606_aa().func_174813_aQ().field_72338_b, this.mc.func_175606_aa().field_70161_v);
        this.enumfacing = this.entity.func_174811_aO();
        this.debugString = this.mc.field_71426_K.split(" ");
    }
    
    private static long bytesToMb(final long bytes) {
        return bytes / 1024L / 1024L;
    }
    
    private String getTime() {
        final Calendar cal = Calendar.getInstance();
        return String.format("%02d/%02d/%02d @ %02d:%02d:%02d", cal.get(5), cal.get(2), cal.get(1), cal.get(11), cal.get(12), cal.get(13));
    }
    
    private String getPlayerCoords() {
        return String.format("%.2f / %.2f / %.2f", this.mc.func_175606_aa().field_70165_t, this.mc.func_175606_aa().func_174813_aQ().field_72338_b, this.mc.func_175606_aa().field_70161_v);
    }
    
    private String getBlockCoords() {
        return String.format("Block: %d %d %d " + this.roundDecimals(MathHelper.func_76142_g(this.entity.field_70125_A), 3), this.blockpos.func_177958_n(), this.blockpos.func_177956_o(), this.blockpos.func_177952_p());
    }
    
    private double roundDecimals(double num, final int a) {
        if (num == 0.0) {
            return num;
        }
        num = (int)(num * Math.pow(10.0, a));
        num /= Math.pow(10.0, a);
        return num;
    }
    
    private String getDirection() {
        switch (this.enumfacing) {
            case NORTH: {
                return "North (-Z)";
            }
            case SOUTH: {
                return "South (+Z)";
            }
            case WEST: {
                return "West (-X)";
            }
            case EAST: {
                return "East (+X)";
            }
            default: {
                return "";
            }
        }
    }
    
    private String getWorldTime() {
        return "" + this.mc.field_71441_e.func_72820_D() / 24000L;
    }
    
    private String getBiome() {
        if (this.mc.field_71441_e != null && this.mc.field_71441_e.func_175667_e(this.blockpos)) {
            final Chunk chunk = this.mc.field_71441_e.func_175726_f(this.blockpos);
            return chunk.func_177411_a(this.blockpos, this.mc.field_71441_e.func_72959_q()).field_76791_y;
        }
        return "";
    }
    
    private String getShader() {
        if (this.mc.field_71460_t != null && this.mc.field_71460_t.func_147702_a()) {
            return this.mc.field_71460_t.func_147706_e().func_148022_b();
        }
        return "";
    }
    
    private String getFPS() {
        final int fps = Integer.valueOf(this.debugString[0]);
        final String stringfps = ((fps < 10) ? "  " : "") + ((fps < 100) ? " " : "") + fps;
        return this.debugString[6].equals("inf") ? stringfps : (stringfps + "/" + this.debugString[6]);
    }
    
    private String getChunkUpdates() {
        return this.debugString[2].substring(1);
    }
    
    private String getJavaVersion() {
        return "Java " + System.getProperty("java.version") + "_" + (this.mc.func_147111_S() ? 64 : 32);
    }
    
    private String getScreenResolution() {
        return Display.getWidth() + "x" + Display.getHeight();
    }
    
    private String getMemory() {
        final long max = Runtime.getRuntime().maxMemory();
        final long total = Runtime.getRuntime().totalMemory();
        final long free = Runtime.getRuntime().freeMemory();
        final long used = total - free;
        return String.format("% 2d%% (%03d/%03dMB)", used * 100L / max, bytesToMb(used), bytesToMb(max));
    }
    
    private String getMaxCPS() {
        return "" + ClickTracker.instance().highestCPS;
    }
    
    private String getCurrentCPS() {
        return "" + ClickTracker.instance().currentCPS;
    }
    
    private String getMaxRCS() {
        return "" + ClickTracker.instance().highestRCS;
    }
    
    private String getCurrentRCS() {
        return "" + ClickTracker.instance().currentRCS;
    }
    
    private String getLookingAtCoords() {
        if (this.mc.field_71476_x != null && this.mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.field_71476_x.func_178782_a() != null) {
            final BlockPos blockpos = this.mc.field_71476_x.func_178782_a();
            IBlockState iblockstate = this.mc.field_71441_e.func_180495_p(blockpos);
            if (this.mc.field_71441_e.func_175624_G() != WorldType.field_180272_g) {
                iblockstate = iblockstate.func_177230_c().func_176221_a(iblockstate, (IBlockAccess)this.mc.field_71441_e, blockpos);
            }
            return String.format("%d %d %d", blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p());
        }
        return "";
    }
    
    private String getLookingAtBlockType() {
        if (this.mc.field_71476_x != null && this.mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.field_71476_x.func_178782_a() != null) {
            final BlockPos blockpos = this.mc.field_71476_x.func_178782_a();
            IBlockState iblockstate = this.mc.field_71441_e.func_180495_p(blockpos);
            if (this.mc.field_71441_e.func_175624_G() != WorldType.field_180272_g) {
                iblockstate = iblockstate.func_177230_c().func_176221_a(iblockstate, (IBlockAccess)this.mc.field_71441_e, blockpos);
            }
            return String.valueOf(Block.field_149771_c.func_177774_c((Object)iblockstate.func_177230_c()));
        }
        return "";
    }
}
