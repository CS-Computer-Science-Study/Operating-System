package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String method;
    private String path;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> queryParameters = new HashMap<>();

    public HttpRequest(BufferedReader reader) throws IOException {
        parseRequestLine(reader);
        parseHeaders(reader);
    }

    private void parseRequestLine(BufferedReader reader) throws IOException {
        // GET /search?a=hello&b=bye HTTP/1.1
        String requestLine = reader.readLine();
        String[] parts = requestLine.split(" ");

        this.method = parts[0];

        String[] pathParts = parts[1].split("\\?");
        this.path = pathParts[0];
        if (pathParts.length > 1) {
            parseQueryParameters(pathParts[1]);
        }
    }

    private void parseQueryParameters(String queryString) {
        for (String queryPair : queryString.split("&")) {
            String[] keyAndValue = queryPair.split("=");

            String key = URLDecoder.decode(keyAndValue[0], StandardCharsets.UTF_8);
            String value = keyAndValue.length > 1 ? URLDecoder.decode(keyAndValue[1], StandardCharsets.UTF_8) : "";

            queryParameters.put(key, value);
        }
    }

    private void parseHeaders(BufferedReader reader) throws IOException {
        String line;
        while (!(line = reader.readLine()).isEmpty()) {
            int index = line.indexOf(":");
            headers.put(line.substring(0, index).trim(), line.substring(index).trim());
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQueryParameter(String name) {
        return queryParameters.get(name);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", queryParmeters=" + queryParameters +
                ", headers=" + headers +
                '}';
    }
}
