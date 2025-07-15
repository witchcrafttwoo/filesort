import javax.swing.JFileChooser;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.io.IOException;

public class DirectorySelector {

    // 他のメソッドでも使えるようにフィールドとして保持
    private static File selectedDirectory;

    public static Path chooseDirectory(int key) {
        String messege = null;
        if (key == 1 ){
            messege = "常に監視するディレクトリを選択してください";
        }
        if (key == 2){
            messege = "移動先のディレクトリを選択してください";
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle(messege);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedDirectory = fileChooser.getSelectedFile();
            System.out.println("選択されたディレクトリ: " + selectedDirectory.getAbsolutePath());
        } else {
            System.out.println("ディレクトリ選択がキャンセルされました。");
        }
        return selectedDirectory.toPath();
    }

  //   選択されたパスを返す
    public static Path getPath() {
        if (selectedDirectory != null) {
            return selectedDirectory.toPath();
        }
        return null;
    }

    // 例：指定ファイルを選択フォルダに移動
    public static void moveFile(Path sourceFilePath) {
        Path destinationDir = getPath();
        if (destinationDir == null) {
            System.out.println("移動先ディレクトリが選択されていません。");
            return;
        }
    //  getfilename()はsourceFilePath.getFileName()
        //sourceFilePath が例えば C:/temp/test.txt なら、
        //
        //getFileName() は "test.txt" というファイル名の部分だけを返します。
        Path destinationPath = destinationDir.resolve(sourceFilePath.getFileName());

        try {
            Files.move(sourceFilePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("ファイルを移動しました: " + destinationPath);
        } catch (IOException e) {
            System.out.println("ファイルの移動に失敗しました: " + e.getMessage());
        }
    }
    public static Path chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("移動元ファイルを選択してください");

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("選択されたファイル: " + selectedFile.getAbsolutePath());
            return selectedFile.toPath();
        } else {
            System.out.println("ファイル選択がキャンセルされました。");
            return null;
        }
    }
}

