package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");

    public static void logRouteTable(RouteTable rt, String IP) {
        System.out.print(sd.format(new Date()));
        System.out.println(" / Receive from " + IP + ", text " + rt.toString());
    }

    public static void logRouteTable(RouteTable rt) {
        System.out.print(sd.format(new Date()));
        System.out.println(" / RouteTable in this host" + rt.toString());
    }

    public static void i(String label, String text) {
        System.out.println(sd.format(new Date()) + " / " + label + ": " + text);
    }
}
