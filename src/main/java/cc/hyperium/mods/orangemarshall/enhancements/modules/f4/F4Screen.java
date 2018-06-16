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
        if (this.mc.thePlayer == null) {
            return "";
        }
        final Vec3 oldPos = new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
        final Vec3 newPos = new Vec3(this.mc.thePlayer.lastTickPosX, this.mc.thePlayer.lastTickPosY, this.mc.thePlayer.lastTickPosZ);
        final double perTick = newPos.distanceTo(oldPos);
        return String.format("%.1f", perTick * 20.0);
    }
    
    private String getMovementSpeedIgnoreY() {
        if (this.mc.thePlayer == null) {
            return "";
        }
        final Vec3 oldPos = new Vec3(this.mc.thePlayer.posX, 0.0, this.mc.thePlayer.posZ);
        final Vec3 newPos = new Vec3(this.mc.thePlayer.lastTickPosX, 0.0, this.mc.thePlayer.lastTickPosZ);
        final double perTick = newPos.distanceTo(oldPos);
        return String.format("%.1f", perTick * 20.0);
    }
    
    private String getPing() {
        return "" + NetworkInfo.getInstance().getPing(Minecraft.getMinecraft().getSession().getUsername());
    }
    
    private String getArmorValue() {
        return ArmorPotential.instance().getAsString();
    }
    
    private String getHorseJumpPower() {
        return this.getHorseStatOrEmpty(horse -> (int)(100.0 * horse.getHorseJumpStrength()) + "");
    }
    
    private String getHorseSpeed() {
        return this.getHorseStatOrEmpty(horse -> (int)(100.0 * horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()) + "");
    }
    
    private String getHorseStatOrEmpty(final Function<EntityHorse, String> func) {
        if (this.mc.thePlayer.ridingEntity instanceof EntityHorse) {
            return func.apply((EntityHorse)this.mc.thePlayer.ridingEntity);
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
        this.entity = this.mc.getRenderViewEntity();
        this.blockpos = new BlockPos(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);
        this.enumfacing = this.entity.getHorizontalFacing();
        this.debugString = this.mc.debug.split(" ");
    }
    
    private static long bytesToMb(final long bytes) {
        return bytes / 1024L / 1024L;
    }
    
    private String getTime() {
        final Calendar cal = Calendar.getInstance();
        return String.format("%02d/%02d/%02d @ %02d:%02d:%02d", cal.get(5), cal.get(2), cal.get(1), cal.get(11), cal.get(12), cal.get(13));
    }
    
    private String getPlayerCoords() {
        return String.format("%.2f / %.2f / %.2f", this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);
    }
    
    private String getBlockCoords() {
        return String.format("Block: %d %d %d " + this.roundDecimals(MathHelper.wrapAngleTo180_float(this.entity.rotationPitch), 3), this.blockpos.getX(), this.blockpos.getY(), this.blockpos.getZ());
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
        return "" + this.mc.theWorld.getWorldTime() / 24000L;
    }
    
    private String getBiome() {
        if (this.mc.theWorld != null && this.mc.theWorld.isBlockLoaded(this.blockpos)) {
            final Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(this.blockpos);
            return chunk.getBiome(this.blockpos, this.mc.theWorld.getWorldChunkManager()).biomeName;
        }
        return "";
    }
    
    private String getShader() {
        if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
            return this.mc.entityRenderer.getShaderGroup().getShaderGroupName();
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
        return "Java " + System.getProperty("java.version") + "_" + (this.mc.isJava64bit() ? 64 : 32);
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
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
            final BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
            IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
            if (this.mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
                iblockstate = iblockstate.getBlock().getActualState(iblockstate, (IBlockAccess)this.mc.theWorld, blockpos);
            }
            return String.format("%d %d %d", blockpos.getX(), blockpos.getY(), blockpos.getZ());
        }
        return "";
    }
    
    private String getLookingAtBlockType() {
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
            final BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
            IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
            if (this.mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
                iblockstate = iblockstate.getBlock().getActualState(iblockstate, (IBlockAccess)this.mc.theWorld, blockpos);
            }
            return String.valueOf(Block.blockRegistry.getNameForObject((Object)iblockstate.getBlock()));
        }
        return "";
    }
}
