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
            (this.drawScoreboardValues = GuiPlayerTabOverlay.class.getDeclaredMethod(Enhancements.isObfuscated ? "func_175247_a" : "drawScoreboardValues", ScoreObjective.class, Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, NetworkPlayerInfo.class)).setAccessible(true);
        }
        catch (NoSuchMethodException | SecurityException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    public void func_175249_a(final int width, final Scoreboard scoreboardIn, final ScoreObjective scoreObjectiveIn) {
        this.transparent = this.config.tabTransparency;
        List<NetworkPlayerInfo> list = (List<NetworkPlayerInfo>)CustomGuiPlayerTabOverlay.ordering.sortedCopy((Iterable)this.mc.func_147114_u().func_175106_d());
        int i = 0;
        int j = 0;
        for (final NetworkPlayerInfo networkplayerinfo : list) {
            int k = this.mc.field_71466_p.func_78256_a(this.func_175243_a(networkplayerinfo));
            i = Math.max(i, k);
            if (scoreObjectiveIn != null && scoreObjectiveIn.func_178766_e() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                k = this.mc.field_71466_p.func_78256_a(" " + scoreboardIn.func_96529_a(networkplayerinfo.func_178845_a().getName(), scoreObjectiveIn).func_96652_c());
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
        final boolean flag = this.mc.func_71387_A() || this.mc.func_147114_u().func_147298_b().func_179292_f();
        int m;
        if (scoreObjectiveIn != null) {
            if (scoreObjectiveIn.func_178766_e() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
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
            list2 = (List<String>)this.mc.field_71466_p.func_78271_c(header.func_150254_d(), width - 50);
            for (final String s : list2) {
                l4 = Math.max(l4, this.mc.field_71466_p.func_78256_a(s));
            }
        }
        final IChatComponent footer = CustomGuiPlayerTabOverlay.footer.get(this);
        if (footer != null) {
            list3 = (List<String>)this.mc.field_71466_p.func_78271_c(footer.func_150254_d(), width - 50);
            for (final String s2 : list3) {
                l4 = Math.max(l4, this.mc.field_71466_p.func_78256_a(s2));
            }
        }
        if (list2 != null) {
            func_73734_a(width / 2 - l4 / 2 - 1, k2 - 1, width / 2 + l4 / 2 + 1, k2 + list2.size() * this.mc.field_71466_p.field_78288_b, this.transparent ? 855638016 : Integer.MIN_VALUE);
            for (final String s3 : list2) {
                final int i4 = this.mc.field_71466_p.func_78256_a(s3);
                this.mc.field_71466_p.func_175063_a(s3, (float)(width / 2 - i4 / 2), (float)k2, this.transparent ? 1308622847 : -1);
                k2 += this.mc.field_71466_p.field_78288_b;
            }
            ++k2;
        }
        func_73734_a(width / 2 - l4 / 2 - 1, k2 - 1, width / 2 + l4 / 2 + 1, k2 + i2 * 9, this.transparent ? 855638016 : Integer.MIN_VALUE);
        for (int k3 = 0; k3 < l3; ++k3) {
            final int l5 = k3 / i2;
            final int i5 = k3 % i2;
            int j4 = j3 + l5 * i3 + l5 * 5;
            final int k4 = k2 + i5 * 9;
            func_73734_a(j4, k4, j4 + i3, k4 + 8, 553648127);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, this.transparent ? 0.3f : 1.0f);
            GlStateManager.func_179141_d();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            if (k3 < list.size()) {
                final NetworkPlayerInfo networkplayerinfo2 = list.get(k3);
                String s4 = this.func_175243_a(networkplayerinfo2);
                final GameProfile gameprofile = networkplayerinfo2.func_178845_a();
                if (flag) {
                    final EntityPlayer entityplayer = this.mc.field_71441_e.func_152378_a(gameprofile.getId());
                    final boolean flag2 = entityplayer != null && entityplayer.func_175148_a(EnumPlayerModelParts.CAPE) && (gameprofile.getName().equals("Dinnerbone") || gameprofile.getName().equals("Grumm"));
                    this.mc.func_110434_K().func_110577_a(networkplayerinfo2.func_178837_g());
                    final int l6 = 8 + (flag2 ? 8 : 0);
                    final int i6 = 8 * (flag2 ? -1 : 1);
                    Gui.func_152125_a(j4, k4, 8.0f, (float)l6, 8, i6, 8, 8, 64.0f, 64.0f);
                    if (entityplayer != null && entityplayer.func_175148_a(EnumPlayerModelParts.HAT)) {
                        final int j5 = 8 + (flag2 ? 8 : 0);
                        final int k5 = 8 * (flag2 ? -1 : 1);
                        Gui.func_152125_a(j4, k4, 40.0f, (float)j5, 8, k5, 8, 8, 64.0f, 64.0f);
                    }
                    j4 += 9;
                }
                if (networkplayerinfo2.func_178848_b() == WorldSettings.GameType.SPECTATOR) {
                    s4 = EnumChatFormatting.ITALIC + s4;
                    this.mc.field_71466_p.func_175063_a(s4, (float)j4, (float)k4, this.transparent ? 872415231 : -1862270977);
                }
                else {
                    this.mc.field_71466_p.func_175063_a(s4, (float)j4, (float)k4, this.transparent ? 1895825407 : -1);
                }
                if (scoreObjectiveIn != null && networkplayerinfo2.func_178848_b() != WorldSettings.GameType.SPECTATOR) {
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
                this.func_175245_a(i3, j4 - (flag ? 9 : 0), k4, networkplayerinfo2);
            }
        }
        if (list3 != null) {
            k2 = k2 + i2 * 9 + 1;
            func_73734_a(width / 2 - l4 / 2 - 1, k2 - 1, width / 2 + l4 / 2 + 1, k2 + list3.size() * this.mc.field_71466_p.field_78288_b, this.transparent ? 855638016 : Integer.MIN_VALUE);
            for (final String s5 : list3) {
                final int j6 = this.mc.field_71466_p.func_78256_a(s5);
                this.mc.field_71466_p.func_175063_a(s5, (float)(width / 2 - j6 / 2), (float)k2, this.transparent ? 1308622847 : -1);
                k2 += this.mc.field_71466_p.field_78288_b;
            }
        }
    }
    
    protected void func_175245_a(final int p_175245_1_, final int p_175245_2_, final int p_175245_3_, final NetworkPlayerInfo networkPlayerInfoIn) {
        final int ping = networkPlayerInfoIn.func_178853_c();
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, this.transparent ? 0.3f : 1.0f);
        this.mc.func_110434_K().func_110577_a(CustomGuiPlayerTabOverlay.field_110324_m);
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
        this.field_73735_i += 100.0f;
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
                final int x = p_175245_2_ + p_175245_1_ - (this.mc.field_71466_p.func_78256_a(ping + "") >> 1) - 2;
                final int y = p_175245_3_ + (this.mc.field_71466_p.field_78288_b >> 2);
                this.mc.field_71466_p.func_175063_a(ping + "", (float)(2 * x), (float)(2 * y), colour);
                GL11.glScalef(2.0f, 2.0f, 2.0f);
                GL11.glPopMatrix();
            }
        }
        else {
            this.func_73729_b(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0 + i * 10, 176 + j * 8, 10, 8);
        }
        this.field_73735_i -= 100.0f;
    }
    
    static {
        ordering = Ordering.from((Comparator)new PlayerComparator());
        footer = new FieldWrapper<IChatComponent>(Enhancements.isObfuscated ? "field_175255_h" : "footer", GuiPlayerTabOverlay.class);
        header = new FieldWrapper<IChatComponent>(Enhancements.isObfuscated ? "field_175256_i" : "header", GuiPlayerTabOverlay.class);
    }
    
    @SideOnly(Side.CLIENT)
    private static class PlayerComparator implements Comparator<NetworkPlayerInfo>
    {
        @Override
        public int compare(final NetworkPlayerInfo p_compare_1_, final NetworkPlayerInfo p_compare_2_) {
            final ScorePlayerTeam scoreplayerteam = p_compare_1_.func_178850_i();
            final ScorePlayerTeam scoreplayerteam2 = p_compare_2_.func_178850_i();
            return ComparisonChain.start().compareTrueFirst(p_compare_1_.func_178848_b() != WorldSettings.GameType.SPECTATOR, p_compare_2_.func_178848_b() != WorldSettings.GameType.SPECTATOR).compare((Comparable)((scoreplayerteam != null) ? scoreplayerteam.func_96661_b() : ""), (Comparable)((scoreplayerteam2 != null) ? scoreplayerteam2.func_96661_b() : "")).compare((Comparable)p_compare_1_.func_178845_a().getName(), (Comparable)p_compare_2_.func_178845_a().getName()).result();
        }
    }
}
