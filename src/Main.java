package src;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        int metodo;
        while (true) {
            System.out.println("Escolha o método de preenchimento:");
            System.out.println("1 - Fila");
            System.out.println("2 - Pilha");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            
            if (sc.hasNextInt()) {
                metodo = sc.nextInt();
                sc.nextLine();
                
                if (metodo == 3) {
                    System.out.println("Saindo do programa...");
                    sc.close();
                    return;
                }
                
                if (metodo == 1 || metodo == 2) {
                    break;
                }
                
                System.out.println("Opção inválida!");
            } else {
                System.out.println("Por favor, digite um número válido!");
                sc.nextLine();
            }
        }
        
        File inputDir = new File("imagem/input");
        File[] arquivos = inputDir.listFiles((d, name) -> 
            name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg"));
        
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhuma imagem encontrada em imagem/input");
            sc.close();
            return;
        }
        
        System.out.println("\nImagens disponíveis:");
        for (int i = 0; i < arquivos.length; i++) {
           System.out.println((i+1) + " - " + arquivos[i].getName());
        }
        
        int escolha = -1;
        while (true) {    
            System.out.print("Escolha uma imagem: ");
            if (sc.hasNextInt()) {
                escolha = sc.nextInt() - 1;
                sc.nextLine(); // Limpa buffer
            } else {
                System.out.println("Entrada inválida!");
                sc.nextLine(); // Limpa entrada inválida
                continue;
            }
            
            if (escolha < 0 || escolha >= arquivos.length) {
                System.out.println("Opção inválida!");
                continue; // Volta ao início do loop
            } else {
                break; // Escolha válida, sai do loop
            }
        }
        
        BufferedImage img = ImageIO.read(arquivos[escolha]);
        
        limparTerminal();
        
        System.out.println("Tamanho da imagem: X: " + img.getWidth() + " / Y: " + img.getHeight());
        
        int x = -1;
        while (true) {
            System.out.print("Escolha um ponto X (0 a " + (img.getWidth()) + "): ");
            if (sc.hasNextInt()) {
                x = sc.nextInt();
            } else {
                System.out.println("Coordenada X inválida!");
                sc.nextLine(); // Limpa entrada inválida
                continue;
            }
            
            if (x < 0 || x >= img.getWidth()) {
                System.out.println("Coordenada X inválida! Deve estar entre 0 e " + (img.getWidth()));
                continue;
            } else {
                break; // Coordenada X válida, sai do loop
            }
        }
        
        int y = -1;
        while (true) {
            System.out.print("Escolha um ponto Y (0 a " + (img.getHeight()-1) + "): ");
            if (sc.hasNextInt()) {
                y = sc.nextInt();
                sc.nextLine(); // Limpa buffer
            } else {
                System.out.println("Coordenada Y inválida!");
                sc.nextLine(); // Limpa entrada inválida
                continue;
            }
            
            if (y < 0 || y >= img.getHeight()) {
                System.out.println("Coordenada Y inválida! Deve estar entre 0 e " + (img.getHeight()-1));
                continue;
            } else {
                break; // Coordenada Y válida, sai do loop
            }
        }
        
        System.out.println("Pressione Enter para continuar...");
        sc.nextLine();
        
        limparTerminal();
        
        Color targetColor = new Color(img.getRGB(x, y));
        System.out.println("Escolha a cor de preenchimento:");
        System.out.println("1 - Vermelho");
        System.out.println("2 - Azul");
        System.out.println("3 - Verde");
        System.out.print("Opção: ");
        int corEscolhida = -1;
        if (sc.hasNextInt()) {
            corEscolhida = sc.nextInt();
            sc.nextLine(); // Limpa buffer
        } else {
            System.out.println("Opção de cor inválida!");
            sc.close();
            return;
        }
        
        Color fillColor;
        if (corEscolhida == 1) fillColor = Color.RED;
        else if (corEscolhida == 2) fillColor = Color.BLUE;
        else if (corEscolhida == 3) fillColor = Color.GREEN;
        else {
            System.out.println("Cor inválida! Usando verde como padrão.");
            fillColor = Color.GREEN;
        }
        
        System.out.println("Colorindo...");
        String outputDir = "imagem/output";
        int step = 100; // salva a cada 100 pixels coloridos
        FloodFill.fill(img, x, y, targetColor, fillColor, step, outputDir, metodo);
        
        // Usar ArrayList do Java para compatibilidade com GifGenerator
        java.util.List<String> imagens = new java.util.ArrayList<>();
        File[] outputFiles = new File(outputDir).listFiles();
        if (outputFiles != null) {
            for (File f : outputFiles) {
                if (f.getName().startsWith("step_") && f.getName().endsWith(".png")) {
                    imagens.add(f.getPath());
                }
            }
        }
        java.util.Collections.sort(imagens);
        
        String gifPath = "gift/" + arquivos[escolha].getName().replaceAll("\\..*", "") + ".gif";
        GifGenerator.createGif(imagens, gifPath);
        System.out.println("Disponível em " + gifPath);
        
        sc.close();
    }
    
    private static void limparTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}