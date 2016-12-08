import Utils.HostChannel;
import Utils.Logger;
import Utils.RouteTable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Host {
    public static final int SERVER_PORT = 2016;
    private ServerSocket serverSocket = null;
    private List<HostChannel> connList = new ArrayList<>();
    private RouteTable routeTable;
    private static Lock lock = new ReentrantLock(true);

    public Host() throws IOException {
        routeTable = new RouteTable();

        Scanner keyboard = new Scanner(System.in);
        System.out.println("please input a port number as server port");
        int port = keyboard.nextInt();

        try {
            serverSocket = new ServerSocket(port);
            // 监听键盘输入的命令
            new CommandHandler();
            // 监听邻居节点的连接请求
            while (true) {
                Socket socket = serverSocket.accept();
                Logger.i("main", "about connecting to " + socket.getPort());
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
                //oos.writeObject(b);
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
                    Logger.logRouteTable(rt, neighbor.getPort());
                    // 更新路由表
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

        public ConnRequest(String IP, int port) {
            try {
                Socket socket = new Socket(IP, port);
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
                broadcast(neighbor.getAddress());

                RouteTable rt;
                while ((rt = (RouteTable)(neighbor.getOis().readObject())) != null) {
                    Logger.logRouteTable(rt, neighbor.getPort());
                    // 更新路由表
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
            System.out.println("Please input neighbor's port to connect");
            start();
        }

        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String str = null;
            String neighborIP = "127.0.0.1";
            try {
                while (true) {
                    str = br.readLine();
                    int port = Integer.valueOf(str);
                    new ConnRequest(neighborIP, port);
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