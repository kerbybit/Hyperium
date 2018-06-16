package cc.hyperium.mods.orangemarshall.enhancements.config.components;

import com.google.common.collect.*;
import cc.hyperium.mods.orangemarshall.enhancements.config.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class GuiConfigList extends GuiScreen
{
    private ArrayList<FieldContainer> entries;
    private int listWidth;
    private FontRenderer fontRendererObj;
    private GuiSlotConfigList modList;
    private int selected;
    private FieldContainer selectedField;
    private GuiValueSelector valueSelector;
    private String updateInfo;
    private long lastUpdate;
    private int updateCounter;
    private Set<String> ignoredCategories;
    
    public GuiConfigList() {
        this.selected = -1;
        this.ignoredCategories = (Set<String>)Sets.newHashSet();
        this.entries = new ArrayList<FieldContainer>(Config.instance().getFields());
        this.fontRendererObj = Minecraft.func_71410_x().field_71466_p;
    }
    
    public void func_73866_w_() {
        this.updateCounter = 0;
        this.lastUpdate = 0L;
        this.entries.removeIf(con -> this.ignoredCategories.contains(con.category()));
        String lastCategory = "";
        for (int i = 0; i < this.entries.size(); ++i) {
            final FieldContainer con2 = this.entries.get(i);
            if (!lastCategory.equals(con2.category())) {
                lastCategory = con2.category();
                if (!this.ignoredCategories.contains(lastCategory)) {
                    if (!lastCategory.isEmpty()) {
                        this.entries.add(i++, new SpacerContainer(""));
                    }
                    this.entries.add(i, new SpacerContainer("�l" + con2.category()));
                }
            }
            this.listWidth = Math.max(this.listWidth, this.getFontRenderer().func_78256_a(con2.name()) + 10);
        }
        this.entries.add(new SpacerContainer(""));
        final ScaledResolution res = new ScaledResolution(this.field_146297_k);
        this.listWidth = Math.min(this.listWidth, res.func_78326_a() / 2 - 20);
        this.modList = new GuiSlotConfigList(this, this.entries, this.listWidth);
        this.addButtons();
    }
    
    private void addButtons() {
        final int buttonWidth = 75;
        final int xBetweenButtons = this.modList.right() + (this.field_146294_l - this.modList.right()) / 2;
        final Buttons.GuiButtonDone buttonDone = new Buttons.GuiButtonDone(6, xBetweenButtons - 75 - 10, this.field_146295_m - 38, 75) {
            public void func_146118_a(final int mouseX, final int mouseY) {
                if (this.func_146115_a()) {
                    GuiConfigList.this.prepareSlotChange();
                    GuiConfigList.this.field_146297_k.func_147108_a((GuiScreen)null);
                    if (GuiConfigList.this.field_146297_k.field_71462_r == null) {
                        GuiConfigList.this.field_146297_k.func_71381_h();
                    }
                    Config.instance().save();
                    Config.instance().load();
                }
            }
        };
        this.field_146292_n.add(buttonDone);
        final Buttons.GuiButtonCancel buttonCancel = new Buttons.GuiButtonCancel(6, xBetweenButtons + 10, this.field_146295_m - 38, 75) {
            public void func_146118_a(final int mouseX, final int mouseY) {
                if (this.func_146115_a()) {
                    GuiConfigList.this.field_146297_k.func_147108_a((GuiScreen)null);
                    if (GuiConfigList.this.field_146297_k.field_71462_r == null) {
                        GuiConfigList.this.field_146297_k.func_71381_h();
                    }
                }
            }
        };
        this.field_146292_n.add(buttonCancel);
        final Buttons.GuiButtonReset buttonRestore = new Buttons.GuiButtonReset(6, xBetweenButtons - 37, this.field_146295_m - 64, 75) {
            public void func_146118_a(final int mouseX, final int mouseY) {
                if (this.func_146115_a() && GuiConfigList.this.selectedField != null) {
                    GuiConfigList.this.valueSelector.setValue(GuiConfigList.this.selectedField.getValue());
                    GuiConfigList.this.updateInfo("Reset");
                }
            }
        };
        this.field_146292_n.add(buttonRestore);
    }
    
    protected void func_73869_a(final char c, final int i) throws IOException {
        super.func_73869_a(c, i);
        if (this.valueSelector != null) {
            this.valueSelector.keyTyped(c, i);
        }
    }
    
    public void func_73864_a(final int i, final int j, final int k) throws IOException {
        super.func_73864_a(i, j, k);
        if (this.valueSelector != null) {
            this.valueSelector.mouseClicked(i, j, k);
        }
    }
    
    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }
    
    public void selectModIndex(final int index) {
        if (index == this.selected) {
            return;
        }
        if (index >= 0 && index <= this.entries.size() && this.entries.get(index) instanceof SpacerContainer) {
            this.selectedField = null;
            this.selected = -1;
            this.valueSelector = null;
            return;
        }
        this.prepareSlotChange();
        this.selectedField = (((this.selected = index) >= 0 && index <= this.entries.size()) ? this.entries.get(this.selected) : null);
        final int middle = this.field_146294_l / 2;
        final int horizontalSpacing = 10;
        final int textfieldLeft = middle + horizontalSpacing;
        final int textfieldTop = this.field_146295_m / 2 - 30;
        this.valueSelector = GuiValueSelector.getFromField(this.selectedField, textfieldLeft, textfieldTop, middle - horizontalSpacing * 2);
    }
    
    private void prepareSlotChange() {
        if (this.selectedField != null) {
            final String prev = this.selectedField.getValue();
            this.selectedField.setValue(this.valueSelector.getValue());
            if (!prev.equals(this.selectedField.getValue())) {
                this.updateInfo("Saved");
            }
        }
    }
    
    public boolean fieldIndexSelected(final int index) {
        return index == this.selected;
    }
    
    public void updateInfo(final String text) {
        this.lastUpdate = System.currentTimeMillis();
        this.updateInfo = text;
        this.updateCounter = 0;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.modList.drawScreen(mouseX, mouseY, partialTicks);
        if (this.valueSelector != null) {
            this.valueSelector.draw();
        }
        final ScaledResolution res = new ScaledResolution(Minecraft.func_71410_x());
        final String text = "�e�lVanilla Enhancements Config";
        final int middle = res.func_78326_a() / 2;
        this.func_73732_a(this.fontRendererObj, text, middle, 12, 16777215);
        if (this.selectedField != null) {
            final int maxlinewidth = middle - 20;
            final String[] info = this.lineBreaksAfterWidth(this.selectedField.name(), maxlinewidth);
            int height = 50;
            for (final String str : info) {
                this.func_73731_b(this.fontRendererObj, str, middle + 10, height, 16777215);
                height += this.fontRendererObj.field_78288_b;
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
        if (System.currentTimeMillis() - this.lastUpdate <= 1000L) {
            this.fontRendererObj.func_78276_b(this.updateInfo, res.func_78326_a() - this.fontRendererObj.func_78256_a(this.updateInfo) - 1, res.func_78328_b() - this.fontRendererObj.field_78288_b - 1, 0xFFFFFF | 255 - this.updateCounter << 24);
            this.updateCounter += 3;
            this.updateCounter = Math.max(this.updateCounter, 150);
        }
    }
    
    private String[] lineBreaksAfterWidth(String text, final int maxlinewidth) {
        final StringBuilder output = new StringBuilder();
        while (this.fontRendererObj.func_78256_a(text) > maxlinewidth) {
            int index;
            for (index = 2; this.fontRendererObj.func_78256_a(text.substring(0, index)) < maxlinewidth; ++index) {}
            --index;
            String subText = text.substring(0, index);
            subText = subText.trim();
            if (subText.contains(" ")) {
                final int spaceIndex = subText.lastIndexOf(" ");
                subText = subText.substring(0, spaceIndex);
                text = text.substring(spaceIndex);
                output.append(subText + "\n");
            }
            else {
                text = text.substring(index);
                output.append(subText + "\n");
            }
        }
        output.append(text + "\n");
        return output.toString().split("\n");
    }
}
