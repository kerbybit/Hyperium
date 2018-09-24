package cc.hyperium.handlers.handlers.restart;

import cc.hyperium.Hyperium;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class RestartUtil {
    public static boolean useDifferentCreds = false;
    public static String uuid;
    public static String username;
    public static String accessToken;

    public static String getLaunchCommand(boolean copyNatives) {
        StringBuilder cmd = new StringBuilder();
        String[] command = System.getProperty("sun.java.command").split(" ");

        String javaPath = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        cmd.append(quoteSpaces(javaPath) + " ");

        ManagementFactory.getRuntimeMXBean().getInputArguments().forEach(s -> {
            if (s.contains("library.path")) {
                String nativePath = s.split("=")[1];
                File hyperiumNativeFolder = new File(Hyperium.folder.getPath() + File.separator + "natives");
                if (copyNatives) {
                    copyNatives(nativePath, hyperiumNativeFolder);
                }

                cmd.append(quoteSpaces("-Djava.library.path=" + hyperiumNativeFolder.getAbsolutePath())).append(" ");
            } else {
                if (useDifferentCreds) {
                    if (s.startsWith("--username"))
                        cmd.append(quoteSpaces("--username " + username));
                    else if (s.startsWith("--uuid"))
                        cmd.append(quoteSpaces("--uuid " + uuid));
                    else if (s.startsWith("--accessToken")) {
                        cmd.append(quoteSpaces("--accessToken " + accessToken));
                    } else cmd.append(quoteSpaces(s)).append(" ");
                }
                else
                cmd.append(quoteSpaces(s)).append(" ");

            }
        });

        if (command[0].endsWith(".jar")) {
            cmd.append("-jar ").append(quoteSpaces(new File(command[0]).getPath())).append(" ");
        } else {
            cmd.append("-cp ").append(quoteSpaces(System.getProperty("java.class.path"))).append(" ").append(command[0]).append(" ");
        }
        for (int i = 1; i < command.length; i++) {
            cmd.append(quoteSpaces(command[i])).append(" ");
        }

        return cmd.toString();
    }

    public static void copyNatives(String nativePath, File newFolder) {
        if (!newFolder.exists()) {
            newFolder.mkdir();
        }

        File tempNatives = new File(nativePath);
        if (!tempNatives.exists()) {
            System.out.println("Error - Natives are missing.");
        } else {
            System.out.println("Copying natives to hyperium folder.");
            try {
                for (File fileEntry : tempNatives.listFiles()) {
                    Files.copy(fileEntry.toPath(), Paths.get(newFolder.getPath() + File.separator + fileEntry.getName()), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String quoteSpaces(String argument) {
        if (argument.contains(" ")) {
            return "\"" + argument + "\"";
        } else {
            return argument;
        }
    }
}
