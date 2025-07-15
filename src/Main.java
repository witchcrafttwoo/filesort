import java.io.File;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.WatchService;

public class Main {
    public static void main(String[] args) {
        Path sourseDir = DirectorySelector.chooseDirectory(1);
        Path targetDir = DirectorySelector.chooseDirectory(2);
        Autosorter.addRule("情報",targetDir );
        System.out.println("監視対象: " + sourseDir);
        System.out.println("保存先: " + targetDir);
        Autosorter.watch(sourseDir);



    }
}