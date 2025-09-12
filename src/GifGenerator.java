package src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.ImageIO;

public class GifGenerator {
    public static void createGif(List<String> imagePaths, String outputPath) throws Exception {
        ImageOutputStream output = new FileImageOutputStream(new File(outputPath));
        GifSequenceWriter writer = null;
        try {
            BufferedImage first = ImageIO.read(new File(imagePaths.get(0)));
            writer = new GifSequenceWriter(output, first.getType(), 200, true);
            for (String path : imagePaths) {
                BufferedImage next = ImageIO.read(new File(path));
                writer.writeToSequence(next);
            }
        } finally {
            if (writer != null) writer.close();
            output.close();
        }
    }
}
// Você precisa adicionar a classe GifSequenceWriter (disponível em vários exemplos online)
