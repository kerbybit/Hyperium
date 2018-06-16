package cc.hyperium.mods.orangemarshall.enhancements.util.http;

import java.security.*;
import javax.net.ssl.*;
import java.security.cert.*;

public class SslState
{
    private static SSLSocketFactory defaultFactory;
    private static HostnameVerifier defaultVerifier;
    
    public static void trustAll() {
        try {
            final SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAllManager() }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new VerifyAll());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void defaultState() {
        try {
            final SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, null, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(SslState.defaultFactory);
            HttpsURLConnection.setDefaultHostnameVerifier(SslState.defaultVerifier);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        SslState.defaultFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
        SslState.defaultVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
    }
    
    private static class VerifyAll implements HostnameVerifier
    {
        @Override
        public boolean verify(final String hostname, final SSLSession session) {
            return true;
        }
    }
    
    private static class TrustAllManager implements X509TrustManager
    {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        
        @Override
        public void checkClientTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {
        }
        
        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
        }
    }
}
