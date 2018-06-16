package cc.hyperium.mods.orangemarshall.enhancements.modules.particle;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import cc.hyperium.mods.orangemarshall.enhancements.*;
import cc.hyperium.mods.orangemarshall.enhancements.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.lang.reflect.*;
import net.minecraft.util.*;

public class BloodParticles
{
    private Minecraft mc;
    private Method spawnParticle;
    
    public BloodParticles() {
        this.mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.loadMethod();
    }
    
    private void loadMethod() {
        try {
            (this.spawnParticle = World.class.getDeclaredMethod(Enhancements.isObfuscated ? "spawnParticle" : "spawnParticle", Integer.TYPE, Boolean.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, int[].class)).setAccessible(true);
        }
        catch (NoSuchMethodException | SecurityException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void onMouseInput(final LeftClickEvent e) {
        if (!this.mc.inGameHasFocus || this.mc.objectMouseOver == null) {
            return;
        }
        if (this.shouldDoEffect()) {
            this.attemptEffect();
        }
    }
    
    private boolean shouldDoEffect() {
        final Entity entity = this.mc.objectMouseOver.entityHit;
        return (entity instanceof EntityLiving || (entity instanceof EntityOtherPlayerMP && ((EntityOtherPlayerMP)entity).isEntityAlive())) && (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SURVIVAL || this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE);
    }
    
    private void attemptEffect() {
        final BlockPos tmp = this.mc.objectMouseOver.entityHit.getPosition();
        if (Config.instance().enableBloodSound) {
            final float random = (float)Math.random() * 0.5f + 0.5f;
            this.mc.thePlayer.playSound("orangemarshall:blood_effect2", 1.0f, random);
        }
        if (Config.instance().enableBloodEffect) {
            try {
                for (int i = 0; i < 5; ++i) {
                    final double x = tmp.getX() + Math.random();
                    final double y = tmp.getY() + 0.3 + Math.random() * 1.3;
                    final double z = tmp.getZ() + Math.random();
                    final double xOffset = Math.random() * 2.0 - 1.3;
                    final double yOffset = Math.random() * 0.8;
                    final double zOffset = Math.random() * 2.0 - 1.3;
                    final int[] data = { Block.getStateId(Blocks.redstone_wire.getDefaultState()) };
                    this.spawnParticle.invoke(this.mc.theWorld, 77, true, x, y, z, xOffset, yOffset, zOffset, data);
                }
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex2) {
                final Exception ex;
                final Exception e = ex;
                e.printStackTrace();
            }
        }
    }
}
