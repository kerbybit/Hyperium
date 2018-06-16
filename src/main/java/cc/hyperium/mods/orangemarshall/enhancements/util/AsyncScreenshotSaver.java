package cc.hyperium.mods.orangemarshall.enhancements.util;

import net.minecraft.client.shader.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.event.*;
import cc.hyperium.mods.orangemarshall.enhancements.util.chat.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import java.text.*;
import java.util.*;

public class AsyncScreenshotSaver implements Runnable
{
    private int width;
    private int height;
    private int[] pixelValues;
    private Framebuffer frameBuffer;
    private File screenshotDir;
    
    public AsyncScreenshotSaver(final int width, final int height, final int[] pixelValues, final Framebuffer frameBuffer, final File screenshotDir) {
        this.width = width;
        this.height = height;
        this.pixelValues = pixelValues;
        this.frameBuffer = frameBuffer;
        this.screenshotDir = screenshotDir;
    }
    
    @Override
    public void run() {
        processPixelValues(this.pixelValues, this.width, this.height);
        BufferedImage bufferedimage = null;
        try {
            if (OpenGlHelper.func_148822_b()) {
                bufferedimage = new BufferedImage(this.frameBuffer.field_147621_c, this.frameBuffer.field_147618_d, 1);
                int k;
                for (int j = k = this.frameBuffer.field_147620_b - this.frameBuffer.field_147618_d; k < this.frameBuffer.field_147620_b; ++k) {
                    for (int l = 0; l < this.frameBuffer.field_147621_c; ++l) {
                        bufferedimage.setRGB(l, k - j, this.pixelValues[k * this.frameBuffer.field_147622_a + l]);
                    }
                }
            }
            else {
                bufferedimage = new BufferedImage(this.width, this.height, 1);
                bufferedimage.setRGB(0, 0, this.width, this.height, this.pixelValues, 0, this.width);
            }
            final File file2 = getTimestampedPNGFileForDirectory(this.screenshotDir);
            ImageIO.write(bufferedimage, "png", file2);
            final IChatComponent ichatcomponent = (IChatComponent)new ChatComponentText(file2.getName());
            ichatcomponent.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
            ichatcomponent.func_150256_b().func_150228_d(true);
            ChatUtil.addMessageWithoutTag((IChatComponent)new ChatComponentTranslation("screenshot.success", new Object[] { ichatcomponent }));
        }
        catch (Exception exception) {
            LogManager.getLogger().warn("Couldn't save screenshot", (Throwable)exception);
            ChatUtil.addMessageWithoutTag((IChatComponent)new ChatComponentTranslation("screenshot.failure", new Object[] { exception.getMessage() }));
        }
    }
    
    private static File getTimestampedPNGFileForDirectory(final File gameDirectory) {
        final String s = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()).toString();
        int i = 1;
        File file1;
        while (true) {
            file1 = new File(gameDirectory, s + ((i == 1) ? "" : ("_" + i)) + ".png");
            if (!file1.exists()) {
                break;
            }
            ++i;
        }
        return file1;
    }
    
    private static void processPixelValues(final int[] p_147953_0_, final int p_147953_1_, final int p_147953_2_) {
        final int[] aint = new int[p_147953_1_];
        for (int i = p_147953_2_ / 2, j = 0; j < i; ++j) {
            System.arraycopy(p_147953_0_, j * p_147953_1_, aint, 0, p_147953_1_);
            System.arraycopy(p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_0_, j * p_147953_1_, p_147953_1_);
            System.arraycopy(aint, 0, p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_1_);
        }
    }
}
