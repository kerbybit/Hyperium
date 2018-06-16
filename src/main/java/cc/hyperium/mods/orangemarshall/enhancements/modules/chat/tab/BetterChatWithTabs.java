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
    public final List<String> sentMessages;
    private String screenshotMessage;
    private final List<ChatTab> TABS;
    private int currentNameOffsetX;
    private final int NAME_SPACING = 5;
    private ChatTab allTab;
    private ChatTab selectedTab;
    private int maxChatLines;
    
    public BetterChatWithTabs(final Minecraft mcIn) {
        super(mcIn);
        this.sentMessages = (List<String>)Lists.newArrayList();
        this.screenshotMessage = new ChatComponentTranslation("screenshot.success", new Object[0]).getUnformattedText();
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
            if (this.mc.thePlayer != null) {
                this.mc.thePlayer.playSound("random.wood_click", 1.0f, 1.0f);
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
        this.currentNameOffsetX += 5 + this.mc.fontRendererObj.getStringWidth(this.allTab.getName());
    }
    
    public void createAndAddChatTab(final String name, final List<Conditions> conditions) {
        final ChatTab tab = new ChatTab(this, name, conditions, this.currentNameOffsetX);
        this.currentNameOffsetX += 5 + this.mc.fontRendererObj.getStringWidth(name);
        this.TABS.add(tab);
    }
    
    public void drawChat(final int updateCounter) {
        this.selectedTab.drawChat(updateCounter);
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
    
    public void clearChatMessages() {
        this.TABS.forEach(ChatTab::clearChatMessages);
        this.allTab.clearChatMessages();
    }
    
    public void printChatMessage(final IChatComponent chatComponent) {
        this.printChatMessageWithOptionalDeletion(chatComponent, 0);
    }
    
    public void printChatMessageWithOptionalDeletion(IChatComponent chatComponent, final int chatLineId) {
        if (this.isScreenshotMessage(chatComponent)) {
            ScreenshotImprovements.onChat(chatComponent);
        }
        final String text = StringUtil.removeFormatting(chatComponent.getUnformattedText(), "�");
        final List<ChatTab> tabs = this.findChatTabForMessage(text);
        if (Config.instance().showTimestamps) {
            chatComponent = this.getTimestamp().appendSibling(chatComponent);
        }
        for (final ChatTab tab : tabs) {
            tab.setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
        }
        this.allTab.setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
        BetterChatWithTabs.logger.info("[CHAT] " + text);
    }
    
    public boolean isScreenshotMessage(final IChatComponent chatComponent) {
        return chatComponent.getUnformattedText().startsWith(this.screenshotMessage);
    }
    
    private List<ChatTab> findChatTabForMessage(final String message) {
        return this.TABS.stream().filter(tab -> tab.takesChatMessage(message)).collect((Collector<? super Object, ?, List<ChatTab>>)Collectors.toList());
    }
    
    private IChatComponent getTimestamp() {
        final Calendar cal = Calendar.getInstance();
        final String time = String.format("%02d/%02d/%02d @ %02d:%02d:%02d", cal.get(5), cal.get(2), cal.get(1), cal.get(11), cal.get(12), cal.get(13));
        final ChatComponentText timestampComponent = new ChatComponentText(EnumChatFormatting.RESET + " ");
        final ChatStyle style = new ChatStyle();
        style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (IChatComponent)new ChatComponentText(time)));
        timestampComponent.setChatStyle(style);
        return (IChatComponent)timestampComponent;
    }
    
    public void refreshChat() {
        this.selectedTab.refreshChat();
    }
    
    public List<String> getSentMessages() {
        return this.sentMessages;
    }
    
    public void addToSentMessages(final String message) {
        if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(message)) {
            this.sentMessages.add(message);
        }
    }
    
    public void resetScroll() {
        this.selectedTab.resetScroll();
    }
    
    public void scroll(final int amount) {
        this.selectedTab.scroll(amount);
    }
    
    public IChatComponent getChatComponent(final int mouseX, final int mouseY) {
        return this.selectedTab.getChatComponent(mouseX, mouseY);
    }
    
    public boolean getChatOpen() {
        return this.mc.currentScreen instanceof GuiChat;
    }
    
    public void deleteChatLine(final int id) {
        this.selectedTab.deleteChatLine(id);
    }
    
    public int getChatWidth() {
        return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
    }
    
    public int getChatHeight() {
        return calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
    }
    
    public float getChatScale() {
        return this.mc.gameSettings.chatScale;
    }
    
    public static int calculateChatboxWidth(final float scale) {
        final int i = 320;
        final int j = 40;
        return MathHelper.floor_float(scale * (i - j) + j);
    }
    
    public static int calculateChatboxHeight(final float scale) {
        final int i = 180;
        final int j = 20;
        return MathHelper.floor_float(scale * (i - j) + j);
    }
    
    public int getLineCount() {
        return this.getChatHeight() / 9;
    }
    
    static {
        logger = LogManager.getLogger("net.minecraft.client.gui.GuiNewChat");
    }
}
