/*
 *     Copyright (C) 2018  Hyperium <https://hyperium.cc/>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cc.hyperium.mods.keystrokes.keys.impl;

import cc.hyperium.event.EventBus;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.LeftMouseClickEvent;
import cc.hyperium.event.RightMouseClickEvent;
import cc.hyperium.mods.keystrokes.KeystrokesMod;
import cc.hyperium.mods.keystrokes.keys.IKey;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CPSKey extends IKey {
    
    private boolean wasPressed = true;
    
    private List<Long> clicks = new ArrayList<>();
    
    public CPSKey(KeystrokesMod mod, int xOffset, int yOffset) {
        super(mod, xOffset, yOffset);
        
        EventBus.INSTANCE.register(this);
    }
    
    @Override
    public void renderKey(int x, int y) {
        int yOffset = this.yOffset;
        
        if (!this.mod.getSettings().isShowingMouseButtons()) {
            yOffset -= 24;
        }
        
        if (!this.mod.getSettings().isShowingSpacebar()) {
            yOffset -= 18;
        }
        
        Mouse.poll();
        
        int textColor = getColor();
        int pressedColor = getPressedColor();
        
        Gui.drawRect(x + this.xOffset, y + yOffset, x + this.xOffset + 70, y + yOffset + 16, new Color(0, 0, 0, 120).getRGB());
        
        String name = this.getCPS() + " CPS";
        if (this.mod.getSettings().isChroma()) {
            drawChromaString(name, ((x + (this.xOffset + 70) / 2) - this.mc.fontRendererObj.getStringWidth(name) / 2), y + (yOffset + 4));
        } else {
            drawCenteredString(name, x + ((this.xOffset + 70) / 2), y + (yOffset + 4), textColor);
        }
    }
    
    private int getCPS() {
        long time = System.currentTimeMillis();
        
        this.clicks.removeIf(o -> o + 1000L < time);
        
        return this.clicks.size();
    }
    
    @InvokeEvent
    public void onClick(LeftMouseClickEvent event) {
        System.out.println("Left Click: " + this.mod.getSettings().isLeftClick());
        if(this.mod.getSettings().isLeftClick()){
        Mouse.poll();
        this.clicks.add(System.currentTimeMillis());
        }
    }
    
    @InvokeEvent
    public void onClickRight(RightMouseClickEvent event) {
        if(!this.mod.getSettings().isLeftClick()) {
            Mouse.poll();
            this.clicks.add(System.currentTimeMillis());
        }
    }
}