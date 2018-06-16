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
    public final List<ChatLine> field_146253_i;
    public int scrollPos;
    public boolean isScrolled;
    private int right;
    private int left;
    private int nameSpacing;
    private boolean hasUnreadMessage;
    private Supplier<Integer> getY;
    
    public ChatTab(final BetterChatWithTabs chat, final String name, final List<Conditions> conditions, final int x) {
        this.mc = Minecraft.func_71410_x();
        this.chatLines = (List<ChatLine>)Lists.newArrayList();
        this.field_146253_i = (List<ChatLine>)Lists.newArrayList();
        this.nameSpacing = 4;
        this.hasUnreadMessage = false;
        this.getY = (() -> new ScaledResolution(this.mc).func_78328_b() - this.mc.field_71466_p.field_78288_b * 3 - 1);
        this.name = name;
        this.conditions = (List<Conditions>)Lists.newArrayList((Iterable)conditions);
        this.chat = chat;
        this.left = x - 2;
        this.right = this.left + this.mc.field_71466_p.func_78256_a(name) + this.nameSpacing + 1;
        final int top = this.getY.get();
        final int bottom = top + this.mc.field_71466_p.field_78288_b + 5;
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
        if (this.mc.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN) {
            final int i = this.chat.func_146232_i();
            boolean flag = false;
            int j = 0;
            final int k = this.field_146253_i.size();
            final float f = this.mc.field_71474_y.field_74357_r * 0.9f + 0.1f;
            if (k > 0) {
                if (this.chat.func_146241_e()) {
                    flag = true;
                }
                final float f2 = this.chat.func_146244_h();
                final int l = MathHelper.func_76123_f(this.chat.func_146228_f() / f2);
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b(2.0f, 20.0f, 0.0f);
                GlStateManager.func_179152_a(f2, f2, 1.0f);
                for (int i2 = 0; i2 + this.scrollPos < this.field_146253_i.size() && i2 < i; ++i2) {
                    final ChatLine chatline = this.field_146253_i.get(i2 + this.scrollPos);
                    if (chatline != null) {
                        final int j2 = counter - chatline.func_74540_b();
                        if (j2 < 200 || flag) {
                            double d0 = j2 / 200.0;
                            d0 = 1.0 - d0;
                            d0 *= 10.0;
                            d0 = MathHelper.func_151237_a(d0, 0.0, 1.0);
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
                                final String s = chatline.func_151461_a().func_150254_d();
                                GlStateManager.func_179147_l();
                                this.mc.field_71466_p.func_175063_a(s, (float)i3, (float)(j3 - 8), 16777215 + (l2 << 24));
                                GlStateManager.func_179118_c();
                                GlStateManager.func_179084_k();
                            }
                        }
                    }
                }
                if (flag) {
                    final int k2 = this.mc.field_71466_p.field_78288_b;
                    GlStateManager.func_179109_b(-3.0f, 0.0f, 0.0f);
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
                GlStateManager.func_179121_F();
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
            BetterChatWithTabs.func_73734_a(left, top, right, bottom, color);
        }
    }
    
    public void clearChatMessages() {
        this.field_146253_i.clear();
        this.chatLines.clear();
        this.hasUnreadMessage = false;
    }
    
    public void deleteChatLine(final int p_146242_1_) {
        Iterator<ChatLine> iterator = this.field_146253_i.iterator();
        while (iterator.hasNext()) {
            final ChatLine chatline = iterator.next();
            if (chatline.func_74539_c() == p_146242_1_) {
                iterator.remove();
            }
        }
        iterator = this.chatLines.iterator();
        while (iterator.hasNext()) {
            final ChatLine chatline2 = iterator.next();
            if (chatline2.func_74539_c() == p_146242_1_) {
                iterator.remove();
                break;
            }
        }
    }
    
    public void setChatLine(final IChatComponent chatComponent, final int chatLineId, final int p_146237_3_, final boolean p_146237_4_) {
        if (chatLineId != 0) {
            this.deleteChatLine(chatLineId);
        }
        final int i = MathHelper.func_76141_d(this.chat.func_146228_f() / this.chat.func_146244_h());
        final List<IChatComponent> list = (List<IChatComponent>)GuiUtilRenderComponents.func_178908_a(chatComponent, i, this.mc.field_71466_p, false, false);
        final boolean flag = this.chat.func_146241_e();
        final int maxChatLines = this.chat.getMaxChatLinesPerTab();
        for (final IChatComponent ichatcomponent : list) {
            if (flag && this.scrollPos > 0) {
                this.isScrolled = true;
                if (this.chatLines.size() <= maxChatLines) {
                    ++this.scrollPos;
                    final int max = maxChatLines - this.chat.func_146232_i();
                    if (this.scrollPos > max) {
                        this.scrollPos = max;
                    }
                }
            }
            this.field_146253_i.add(0, new ChatLine(p_146237_3_, ichatcomponent, chatLineId));
        }
        while (this.field_146253_i.size() > maxChatLines) {
            this.field_146253_i.remove(this.field_146253_i.size() - 1);
        }
        if (!p_146237_4_) {
            this.chatLines.add(0, new ChatLine(p_146237_3_, chatComponent, chatLineId));
            while (this.chatLines.size() > maxChatLines) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
        if (!this.isSelected()) {
            this.hasUnreadMessage = true;
        }
    }
    
    public void refreshChat() {
        this.field_146253_i.clear();
        this.resetScroll();
        for (int i = this.chatLines.size() - 1; i >= 0; --i) {
            final ChatLine chatline = this.chatLines.get(i);
            this.setChatLine(chatline.func_151461_a(), chatline.func_74539_c(), chatline.func_74540_b(), true);
        }
    }
    
    public void resetScroll() {
        this.scrollPos = 0;
        this.isScrolled = false;
    }
    
    public void scroll(final int p_146229_1_) {
        this.scrollPos += p_146229_1_;
        final int i = this.field_146253_i.size();
        if (this.scrollPos > i - this.chat.func_146232_i()) {
            this.scrollPos = i - this.chat.func_146232_i();
        }
        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }
    
    public IChatComponent getChatComponent(final int p_146236_1_, final int p_146236_2_) {
        if (!this.chat.func_146241_e()) {
            return null;
        }
        final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        final int i = scaledresolution.func_78325_e();
        final float f = this.chat.func_146244_h();
        int j = p_146236_1_ / i - 3;
        int k = p_146236_2_ / i - 27;
        j = MathHelper.func_76141_d(j / f);
        k = MathHelper.func_76141_d(k / f);
        if (j < 0 || k < 0) {
            return null;
        }
        final int l = Math.min(this.chat.func_146232_i(), this.field_146253_i.size());
        if (j <= MathHelper.func_76141_d(this.chat.func_146228_f() / this.chat.func_146244_h()) && k < this.mc.field_71466_p.field_78288_b * l + l) {
            final int i2 = k / this.mc.field_71466_p.field_78288_b + this.scrollPos;
            if (i2 >= 0 && i2 < this.field_146253_i.size()) {
                final ChatLine chatline = this.field_146253_i.get(i2);
                int j2 = 0;
                for (final IChatComponent ichatcomponent : chatline.func_151461_a()) {
                    if (ichatcomponent instanceof ChatComponentText) {
                        j2 += this.mc.field_71466_p.func_78256_a(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent).func_150265_g(), false));
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
