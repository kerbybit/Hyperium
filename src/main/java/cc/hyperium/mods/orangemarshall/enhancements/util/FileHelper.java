package cc.hyperium.mods.orangemarshall.enhancements.util;

import net.minecraft.util.*;
import org.apache.commons.io.*;
import java.util.*;
import java.io.*;

public class FileHelper
{
    private File file;
    private PrintWriter writer;
    private Scanner scanner;
    
    public FileHelper(final File file) {
        this.file = file;
        this.validate();
    }
    
    protected void validate() {
        try {
            if (!this.file.exists()) {
                this.file.createNewFile();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean append(final String... content) {
        try {
            this.writer = new PrintWriter(new BufferedWriter(new FileWriter(this.file, true)));
            for (final String word : content) {
                this.writer.print(word + " ");
            }
            this.writer.print("\n");
            this.writer.flush();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            this.writer.close();
        }
        return false;
    }
    
    public boolean clearFile() {
        try {
            (this.writer = new PrintWriter(new BufferedWriter(new FileWriter(this.file, false)))).print("");
            this.writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            this.writer.close();
        }
        return true;
    }
    
    public String getLine(final int line) {
        try {
            this.scanner = new Scanner(this.file);
            int i = 1;
            while (this.scanner.hasNextLine()) {
                if (i == line) {
                    final String ret = this.scanner.nextLine();
                    this.scanner.close();
                    return ret;
                }
                this.scanner.nextLine();
                ++i;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            this.scanner.close();
        }
        return null;
    }
    
    public String getLines(int from, int to) {
        from = Math.max(from, 1);
        to = Math.max(to, from + 1);
        final StringBuilder sb = new StringBuilder();
        try {
            this.scanner = new Scanner(this.file);
            if (from > 1) {
                for (int i = from - 1; i > 0 && this.scanner.hasNextLine(); --i) {
                    this.scanner.nextLine();
                }
                if (!this.scanner.hasNextLine()) {
                    return null;
                }
            }
            for (int i = 1; this.scanner.hasNextLine() && i <= to - from + 1; ++i) {
                final String line = this.scanner.nextLine();
                if (!line.isEmpty()) {
                    sb.append(" " + (i + from - 1) + " | " + EnumChatFormatting.GRAY + line);
                    sb.append("\n");
                }
            }
            if (sb.toString().endsWith("\n")) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            this.scanner.close();
        }
        return sb.toString();
    }
    
    public int getLines() {
        LineNumberReader lnr = null;
        try {
            lnr = new LineNumberReader(new FileReader(this.file));
            lnr.skip(Long.MAX_VALUE);
            return lnr.getLineNumber() + 1;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        finally {
            try {
                if (lnr != null) {
                    lnr.close();
                }
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        return -1;
    }
    
    public boolean deleteLines(final int... lines) {
        try {
            boolean flag = false;
            final List<String> old = (List<String>)FileUtils.readLines(this.file);
            for (final int line : lines) {
                if (line - 1 < old.size() && old.get(line - 1) != null) {
                    old.remove(line - 1);
                    flag = true;
                }
            }
            FileUtils.writeLines(this.file, (Collection)old, false);
            return flag;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public File getFile() {
        return this.file;
    }
    
    public boolean resetFile() {
        final boolean flag = this.file.delete();
        try {
            this.file.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return flag;
    }
    
    public List<String> getLinesAsList() {
        try {
            return (List<String>)FileUtils.readLines(this.file);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }
}
