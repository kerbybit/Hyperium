package cc.hyperium.mods.orangemarshall.enhancements.util.http;

import java.net.*;
import javax.net.ssl.*;
import java.io.*;

public class HttpsRequest extends HttpRequest
{
    public HttpsRequest(final String url) {
        super(url);
    }
    
    @Override
    protected HttpURLConnection buildConnection() {
        try {
            final HttpsURLConnection con = (HttpsURLConnection)this.url.openConnection();
            con.addRequestProperty("User-Agent", this.userAgent);
            return con;
        }
        catch (IOException e) {
            throw new IllegalStateException("Could not open connection to " + this.url);
        }
    }
    
    @Override
    protected String getResponse(final HttpURLConnection connection) {
        SslState.trustAll();
        final String response = super.getResponse(connection);
        SslState.defaultState();
        return response;
    }
}
