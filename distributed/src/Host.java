import Utils.HostChannel;
import Utils.Logger;
import Utils.RouteTable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Host {
    private ServerSocket serverSocket = null;
    private List<HostChannel> connList = new ArrayList<>();
    private RouteTable routeTable;
    private static Lock lock = new ReentrantLock(true);
    private String localIP;

    public static final int LISTENING_PORT = 4000;

    public Host() throws IOException {
        routeTable = new RouteTable();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("please input your IP address");
        localIP = br.readLine();

        try {
            serverSocket = new ServerSocket(LISTENING_PORT);
            // 监听键盘输入的命令
            new CommandHandler();
            // 监听邻居节点的连接请求
            while (true) {
                Socket socket = serverSocket.accept();
                Logger.i("main", "about connecting to " + socket.getInetAddress());
                new ConnRequestHandler(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    /**
     * 发送路由表至指定主机
     * 1. 通过指定 IP, port 发送至主机
     * 2. 除去某主机的广播
     * 以上未实现
     * @param address `IP:port`
     */
    private void broadcast(String address) {
        lock.lock();
        for (HostChannel hc : connList) {
            try {
                Logger.i("broadcast", "send to " + hc.getAddress());
                hc.sendRouteTable(routeTable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }

    /**
     * 处理邻居节点的连接请求
     */
    private class ConnRequestHandler extends Thread {
        private HostChannel neighbor;

        public ConnRequestHandler(Socket socket) {
            try {
                neighbor = new HostChannel(socket, false);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Logger.i("requestHandler", "setup connection with " + neighbor.getAddress());
            connList.add(neighbor);
            start();
        }

        @Override
        public void run() {
            try {
                RouteTable rt = null;
                while ((rt = (RouteTable)(neighbor.getOis().readObject())) != null) {
                    Logger.logRouteTable(rt, neighbor.getIP());
                    // 如果路由表有改动，广播新路由表
                    if (routeTable.updateTable(rt)) {
                        Logger.logRouteTable(routeTable);
                        broadcast(neighbor.getIP());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    neighbor.close();
                    connList.remove(neighbor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 向邻居请求连接
     */
    private class ConnRequest extends Thread {
        private HostChannel neighbor;

        public ConnRequest(String IP, int distance) {
            try {
                Socket socket = new Socket(IP, LISTENING_PORT);
                routeTable.updateTable(new RouteTable(localIP, IP, distance));
                neighbor = new HostChannel(socket, true);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Logger.i("connRequest", "setup connection with " + neighbor.getAddress());
            connList.add(neighbor);
            start();
        }

        @Override
        public void run() {
            try {
                // 向邻居发送初始化的链路信息
                broadcast(neighbor.getIP());

                RouteTable rt;
                while ((rt = (RouteTable)(neighbor.getOis().readObject())) != null) {
                    Logger.logRouteTable(rt, neighbor.getIP());
                    // 如果路由表有改动，广播新路由表
                    if (routeTable.updateTable(rt)) {
                        Logger.logRouteTable(routeTable);
                        broadcast(neighbor.getIP());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    neighbor.close();
                    connList.remove(neighbor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理命令
     * 如：向其他请求建立连接
     */
    private class CommandHandler extends Thread {
        public CommandHandler() {
            start();
        }

        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                while (true) {
                    System.out.println("Please input neighbor's IP to connect");
                    String IP = br.readLine();
                    System.out.println("Please input the distance between you and your neighbor");
                    int distance = Integer.valueOf(br.readLine());

                    new ConnRequest(IP, distance);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Host up");
        new Host();// 启动服务端
    }
}
