package cc.hyperium.mods.orangemarshall.enhancements.modules;

import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import java.nio.*;
import net.minecraftforge.common.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import java.io.*;
import net.minecraft.client.shader.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class ScreenshotImprovements
{
    private Minecraft mc;
    private boolean cooldown;
    public static final String key = "(UN9vamur398M(QMO)VMW=AVAIUBBR(V)MAWVWA";
    public static final String DELETE = "NAUINHARIBHIRHUANIUBOJIAFJSFSMSAJFMSA";
    public static final String COPY = "agfafafFJSFSMSDASFBfbAssFSAFMSA";
    private KeyBinding keybindBetterScreenshot;
    private IntBuffer pixelBuffer;
    private int[] pixelValues;
    
    public ScreenshotImprovements() {
        this.mc = Minecraft.getMinecraft();
        this.cooldown = false;
        this.keybindBetterScreenshot = this.bindScreenshotKey();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    private KeyBinding bindScreenshotKey() {
        final KeyBinding[] list = this.mc.gameSettings.keyBindings;
        final KeyBinding target = this.mc.gameSettings.keyBindScreenshot;
        final KeyBinding newKeybind = new KeyBinding(target.getKeyDescription(), target.getKeyCode(), target.getKeyCategory());
        for (int i = 0; i < list.length; ++i) {
            if (list[i].getKeyDescription().equals(target.getKeyDescription())) {
                list[i] = newKeybind;
                target.setKeyCode(-1);
            }
        }
        return newKeybind;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.currentScreen != null) {
            if (this.keybindBetterScreenshot.getKeyCode() > 0 && Keyboard.isKeyDown(this.keybindBetterScreenshot.getKeyCode())) {
                if (!this.cooldown) {
                    this.takeScreenshot();
                    this.cooldown = true;
                }
            }
            else {
                this.cooldown = false;
            }
        }
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent e) {
        if (this.keybindBetterScreenshot.isPressed()) {
            if (!this.cooldown) {
                this.takeScreenshot();
                this.cooldown = true;
            }
        }
        else {
            this.cooldown = false;
        }
    }
    
    private void takeScreenshot() {
        this.saveScreenshot(this.mc.mcDataDir, this.mc.displayWidth, this.mc.displayHeight, this.mc.getFramebuffer());
    }
    
    public static void onChat(final IChatComponent ic) {
        final String msg = ic.getUnformattedText();
        final String[] split = msg.split(" ");
        final String filename = split[split.length - 1];
        final CCT copy = CCT.newComponent("[COPY] ");
        copy.green().bold().execute("/screenshotdummy (UN9vamur398M(QMO)VMW=AVAIUBBR(V)MAWVWA agfafafFJSFSMSDASFBfbAssFSAFMSA " + filename).hover("Copy file to clipboard");
        final CCT delete = CCT.newComponent("[DELETE] ");
        delete.red().bold().execute("/screenshotdummy (UN9vamur398M(QMO)VMW=AVAIUBBR(V)MAWVWA NAUINHARIBHIRHUANIUBOJIAFJSFSMSAJFMSA " + filename).hover("Delete file");
        new DelayedTask(1, () -> ChatUtil.addMessageWithoutTag(copy.appendSibling((IChatComponent)delete)));
    }
    
    public static void delete(final String filename) {
        final File f = new File(Minecraft.getMinecraft().mcDataDir, "screenshots\\" + filename);
        if (!f.exists()) {
            ChatUtil.addMessage("Could not find file!");
            return;
        }
        if (!f.delete()) {
            f.deleteOnExit();
            ChatUtil.addMessage("Could not delete yet!");
        }
        else {
            ChatUtil.addMessage("Deleted the screenshot!");
        }
    }
    
    public static void copy(final String filename) {
        final File f = new File(Minecraft.getMinecraft().mcDataDir, "screenshots\\" + filename);
        if (!f.exists()) {
            ChatUtil.addMessage("Could not find file!");
            return;
        }
        final Toolkit tolkit = Toolkit.getDefaultToolkit();
        final Clipboard clip = tolkit.getSystemClipboard();
        clip.setContents(new ImageSelection(tolkit.getImage(f.getAbsolutePath())), null);
        ChatUtil.addMessage("Copied screenshot!");
    }
    
    public void saveScreenshot(final File gameDirectory, int width, int height, final Framebuffer buffer) {
        final File file1 = new File(this.mc.mcDataDir, "screenshots");
        file1.mkdir();
        if (OpenGlHelper.isFramebufferEnabled()) {
            width = buffer.framebufferTextureWidth;
            height = buffer.framebufferTextureHeight;
        }
        final int i = width * height;
        if (this.pixelBuffer == null || this.pixelBuffer.capacity() < i) {
            this.pixelBuffer = BufferUtils.createIntBuffer(i);
            this.pixelValues = new int[i];
        }
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        this.pixelBuffer.clear();
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(buffer.framebufferTexture);
            GL11.glGetTexImage(3553, 0, 32993, 33639, this.pixelBuffer);
        }
        else {
            GL11.glReadPixels(0, 0, width, height, 32993, 33639, this.pixelBuffer);
        }
        this.pixelBuffer.get(this.pixelValues);
        new Thread(new AsyncScreenshotSaver(width, height, this.pixelValues, this.mc.getFramebuffer(), new File(this.mc.mcDataDir, "screenshots"))).start();
    }
    
    private static class ImageSelection implements Transferable
    {
        private Image image;
        
        public ImageSelection(final Image image) {
            this.image = image;
        }
        
        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { DataFlavor.imageFlavor };
        }
        
        @Override
        public boolean isDataFlavorSupported(final DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }
        
        @Override
        public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException {
            if (!DataFlavor.imageFlavor.equals(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return this.image;
        }
    }
}
