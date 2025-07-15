import java.io.IOException;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;

import java.util.HashMap;
import java.util.Map;

public class Autosorter {
    private static final Map<String, Path> keywordRules = new HashMap<>();
    public static void fileMoving() throws IOException, InterruptedException{











    }
    public static void watch (Path watchPath)  {

        try{
            WatchService watcher = FileSystems.getDefault().newWatchService();
            watchPath.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
            System.out.println("Watching " + watchPath);

            while (true){
                WatchKey  key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    // 作成されたファイル名を取得
                    Path newFile = watchPath.resolve((Path) event.context());
                    String fileName = newFile.getFileName().toString();
                    System.out.println("新しいファイル発見: " + fileName);

                    // すでに登録してあるキーワードルールを調べて移動する
                    for (Map.Entry<String, Path> rule : keywordRules.entrySet()) {
                        String keyword = rule.getKey();
                        Path destination = rule.getValue();

                        if (fileName.toLowerCase().contains(keyword)) {
                            Path targetPath = destination.resolve(fileName);
                            try {
                                Files.move(newFile, targetPath, StandardCopyOption.REPLACE_EXISTING);
                                System.out.println("→ ファイルを移動しました: " + targetPath);
                            } catch (IOException e) {
                                System.out.println("移動失敗: " + e.getMessage());
                            }
                            break; // 最初に一致した1つだけ処理
                        }
                    }
                }
                key.reset();
            }

        }catch (IOException  |  InterruptedException e){
            e.printStackTrace();
            return;
        }


    }
    public static void addRule(String keyword, Path targetDir){
        keywordRules.put(keyword, targetDir);
        System.out.println("登録されたルール: 「" + keyword + "」→ " + targetDir);
    }
}
