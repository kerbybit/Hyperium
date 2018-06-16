package cc.hyperium.mods.orangemarshall.enhancements.util.http;

import java.net.*;
import java.io.*;
import org.apache.commons.io.*;

public class HttpRequest implements Request
{
    protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36";
    protected URL url;
    protected String userAgent;
    
    public HttpRequest(final String url) {
        this.userAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36";
        try {
            this.url = new URL(url);
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL format: " + url);
        }
    }
    
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }
    
    @Override
    public String request() {
        final HttpURLConnection connection = this.buildConnection();
        return this.getResponse(connection);
    }
    
    protected HttpURLConnection buildConnection() {
        try {
            final HttpURLConnection con = (HttpURLConnection)this.url.openConnection();
            con.addRequestProperty("User-Agent", this.userAgent);
            return con;
        }
        catch (IOException e) {
            throw new IllegalStateException("Could not open connection to " + this.url);
        }
    }
    
    protected String getResponse(final HttpURLConnection connection) {
        try {
            connection.connect();
            final String response = IOUtils.toString(connection.getInputStream());
            connection.disconnect();
            return response;
        }
        catch (IOException e) {
            throw new IllegalStateException("Could not open connection to " + this.url);
        }
    }
}
