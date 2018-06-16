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
        this.mc = Minecraft.func_71410_x();
        this.config = Config.instance();
    }
    
    public void initialize() {
        CustomHitboxRenderManager.renderManagerField.set(this.mc.field_71438_f, this);
    }
    
    public boolean func_147939_a(final Entity entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final boolean p_147939_10_) {
        Render<Entity> render = null;
        try {
            render = (Render<Entity>)this.func_78713_a(entity);
            if (render != null && this.field_78724_e != null) {
                try {
                    if (render instanceof RendererLivingEntity) {
                        final boolean renderOutlines = CustomHitboxRenderManager.renderOutlinesField.get(this);
                        ((RendererLivingEntity)render).func_177086_a(renderOutlines);
                    }
                    render.func_76986_a(entity, x, y, z, entityYaw, partialTicks);
                }
                catch (Throwable throwable2) {
                    throw new ReportedException(CrashReport.func_85055_a(throwable2, "Rendering entity in world"));
                }
                try {
                    final boolean renderOutlines = CustomHitboxRenderManager.renderOutlinesField.get(this);
                    if (!renderOutlines) {
                        render.func_76979_b(entity, x, y, z, entityYaw, partialTicks);
                    }
                }
                catch (Throwable throwable3) {
                    throw new ReportedException(CrashReport.func_85055_a(throwable3, "Post-rendering entity in world"));
                }
                final boolean debugBoundingBox = this.func_178634_b();
                final boolean red = this.config.enableRedHitboxOnMouseover && (this.mc.field_71476_x != null && this.mc.field_71476_x.field_72308_g != null && this.mc.field_71476_x.field_72308_g == entity);
                boolean flag = debugBoundingBox && !entity.func_82150_aj() && !p_147939_10_ && !(entity instanceof EntityArmorStand) && !(entity instanceof EntityExpBottle) && !(entity instanceof EntityItem) && !(entity instanceof EntityItemFrame) && !(entity instanceof EntityMinecart) && !(entity instanceof EntityPainting) && !(entity instanceof EntityXPOrb);
                if (this.config.enableHitboxDistance) {
                    flag = (flag && debugBoundingBox && !entity.func_82150_aj() && !p_147939_10_ && this.getDistanceSqToEntity((Entity)this.mc.field_71439_g, entity) > this.config.hitboxDistance * this.config.hitboxDistance);
                }
                if (flag) {
                    try {
                        this.renderDebugBoundingBox(entity, x, y, z, entityYaw, partialTicks, red);
                    }
                    catch (Throwable throwable4) {
                        throw new ReportedException(CrashReport.func_85055_a(throwable4, "Rendering entity hitbox in world"));
                    }
                }
            }
            else if (this.field_78724_e != null) {
                return false;
            }
            return true;
        }
        catch (Throwable throwable5) {
            final CrashReport crashreport = CrashReport.func_85055_a(throwable5, "Rendering entity in world");
            final CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being rendered");
            entity.func_85029_a(crashreportcategory);
            final CrashReportCategory crashreportcategory2 = crashreport.func_85058_a("Renderer details");
            crashreportcategory2.func_71507_a("Assigned renderer", (Object)render);
            crashreportcategory2.func_71507_a("Location", (Object)CrashReportCategory.func_85074_a(x, y, z));
            crashreportcategory2.func_71507_a("Rotation", (Object)entityYaw);
            crashreportcategory2.func_71507_a("Delta", (Object)partialTicks);
            throw new ReportedException(crashreport);
        }
    }
    
    private void renderDebugBoundingBox(final Entity entityIn, final double p_85094_2_, final double p_85094_4_, final double p_85094_6_, final float p_85094_8_, final float p_85094_9_, final boolean red) {
        GlStateManager.func_179132_a(false);
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GlStateManager.func_179129_p();
        GlStateManager.func_179084_k();
        final float f = entityIn.field_70130_N / 2.0f;
        final AxisAlignedBB axisalignedbb = entityIn.func_174813_aQ();
        final AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(axisalignedbb.field_72340_a - entityIn.field_70165_t + p_85094_2_, axisalignedbb.field_72338_b - entityIn.field_70163_u + p_85094_4_, axisalignedbb.field_72339_c - entityIn.field_70161_v + p_85094_6_, axisalignedbb.field_72336_d - entityIn.field_70165_t + p_85094_2_, axisalignedbb.field_72337_e - entityIn.field_70163_u + p_85094_4_, axisalignedbb.field_72334_f - entityIn.field_70161_v + p_85094_6_);
        if (red) {
            RenderGlobal.func_181563_a(axisalignedbb2, 255, 0, 0, 255);
        }
        else {
            RenderGlobal.func_181563_a(axisalignedbb2, 255, 255, 255, 255);
        }
        if (entityIn instanceof EntityLivingBase && !this.config.disableHitboxEyeHeight) {
            RenderGlobal.func_181563_a(new AxisAlignedBB(p_85094_2_ - f, p_85094_4_ + entityIn.func_70047_e() - 0.009999999776482582, p_85094_6_ - f, p_85094_2_ + f, p_85094_4_ + entityIn.func_70047_e() + 0.009999999776482582, p_85094_6_ + f), 255, 0, 0, 255);
        }
        final Tessellator tessellator = Tessellator.func_178181_a();
        if (!this.config.disableHitboxEyesight) {
            final WorldRenderer worldrenderer = tessellator.func_178180_c();
            final Vec3 vec3 = entityIn.func_70676_i(p_85094_9_);
            worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
            worldrenderer.func_181662_b(p_85094_2_, p_85094_4_ + entityIn.func_70047_e(), p_85094_6_).func_181669_b(0, 0, 255, 255).func_181675_d();
            worldrenderer.func_181662_b(p_85094_2_ + vec3.field_72450_a * 2.0, p_85094_4_ + entityIn.func_70047_e() + vec3.field_72448_b * 2.0, p_85094_6_ + vec3.field_72449_c * 2.0).func_181669_b(0, 0, 255, 255).func_181675_d();
            tessellator.func_78381_a();
        }
        GlStateManager.func_179098_w();
        GlStateManager.func_179145_e();
        GlStateManager.func_179089_o();
        GlStateManager.func_179084_k();
        GlStateManager.func_179132_a(true);
    }
    
    private float getDistanceSqToEntity(final Entity entityIn, final Entity entityIn2) {
        final float f = (float)(entityIn2.field_70165_t - entityIn.field_70165_t);
        final float f2 = (float)(entityIn2.field_70163_u - entityIn.field_70163_u);
        final float f3 = (float)(entityIn2.field_70161_v - entityIn.field_70161_v);
        return f * f + f2 * f2 + f3 * f3;
    }
    
    static {
        renderOutlinesField = new FieldWrapper<Boolean>(Enhancements.isObfuscated ? "field_178639_r" : "renderOutlines", RenderManager.class);
        renderManagerField = new FieldWrapper<RenderManager>(Enhancements.isObfuscated ? "field_175010_j" : "renderManager", RenderGlobal.class);
    }
}
