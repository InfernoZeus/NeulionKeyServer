package infernozeus.neulion.spark;

import spark.*;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpVersion;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class KeyRequestRoute extends Route {

    private static final String PATH = "/nlsk/hls/securekey";
    private static final String USER_AGENT = "PS4 libhttp/1.60 (PlayStation 4)";

    private final HttpClient httpClient;

    public KeyRequestRoute() throws Exception {
        super(PATH);
        httpClient = new HttpClient();
        httpClient.setUserAgentField(new HttpField(HttpHeader.USER_AGENT, USER_AGENT));
        httpClient.start();
    }

    @Override
    public Object handle(Request request, Response response) {
        System.out.println("Request from VLC:");
        System.out.println(request);

        try {
            String authCookie = getAuthCookie();
            addAuthCookieToCookieStore(authCookie);
//            NeulionResponse neulionResponse = getKeyFromNeulion(request.uri(), authCookie);
//            authCookie = getAuthCookie();
            NeulionResponse neulionResponse2 = getKeyFromNeulionWithoutJetty(request.uri() + "?" + request.queryString(), authCookie);
//            if (!neulionResponse.equals(neulionResponse2)) {
//                System.err.println("Responses not equal!!!");
//            }
            sendDataToResponse(response, neulionResponse2);
            return ""; // TODO
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.status(503);
        return "Error retrieving key from Neulion";
    }

    private void sendDataToResponse(Response response, NeulionResponse neulionResponse) throws IOException {
        DataOutputStream out = new DataOutputStream(response.raw().getOutputStream());
        out.write(neulionResponse.content());
        out.flush();
        out.close();
    }

    private void addCookiesToResponse(Response response) throws URISyntaxException {
        CookieStore cookieStore = httpClient.getCookieStore();
        List<HttpCookie> cookies = cookieStore.get(new URI(".neulion.com"));
        for (HttpCookie cookie : cookies) {
            response.cookie(cookie.getName(), cookie.getValue(), (int) cookie.getMaxAge());
        }
    }

    private void addAuthCookieToCookieStore(String authCookie) {
        CookieStore cookieStore = httpClient.getCookieStore();
        HttpCookie cookie = new HttpCookie("nlqptid", "__auth__=" + authCookie);
        cookieStore.add(URI.create("http://209.87.141.51"), cookie);
    }

    public String getAuthCookie() throws IOException {
        String playlistUrl = "a";
        URL url = new URL("http://gamecenter.nhl.com/nhlgeo/dgeth?cdn_type=3&url="+playlistUrl+"&token=0");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestProperty("User-Agent", "PS4Application libhttp/1.000 (PS4) libhttp/1.60 (PlayStation 4)");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return response.toString();
        } else {
            throw new IOException("Invalid response when attempting to retrieve AuthCookie");
        }
    }

    private NeulionResponse getKeyFromNeulion(String uri, String authCookie) throws IOException {
        try {
            ContentResponse response = httpClient.GET("http://209.87.141.51" + uri);
            NeulionResponse neulionResponse = new NeulionResponse(response);
            return neulionResponse;
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new IOException();
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new IOException();
        } catch (TimeoutException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new IOException();
        }
    }

    private NeulionResponse getKeyFromNeulionWithoutJetty(String uri, String authCookie) throws IOException {
        URL url = new URL("http://209.87.141.51" + uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Host", "nlsk.neulion.com");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Cookie", "nlqptid=__auth__=" + authCookie);
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            Map<String, List<String>> headers = conn.getHeaderFields();

            List<String> statusLineList = headers.get(null);
            String statusLine = statusLineList.get(0);
            String[] statusLineParts = statusLine.split(" ");
            String versionStr = statusLineParts[0];
            String statusStr = statusLineParts[1];
            HttpVersion version = HttpVersion.fromString(versionStr);
            int status = Integer.valueOf(statusStr);
            String reason = statusLineParts[2];

            HttpFields actualHeaders = new HttpFields();
            for (String name : headers.keySet()) {
                if (name == null || name.isEmpty()) {
                    continue;
                }
                List<String> values = headers.get(name);
                for (String value : values) {
                    actualHeaders.add(name, value);
                }
            }

            byte[] content = new byte[0];
            String value = actualHeaders.get("Content-Length");
            if (value != null && !value.isEmpty()) {
                int contentLength = Integer.valueOf(value);
                content = new byte[contentLength];
                conn.getInputStream().read(content);
//                int lastField = content[contentLength-1] & 0xff;
//                System.out.println("last: " + lastField);
            }
//            System.out.println("Key:");
//            System.out.println(DatatypeConverter.printHexBinary(content));
            NeulionResponse response = new NeulionResponse(version, status, reason, actualHeaders, content);
            return response;


//            InputStream inputStream = conn.getInputStream();
//            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
//            // First line
//            String line = in.readLine();
//
//            String[] firstLineParts = line.split(" ");
//            String versionStr = firstLineParts[0];
//            String statusStr = firstLineParts[1];
//            HttpVersion version = HttpVersion.fromString(versionStr);
//            int status = Integer.valueOf(statusStr);
//            String reason = firstLineParts[2];
//
//            //Read headers
//            HttpFields headers = new HttpFields();
//            int contentLength = 0;
//            while ((line = in.readLine()) != null && !line.isEmpty()) {
//                int colonIndex = line.indexOf(':');
//                String name = line.substring(0, colonIndex);
//                String value = line.substring(colonIndex + 2, line.length()-1);
//                headers.add(name, value);
//                if (name.equalsIgnoreCase("Content-Length")) {
//                    contentLength = Integer.valueOf(value);
//                }
//            }
//            byte[] content = new byte[0];
//            if (contentLength > 0) {
//                content = new byte[contentLength];
//                inputStream.read(content);
//            }
//
//            in.close();
//            NeulionResponse response = new NeulionResponse(version, status, reason, headers, content);
//            return response;
        } else {
            throw new IOException("Invalid response when attempting to retrieve AuthCookie");
        }
    }


}
