package Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HostChannel {
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Socket socket;

    public HostChannel(Socket s) throws IOException {
        this.socket = s;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    public void sendRouteTable (RouteTable rt) throws IOException {
        oos.writeObject(rt);
        oos.flush();
    }

    public ObjectInputStream getOis() throws IOException {
        return ois;
    }

    public String getIP() {
        return socket.getInetAddress().toString();
    }

    public int getPort() {
        return socket.getPort();
    }

    public String getAddress() {
        return getIP() + ":" + getPort();
    }

    public void close() throws IOException {
        socket.close();
        if (oos != null) oos.close();
        if (ois != null) ois.close();
    }
}
