package algo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public final class SimpleCsv {
    private SimpleCsv(){}

    public static void append(String path, String headerIfNew, String line) {
        try {
            File f = new File(path);
            boolean fresh = !f.exists() || f.length() == 0;
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(f, true), StandardCharsets.UTF_8))) {
                if (fresh && headerIfNew != null) out.println(headerIfNew);
                out.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
