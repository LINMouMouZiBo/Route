package Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HostChannel {
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Socket socket;

    public HostChannel(Socket s, boolean isRequest) throws IOException {
        this.socket = s;
        if (isRequest) {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
        } else {
            this.ois = new ObjectInputStream(socket.getInputStream());
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        }
    }

    public void sendRouteTable (RouteTable rt) throws IOException {
        oos.writeObject(rt);
    }

    public ObjectInputStream getOis() throws IOException {
        return ois;
    }

    public String getIP() {
        return socket.getLocalAddress().toString();
    }

    public int getPort() {
        return socket.getLocalPort();
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
