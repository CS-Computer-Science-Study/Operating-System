package v5_final;

import common.HttpRequest;
import common.HttpResponse;
import common.HttpServlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CustomNotFoundServlet implements HttpServlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.setStatusCode(404);
        Path path = Path.of("resources/404.html");
        try (Stream<String> lineStream = Files.lines(path, StandardCharsets.UTF_8)) {
            lineStream.forEach(response::writeBody);
        }
    }
}
