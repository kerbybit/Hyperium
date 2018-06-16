package cc.hyperium.mods.orangemarshall.enhancements.modules;

import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;

public class FovModifier
{
    private static final float MODIFIER_SPEED = 0.1f;
    private static final float MODIFIER_SLOWNESS = -0.075f;
    private static final float MODIFIER_SPRINT = 0.15f;
    private static final Map<Integer, Float> MODIFIER_BY_TICK;
    private static final float MAX_BOW_TICKS = 20.0f;
    
    public FovModifier() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onFovChange(final FOVUpdateEvent event) {
        float base = 1.0f;
        if (event.entity.func_70051_ag()) {
            base += (float)(0.15000000596046448 * Config.instance().sprintFovFactor);
        }
        if (event.entity.func_71011_bu() != null && event.entity.func_71011_bu().func_77973_b().equals(Items.field_151031_f)) {
            final int duration = (int)Math.min(event.entity.func_71057_bx(), 20.0f);
            final float modifier = FovModifier.MODIFIER_BY_TICK.get(duration);
            base -= (float)(modifier * Config.instance().bowFovFactor);
        }
        final Collection<PotionEffect> effects = (Collection<PotionEffect>)event.entity.func_70651_bq();
        for (final PotionEffect effect : effects) {
            if (effect.func_76456_a() == 1) {
                base += (float)(0.1f * (effect.func_76458_c() + 1) * Config.instance().speedFovFactor);
            }
            if (effect.func_76456_a() == 2) {
                base += (float)(-0.075f * (effect.func_76458_c() + 1) * Config.instance().slownessFovFactor);
            }
        }
        event.newfov = base;
    }
    
    static {
        (MODIFIER_BY_TICK = new HashMap<Integer, Float>()).put(0, 0.0f);
        FovModifier.MODIFIER_BY_TICK.put(1, 3.7497282E-4f);
        FovModifier.MODIFIER_BY_TICK.put(2, 0.0015000105f);
        FovModifier.MODIFIER_BY_TICK.put(3, 0.0033749938f);
        FovModifier.MODIFIER_BY_TICK.put(4, 0.0059999824f);
        FovModifier.MODIFIER_BY_TICK.put(5, 0.009374976f);
        FovModifier.MODIFIER_BY_TICK.put(6, 0.013499975f);
        FovModifier.MODIFIER_BY_TICK.put(7, 0.01837498f);
        FovModifier.MODIFIER_BY_TICK.put(8, 0.023999989f);
        FovModifier.MODIFIER_BY_TICK.put(9, 0.030375004f);
        FovModifier.MODIFIER_BY_TICK.put(10, 0.037500024f);
        FovModifier.MODIFIER_BY_TICK.put(11, 0.04537499f);
        FovModifier.MODIFIER_BY_TICK.put(12, 0.05400002f);
        FovModifier.MODIFIER_BY_TICK.put(13, 0.063374996f);
        FovModifier.MODIFIER_BY_TICK.put(14, 0.07349998f);
        FovModifier.MODIFIER_BY_TICK.put(15, 0.084375024f);
        FovModifier.MODIFIER_BY_TICK.put(16, 0.096000016f);
        FovModifier.MODIFIER_BY_TICK.put(17, 0.10837501f);
        FovModifier.MODIFIER_BY_TICK.put(18, 0.121500015f);
        FovModifier.MODIFIER_BY_TICK.put(19, 0.13537502f);
        FovModifier.MODIFIER_BY_TICK.put(20, 0.14999998f);
    }
}
