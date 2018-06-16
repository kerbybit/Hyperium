package cc.hyperium.mods.orangemarshall.enhancements.config.components;

import net.minecraft.client.*;
import java.util.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public abstract class GuiScrollingList
{
    private final Minecraft client;
    protected final int listWidth;
    protected final int listHeight;
    protected final int screenWidth;
    protected final int screenHeight;
    protected final int top;
    protected final int bottom;
    protected final int right;
    protected final int left;
    protected final int slotHeight;
    private int scrollUpActionId;
    private int scrollDownActionId;
    protected int mouseX;
    protected int mouseY;
    private float initialMouseClickY;
    private float scrollFactor;
    private float scrollDistance;
    protected int selectedIndex;
    private long lastClickTime;
    private boolean highlightSelected;
    private boolean hasHeader;
    private int headerHeight;
    protected boolean captureMouse;
    
    public GuiScrollingList(final Minecraft client, final int width, final int height, final int top, final int bottom, final int left, final int entryHeight) {
        this(client, width, height, top, bottom, left, entryHeight, width, height);
    }
    
    public GuiScrollingList(final Minecraft client, final int width, final int height, final int top, final int bottom, final int left, final int entryHeight, final int screenWidth, final int screenHeight) {
        this.initialMouseClickY = -2.0f;
        this.selectedIndex = -1;
        this.lastClickTime = 0L;
        this.highlightSelected = true;
        this.captureMouse = true;
        this.client = client;
        this.listWidth = width;
        this.listHeight = height;
        this.top = top;
        this.bottom = bottom;
        this.slotHeight = entryHeight;
        this.left = left;
        this.right = width + this.left;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    
    public void func_27258_a(final boolean p_27258_1_) {
        this.highlightSelected = p_27258_1_;
    }
    
    @Deprecated
    protected void func_27259_a(final boolean hasFooter, final int footerHeight) {
        this.setHeaderInfo(hasFooter, footerHeight);
    }
    
    protected void setHeaderInfo(final boolean hasHeader, final int headerHeight) {
        this.hasHeader = hasHeader;
        this.headerHeight = headerHeight;
        if (!hasHeader) {
            this.headerHeight = 0;
        }
    }
    
    protected abstract int getSize();
    
    protected abstract void elementClicked(final int p0, final boolean p1);
    
    protected abstract boolean isSelected(final int p0);
    
    protected int getContentHeight() {
        return this.getSize() * this.slotHeight + this.headerHeight;
    }
    
    protected abstract void drawBackground();
    
    protected abstract void drawSlot(final int p0, final int p1, final int p2, final int p3, final Tessellator p4);
    
    @Deprecated
    protected void func_27260_a(final int entryRight, final int relativeY, final Tessellator tess) {
    }
    
    protected void drawHeader(final int entryRight, final int relativeY, final Tessellator tess) {
        this.func_27260_a(entryRight, relativeY, tess);
    }
    
    @Deprecated
    protected void func_27255_a(final int x, final int y) {
    }
    
    protected void clickHeader(final int x, final int y) {
        this.func_27255_a(x, y);
    }
    
    @Deprecated
    protected void func_27257_b(final int mouseX, final int mouseY) {
    }
    
    protected void drawScreen(final int mouseX, final int mouseY) {
        this.func_27257_b(mouseX, mouseY);
    }
    
    public int func_27256_c(final int x, final int y) {
        final int left = this.left + 1;
        final int right = this.left + this.listWidth - 7;
        final int relativeY = y - this.top - this.headerHeight + (int)this.scrollDistance - 4;
        final int entryIndex = relativeY / this.slotHeight;
        return (x >= left && x <= right && entryIndex >= 0 && relativeY >= 0 && entryIndex < this.getSize()) ? entryIndex : -1;
    }
    
    public void registerScrollButtons(final List buttons, final int upActionID, final int downActionID) {
        this.scrollUpActionId = upActionID;
        this.scrollDownActionId = downActionID;
    }
    
    private void applyScrollLimits() {
        int listHeight = this.getContentHeight() - (this.bottom - this.top - 4);
        if (listHeight < 0) {
            listHeight /= 2;
        }
        if (this.scrollDistance < 0.0f) {
            this.scrollDistance = 0.0f;
        }
        if (this.scrollDistance > listHeight) {
            this.scrollDistance = listHeight;
        }
    }
    
    public void actionPerformed(final GuiButton button) {
        if (button.field_146124_l) {
            if (button.field_146127_k == this.scrollUpActionId) {
                this.scrollDistance -= this.slotHeight * 2 / 3;
                this.initialMouseClickY = -2.0f;
                this.applyScrollLimits();
            }
            else if (button.field_146127_k == this.scrollDownActionId) {
                this.scrollDistance += this.slotHeight * 2 / 3;
                this.initialMouseClickY = -2.0f;
                this.applyScrollLimits();
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.drawBackground();
        final boolean isHovering = mouseX >= this.left && mouseX <= this.left + this.listWidth && mouseY >= this.top && mouseY <= this.bottom;
        final int listLength = this.getSize();
        final int scrollBarWidth = 6;
        final int scrollBarRight = this.left + this.listWidth;
        final int scrollBarLeft = scrollBarRight - scrollBarWidth;
        final int entryLeft = this.left;
        final int entryRight = scrollBarLeft - 1;
        final int viewHeight = this.bottom - this.top;
        final int border = 4;
        if (Mouse.isButtonDown(0)) {
            if (this.initialMouseClickY == -1.0f) {
                if (isHovering) {
                    final int mouseListY = mouseY - this.top - this.headerHeight + (int)this.scrollDistance - border;
                    final int slotIndex = mouseListY / this.slotHeight;
                    if (mouseX >= entryLeft && mouseX <= entryRight && slotIndex >= 0 && mouseListY >= 0 && slotIndex < listLength) {
                        this.elementClicked(slotIndex, slotIndex == this.selectedIndex && System.currentTimeMillis() - this.lastClickTime < 250L);
                        this.selectedIndex = slotIndex;
                        this.lastClickTime = System.currentTimeMillis();
                    }
                    else if (mouseX >= entryLeft && mouseX <= entryRight && mouseListY < 0) {
                        this.clickHeader(mouseX - entryLeft, mouseY - this.top + (int)this.scrollDistance - border);
                    }
                    if (mouseX >= scrollBarLeft && mouseX <= scrollBarRight) {
                        this.scrollFactor = -1.0f;
                        int scrollHeight = this.getContentHeight() - viewHeight - border;
                        if (scrollHeight < 1) {
                            scrollHeight = 1;
                        }
                        int var13 = viewHeight * viewHeight / this.getContentHeight();
                        if (var13 < 32) {
                            var13 = 32;
                        }
                        if (var13 > viewHeight - border * 2) {
                            var13 = viewHeight - border * 2;
                        }
                        this.scrollFactor /= (viewHeight - var13) / scrollHeight;
                    }
                    else {
                        this.scrollFactor = 1.0f;
                    }
                    this.initialMouseClickY = mouseY;
                }
                else {
                    this.initialMouseClickY = -2.0f;
                }
            }
            else if (this.initialMouseClickY >= 0.0f) {
                this.scrollDistance -= (mouseY - this.initialMouseClickY) * this.scrollFactor;
                this.initialMouseClickY = mouseY;
            }
        }
        else {
            while (isHovering && Mouse.next()) {
                int scroll = Mouse.getEventDWheel();
                if (scroll != 0) {
                    if (scroll > 0) {
                        scroll = -1;
                    }
                    else if (scroll < 0) {
                        scroll = 1;
                    }
                    this.scrollDistance += scroll * this.slotHeight * 2;
                }
            }
            this.initialMouseClickY = -1.0f;
        }
        this.applyScrollLimits();
        final Tessellator tess = Tessellator.func_178181_a();
        final WorldRenderer worldr = tess.func_178180_c();
        final ScaledResolution res = new ScaledResolution(this.client);
        final double scaleW = this.client.field_71443_c / res.func_78327_c();
        final double scaleH = this.client.field_71440_d / res.func_78324_d();
        GL11.glEnable(3089);
        GL11.glScissor((int)(this.left * scaleW), (int)(this.client.field_71440_d - this.bottom * scaleH), (int)(this.listWidth * scaleW), (int)(viewHeight * scaleH));
        if (this.client.field_71441_e != null) {
            this.drawGradientRect(this.left, this.top, this.right, this.bottom, -1072689136, -804253680);
        }
        else {
            GlStateManager.func_179140_f();
            GlStateManager.func_179106_n();
            this.client.field_71446_o.func_110577_a(Gui.field_110325_k);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            final float scale = 32.0f;
            worldr.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            worldr.func_181662_b((double)this.left, (double)this.bottom, 0.0).func_181673_a((double)(this.left / scale), (double)((this.bottom + (int)this.scrollDistance) / scale)).func_181669_b(32, 32, 32, 255).func_181675_d();
            worldr.func_181662_b((double)this.right, (double)this.bottom, 0.0).func_181673_a((double)(this.right / scale), (double)((this.bottom + (int)this.scrollDistance) / scale)).func_181669_b(32, 32, 32, 255).func_181675_d();
            worldr.func_181662_b((double)this.right, (double)this.top, 0.0).func_181673_a((double)(this.right / scale), (double)((this.top + (int)this.scrollDistance) / scale)).func_181669_b(32, 32, 32, 255).func_181675_d();
            worldr.func_181662_b((double)this.left, (double)this.top, 0.0).func_181673_a((double)(this.left / scale), (double)((this.top + (int)this.scrollDistance) / scale)).func_181669_b(32, 32, 32, 255).func_181675_d();
            tess.func_78381_a();
        }
        final int baseY = this.top + border - (int)this.scrollDistance;
        if (this.hasHeader) {
            this.drawHeader(entryRight, baseY, tess);
        }
        for (int slotIdx = 0; slotIdx < listLength; ++slotIdx) {
            final int slotTop = baseY + slotIdx * this.slotHeight + this.headerHeight;
            final int slotBuffer = this.slotHeight - border;
            if (slotTop <= this.bottom && slotTop + slotBuffer >= this.top) {
                if (this.highlightSelected && this.isSelected(slotIdx)) {
                    final int min = this.left;
                    final int max = entryRight;
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.func_179090_x();
                    worldr.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                    worldr.func_181662_b((double)min, (double)(slotTop + slotBuffer + 2), 0.0).func_181673_a(0.0, 1.0).func_181669_b(128, 128, 128, 255).func_181675_d();
                    worldr.func_181662_b((double)max, (double)(slotTop + slotBuffer + 2), 0.0).func_181673_a(1.0, 1.0).func_181669_b(128, 128, 128, 255).func_181675_d();
                    worldr.func_181662_b((double)max, (double)(slotTop - 2), 0.0).func_181673_a(1.0, 0.0).func_181669_b(128, 128, 128, 255).func_181675_d();
                    worldr.func_181662_b((double)min, (double)(slotTop - 2), 0.0).func_181673_a(0.0, 0.0).func_181669_b(128, 128, 128, 255).func_181675_d();
                    worldr.func_181662_b((double)(min + 1), (double)(slotTop + slotBuffer + 1), 0.0).func_181673_a(0.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                    worldr.func_181662_b((double)(max - 1), (double)(slotTop + slotBuffer + 1), 0.0).func_181673_a(1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                    worldr.func_181662_b((double)(max - 1), (double)(slotTop - 1), 0.0).func_181673_a(1.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                    worldr.func_181662_b((double)(min + 1), (double)(slotTop - 1), 0.0).func_181673_a(0.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                    tess.func_78381_a();
                    GlStateManager.func_179098_w();
                }
                this.drawSlot(slotIdx, entryRight, slotTop, slotBuffer, tess);
            }
        }
        GlStateManager.func_179097_i();
        final int extraHeight = this.getContentHeight() - viewHeight - border;
        if (extraHeight > 0) {
            int height = viewHeight * viewHeight / this.getContentHeight();
            if (height < 32) {
                height = 32;
            }
            if (height > viewHeight - border * 2) {
                height = viewHeight - border * 2;
            }
            int barTop = (int)this.scrollDistance * (viewHeight - height) / extraHeight + this.top;
            if (barTop < this.top) {
                barTop = this.top;
            }
            GlStateManager.func_179090_x();
            worldr.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            worldr.func_181662_b((double)scrollBarLeft, (double)this.bottom, 0.0).func_181673_a(0.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            worldr.func_181662_b((double)scrollBarRight, (double)this.bottom, 0.0).func_181673_a(1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            worldr.func_181662_b((double)scrollBarRight, (double)this.top, 0.0).func_181673_a(1.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            worldr.func_181662_b((double)scrollBarLeft, (double)this.top, 0.0).func_181673_a(0.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            tess.func_78381_a();
            worldr.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            worldr.func_181662_b((double)scrollBarLeft, (double)(barTop + height), 0.0).func_181673_a(0.0, 1.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            worldr.func_181662_b((double)scrollBarRight, (double)(barTop + height), 0.0).func_181673_a(1.0, 1.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            worldr.func_181662_b((double)scrollBarRight, (double)barTop, 0.0).func_181673_a(1.0, 0.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            worldr.func_181662_b((double)scrollBarLeft, (double)barTop, 0.0).func_181673_a(0.0, 0.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            tess.func_78381_a();
            worldr.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            worldr.func_181662_b((double)scrollBarLeft, (double)(barTop + height - 1), 0.0).func_181673_a(0.0, 1.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            worldr.func_181662_b((double)(scrollBarRight - 1), (double)(barTop + height - 1), 0.0).func_181673_a(1.0, 1.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            worldr.func_181662_b((double)(scrollBarRight - 1), (double)barTop, 0.0).func_181673_a(1.0, 0.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            worldr.func_181662_b((double)scrollBarLeft, (double)barTop, 0.0).func_181673_a(0.0, 0.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            tess.func_78381_a();
        }
        this.drawScreen(mouseX, mouseY);
        GlStateManager.func_179098_w();
        GlStateManager.func_179103_j(7424);
        GlStateManager.func_179141_d();
        GlStateManager.func_179084_k();
        GL11.glDisable(3089);
    }
    
    protected void drawGradientRect(final int left, final int top, final int right, final int bottom, final int color1, final int color2) {
        final float a1 = (color1 >> 24 & 0xFF) / 255.0f;
        final float r1 = (color1 >> 16 & 0xFF) / 255.0f;
        final float g1 = (color1 >> 8 & 0xFF) / 255.0f;
        final float b1 = (color1 & 0xFF) / 255.0f;
        final float a2 = (color2 >> 24 & 0xFF) / 255.0f;
        final float r2 = (color2 >> 16 & 0xFF) / 255.0f;
        final float g2 = (color2 >> 8 & 0xFF) / 255.0f;
        final float b2 = (color2 & 0xFF) / 255.0f;
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179103_j(7425);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b((double)right, (double)top, 0.0).func_181666_a(r1, g1, b1, a1).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)top, 0.0).func_181666_a(r1, g1, b1, a1).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)bottom, 0.0).func_181666_a(r2, g2, b2, a2).func_181675_d();
        worldrenderer.func_181662_b((double)right, (double)bottom, 0.0).func_181666_a(r2, g2, b2, a2).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179103_j(7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
    }
}
