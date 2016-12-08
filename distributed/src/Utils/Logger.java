package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");

    public static void logRouteTable(RouteTable rt, int port) {
        System.out.print(sd.format(new Date()));
        System.out.println(" / Receive from " + port + ", text " + rt.getText());
    }

    public static void i(String label, String text) {
        System.out.println(sd.format(new Date()) + " / " + label + ": " + text);
    }
}