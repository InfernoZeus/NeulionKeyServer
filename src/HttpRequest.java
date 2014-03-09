import java.io.*;

public final class HttpRequest
{
    private String method;
    private String requestUri;
    private String httpVersion;
    private String requestBody;
    private String userAgent;
    String host;
    
    public HttpRequest(final BufferedReader bufferedReader) {
        super();
        this.requestBody = "";
        this.userAgent = "PS4 libhttp/1.60 (PlayStation 4)";
        this.host = "";
        String requestLine = "";
        try {
            requestLine = bufferedReader.readLine();
        }
        catch (IOException e) {
            System.out.println("COULDN'T READ REQUEST FROM VLC SHITS BROKE ALSO FUCK NEULION. GET BETTER DEVELOPERS, LIKE HOLY SHIT THIS IS SO EASY");
            System.exit(0);
        }
        String[] requestLineParts = new String[3];
        try {
            requestLineParts = requestLine.split(" ");
        }
        catch (NullPointerException e) {
            System.out.println("REQUEST FROM VLC WAS NULL SOMETHING IS WRONG LOL");
            System.exit(0);
        }
        this.method = requestLineParts[0];
        this.requestUri = requestLineParts[1];
        this.httpVersion = requestLineParts[2];
        if (this.requestUri.contains(".key") && MainClass.key == null) {
//            goto Label_0144;
//        }
            try {
//                MainClass.key = KeyGetter.getKey(MainClass.url);
                MainClass.key = KeyGetter.getKeyWithHttpUrlConnection(MainClass.url);
            }
            catch (IOException e) {
                System.out.println("Couldn't Generate key!");
                System.exit(0);
            }
        }
        try {
            requestLine = bufferedReader.readLine();
            while(requestLine.length() != 0) {
                if (requestLine.startsWith("Connection:") || requestLine.startsWith("Icy") || requestLine.contains("Pragma") || requestLine.contains("Range")) {
                    requestLine = bufferedReader.readLine();
                    continue;
                } else if (requestLine.startsWith("User-Agent")) {
                    this.requestBody = this.requestBody + "User-Agent: " + this.userAgent + "\r\n";
                } else {
                    this.requestBody = this.requestBody + (String)requestLine + "\r\n";
                }

                if (requestLine.startsWith("Host:")) {
                    if ((requestLineParts = requestLine.split(" "))[1].indexOf(':') > 0) {
                        String[] split = requestLineParts[1].split(":");
                        this.host = split[0];
                        Integer.parseInt(split[1]);
                    }
                    else {
                        this.host = requestLineParts[1];
                    }
                    if (this.host.contains("nlsk.neulion.com")) {
                        this.host = this.host.replace("nlsk.neulion.com", "209.87.141.51");
                    }
                }
                requestLine = bufferedReader.readLine();
            }
            if (MainClass.key != null) {
                if (!MainClass.hasActualKey) {
                    this.requestBody = this.requestBody + "Cookie: nlqptid=__auth__=" + MainClass.key + "\r\n";
                    return;
                }
                if (MainClass.otherCookieValue != null) {
                    this.requestBody = this.requestBody + "Cookie: " + MainClass.otherCookieValue + " " + MainClass.key + "\r\n";
                    return;
                }
                this.requestBody = this.requestBody + "Cookie: " + MainClass.key + "\r\n";
                return;
            }
        }
        catch (IOException e) {
            System.out.println(new StringBuilder("Error reading from socket: ").append(e).toString());
            System.exit(0);
        }
    }
    
    public final String toString() {
        String s = this.method + " " + this.requestUri + " " + this.httpVersion + "\r\n";
        s = s + this.requestBody;
        s = s + "Connection: close\r\n";
        return s + "\r\n";
    }
}
