package my.hello;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class JniLoader {
    public static String getPackageName(Class<?> clazz) {
        String name = clazz.getCanonicalName();
        int comma = name.lastIndexOf('.');
        if (comma == -1) {
            return "";
        }
        return name.substring(0, comma);
    }

    public static void load(Class<?> clazz, String soName) {
        File soDir = new File(System.getProperty("java.io.tmpdir"), getPackageName(clazz));
        File soFile = new File(soDir, soName);

        if (!soDir.exists()) {
            if (!soDir.mkdirs()) {
                throw new RuntimeException(String.format("Failed to mkdirs(\"%s\").", soDir.toString()));
            }
        }

        InputStream is = clazz.getResourceAsStream(soName);
        if (is == null) {
            throw new RuntimeException(String.format("Failed to getResourceAsStream(\"%s\").", soName));
        }

        try {
            Files.copy(is, soFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            is.close();
            soFile.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.load(soFile.toString());
    }
}
