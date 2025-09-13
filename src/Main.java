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
            System.out.print("Escolha um ponto Y (0 a " + (img.getHeight()) + "): ");
            if (sc.hasNextInt()) {
                y = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Coordenada Y inválida!");
                sc.nextLine();
                continue;
            }
            
            if (y < 0 || y >= img.getHeight()) {
                System.out.println("Coordenada Y inválida! Deve estar entre 0 e " + (img.getHeight()));
                continue;
            } else {
                break;
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
        System.out.println("4 - RGB personalizado");
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
        else if (corEscolhida == 4) {
            // RGB personalizado
            int r = -1, g = -1, b = -1;
            limparTerminal();
            while (true) {
                System.out.print("Digite o valor R (0-255): ");
                if (sc.hasNextInt()) {
                    r = sc.nextInt();
                    if (r >= 0 && r <= 255) break;
                    else System.out.println("Valor deve estar entre 0 e 255!");
                } else {
                    System.out.println("Digite um número válido!");
                    sc.nextLine();
                }
            }
            
            while (true) {
                System.out.print("Digite o valor G (0-255): ");
                if (sc.hasNextInt()) {
                    g = sc.nextInt();
                    if (g >= 0 && g <= 255) break;
                    else System.out.println("Valor deve estar entre 0 e 255!");
                } else {
                    System.out.println("Digite um número válido!");
                    sc.nextLine();
                }
            }
            
            while (true) {
                System.out.print("Digite o valor B (0-255): ");
                if (sc.hasNextInt()) {
                    b = sc.nextInt();
                    sc.nextLine();
                    if (b >= 0 && b <= 255) break;
                    else System.out.println("Valor deve estar entre 0 e 255!");
                } else {
                    System.out.println("Digite um número válido!");
                    sc.nextLine();
                }
            }
            
            fillColor = new Color(r, g, b);
            System.out.println("Cor personalizada: RGB(" + r + ", " + g + ", " + b + ")");
        }
        else {
            System.out.println("Opção inválida! Usando verde como padrão.");
            fillColor = Color.GREEN;
        }
        
        System.out.println("Colorindo...");
        String outputDir = "imagem/output";
        int step = 100;
        FloodFill.fill(img, x, y, targetColor, fillColor, step, outputDir, metodo);
      
        ArrayList<String> imagens = new ArrayList<>();
        File[] outputFiles = new File(outputDir).listFiles();
        if (outputFiles != null) {
            for (File f : outputFiles) {
                if (f.getName().startsWith("step_") && f.getName().endsWith(".png")) {
                    imagens.add(f.getPath());
                }
            }
        }
        sortImagePaths(imagens);
        
        String gifPath = "gift/" + arquivos[escolha].getName().replaceAll("\\..*", "") + ".gif";
        GifGenerator.createGif(imagens, gifPath);
        System.out.println("Disponível em " + gifPath);
        
        sc.close();
    }
    
    /**
     * Ordena os caminhos das imagens numericamente
     * @param imagens lista de caminhos de imagens para ordenar
     */
    private static void sortImagePaths(ArrayList<String> imagens) {
        // Implementação simples de bubble sort para ordenar os caminhos das imagens
        int n = imagens.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String path1 = imagens.get(j);
                String path2 = imagens.get(j + 1);
                
                // Extrai o número do step_ do nome do arquivo
                int num1 = extractStepNumber(path1);
                int num2 = extractStepNumber(path2);
                
                if (num1 > num2) {
                    // Troca as posições
                    String temp = imagens.get(j);
                    imagens.set(j, imagens.get(j + 1));
                    imagens.set(j + 1, temp);
                }
            }
        }
    }
    
    /**
     * Extrai o número do step do nome do arquivo
     * @param path caminho do arquivo
     * @return número do step
     */
    private static int extractStepNumber(String path) {
        try {
            // Extrai o nome do arquivo do caminho completo
            String fileName = new File(path).getName();
            // Remove "step_" do início e ".png" do final
            String numberStr = fileName.substring(5, fileName.length() - 4);
            return Integer.parseInt(numberStr);
        } catch (Exception e) {
            return 0; // Retorna 0 se não conseguir extrair o número
        }
    }
    
    private static void limparTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}