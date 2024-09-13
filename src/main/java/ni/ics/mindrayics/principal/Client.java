package ni.ics.mindrayics.principal;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {

        final String HOST = "192.168.1.250";
        final Integer PORT = 5100;

        Socket socket =  (Socket) ((SocketFactory) SocketFactory.getDefault()).createSocket(HOST, PORT);

        ThreadClient threadClient = new ThreadClient(socket);
        threadClient.run();
    }
}
