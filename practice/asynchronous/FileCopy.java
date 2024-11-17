package asynchronous;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileCopy implements Runnable {

    private final String source;
    private final String target;

    public FileCopy(String source, String target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public void run() {
        Path source = Path.of(this.source);
        Path target = Path.of(this.target);

        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
