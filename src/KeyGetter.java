import java.net.*;
import java.io.*;

public final class KeyGetter
{
    public static String getKey(String inputString) throws IOException {
        Socket socket = new Socket("gamecenter.nhl.com", 80);
        inputString = "GET /nhlgeo/dgeth?cdn_type=3&url=" + (String)inputString + "&token=0 HTTP/1.1" + "\r\n";
        inputString = inputString + "HOST: gamecenter.nhl.com" + "\r\n";
        inputString = inputString + "User-Agent: PS4Application libhttp/1.000 (PS4) libhttp/1.60 (PlayStation 4)" + "\r\n";
        inputString = inputString + "Connection: Close" + "\r\n\r\n";
        new DataOutputStream(socket.getOutputStream()).writeBytes(inputString);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        while (dataInputStream.readLine().length() != 0) {}
        byte[] byteArray = new byte[500];
        dataInputStream.read(byteArray);
        String retString = new String(byteArray, "UTF-8");
        return retString.trim();
    }

    public static String getKeyWithHttpUrlConnection(String playlistUrl) throws IOException {
        URL url = new URL("http://gamecenter.nhl.com/nhlgeo/dgeth?cdn_type=3&url="+playlistUrl+"&token=0");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "PS4Application libhttp/1.000 (PS4) libhttp/1.60 (PlayStation 4)");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new IOException();
        }
    }
}
