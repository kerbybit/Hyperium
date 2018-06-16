package cc.hyperium.mods.orangemarshall.enhancements.modules;

import net.minecraft.client.settings.*;
import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.input.*;

public class DropListener
{
    private boolean cooldown;
    private KeyBinding keybind;
    private Minecraft mc;
    
    public DropListener() {
        this.cooldown = false;
        this.keybind = KeyBindings.createAndRegister("Drop Stack", 22, "key.categories.gameplay");
        this.mc = Minecraft.func_71410_x();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onTossItem(final InputEvent.KeyInputEvent e) {
        if (this.shouldActivate()) {
            if (!this.cooldown) {
                this.drop();
                this.cooldown = true;
            }
        }
        else {
            this.cooldown = false;
        }
    }
    
    private boolean shouldActivate() {
        final int keyCodeStack = this.keybind.func_151463_i();
        final int keyCodeDrop = this.mc.field_71474_y.field_74316_C.func_151463_i();
        return keyCodeDrop > 0 && keyCodeStack > 0 && Keyboard.isKeyDown(keyCodeDrop) && Keyboard.isKeyDown(keyCodeStack);
    }
    
    private void drop() {
        this.mc.field_71439_g.func_71040_bB(true);
    }
}
