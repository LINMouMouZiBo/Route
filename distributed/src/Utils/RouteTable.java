package Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class RouteTable implements Serializable {
    // 索引值是 `IP:port`
    private Map<String, Map<String, Integer>> table;

    public String text; // 测试

    public RouteTable() {
        text = "hhhhhh";
        table = new HashMap<>();
    }
    public RouteTable(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void addVertex(String address) {
        Map<String, Integer> newEntry = new HashMap<>();
        for (String addr : table.keySet()) {
            newEntry.put(addr, 0);
            table.get(addr).put(address, 0);
        }
        newEntry.put(address, 0);
        table.put(address, newEntry);
    }

    public void updateDistance(String addr1, String addr2, int distance) {
        table.get(addr1).put(addr2, distance);
    }
}
