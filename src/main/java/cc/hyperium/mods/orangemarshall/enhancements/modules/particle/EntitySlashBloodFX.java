package cc.hyperium.mods.orangemarshall.enhancements.modules.particle;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.particle.*;

@SideOnly(Side.CLIENT)
public class EntitySlashBloodFX extends EntityFX
{
    private IBlockState field_174847_a;
    private BlockPos field_181019_az;
    
    protected EntitySlashBloodFX(final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final IBlockState state) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.field_174847_a = state;
        this.func_180435_a(Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178122_a(state));
        this.field_70545_g = state.func_177230_c().field_149763_I;
        this.field_70552_h = (float)(Math.random() * 0.25 + 0.3);
        final float n = 0.0f;
        this.field_70551_j = n;
        this.field_70553_i = n;
        this.field_70544_f /= 2.5f;
    }
    
    public EntitySlashBloodFX func_174846_a(final BlockPos pos) {
        this.field_181019_az = pos;
        if (this.field_174847_a.func_177230_c() == Blocks.field_150349_c) {
            return this;
        }
        final int i = this.field_174847_a.func_177230_c().func_176202_d((IBlockAccess)this.field_70170_p, pos);
        this.field_70552_h *= (i >> 16 & 0xFF) / 255.0f;
        this.field_70553_i *= (i >> 8 & 0xFF) / 255.0f;
        this.field_70551_j *= (i & 0xFF) / 255.0f;
        return this;
    }
    
    public EntitySlashBloodFX func_174845_l() {
        this.field_181019_az = new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        final Block block = this.field_174847_a.func_177230_c();
        if (block == Blocks.field_150349_c) {
            return this;
        }
        final int i = block.func_180644_h(this.field_174847_a);
        this.field_70552_h *= (i >> 16 & 0xFF) / 255.0f;
        this.field_70553_i *= (i >> 8 & 0xFF) / 255.0f;
        this.field_70551_j *= (i & 0xFF) / 255.0f;
        return this;
    }
    
    public int func_70537_b() {
        return 1;
    }
    
    public void func_180434_a(final WorldRenderer worldRendererIn, final Entity entityIn, final float partialTicks, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        float f = (this.field_94054_b + this.field_70548_b / 4.0f) / 16.0f;
        float f2 = f + 0.015609375f;
        float f3 = (this.field_94055_c + this.field_70549_c / 4.0f) / 16.0f;
        float f4 = f3 + 0.015609375f;
        final float f5 = 0.1f * this.field_70544_f;
        if (this.field_70550_a != null) {
            f = this.field_70550_a.func_94214_a((double)(this.field_70548_b / 4.0f * 16.0f));
            f2 = this.field_70550_a.func_94214_a((double)((this.field_70548_b + 1.0f) / 4.0f * 16.0f));
            f3 = this.field_70550_a.func_94207_b((double)(this.field_70549_c / 4.0f * 16.0f));
            f4 = this.field_70550_a.func_94207_b((double)((this.field_70549_c + 1.0f) / 4.0f * 16.0f));
        }
        final float f6 = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * partialTicks - EntitySlashBloodFX.field_70556_an);
        final float f7 = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * partialTicks - EntitySlashBloodFX.field_70554_ao);
        final float f8 = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * partialTicks - EntitySlashBloodFX.field_70555_ap);
        final int i = this.func_70070_b(partialTicks);
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        worldRendererIn.func_181662_b((double)(f6 - p_180434_4_ * f5 - p_180434_7_ * f5), (double)(f7 - p_180434_5_ * f5), (double)(f8 - p_180434_6_ * f5 - p_180434_8_ * f5)).func_181673_a((double)f, (double)f4).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0f).func_181671_a(j, k).func_181675_d();
        worldRendererIn.func_181662_b((double)(f6 - p_180434_4_ * f5 + p_180434_7_ * f5), (double)(f7 + p_180434_5_ * f5), (double)(f8 - p_180434_6_ * f5 + p_180434_8_ * f5)).func_181673_a((double)f, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0f).func_181671_a(j, k).func_181675_d();
        worldRendererIn.func_181662_b((double)(f6 + p_180434_4_ * f5 + p_180434_7_ * f5), (double)(f7 + p_180434_5_ * f5), (double)(f8 + p_180434_6_ * f5 + p_180434_8_ * f5)).func_181673_a((double)f2, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0f).func_181671_a(j, k).func_181675_d();
        worldRendererIn.func_181662_b((double)(f6 + p_180434_4_ * f5 - p_180434_7_ * f5), (double)(f7 - p_180434_5_ * f5), (double)(f8 + p_180434_6_ * f5 - p_180434_8_ * f5)).func_181673_a((double)f2, (double)f4).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0f).func_181671_a(j, k).func_181675_d();
    }
    
    public int func_70070_b(final float partialTicks) {
        final int i = super.func_70070_b(partialTicks);
        int j = 0;
        if (this.field_70170_p.func_175667_e(this.field_181019_az)) {
            j = this.field_70170_p.func_175626_b(this.field_181019_az, 0);
        }
        return (i == 0) ? j : i;
    }
    
    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
        public EntityFX func_178902_a(final int particleID, final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final int... p_178902_15_) {
            return new EntitySlashBloodFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Block.func_176220_d(p_178902_15_[0])).func_174845_l();
        }
    }
}
