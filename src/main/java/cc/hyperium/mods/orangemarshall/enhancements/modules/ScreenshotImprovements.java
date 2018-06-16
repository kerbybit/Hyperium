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
        this.mc = Minecraft.func_71410_x();
        this.cooldown = false;
        this.keybindBetterScreenshot = this.bindScreenshotKey();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    private KeyBinding bindScreenshotKey() {
        final KeyBinding[] list = this.mc.field_71474_y.field_74324_K;
        final KeyBinding target = this.mc.field_71474_y.field_151447_Z;
        final KeyBinding newKeybind = new KeyBinding(target.func_151464_g(), target.func_151463_i(), target.func_151466_e());
        for (int i = 0; i < list.length; ++i) {
            if (list[i].func_151464_g().equals(target.func_151464_g())) {
                list[i] = newKeybind;
                target.func_151462_b(-1);
            }
        }
        return newKeybind;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.field_71462_r != null) {
            if (this.keybindBetterScreenshot.func_151463_i() > 0 && Keyboard.isKeyDown(this.keybindBetterScreenshot.func_151463_i())) {
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
        if (this.keybindBetterScreenshot.func_151468_f()) {
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
        this.saveScreenshot(this.mc.field_71412_D, this.mc.field_71443_c, this.mc.field_71440_d, this.mc.func_147110_a());
    }
    
    public static void onChat(final IChatComponent ic) {
        final String msg = ic.func_150260_c();
        final String[] split = msg.split(" ");
        final String filename = split[split.length - 1];
        final CCT copy = CCT.newComponent("[COPY] ");
        copy.green().bold().execute("/screenshotdummy (UN9vamur398M(QMO)VMW=AVAIUBBR(V)MAWVWA agfafafFJSFSMSDASFBfbAssFSAFMSA " + filename).hover("Copy file to clipboard");
        final CCT delete = CCT.newComponent("[DELETE] ");
        delete.red().bold().execute("/screenshotdummy (UN9vamur398M(QMO)VMW=AVAIUBBR(V)MAWVWA NAUINHARIBHIRHUANIUBOJIAFJSFSMSAJFMSA " + filename).hover("Delete file");
        new DelayedTask(1, () -> ChatUtil.addMessageWithoutTag(copy.func_150257_a((IChatComponent)delete)));
    }
    
    public static void delete(final String filename) {
        final File f = new File(Minecraft.func_71410_x().field_71412_D, "screenshots\\" + filename);
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
        final File f = new File(Minecraft.func_71410_x().field_71412_D, "screenshots\\" + filename);
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
        final File file1 = new File(this.mc.field_71412_D, "screenshots");
        file1.mkdir();
        if (OpenGlHelper.func_148822_b()) {
            width = buffer.field_147622_a;
            height = buffer.field_147620_b;
        }
        final int i = width * height;
        if (this.pixelBuffer == null || this.pixelBuffer.capacity() < i) {
            this.pixelBuffer = BufferUtils.createIntBuffer(i);
            this.pixelValues = new int[i];
        }
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        this.pixelBuffer.clear();
        if (OpenGlHelper.func_148822_b()) {
            GlStateManager.func_179144_i(buffer.field_147617_g);
            GL11.glGetTexImage(3553, 0, 32993, 33639, this.pixelBuffer);
        }
        else {
            GL11.glReadPixels(0, 0, width, height, 32993, 33639, this.pixelBuffer);
        }
        this.pixelBuffer.get(this.pixelValues);
        new Thread(new AsyncScreenshotSaver(width, height, this.pixelValues, this.mc.func_147110_a(), new File(this.mc.field_71412_D, "screenshots"))).start();
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
