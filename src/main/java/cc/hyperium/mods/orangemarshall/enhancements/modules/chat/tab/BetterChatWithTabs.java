package cc.hyperium.mods.orangemarshall.enhancements.modules.chat.tab;

import net.minecraft.client.*;
import com.google.common.collect.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import cc.hyperium.mods.orangemarshall.enhancements.other.*;
import cc.hyperium.mods.orangemarshall.enhancements.modules.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.event.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

public class BetterChatWithTabs extends GuiNewChat
{
    private static final Logger logger;
    private final Minecraft mc;
    public final List<String> field_146248_g;
    private String screenshotMessage;
    private final List<ChatTab> TABS;
    private int currentNameOffsetX;
    private final int NAME_SPACING = 5;
    private ChatTab allTab;
    private ChatTab selectedTab;
    private int maxChatLines;
    
    public BetterChatWithTabs(final Minecraft mcIn) {
        super(mcIn);
        this.field_146248_g = (List<String>)Lists.newArrayList();
        this.screenshotMessage = new ChatComponentTranslation("screenshot.success", new Object[0]).func_150260_c();
        this.TABS = (List<ChatTab>)Lists.newArrayList();
        this.currentNameOffsetX = 4;
        this.maxChatLines = Math.max(50, Math.min(10000, Config.instance().longerChatLines));
        this.mc = mcIn;
        this.addAllTab();
        this.parseChatTabs();
    }
    
    private void parseChatTabs() {
        ChatTabLoader.load(this);
    }
    
    public void setSelectedTab(final ChatTab tab) {
        if (tab == null) {
            throw new NullPointerException();
        }
        if (tab != this.selectedTab) {
            if (this.mc.field_71439_g != null) {
                this.mc.field_71439_g.func_85030_a("random.wood_click", 1.0f, 1.0f);
            }
            this.selectedTab = tab;
        }
    }
    
    public int getMaxChatLinesPerTab() {
        return this.maxChatLines / (this.TABS.size() + 1);
    }
    
    public ChatTab getSelectedTab() {
        return this.selectedTab;
    }
    
    private void addAllTab() {
        final ChatTab chatTab = new ChatTab(this, "All", Lists.newArrayList((Object[])new Conditions[] { Conditions.alwaysTrue() }), this.currentNameOffsetX);
        this.allTab = chatTab;
        this.selectedTab = chatTab;
        this.currentNameOffsetX += 5 + this.mc.field_71466_p.func_78256_a(this.allTab.getName());
    }
    
    public void createAndAddChatTab(final String name, final List<Conditions> conditions) {
        final ChatTab tab = new ChatTab(this, name, conditions, this.currentNameOffsetX);
        this.currentNameOffsetX += 5 + this.mc.field_71466_p.func_78256_a(name);
        this.TABS.add(tab);
    }
    
    public void func_146230_a(final int p_146230_1_) {
        this.selectedTab.drawChat(p_146230_1_);
    }
    
    public boolean hasUnreadMessage() {
        if (this.allTab.hasUnreadMessage()) {
            return true;
        }
        for (final ChatTab tab : this.TABS) {
            if (tab.hasUnreadMessage()) {
                return true;
            }
        }
        return false;
    }
    
    public void func_146231_a() {
        this.TABS.forEach(ChatTab::clearChatMessages);
        this.allTab.clearChatMessages();
    }
    
    public void func_146227_a(final IChatComponent p_146227_1_) {
        this.func_146234_a(p_146227_1_, 0);
    }
    
    public void func_146234_a(IChatComponent chatComponent, final int chatLineId) {
        if (this.isScreenshotMessage(chatComponent)) {
            ScreenshotImprovements.onChat(chatComponent);
        }
        final String text = StringUtil.removeFormatting(chatComponent.func_150260_c(), "�");
        final List<ChatTab> tabs = this.findChatTabForMessage(text);
        if (Config.instance().showTimestamps) {
            chatComponent = this.getTimestamp().func_150257_a(chatComponent);
        }
        for (final ChatTab tab : tabs) {
            tab.setChatLine(chatComponent, chatLineId, this.mc.field_71456_v.func_73834_c(), false);
        }
        this.allTab.setChatLine(chatComponent, chatLineId, this.mc.field_71456_v.func_73834_c(), false);
        BetterChatWithTabs.logger.info("[CHAT] " + text);
    }
    
    public boolean isScreenshotMessage(final IChatComponent chatComponent) {
        return chatComponent.func_150260_c().startsWith(this.screenshotMessage);
    }
    
    private List<ChatTab> findChatTabForMessage(final String message) {
        return this.TABS.stream().filter(tab -> tab.takesChatMessage(message)).collect((Collector<? super Object, ?, List<ChatTab>>)Collectors.toList());
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
    
    public void func_146245_b() {
        this.selectedTab.refreshChat();
    }
    
    public List<String> func_146238_c() {
        return this.field_146248_g;
    }
    
    public void func_146239_a(final String p_146239_1_) {
        if (this.field_146248_g.isEmpty() || !this.field_146248_g.get(this.field_146248_g.size() - 1).equals(p_146239_1_)) {
            this.field_146248_g.add(p_146239_1_);
        }
    }
    
    public void func_146240_d() {
        this.selectedTab.resetScroll();
    }
    
    public void func_146229_b(final int p_146229_1_) {
        this.selectedTab.scroll(p_146229_1_);
    }
    
    public IChatComponent func_146236_a(final int p_146236_1_, final int p_146236_2_) {
        return this.selectedTab.getChatComponent(p_146236_1_, p_146236_2_);
    }
    
    public boolean func_146241_e() {
        return this.mc.field_71462_r instanceof GuiChat;
    }
    
    public void func_146242_c(final int p_146242_1_) {
        this.selectedTab.deleteChatLine(p_146242_1_);
    }
    
    public int func_146228_f() {
        return calculateChatboxWidth(this.mc.field_71474_y.field_96692_F);
    }
    
    public int func_146246_g() {
        return calculateChatboxHeight(this.func_146241_e() ? this.mc.field_71474_y.field_96694_H : this.mc.field_71474_y.field_96693_G);
    }
    
    public float func_146244_h() {
        return this.mc.field_71474_y.field_96691_E;
    }
    
    public static int calculateChatboxWidth(final float p_146233_0_) {
        final int i = 320;
        final int j = 40;
        return MathHelper.func_76141_d(p_146233_0_ * (i - j) + j);
    }
    
    public static int calculateChatboxHeight(final float p_146243_0_) {
        final int i = 180;
        final int j = 20;
        return MathHelper.func_76141_d(p_146243_0_ * (i - j) + j);
    }
    
    public int func_146232_i() {
        return this.func_146246_g() / 9;
    }
    
    static {
        logger = LogManager.getLogger("net.minecraft.client.gui.GuiNewChat");
    }
}
