package cc.hyperium.mods.orangemarshall.enhancements.custom;

import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.item.*;
import net.minecraft.crash.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.*;

public class CustomHitboxRenderManager extends RenderManager
{
    private Minecraft mc;
    private Config config;
    private static final FieldWrapper<Boolean> renderOutlinesField;
    private static final FieldWrapper<RenderManager> renderManagerField;
    
    public CustomHitboxRenderManager(final TextureManager renderEngineIn, final RenderItem itemRendererIn) {
        super(renderEngineIn, itemRendererIn);
        this.mc = Minecraft.getMinecraft();
        this.config = Config.instance();
    }
    
    public void initialize() {
        CustomHitboxRenderManager.renderManagerField.set(this.mc.renderGlobal, this);
    }
    
    public boolean doRenderEntity(final Entity entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final boolean hideDebugBox) {
        Render<Entity> render = null;
        try {
            render = (Render<Entity>)this.getEntityRenderObject(entity);
            if (render != null && this.renderEngine != null) {
                try {
                    if (render instanceof RendererLivingEntity) {
                        final boolean renderOutlines = CustomHitboxRenderManager.renderOutlinesField.get(this);
                        ((RendererLivingEntity)render).setRenderOutlines(renderOutlines);
                    }
                    render.doRender(entity, x, y, z, entityYaw, partialTicks);
                }
                catch (Throwable throwable2) {
                    throw new ReportedException(CrashReport.makeCrashReport(throwable2, "Rendering entity in world"));
                }
                try {
                    final boolean renderOutlines = CustomHitboxRenderManager.renderOutlinesField.get(this);
                    if (!renderOutlines) {
                        render.doRenderShadowAndFire(entity, x, y, z, entityYaw, partialTicks);
                    }
                }
                catch (Throwable throwable3) {
                    throw new ReportedException(CrashReport.makeCrashReport(throwable3, "Post-rendering entity in world"));
                }
                final boolean debugBoundingBox = this.isDebugBoundingBox();
                final boolean red = this.config.enableRedHitboxOnMouseover && (this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit != null && this.mc.objectMouseOver.entityHit == entity);
                boolean flag = debugBoundingBox && !entity.isInvisible() && !hideDebugBox && !(entity instanceof EntityArmorStand) && !(entity instanceof EntityExpBottle) && !(entity instanceof EntityItem) && !(entity instanceof EntityItemFrame) && !(entity instanceof EntityMinecart) && !(entity instanceof EntityPainting) && !(entity instanceof EntityXPOrb);
                if (this.config.enableHitboxDistance) {
                    flag = (flag && debugBoundingBox && !entity.isInvisible() && !hideDebugBox && this.getDistanceSqToEntity((Entity)this.mc.thePlayer, entity) > this.config.hitboxDistance * this.config.hitboxDistance);
                }
                if (flag) {
                    try {
                        this.renderDebugBoundingBox(entity, x, y, z, entityYaw, partialTicks, red);
                    }
                    catch (Throwable throwable4) {
                        throw new ReportedException(CrashReport.makeCrashReport(throwable4, "Rendering entity hitbox in world"));
                    }
                }
            }
            else if (this.renderEngine != null) {
                return false;
            }
            return true;
        }
        catch (Throwable throwable5) {
            final CrashReport crashreport = CrashReport.makeCrashReport(throwable5, "Rendering entity in world");
            final CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being rendered");
            entity.addEntityCrashInfo(crashreportcategory);
            final CrashReportCategory crashreportcategory2 = crashreport.makeCategory("Renderer details");
            crashreportcategory2.addCrashSection("Assigned renderer", (Object)render);
            crashreportcategory2.addCrashSection("Location", (Object)CrashReportCategory.getCoordinateInfo(x, y, z));
            crashreportcategory2.addCrashSection("Rotation", (Object)entityYaw);
            crashreportcategory2.addCrashSection("Delta", (Object)partialTicks);
            throw new ReportedException(crashreport);
        }
    }
    
    private void renderDebugBoundingBox(final Entity entityIn, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final boolean red) {
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        final float f = entityIn.width / 2.0f;
        final AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
        final AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(axisalignedbb.minX - entityIn.posX + x, axisalignedbb.minY - entityIn.posY + y, axisalignedbb.minZ - entityIn.posZ + z, axisalignedbb.maxX - entityIn.posX + x, axisalignedbb.maxY - entityIn.posY + y, axisalignedbb.maxZ - entityIn.posZ + z);
        if (red) {
            RenderGlobal.drawOutlinedBoundingBox(axisalignedbb2, 255, 0, 0, 255);
        }
        else {
            RenderGlobal.drawOutlinedBoundingBox(axisalignedbb2, 255, 255, 255, 255);
        }
        if (entityIn instanceof EntityLivingBase && !this.config.disableHitboxEyeHeight) {
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x - f, y + entityIn.getEyeHeight() - 0.009999999776482582, z - f, x + f, y + entityIn.getEyeHeight() + 0.009999999776482582, z + f), 255, 0, 0, 255);
        }
        final Tessellator tessellator = Tessellator.getInstance();
        if (!this.config.disableHitboxEyesight) {
            final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            final Vec3 vec3 = entityIn.getLook(partialTicks);
            worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(x, y + entityIn.getEyeHeight(), z).color(0, 0, 255, 255).endVertex();
            worldrenderer.pos(x + vec3.xCoord * 2.0, y + entityIn.getEyeHeight() + vec3.yCoord * 2.0, z + vec3.zCoord * 2.0).color(0, 0, 255, 255).endVertex();
            tessellator.draw();
        }
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
    }
    
    private float getDistanceSqToEntity(final Entity entityIn, final Entity entityIn2) {
        final float f = (float)(entityIn2.posX - entityIn.posX);
        final float f2 = (float)(entityIn2.posY - entityIn.posY);
        final float f3 = (float)(entityIn2.posZ - entityIn.posZ);
        return f * f + f2 * f2 + f3 * f3;
    }
    
    static {
        renderOutlinesField = new FieldWrapper<Boolean>(Enhancements.isObfuscated ? "renderOutlines" : "renderOutlines", RenderManager.class);
        renderManagerField = new FieldWrapper<RenderManager>(Enhancements.isObfuscated ? "renderManager" : "renderManager", RenderGlobal.class);
    }
}
