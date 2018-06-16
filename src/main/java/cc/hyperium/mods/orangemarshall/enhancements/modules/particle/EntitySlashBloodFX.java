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
    private IBlockState sourceState;
    private BlockPos sourcePos;
    
    protected EntitySlashBloodFX(final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final IBlockState state) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.sourceState = state;
        this.setParticleIcon(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
        this.particleGravity = state.getBlock().blockParticleGravity;
        this.particleRed = (float)(Math.random() * 0.25 + 0.3);
        final float n = 0.0f;
        this.particleBlue = n;
        this.particleGreen = n;
        this.particleScale /= 2.5f;
    }
    
    public EntitySlashBloodFX setBlockPos(final BlockPos pos) {
        this.sourcePos = pos;
        if (this.sourceState.getBlock() == Blocks.grass) {
            return this;
        }
        final int i = this.sourceState.getBlock().colorMultiplier((IBlockAccess)this.worldObj, pos);
        this.particleRed *= (i >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (i >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (i & 0xFF) / 255.0f;
        return this;
    }
    
    public EntitySlashBloodFX func_174845_l() {
        this.sourcePos = new BlockPos(this.posX, this.posY, this.posZ);
        final Block block = this.sourceState.getBlock();
        if (block == Blocks.grass) {
            return this;
        }
        final int i = block.getRenderColor(this.sourceState);
        this.particleRed *= (i >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (i >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (i & 0xFF) / 255.0f;
        return this;
    }
    
    public int getFXLayer() {
        return 1;
    }
    
    public void renderParticle(final WorldRenderer worldRendererIn, final Entity entityIn, final float partialTicks, final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY, final float rotationXZ) {
        float f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0f) / 16.0f;
        float f2 = f + 0.015609375f;
        float f3 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0f) / 16.0f;
        float f4 = f3 + 0.015609375f;
        final float f5 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            f = this.particleIcon.getInterpolatedU((double)(this.particleTextureJitterX / 4.0f * 16.0f));
            f2 = this.particleIcon.getInterpolatedU((double)((this.particleTextureJitterX + 1.0f) / 4.0f * 16.0f));
            f3 = this.particleIcon.getInterpolatedV((double)(this.particleTextureJitterY / 4.0f * 16.0f));
            f4 = this.particleIcon.getInterpolatedV((double)((this.particleTextureJitterY + 1.0f) / 4.0f * 16.0f));
        }
        final float f6 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - EntitySlashBloodFX.interpPosX);
        final float f7 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - EntitySlashBloodFX.interpPosY);
        final float f8 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - EntitySlashBloodFX.interpPosZ);
        final int i = this.getBrightnessForRender(partialTicks);
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        worldRendererIn.pos((double)(f6 - rotationX * f5 - rotationXY * f5), (double)(f7 - rotationZ * f5), (double)(f8 - rotationYZ * f5 - rotationXZ * f5)).tex((double)f, (double)f4).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(j, k).endVertex();
        worldRendererIn.pos((double)(f6 - rotationX * f5 + rotationXY * f5), (double)(f7 + rotationZ * f5), (double)(f8 - rotationYZ * f5 + rotationXZ * f5)).tex((double)f, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(j, k).endVertex();
        worldRendererIn.pos((double)(f6 + rotationX * f5 + rotationXY * f5), (double)(f7 + rotationZ * f5), (double)(f8 + rotationYZ * f5 + rotationXZ * f5)).tex((double)f2, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(j, k).endVertex();
        worldRendererIn.pos((double)(f6 + rotationX * f5 - rotationXY * f5), (double)(f7 - rotationZ * f5), (double)(f8 + rotationYZ * f5 - rotationXZ * f5)).tex((double)f2, (double)f4).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(j, k).endVertex();
    }
    
    public int getBrightnessForRender(final float partialTicks) {
        final int i = super.getBrightnessForRender(partialTicks);
        int j = 0;
        if (this.worldObj.isBlockLoaded(this.sourcePos)) {
            j = this.worldObj.getCombinedLight(this.sourcePos, 0);
        }
        return (i == 0) ? j : i;
    }
    
    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
        public EntityFX getEntityFX(final int particleID, final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final int... p_178902_15_) {
            return new EntitySlashBloodFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Block.getStateById(p_178902_15_[0])).func_174845_l();
        }
    }
}
