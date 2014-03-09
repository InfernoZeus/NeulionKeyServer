import java.net.*;
import java.io.*;

public class MainClass
{
    private static int port;
    private static ServerSocket socket;
    public static String url;
    public static String key;
    public static String otherCookieValue;
    public static boolean hasActualKey;
    
    static {
        MainClass.socket = null;
        MainClass.key = null;
        MainClass.otherCookieValue = null;
        MainClass.hasActualKey = false;
    }
    
    public static void main(String[] args) {
        if (args.length <= 0) {
            System.out.println("Please enter URL");
            return;
        }
        MainClass.url = args[0];
        MainClass.port = 80;
        try {
            MainClass.socket = new ServerSocket(port, 0, InetAddress.getByName("localhost"));
        }
        catch (IOException exception) {
//            final Socket socket;
            System.out.println(new StringBuilder("Error creating socket: ").append(exception).toString());
            System.exit(-1);
        }

        while (true) {
            try {
                while (true) {
                    Socket readSocket = null;
                    if (!MainClass.url.contains("nba") || !MainClass.url.contains("nfl")) {
                        readSocket = MainClass.socket.accept();
                    }
                    if (readSocket.isConnected()) {
                        Closeable reader = null;
                        Object o;
                        try {
                            readSocket.setSoTimeout(5000);
                            reader = new BufferedReader(new InputStreamReader(readSocket.getInputStream()));
                            ((BufferedReader)reader).mark(8192);
                            String s = null;
                            System.out.println("VLC Request:");
                            while ((s=((BufferedReader)reader).readLine())!=null && !s.isEmpty())
                            {
                                System.out.println(s);
                            }
                            ((BufferedReader)reader).reset();
                            PushbackReader pushReader = new PushbackReader((BufferedReader)reader);
                            int firstChar = pushReader.read();
                            if (firstChar == 0) {
                                System.out.println("BufferedReader is not ready");
                                continue;
                            } else {
                                pushReader.unread(firstChar);
                            }
                            o = new HttpRequest(new BufferedReader(pushReader));
                        }
                        catch (IOException e) {
                            System.out.println(new StringBuilder("Error reading request from client: ").append(e).toString());
                            continue;
                        }
                        final Socket socket;
                        try {
                            socket = new Socket(((HttpRequest)o).host, 80);
                            new DataOutputStream(socket.getOutputStream()).writeBytes(((HttpRequest)o).toString());
                        }
                        catch (UnknownHostException e) {
                            System.out.println("Unknown host: " + ((HttpRequest)o).host);
                            System.out.println(e);
                            continue;
                        }
                        catch (IOException e) {
                            System.out.println(new StringBuilder("Error writing request to server: ").append(e).toString());
                            continue;
                        }
                        try {
                            reader = new DataInputStream(socket.getInputStream());
                            final NeulionResponseReader neulionResponseReader = new NeulionResponseReader((DataInputStream)reader);
//                            final Closeable closeable2 = reader;
//                            socket.getInputStream();
//                            new NeulionResponseReader((DataInputStream)closeable2);
//                            o = otherCookieValue;
                            ((DataOutputStream)(reader = new DataOutputStream(readSocket.getOutputStream()))).writeBytes(neulionResponseReader.toString());
                            ((FilterOutputStream)reader).write(neulionResponseReader.responseContent);
                            readSocket.close();
                            socket.close();
                            continue;
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            continue;

                        }
                    }
                    System.err.println("Socket is not connected");
                }
            }
            catch (IOException e) {
                System.out.println(new StringBuilder("Error reading request from client: ").append(e).toString());
                continue;
            }
//            break;
        }
    }
}
