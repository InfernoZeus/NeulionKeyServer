import java.io.*;

public final class NeulionResponseReader implements Serializable
{
    private String firstLine;
    private String responseBody;
    private boolean noCookieYet;
    byte[] responseContent;
    
    public NeulionResponseReader(final DataInputStream dataInputStream) {
        super();
        this.firstLine = "";
        this.responseBody = "";
        this.noCookieYet = true;
        this.responseContent = new byte[100000000];
        int contentLength = -1;
        int read = 0;
        try {
            for (String responseLine = dataInputStream.readLine(); responseLine != null; responseLine = dataInputStream.readLine()) {
                if (responseLine.length() == 0) {
                    break;
                }
                if (read == 0) {
                    this.firstLine = responseLine;
                    read = 1;
                }
                else {
                    this.responseBody = this.responseBody + responseLine + "\r\n";
                }
                if (responseLine.startsWith("Content-Length") || responseLine.startsWith("Content-length")) {
                    final String[] split = responseLine.split(" ");
                    try {
                        contentLength = Integer.parseInt(split[1]);
                    }
                    catch (NumberFormatException e) {
                        contentLength = -1;
                    }
                }
                if (responseLine.startsWith("Set-Cookie") && this.noCookieYet) {
                    MainClass.key = (MainClass.key = responseLine.substring(responseLine.indexOf(" "), responseLine.length()).trim()).substring(0, MainClass.key.indexOf(";")).trim();
                    this.noCookieYet = false;
                    MainClass.hasActualKey = true;
                }
                else if (!this.noCookieYet && responseLine.startsWith("Set-Cookie")) {
                    MainClass.otherCookieValue = (MainClass.otherCookieValue = responseLine.substring(responseLine.indexOf(" "), responseLine.length()).trim()).substring(0, MainClass.otherCookieValue.indexOf(";") + 1).trim();
                    this.noCookieYet = true;
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error!!: ");
            return;
        }
        try {
            int i = 0;
            final byte[] split = new byte[15000000];
            while (i < contentLength) {
                if ((read = dataInputStream.read(split)) == -1) {
                    return;
                }
                int n = 0;
                while (n < read && n + i < 100000000) {
                    this.responseContent[i++] = split[n];
                    ++n;
                }
            }
            return;
        }
        catch (IOException ex) {
            System.out.println(new StringBuilder("Error reading response body: ").append(ex).toString());
            return;
        }
    }
    
    public final String toString() {
        String s = this.firstLine + "\r\n";
        s = s + this.responseBody;
        return s + "\r\n";
    }
}
