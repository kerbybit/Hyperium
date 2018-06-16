package cc.hyperium.mods.orangemarshall.enhancements.modules.tab;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import java.lang.reflect.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import cc.hyperium.mods.orangemarshall.enhancements.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraftforge.fml.relauncher.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;

public class CustomGuiPlayerTabOverlay extends GuiPlayerTabOverlay
{
    private Minecraft mc;
    private boolean transparent;
    private static final Ordering<NetworkPlayerInfo> ordering;
    private static final FieldWrapper<IChatComponent> footer;
    private static final FieldWrapper<IChatComponent> header;
    private Method drawScoreboardValues;
    private Config config;
    
    public CustomGuiPlayerTabOverlay(final Minecraft mcIn, final GuiIngame guiIngameIn) {
        super(mcIn, guiIngameIn);
        this.config = Config.instance();
        this.mc = mcIn;
        this.initialize();
    }
    
    private void initialize() {
        try {
            (this.drawScoreboardValues = GuiPlayerTabOverlay.class.getDeclaredMethod(Enhancements.isObfuscated ? "drawScoreboardValues" : "drawScoreboardValues", ScoreObjective.class, Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, NetworkPlayerInfo.class)).setAccessible(true);
        }
        catch (NoSuchMethodException | SecurityException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    public void renderPlayerlist(final int width, final Scoreboard scoreboardIn, final ScoreObjective scoreObjectiveIn) {
        this.transparent = this.config.tabTransparency;
        List<NetworkPlayerInfo> list = (List<NetworkPlayerInfo>)CustomGuiPlayerTabOverlay.ordering.sortedCopy((Iterable)this.mc.getNetHandler().getPlayerInfoMap());
        int i = 0;
        int j = 0;
        for (final NetworkPlayerInfo networkplayerinfo : list) {
            int k = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(networkplayerinfo));
            i = Math.max(i, k);
            if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                k = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getValueFromObjective(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                j = Math.max(j, k);
            }
        }
        final int size_tab = Math.max(this.config.tabSize, 5);
        list = list.subList(0, Math.min(list.size(), size_tab));
        int i2;
        int l3;
        int coloumn_length;
        int j2;
        for (l3 = (i2 = list.size()), coloumn_length = Math.max(this.config.tabColoumnLength, 5), j2 = 1; i2 > coloumn_length; i2 = (l3 + j2 - 1) / j2) {
            ++j2;
        }
        final boolean flag = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
        int m;
        if (scoreObjectiveIn != null) {
            if (scoreObjectiveIn.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                m = 90;
            }
            else {
                m = j;
            }
        }
        else {
            m = 0;
        }
        final int i3 = Math.min(j2 * ((flag ? 9 : 0) + i + m + 13), width - 50) / j2;
        final int j3 = width / 2 - (i3 * j2 + (j2 - 1) * 5) / 2;
        int k2 = 10;
        int l4 = i3 * j2 + (j2 - 1) * 5;
        List<String> list2 = null;
        List<String> list3 = null;
        final IChatComponent header = CustomGuiPlayerTabOverlay.header.get(this);
        if (header != null) {
            list2 = (List<String>)this.mc.fontRendererObj.listFormattedStringToWidth(header.getFormattedText(), width - 50);
            for (final String s : list2) {
                l4 = Math.max(l4, this.mc.fontRendererObj.getStringWidth(s));
            }
        }
        final IChatComponent footer = CustomGuiPlayerTabOverlay.footer.get(this);
        if (footer != null) {
            list3 = (List<String>)this.mc.fontRendererObj.listFormattedStringToWidth(footer.getFormattedText(), width - 50);
            for (final String s2 : list3) {
                l4 = Math.max(l4, this.mc.fontRendererObj.getStringWidth(s2));
            }
        }
        if (list2 != null) {
            drawRect(width / 2 - l4 / 2 - 1, k2 - 1, width / 2 + l4 / 2 + 1, k2 + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT, this.transparent ? 855638016 : Integer.MIN_VALUE);
            for (final String s3 : list2) {
                final int i4 = this.mc.fontRendererObj.getStringWidth(s3);
                this.mc.fontRendererObj.drawStringWithShadow(s3, (float)(width / 2 - i4 / 2), (float)k2, this.transparent ? 1308622847 : -1);
                k2 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
            ++k2;
        }
        drawRect(width / 2 - l4 / 2 - 1, k2 - 1, width / 2 + l4 / 2 + 1, k2 + i2 * 9, this.transparent ? 855638016 : Integer.MIN_VALUE);
        for (int k3 = 0; k3 < l3; ++k3) {
            final int l5 = k3 / i2;
            final int i5 = k3 % i2;
            int j4 = j3 + l5 * i3 + l5 * 5;
            final int k4 = k2 + i5 * 9;
            drawRect(j4, k4, j4 + i3, k4 + 8, 553648127);
            GlStateManager.color(1.0f, 1.0f, 1.0f, this.transparent ? 0.3f : 1.0f);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            if (k3 < list.size()) {
                final NetworkPlayerInfo networkplayerinfo2 = list.get(k3);
                String s4 = this.getPlayerName(networkplayerinfo2);
                final GameProfile gameprofile = networkplayerinfo2.getGameProfile();
                if (flag) {
                    final EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(gameprofile.getId());
                    final boolean flag2 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && (gameprofile.getName().equals("Dinnerbone") || gameprofile.getName().equals("Grumm"));
                    this.mc.getTextureManager().bindTexture(networkplayerinfo2.getLocationSkin());
                    final int l6 = 8 + (flag2 ? 8 : 0);
                    final int i6 = 8 * (flag2 ? -1 : 1);
                    Gui.drawScaledCustomSizeModalRect(j4, k4, 8.0f, (float)l6, 8, i6, 8, 8, 64.0f, 64.0f);
                    if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
                        final int j5 = 8 + (flag2 ? 8 : 0);
                        final int k5 = 8 * (flag2 ? -1 : 1);
                        Gui.drawScaledCustomSizeModalRect(j4, k4, 40.0f, (float)j5, 8, k5, 8, 8, 64.0f, 64.0f);
                    }
                    j4 += 9;
                }
                if (networkplayerinfo2.getGameType() == WorldSettings.GameType.SPECTATOR) {
                    s4 = EnumChatFormatting.ITALIC + s4;
                    this.mc.fontRendererObj.drawStringWithShadow(s4, (float)j4, (float)k4, this.transparent ? 872415231 : -1862270977);
                }
                else {
                    this.mc.fontRendererObj.drawStringWithShadow(s4, (float)j4, (float)k4, this.transparent ? 1895825407 : -1);
                }
                if (scoreObjectiveIn != null && networkplayerinfo2.getGameType() != WorldSettings.GameType.SPECTATOR) {
                    final int k6 = j4 + i + 1;
                    final int l7 = k6 + m;
                    if (l7 - k6 > 5) {
                        try {
                            this.drawScoreboardValues.invoke(this, scoreObjectiveIn, k4, gameprofile.getName(), k6, l7, networkplayerinfo2);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                this.drawPing(i3, j4 - (flag ? 9 : 0), k4, networkplayerinfo2);
            }
        }
        if (list3 != null) {
            k2 = k2 + i2 * 9 + 1;
            drawRect(width / 2 - l4 / 2 - 1, k2 - 1, width / 2 + l4 / 2 + 1, k2 + list3.size() * this.mc.fontRendererObj.FONT_HEIGHT, this.transparent ? 855638016 : Integer.MIN_VALUE);
            for (final String s5 : list3) {
                final int j6 = this.mc.fontRendererObj.getStringWidth(s5);
                this.mc.fontRendererObj.drawStringWithShadow(s5, (float)(width / 2 - j6 / 2), (float)k2, this.transparent ? 1308622847 : -1);
                k2 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
        }
    }
    
    protected void drawPing(final int p_175245_1_, final int p_175245_2_, final int p_175245_3_, final NetworkPlayerInfo networkPlayerInfoIn) {
        final int ping = networkPlayerInfoIn.getResponseTime();
        GlStateManager.color(1.0f, 1.0f, 1.0f, this.transparent ? 0.3f : 1.0f);
        this.mc.getTextureManager().bindTexture(CustomGuiPlayerTabOverlay.icons);
        final int i = 0;
        int j = 0;
        if (ping < 0) {
            j = 5;
        }
        else if (ping < 150) {
            j = 0;
        }
        else if (ping < 300) {
            j = 1;
        }
        else if (ping < 600) {
            j = 2;
        }
        else if (ping < 1000) {
            j = 3;
        }
        else {
            j = 4;
        }
        this.zLevel += 100.0f;
        if (this.config.showPingInTab) {
            int colour;
            if (ping > 500) {
                colour = 11141120;
            }
            else if (ping > 300) {
                colour = 11184640;
            }
            else if (ping > 200) {
                colour = 11193344;
            }
            else if (ping > 135) {
                colour = 2128640;
            }
            else if (ping > 70) {
                colour = 39168;
            }
            else if (ping > 0) {
                colour = 47872;
            }
            else {
                colour = 11141120;
            }
            if (ping > 0 && ping < 10000) {
                GL11.glPushMatrix();
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                final int x = p_175245_2_ + p_175245_1_ - (this.mc.fontRendererObj.getStringWidth(ping + "") >> 1) - 2;
                final int y = p_175245_3_ + (this.mc.fontRendererObj.FONT_HEIGHT >> 2);
                this.mc.fontRendererObj.drawStringWithShadow(ping + "", (float)(2 * x), (float)(2 * y), colour);
                GL11.glScalef(2.0f, 2.0f, 2.0f);
                GL11.glPopMatrix();
            }
        }
        else {
            this.drawTexturedModalRect(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0 + i * 10, 176 + j * 8, 10, 8);
        }
        this.zLevel -= 100.0f;
    }
    
    static {
        ordering = Ordering.from((Comparator)new PlayerComparator());
        footer = new FieldWrapper<IChatComponent>(Enhancements.isObfuscated ? "footer" : "footer", GuiPlayerTabOverlay.class);
        header = new FieldWrapper<IChatComponent>(Enhancements.isObfuscated ? "header" : "header", GuiPlayerTabOverlay.class);
    }
    
    @SideOnly(Side.CLIENT)
    private static class PlayerComparator implements Comparator<NetworkPlayerInfo>
    {
        @Override
        public int compare(final NetworkPlayerInfo p_compare_1_, final NetworkPlayerInfo p_compare_2_) {
            final ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
            final ScorePlayerTeam scoreplayerteam2 = p_compare_2_.getPlayerTeam();
            return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != WorldSettings.GameType.SPECTATOR, p_compare_2_.getGameType() != WorldSettings.GameType.SPECTATOR).compare((Comparable)((scoreplayerteam != null) ? scoreplayerteam.getRegisteredName() : ""), (Comparable)((scoreplayerteam2 != null) ? scoreplayerteam2.getRegisteredName() : "")).compare((Comparable)p_compare_1_.getGameProfile().getName(), (Comparable)p_compare_2_.getGameProfile().getName()).result();
        }
    }
}
