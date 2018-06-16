package cc.hyperium.mods.orangemarshall.enhancements.event;

import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import net.minecraft.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import cc.hyperium.mods.orangemarshall.enhancements.*;
import net.minecraft.client.settings.*;

public class EventPoster
{
    private static final FieldWrapper<Integer> pressTime;
    private Minecraft mc;
    private boolean prevLeft;
    private boolean prevRight;
    
    public EventPoster() {
        this.mc = Minecraft.func_71410_x();
        this.prevLeft = false;
        this.prevRight = false;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onClick(final InputEvent.MouseInputEvent event) {
        final boolean leftclick = EventPoster.pressTime.get(this.mc.field_71474_y.field_74312_F) != 0;
        final boolean rightclick = EventPoster.pressTime.get(this.mc.field_71474_y.field_74313_G) != 0;
        if (leftclick) {
            if (!this.prevLeft) {
                MinecraftForge.EVENT_BUS.post((Event)new LeftClickEvent());
                this.prevLeft = true;
            }
        }
        else {
            this.prevLeft = false;
        }
        if (rightclick) {
            if (!this.prevRight) {
                MinecraftForge.EVENT_BUS.post((Event)new RightClickEvent());
                this.prevRight = true;
            }
        }
        else {
            this.prevRight = false;
        }
    }
    
    static {
        pressTime = new FieldWrapper<Integer>(Enhancements.isObfuscated ? "field_151474_i" : "pressTime", KeyBinding.class);
    }
}
