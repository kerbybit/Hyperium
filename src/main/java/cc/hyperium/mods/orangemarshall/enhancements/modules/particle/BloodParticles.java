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
        this.mc = Minecraft.func_71410_x();
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.loadMethod();
    }
    
    private void loadMethod() {
        try {
            (this.spawnParticle = World.class.getDeclaredMethod(Enhancements.isObfuscated ? "func_175720_a" : "spawnParticle", Integer.TYPE, Boolean.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, int[].class)).setAccessible(true);
        }
        catch (NoSuchMethodException | SecurityException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void onMouseInput(final LeftClickEvent e) {
        if (!this.mc.field_71415_G || this.mc.field_71476_x == null) {
            return;
        }
        if (this.shouldDoEffect()) {
            this.attemptEffect();
        }
    }
    
    private boolean shouldDoEffect() {
        final Entity entity = this.mc.field_71476_x.field_72308_g;
        return (entity instanceof EntityLiving || (entity instanceof EntityOtherPlayerMP && ((EntityOtherPlayerMP)entity).func_70089_S())) && (this.mc.field_71442_b.func_178889_l() == WorldSettings.GameType.SURVIVAL || this.mc.field_71442_b.func_178889_l() == WorldSettings.GameType.CREATIVE);
    }
    
    private void attemptEffect() {
        final BlockPos tmp = this.mc.field_71476_x.field_72308_g.func_180425_c();
        if (Config.instance().enableBloodSound) {
            final float random = (float)Math.random() * 0.5f + 0.5f;
            this.mc.field_71439_g.func_85030_a("orangemarshall:blood_effect2", 1.0f, random);
        }
        if (Config.instance().enableBloodEffect) {
            try {
                for (int i = 0; i < 5; ++i) {
                    final double x = tmp.func_177958_n() + Math.random();
                    final double y = tmp.func_177956_o() + 0.3 + Math.random() * 1.3;
                    final double z = tmp.func_177952_p() + Math.random();
                    final double xOffset = Math.random() * 2.0 - 1.3;
                    final double yOffset = Math.random() * 0.8;
                    final double zOffset = Math.random() * 2.0 - 1.3;
                    final int[] data = { Block.func_176210_f(Blocks.field_150488_af.func_176223_P()) };
                    this.spawnParticle.invoke(this.mc.field_71441_e, 77, true, x, y, z, xOffset, yOffset, zOffset, data);
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
