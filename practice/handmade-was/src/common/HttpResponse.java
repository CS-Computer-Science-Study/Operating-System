package common;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final PrintWriter writer;
    private int statusCode = 200;
    private final Map<String, String> headers = new HashMap<>();
    private final StringBuilder bodyBuilder = new StringBuilder();

    public HttpResponse(PrintWriter writer) {
        this.writer = writer;
        this.headers.put("Content-Type", "text/html; charset=UTF-8");
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void writeBody(String body) {
        bodyBuilder.append(body);
    }

    public void flush() {
        String body = bodyBuilder.toString();
        int contentLength = body.getBytes(StandardCharsets.UTF_8).length;

        // HTTP/1.1 200 OK
        writer.println("HTTP/1.1 " + statusCode + " " + getReasonPhrase(statusCode));
        writer.println("Content-Length: " + contentLength);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            writer.println(key + ": " + value);
        }

        writer.println();
        writer.println(body);
        writer.flush();
    }

    private String getReasonPhrase(int statusCode) {
        return switch (statusCode) {
            case 200 -> "OK";
            case 302 -> "Found";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default -> "Unknown Status";
        };
    }
}
