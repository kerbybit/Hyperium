package cc.hyperium.mods.orangemarshall.enhancements.modules.chat;

import net.minecraft.client.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import com.google.common.collect.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import net.minecraft.event.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import org.apache.logging.log4j.*;

public class BetterChat extends GuiNewChat
{
    private static final Logger logger;
    private Minecraft mc;
    private String screenshotMessage;
    private final List<String> sentMessages;
    private final List<ChatLine> chatLines;
    private final List<ChatLine> field_146253_i;
    private int scrollPos;
    private boolean isScrolled;
    private Config config;
    private int maxChatLines;
    
    public BetterChat(final Minecraft mcIn) {
        super(mcIn);
        this.screenshotMessage = new ChatComponentTranslation("screenshot.success", new Object[0]).func_150260_c();
        this.sentMessages = (List<String>)Lists.newArrayList();
        this.chatLines = (List<ChatLine>)Lists.newArrayList();
        this.field_146253_i = (List<ChatLine>)Lists.newArrayList();
        this.config = Config.instance();
        this.maxChatLines = Math.max(50, Math.min(1500, this.config.longerChatLines));
        this.mc = mcIn;
    }
    
    public void func_146234_a(IChatComponent chatComponent, final int chatLineId) {
        if (this.isScreenshotMessage(chatComponent)) {
            ScreenshotImprovements.onChat(chatComponent);
        }
        if (this.config.showTimestamps) {
            chatComponent = this.getTimestamp().func_150257_a(chatComponent);
        }
        this.setChatLine(chatComponent, chatLineId, this.mc.field_71456_v.func_73834_c(), false);
        final String text = StringUtil.removeFormatting(chatComponent.func_150260_c(), "�");
        BetterChat.logger.info("[CHAT] " + text);
    }
    
    public boolean isScreenshotMessage(final IChatComponent chatComponent) {
        return chatComponent.func_150260_c().startsWith(this.screenshotMessage);
    }
    
    private IChatComponent getTimestamp() {
        final Calendar cal = Calendar.getInstance();
        final String time = String.format("%02d/%02d/%02d @ %02d:%02d:%02d", cal.get(5), cal.get(2), cal.get(1), cal.get(11), cal.get(12), cal.get(13));
        final ChatComponentText timestampComponent = new ChatComponentText(EnumChatFormatting.RESET + " ");
        final ChatStyle style = new ChatStyle();
        style.func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (IChatComponent)new ChatComponentText(time)));
        timestampComponent.func_150255_a(style);
        return (IChatComponent)timestampComponent;
    }
    
    private void setChatLine(final IChatComponent chatComponent, final int chatLineId, final int p_146237_3_, final boolean p_146237_4_) {
        if (chatLineId != 0) {
            this.func_146242_c(chatLineId);
        }
        final int i = MathHelper.func_76141_d(this.func_146228_f() / this.func_146244_h());
        final List<IChatComponent> list = (List<IChatComponent>)GuiUtilRenderComponents.func_178908_a(chatComponent, i, this.mc.field_71466_p, false, false);
        final boolean flag = this.func_146241_e();
        for (final IChatComponent ichatcomponent : list) {
            if (flag && this.scrollPos > 0) {
                this.isScrolled = true;
                if (this.chatLines.size() <= this.maxChatLines) {
                    ++this.scrollPos;
                    final int max = this.maxChatLines - this.func_146232_i();
                    if (this.scrollPos > max) {
                        this.scrollPos = max;
                    }
                }
            }
            this.field_146253_i.add(0, new ChatLine(p_146237_3_, ichatcomponent, chatLineId));
        }
        while (this.field_146253_i.size() > this.maxChatLines) {
            this.field_146253_i.remove(this.field_146253_i.size() - 1);
        }
        if (!p_146237_4_) {
            this.chatLines.add(0, new ChatLine(p_146237_3_, chatComponent, chatLineId));
            while (this.chatLines.size() > this.maxChatLines) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
    }
    
    public void func_146230_a(final int p_146230_1_) {
        if (this.mc.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN) {
            final int i = this.func_146232_i();
            boolean flag = false;
            int j = 0;
            final int k = this.field_146253_i.size();
            final float f = this.mc.field_71474_y.field_74357_r * 0.9f + 0.1f;
            if (k > 0) {
                if (this.func_146241_e()) {
                    flag = true;
                }
                final float f2 = this.func_146244_h();
                final int l = MathHelper.func_76123_f(this.func_146228_f() / f2);
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b(2.0f, 20.0f, 0.0f);
                GlStateManager.func_179152_a(f2, f2, 1.0f);
                for (int i2 = 0; i2 + this.scrollPos < this.field_146253_i.size() && i2 < i; ++i2) {
                    final ChatLine chatline = this.field_146253_i.get(i2 + this.scrollPos);
                    if (chatline != null) {
                        final int j2 = p_146230_1_ - chatline.func_74540_b();
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
    
    private void drawBackgroundIfTrue(final int left, final int top, final int right, final int bottom, final int color) {
        if (!this.config.disabledChatBackground) {
            func_73734_a(left, top, right, bottom, color);
        }
    }
    
    public void func_146231_a() {
        this.field_146253_i.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }
    
    public void func_146227_a(final IChatComponent p_146227_1_) {
        this.func_146234_a(p_146227_1_, 0);
    }
    
    public void func_146245_b() {
        this.field_146253_i.clear();
        this.func_146240_d();
        for (int i = this.chatLines.size() - 1; i >= 0; --i) {
            final ChatLine chatline = this.chatLines.get(i);
            this.setChatLine(chatline.func_151461_a(), chatline.func_74539_c(), chatline.func_74540_b(), true);
        }
    }
    
    public List<String> func_146238_c() {
        return this.sentMessages;
    }
    
    public void func_146239_a(final String p_146239_1_) {
        if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(p_146239_1_)) {
            this.sentMessages.add(p_146239_1_);
        }
    }
    
    public void func_146240_d() {
        this.scrollPos = 0;
        this.isScrolled = false;
    }
    
    public void func_146229_b(final int p_146229_1_) {
        this.scrollPos += p_146229_1_;
        final int i = this.field_146253_i.size();
        if (this.scrollPos > i - this.func_146232_i()) {
            this.scrollPos = i - this.func_146232_i();
        }
        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }
    
    public IChatComponent func_146236_a(final int p_146236_1_, final int p_146236_2_) {
        if (!this.func_146241_e()) {
            return null;
        }
        final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        final int i = scaledresolution.func_78325_e();
        final float f = this.func_146244_h();
        int j = p_146236_1_ / i - 3;
        int k = p_146236_2_ / i - 27;
        j = MathHelper.func_76141_d(j / f);
        k = MathHelper.func_76141_d(k / f);
        if (j < 0 || k < 0) {
            return null;
        }
        final int l = Math.min(this.func_146232_i(), this.field_146253_i.size());
        if (j <= MathHelper.func_76141_d(this.func_146228_f() / this.func_146244_h()) && k < this.mc.field_71466_p.field_78288_b * l + l) {
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
    
    public void func_146242_c(final int p_146242_1_) {
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
    
    static {
        logger = LogManager.getLogger("net.minecraft.client.gui.GuiNewChat");
    }
}
