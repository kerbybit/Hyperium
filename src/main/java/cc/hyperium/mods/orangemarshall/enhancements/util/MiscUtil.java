package cc.hyperium.mods.orangemarshall.enhancements.util;

import java.util.*;
import java.net.*;
import javax.net.ssl.*;
import java.io.*;

public class MiscUtil
{
    public static ArrayList<String> httpRequest(final String url) {
        final ArrayList<String> out = new ArrayList<String>();
        try {
            final URL u = new URL(url);
            final HttpsURLConnection con = (HttpsURLConnection)u.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
            final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input;
            while ((input = br.readLine()) != null) {
                out.add(input);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
    
    public static String httpRequestAsString(final String url) {
        String out = "";
        try {
            final URL u = new URL(url);
            final HttpsURLConnection con = (HttpsURLConnection)u.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
            final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input;
            while ((input = br.readLine()) != null) {
                out += input;
            }
            br.close();
        }
        catch (FileNotFoundException e2) {
            System.out.println("Error happened :/ Wrong name! @ " + url);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
}
