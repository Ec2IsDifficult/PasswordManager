package sample.Model;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    public static byte[] readAllBytes(Path path){
        byte[] bytesRead = {};
        try{
            bytesRead = Files.readAllBytes(path);
        }catch(Exception e){
            e.printStackTrace();
        }
        return bytesRead;
    }

    public static void write(Path path, byte[] output) {
        String outfile = "";
        try{
            Files.write(path, output);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
