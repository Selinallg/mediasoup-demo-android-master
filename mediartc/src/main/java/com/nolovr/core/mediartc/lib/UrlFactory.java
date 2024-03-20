package com.nolovr.core.mediartc.lib;

import java.util.Locale;

/**
 * https://82.157.172.119:4443
 * <p>
 * ws://82.157.172.119:4443/?roomId=broadcaster&peerId=broadcasterID
 */
public class UrlFactory {

    //https://82.157.172.119:4443
    //https://8.147.104.161:3000/?info=true&roomId=ucsuhgeg
    //https://82.157.172.119:3000/?info=true&roomId=ucsuhgeg
//  private static final String HOSTNAME = "v3demo.mediasoup.org";
//  private static final String HOSTNAME = "82.157.172.119";
    public static String HOSTNAME = "8.147.104.161";
    //  private static final String HOSTNAME = "192.168.1.103";
//  private static final int PORT = 4443;
    public static int PORT_WSS = 4443;
    public static int PORT_HTTP = 3000;

    public static String getInvitationLink(String roomId, boolean forceH264, boolean forceVP9) {
        // v3demo.mediasoup.org 这个域名，不需要 指定端口
//    String url = String.format(Locale.US, "https://%s/?roomId=%s", HOSTNAME, roomId);
        String url = String.format(Locale.US, "https://%s:%d/?info=true&roomId=%s", HOSTNAME, PORT_HTTP, roomId);
        if (forceH264) {
            url += "&forceH264=true";
        } else if (forceVP9) {
            url += "&forceVP9=true";
        }
        return url;
    }

    public static String getProtooUrl(
            String roomId, String peerId, boolean forceH264, boolean forceVP9) {
        String url =
                String.format(
//            Locale.US, "wss://%s:%d/?roomId=%s&peerId=%s", HOSTNAME, PORT, roomId, peerId);
                        Locale.US, "wss://%s:%d/?info=true&roomId=%s&peerId=%s", HOSTNAME, PORT_WSS, roomId, peerId);
        if (forceH264) {
            url += "&forceH264=true";
        } else if (forceVP9) {
            url += "&forceVP9=true";
        }
        return url;
    }
}
