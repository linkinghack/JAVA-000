import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * HelloClassLoader
 */
public class HelloClassLoader extends ClassLoader {

    private static String helloClassFileName = "./Hello.xlass";

    public static void main(String[] args) {
        if (args.length > 0) {
            helloClassFileName = args[0];
        }

        try {
            HelloClassLoader classLoader = new HelloClassLoader();
            byte[] classBytes = classLoader.loadClassFile(helloClassFileName);
            Class<?> helloClass = classLoader.parseClassFromBytes("Hello", classBytes);
            Object helloObj = helloClass.getConstructor().newInstance();
            helloClass.getMethod("hello").invoke(helloObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class<?> parseClassFromBytes(String name, byte[] classBytes) {
        return defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadClassFile(String fileName) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(fileName);
        byte[] fileBytes = is.readAllBytes();
        for (int i = 0; i < fileBytes.length; i++) {
            fileBytes[i] = (byte) (255 - fileBytes[i]);
        }
        is.close();
        return fileBytes;
    }

}