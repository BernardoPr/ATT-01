package src;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;

public class FloodFill {
    public static void fill(BufferedImage img, int x, int y, Color targetColor, Color fillColor, int step, String outputDir, int metodo) throws Exception {
        int width = img.getWidth();
        int height = img.getHeight();
        boolean[][] visited = new boolean[height][width];
        int count = 0;
        if (metodo == 1) {
            Fila<Ponto> fila = new Fila<>();
            fila.enfileirar(new Ponto(x, y));
            while (!fila.vazia()) {
                Ponto p = fila.desenfileirar();
                int px = p.x, py = p.y;
                if (px < 0 || py < 0 || px >= width || py >= height) continue;
                if (visited[py][px]) continue;
                if (new Color(img.getRGB(px, py)).equals(targetColor)) {
                    img.setRGB(px, py, fillColor.getRGB());
                    visited[py][px] = true;
                    count++;
                    if (count % step == 0) {
                        ImageIO.write(img, "png", new File(outputDir + "/step_" + count + ".png"));
                    }
                    fila.enfileirar(new Ponto(px+1, py));
                    fila.enfileirar(new Ponto(px-1, py));
                    fila.enfileirar(new Ponto(px, py+1));
                    fila.enfileirar(new Ponto(px, py-1));
                }
            }
        } else {
            Pilha<Ponto> pilha = new Pilha<>();
            pilha.empilhar(new Ponto(x, y));
            while (!pilha.vazia()) {
                Ponto p = pilha.desempilhar();
                int px = p.x, py = p.y;
                if (px < 0 || py < 0 || px >= width || py >= height) continue;
                if (visited[py][px]) continue;
                if (new Color(img.getRGB(px, py)).equals(targetColor)) {
                    img.setRGB(px, py, fillColor.getRGB());
                    visited[py][px] = true;
                    count++;
                    if (count == 1000) {
                        System.out.println("1000 pixels coloridos...");
                    }
                    if (count % step == 0) {
                        ImageIO.write(img, "png", new File(outputDir + "/step_" + count + ".png"));
                    }
                    pilha.empilhar(new Ponto(px+1, py));
                    pilha.empilhar(new Ponto(px-1, py));
                    pilha.empilhar(new Ponto(px, py+1));
                    pilha.empilhar(new Ponto(px, py-1));
                }
            }
        }
        ImageIO.write(img, "png", new File(outputDir + "/final.png"));
    }
}
