# Sistema Flood Fill

Sistema de preenchimento de imagens utilizando algoritmos de flood fill com estruturas de dados customizadas (Fila e Pilha).

## 📋 Funcionalidades

- **Algoritmos de Flood Fill:**
  - **BFS (Breadth-First Search)** - usando Fila customizada
  - **DFS (Depth-First Search)** - usando Pilha customizada
- **Processamento de Imagens:**
  - Suporte a formatos PNG, JPG, JPEG
  - Salvamento passo-a-passo do processo
  - Geração de GIF animado
- **Interface Terminal:**
  - Menu interativo
  - Validação de entradas
  - Tratamento de erros

## 🛠️ Estrutura do Projeto

```
src/
├── Main.java           # Classe principal com interface do usuário
├── FloodFill.java      # Algoritmo de flood fill
├── Fila.java           # Implementação customizada de fila
├── Pilha.java          # Implementação customizada de pilha
├── Ponto.java          # Classe para coordenadas
├── GifGenerator.java   # Gerador de GIF animado
└── GifSequenceWriter.java # Helper para criação de GIF

imagem/
├── input/              # Imagens de entrada (PNG, JPG, JPEG)
└── output/             # Imagens dos passos intermediários

gift/                   # GIFs gerados
```

## 🚀 Como Usar

1. **Compilar o projeto:**
   ```bash
   javac -cp . src/*.java
   ```

2. **Executar:**
   ```bash
   java -cp . src.Main
   ```

3. **Usar o sistema:**
   - Coloque suas imagens na pasta `imagem/input`
   - Escolha o método (Fila ou Pilha)
   - Selecione a imagem
   - Defina as coordenadas do ponto inicial
   - Escolha a cor de preenchimento
   - Veja o resultado em `gift/`

## 🎯 Estruturas de Dados Customizadas

### Fila (Queue)
- Implementação com array circular
- Métodos: `enfileirar()`, `desenfileirar()`, `vazia()`
- Usado para algoritmo BFS (largura)

### Pilha (Stack) 
- Implementação com lista ligada
- Métodos: `empilhar()`, `desempilhar()`, `vazia()`
- Usado para algoritmo DFS (profundidade)

## 📦 Dependências

- Java 8 ou superior
- Bibliotecas permitidas:
  - `java.awt.image.BufferedImage`
  - `java.io.File`
  - `javax.imageio.ImageIO`

## 🎨 Cores Disponíveis

- Vermelho
- Azul  
- Verde

## 📸 Saída

O sistema gera:
- Imagens intermediárias em `imagem/output/`
- GIF animado em `gift/` mostrando o processo passo-a-passo

---

*Projeto desenvolvido para demonstrar algoritmos de flood fill com estruturas de dados customizadas.*