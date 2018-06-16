package cc.hyperium.mods.orangemarshall.enhancements.modules.chat.tab;

import cc.hyperium.mods.orangemarshall.enhancements.other.*;
import net.minecraft.client.*;
import java.util.function.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import java.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class ChatTab
{
    private String name;
    private List<Conditions> conditions;
    private Minecraft mc;
    private BetterChatWithTabs chat;
    public final List<ChatLine> chatLines;
    public final List<ChatLine> drawnChatLines;
    public int scrollPos;
    public boolean isScrolled;
    private int right;
    private int left;
    private int nameSpacing;
    private boolean hasUnreadMessage;
    private Supplier<Integer> getY;
    
    public ChatTab(final BetterChatWithTabs chat, final String name, final List<Conditions> conditions, final int x) {
        this.mc = Minecraft.getMinecraft();
        this.chatLines = (List<ChatLine>)Lists.newArrayList();
        this.drawnChatLines = (List<ChatLine>)Lists.newArrayList();
        this.nameSpacing = 4;
        this.hasUnreadMessage = false;
        this.getY = (() -> new ScaledResolution(this.mc).getScaledHeight() - this.mc.fontRendererObj.FONT_HEIGHT * 3 - 1);
        this.name = name;
        this.conditions = (List<Conditions>)Lists.newArrayList((Iterable)conditions);
        this.chat = chat;
        this.left = x - 2;
        this.right = this.left + this.mc.fontRendererObj.getStringWidth(name) + this.nameSpacing + 1;
        final int top = this.getY.get();
        final int bottom = top + this.mc.fontRendererObj.FONT_HEIGHT + 5;
        new ChatTabRenderer(name, this.left, this.getY, this.right - this.left, bottom - top, this);
    }
    
    public boolean takesChatMessage(final String message) {
        return this.conditions.stream().filter(con -> con.meetsConditions(message)).count() > 0L;
    }
    
    public boolean isSelected() {
        return this.chat.getSelectedTab() == this;
    }
    
    public void select() {
        this.chat.setSelectedTab(this);
        this.hasUnreadMessage = false;
    }
    
    public BetterChatWithTabs getChat() {
        return this.chat;
    }
    
    public void drawChat(final int counter) {
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            final int i = this.chat.getLineCount();
            boolean flag = false;
            int j = 0;
            final int k = this.drawnChatLines.size();
            final float f = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (k > 0) {
                if (this.chat.getChatOpen()) {
                    flag = true;
                }
                final float f2 = this.chat.getChatScale();
                final int l = MathHelper.ceiling_float_int(this.chat.getChatWidth() / f2);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0f, 20.0f, 0.0f);
                GlStateManager.scale(f2, f2, 1.0f);
                for (int i2 = 0; i2 + this.scrollPos < this.drawnChatLines.size() && i2 < i; ++i2) {
                    final ChatLine chatline = this.drawnChatLines.get(i2 + this.scrollPos);
                    if (chatline != null) {
                        final int j2 = counter - chatline.getUpdatedCounter();
                        if (j2 < 200 || flag) {
                            double d0 = j2 / 200.0;
                            d0 = 1.0 - d0;
                            d0 *= 10.0;
                            d0 = MathHelper.clamp_double(d0, 0.0, 1.0);
                            d0 *= d0;
                            int l2 = (int)(255.0 * d0);
                            if (flag) {
                                l2 = 255;
                            }
                            l2 *= (int)f;
                            ++j;
                            if (l2 > 3) {
                                final int i3 = 0;
                                final int j3 = -i2 * 9;
                                this.drawBackgroundIfTrue(i3, j3 - 9, i3 + l + 4, j3, l2 / 2 << 24);
                                final String s = chatline.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();
                                this.mc.fontRendererObj.drawStringWithShadow(s, (float)i3, (float)(j3 - 8), 16777215 + (l2 << 24));
                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }
                if (flag) {
                    final int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
                    GlStateManager.translate(-3.0f, 0.0f, 0.0f);
                    final int l3 = k * k2 + k;
                    final int i4 = j * k2 + j;
                    final int j4 = this.scrollPos * i4 / k;
                    final int k3 = i4 * i4 / l3;
                    if (l3 != i4) {
                        final int k4 = (j4 > 0) ? 170 : 96;
                        final int l4 = this.isScrolled ? 13382451 : 3355562;
                        this.drawBackgroundIfTrue(0, -j4, 2, -j4 - k3, l4 + (k4 << 24));
                        this.drawBackgroundIfTrue(2, -j4, 1, -j4 - k3, 13421772 + (k4 << 24));
                    }
                }
                GlStateManager.popMatrix();
            }
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean hasUnreadMessage() {
        return this.hasUnreadMessage;
    }
    
    private void drawBackgroundIfTrue(final int left, final int top, final int right, final int bottom, final int color) {
        if (!Config.instance().disabledChatBackground) {
            BetterChatWithTabs.drawRect(left, top, right, bottom, color);
        }
    }
    
    public void clearChatMessages() {
        this.drawnChatLines.clear();
        this.chatLines.clear();
        this.hasUnreadMessage = false;
    }
    
    public void deleteChatLine(final int id) {
        Iterator<ChatLine> iterator = this.drawnChatLines.iterator();
        while (iterator.hasNext()) {
            final ChatLine chatline = iterator.next();
            if (chatline.getChatLineID() == id) {
                iterator.remove();
            }
        }
        iterator = this.chatLines.iterator();
        while (iterator.hasNext()) {
            final ChatLine chatline2 = iterator.next();
            if (chatline2.getChatLineID() == id) {
                iterator.remove();
                break;
            }
        }
    }
    
    public void setChatLine(final IChatComponent chatComponent, final int chatLineId, final int updateCounter, final boolean displayOnly) {
        if (chatLineId != 0) {
            this.deleteChatLine(chatLineId);
        }
        final int i = MathHelper.floor_float(this.chat.getChatWidth() / this.chat.getChatScale());
        final List<IChatComponent> list = (List<IChatComponent>)GuiUtilRenderComponents.splitText(chatComponent, i, this.mc.fontRendererObj, false, false);
        final boolean flag = this.chat.getChatOpen();
        final int maxChatLines = this.chat.getMaxChatLinesPerTab();
        for (final IChatComponent ichatcomponent : list) {
            if (flag && this.scrollPos > 0) {
                this.isScrolled = true;
                if (this.chatLines.size() <= maxChatLines) {
                    ++this.scrollPos;
                    final int max = maxChatLines - this.chat.getLineCount();
                    if (this.scrollPos > max) {
                        this.scrollPos = max;
                    }
                }
            }
            this.drawnChatLines.add(0, new ChatLine(updateCounter, ichatcomponent, chatLineId));
        }
        while (this.drawnChatLines.size() > maxChatLines) {
            this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
        }
        if (!displayOnly) {
            this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
            while (this.chatLines.size() > maxChatLines) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
        if (!this.isSelected()) {
            this.hasUnreadMessage = true;
        }
    }
    
    public void refreshChat() {
        this.drawnChatLines.clear();
        this.resetScroll();
        for (int i = this.chatLines.size() - 1; i >= 0; --i) {
            final ChatLine chatline = this.chatLines.get(i);
            this.setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
        }
    }
    
    public void resetScroll() {
        this.scrollPos = 0;
        this.isScrolled = false;
    }
    
    public void scroll(final int amount) {
        this.scrollPos += amount;
        final int i = this.drawnChatLines.size();
        if (this.scrollPos > i - this.chat.getLineCount()) {
            this.scrollPos = i - this.chat.getLineCount();
        }
        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }
    
    public IChatComponent getChatComponent(final int mouseX, final int mouseY) {
        if (!this.chat.getChatOpen()) {
            return null;
        }
        final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        final int i = scaledresolution.getScaleFactor();
        final float f = this.chat.getChatScale();
        int j = mouseX / i - 3;
        int k = mouseY / i - 27;
        j = MathHelper.floor_float(j / f);
        k = MathHelper.floor_float(k / f);
        if (j < 0 || k < 0) {
            return null;
        }
        final int l = Math.min(this.chat.getLineCount(), this.drawnChatLines.size());
        if (j <= MathHelper.floor_float(this.chat.getChatWidth() / this.chat.getChatScale()) && k < this.mc.fontRendererObj.FONT_HEIGHT * l + l) {
            final int i2 = k / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;
            if (i2 >= 0 && i2 < this.drawnChatLines.size()) {
                final ChatLine chatline = this.drawnChatLines.get(i2);
                int j2 = 0;
                for (final IChatComponent ichatcomponent : chatline.getChatComponent()) {
                    if (ichatcomponent instanceof ChatComponentText) {
                        j2 += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent).getChatComponentText_TextValue(), false));
                        if (j2 > j) {
                            return ichatcomponent;
                        }
                        continue;
                    }
                }
            }
            return null;
        }
        return null;
    }
}
