package Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class RouteTable implements Serializable {
    // 索引值是 `IP:port`
    private Map<String, Map<String, Integer>> table;

    // 16视为不可达
    public static final int INF = 16;

    public RouteTable() {
        table = new HashMap<>();
    }

    public RouteTable(String addr1, String addr2, Integer distance) {
        table = new HashMap<>();
        addVertex(addr1);
        addVertex(addr2);
        table.get(addr1).put(addr2, distance);
        table.get(addr2).put(addr1, distance);
    }

    public Map<String, Map<String, Integer>> getTable() {
        return table;
    }

    private void addVertex(String address) {
        Map<String, Integer> newEntry = new HashMap<>();
        for (String addr : table.keySet()) {
            newEntry.put(addr, INF);
            table.get(addr).put(address, INF);
        }
        newEntry.put(address, 0); // 到自身的距离为0
        table.put(address, newEntry);
    }

    public boolean updateTable(RouteTable rt) {
        boolean changed = false;
        Map<String, Map<String, Integer>> otherTable = rt.getTable();
        // 调整表的大小
        for (String key: otherTable.keySet()) {
            if (!table.keySet().contains(key)) {
                addVertex(key);
                changed = true;
            }
        }
        // 更新
        for (String addr1: otherTable.keySet()) {
            for (String addr2: otherTable.get(addr1).keySet()) {
                Integer distance = otherTable.get(addr1).get(addr2);
                if (!distance.equals(table.get(addr1).get(addr2))) {
                    table.get(addr1).put(addr2, distance);
                    changed = true;
                }
            }
        }
        return changed;
    }

    @Override
    public String toString() {
        String str = "\n==================================\n";
        boolean isTableHead = true;
        for (String addr1: table.keySet()) {
            if (isTableHead) {
                // 插入表头
                isTableHead = false;
                for (String addr2 : table.get(addr1).keySet()) {
                    str += "\t" + addr2;
                }
                str += "\n";
            }
            str += addr1 + ":";
            for (String addr2 : table.get(addr1).keySet()) {
                str += "\t" + table.get(addr1).get(addr2);
            }
            str += "\n";
        }
        str += "==================================\n";
        return str;
    }
}
