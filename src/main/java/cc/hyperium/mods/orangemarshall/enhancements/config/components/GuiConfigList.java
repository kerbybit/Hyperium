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
        this.fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
    }
    
    public void initGui() {
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
            this.listWidth = Math.max(this.listWidth, this.getFontRenderer().getStringWidth(con2.name()) + 10);
        }
        this.entries.add(new SpacerContainer(""));
        final ScaledResolution res = new ScaledResolution(this.mc);
        this.listWidth = Math.min(this.listWidth, res.getScaledWidth() / 2 - 20);
        this.modList = new GuiSlotConfigList(this, this.entries, this.listWidth);
        this.addButtons();
    }
    
    private void addButtons() {
        final int buttonWidth = 75;
        final int xBetweenButtons = this.modList.right() + (this.width - this.modList.right()) / 2;
        final Buttons.GuiButtonDone buttonDone = new Buttons.GuiButtonDone(6, xBetweenButtons - 75 - 10, this.height - 38, 75) {
            public void mouseReleased(final int mouseX, final int mouseY) {
                if (this.isMouseOver()) {
                    GuiConfigList.this.prepareSlotChange();
                    GuiConfigList.this.mc.displayGuiScreen((GuiScreen)null);
                    if (GuiConfigList.this.mc.currentScreen == null) {
                        GuiConfigList.this.mc.setIngameFocus();
                    }
                    Config.instance().save();
                    Config.instance().load();
                }
            }
        };
        this.buttonList.add(buttonDone);
        final Buttons.GuiButtonCancel buttonCancel = new Buttons.GuiButtonCancel(6, xBetweenButtons + 10, this.height - 38, 75) {
            public void mouseReleased(final int mouseX, final int mouseY) {
                if (this.isMouseOver()) {
                    GuiConfigList.this.mc.displayGuiScreen((GuiScreen)null);
                    if (GuiConfigList.this.mc.currentScreen == null) {
                        GuiConfigList.this.mc.setIngameFocus();
                    }
                }
            }
        };
        this.buttonList.add(buttonCancel);
        final Buttons.GuiButtonReset buttonRestore = new Buttons.GuiButtonReset(6, xBetweenButtons - 37, this.height - 64, 75) {
            public void mouseReleased(final int mouseX, final int mouseY) {
                if (this.isMouseOver() && GuiConfigList.this.selectedField != null) {
                    GuiConfigList.this.valueSelector.setValue(GuiConfigList.this.selectedField.getValue());
                    GuiConfigList.this.updateInfo("Reset");
                }
            }
        };
        this.buttonList.add(buttonRestore);
    }
    
    protected void keyTyped(final char c, final int i) throws IOException {
        super.keyTyped(c, i);
        if (this.valueSelector != null) {
            this.valueSelector.keyTyped(c, i);
        }
    }
    
    public void mouseClicked(final int i, final int j, final int k) throws IOException {
        super.mouseClicked(i, j, k);
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
        final int middle = this.width / 2;
        final int horizontalSpacing = 10;
        final int textfieldLeft = middle + horizontalSpacing;
        final int textfieldTop = this.height / 2 - 30;
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
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.modList.drawScreen(mouseX, mouseY, partialTicks);
        if (this.valueSelector != null) {
            this.valueSelector.draw();
        }
        final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        final String text = "�e�lVanilla Enhancements Config";
        final int middle = res.getScaledWidth() / 2;
        this.drawCenteredString(this.fontRendererObj, text, middle, 12, 16777215);
        if (this.selectedField != null) {
            final int maxlinewidth = middle - 20;
            final String[] info = this.lineBreaksAfterWidth(this.selectedField.name(), maxlinewidth);
            int height = 50;
            for (final String str : info) {
                this.drawString(this.fontRendererObj, str, middle + 10, height, 16777215);
                height += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (System.currentTimeMillis() - this.lastUpdate <= 1000L) {
            this.fontRendererObj.drawString(this.updateInfo, res.getScaledWidth() - this.fontRendererObj.getStringWidth(this.updateInfo) - 1, res.getScaledHeight() - this.fontRendererObj.FONT_HEIGHT - 1, 0xFFFFFF | 255 - this.updateCounter << 24);
            this.updateCounter += 3;
            this.updateCounter = Math.max(this.updateCounter, 150);
        }
    }
    
    private String[] lineBreaksAfterWidth(String text, final int maxlinewidth) {
        final StringBuilder output = new StringBuilder();
        while (this.fontRendererObj.getStringWidth(text) > maxlinewidth) {
            int index;
            for (index = 2; this.fontRendererObj.getStringWidth(text.substring(0, index)) < maxlinewidth; ++index) {}
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
