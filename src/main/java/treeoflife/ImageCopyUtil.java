package treeoflife;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageCopyUtil {
    public static void copyImage(String sourcePath, String destinationPath) {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);

        try {
            // 使用Files.copy复制文件
            Files.copy(source, destination.resolve(source.getFileName()));
            System.out.println("图片复制成功！");
        } catch (IOException e) {
            System.out.println("复制图片时出错：" + e.getMessage());
        }
    }
}
