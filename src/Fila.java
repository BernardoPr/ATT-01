package src;

import java.util.NoSuchElementException;

public class Fila<T> {
    private T[] elementos;
    private int frente;
    private int tras; 
    private int tamanho;
    private int capacidade;
    private static final int CAPACIDADE_PADRAO = 10;
    
 
    public Fila() {
        this(CAPACIDADE_PADRAO);
    }


    @SuppressWarnings("unchecked")
    public Fila(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero.");
        }
        this.capacidade = capacidade;
        this.elementos = (T[]) new Object[capacidade];
        this.frente = 0;
        this.tras = -1;
        this.tamanho = 0;
    }

    public void enfileirar(T elemento) {
        if (tamanho == capacidade) {
            redimensionar();
        }
        tras = (tras + 1) % capacidade; // Avança o índice 'tras' de forma circular
        elementos[tras] = elemento;
        tamanho++;
    }

    public T desenfileirar() {
        if (vazia()) {
            throw new NoSuchElementException("A fila está vazia.");
        }
        T elemento = elementos[frente];
        elementos[frente] = null; // Libera a referência para o GC
        frente = (frente + 1) % capacidade; // Avança o índice 'frente' de forma circular
        tamanho--;
        return elemento;
    }

    public T frente() {
        if (vazia()) {
            throw new NoSuchElementException("A fila está vazia.");
        }
        return elementos[frente];
    }

    public boolean vazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }

    /**
     * Dobra a capacidade da fila, reorganizando os elementos em um novo array linear.
     */
    @SuppressWarnings("unchecked")
    private void redimensionar() {
        int novaCapacidade = capacidade * 2;
        T[] novoArray = (T[]) new Object[novaCapacidade];

        // Copia os elementos do array circular para o novo array linear
        for (int i = 0; i < tamanho; i++) {
            novoArray[i] = elementos[(frente + i) % capacidade];
        }

        elementos = novoArray;
        capacidade = novaCapacidade;
        frente = 0;
        tras = tamanho - 1;
    }
}