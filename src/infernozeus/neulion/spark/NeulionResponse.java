package infernozeus.neulion.spark;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpVersion;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.util.Arrays;

public class NeulionResponse {

    private final HttpVersion version;
    private final int status;
    private final String reason;
    private final HttpFields headers;
    private final byte[] content;

    public NeulionResponse(ContentResponse response) {
        this.version = response.getVersion();
        this.status = response.getStatus();
        this.reason = response.getReason();
        this.headers = response.getHeaders();
        this.content = response.getContent();

        headers.remove("Connection");
        headers.remove("Keep-Alive");
        headers.add("Connection", "close");
    }

    public NeulionResponse(HttpVersion version, int status, String reason, HttpFields headers, byte[] content) {
        this.version = version;
        this.status = status;
        this.reason = reason;
        this.headers = headers;
        this.content = content;

        headers.remove("Connection");
        headers.remove("Keep-Alive");
        headers.add("Connection", "close");
    }

    public byte[] data() {
        byte[] headersBytes = headerData();
        byte[] data = new byte[headersBytes.length + content.length];
        System.arraycopy(headersBytes, 0, data, 0, headersBytes.length);
        System.arraycopy(content, 0, data, headersBytes.length, content.length);
        return data;
    }

    public byte[] content() {
        return content;
    }

    public byte[] headerData() {
        String headers = constructHeaders();
        byte[] headersBytes = headers.getBytes(Charset.forName("UTF-8"));
        return headersBytes;
    }

    private String constructHeaders() {
        String retString = version.toString() + " " + status + " " + reason + "\r\n";
        retString += headers.toString();
        // HttpFields.toString() includes a trailing blank line
        return retString;
    }

    @Override
    public String toString() {
        String retString = constructHeaders();
        retString += DatatypeConverter.printHexBinary(content) + "\r\n";
//        try {
//            retString += new String(content, "UTF-8") + "\r\n";
//        } catch (UnsupportedEncodingException e) {
//            retString += content + "\r\n";
//        }
        return retString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof NeulionResponse)) {
            return false;
        }
        NeulionResponse other = (NeulionResponse) obj;
        if (!this.version.asString().equals(other.version.toString())) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!this.reason.equals(other.reason)) {
            return false;
        }
        if (!this.headers.toString().equals(other.headers.toString())) {
            return false;
        }
        if (!Arrays.equals(this.content, other.content)) {
            return false;
        }
        return true;
    }


}
