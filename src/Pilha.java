package src;

import java.util.EmptyStackException;

public class Pilha<T> {

    private static class No<T> {
        private final T dado;
        private No<T> proximo;

        public No(T dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }

    private No<T> topo; // Referência para o nó no topo da pilha
    private int tamanho;

    /**
     * Constrói uma pilha vazia.
     */
    public Pilha() {
        this.topo = null;
        this.tamanho = 0;
    }

    /**
     * Adiciona um elemento no topo da pilha.
     *
     * @param elemento o elemento a ser adicionado.
     */
    public void empilhar(T elemento) {
        No<T> novoNo = new No<>(elemento);
        if (!vazia()) {
            novoNo.proximo = this.topo;
        }
        this.topo = novoNo;
        this.tamanho++;
    }

    public T desempilhar() {
        if (vazia()) {
            throw new EmptyStackException();
        }
        T dado = this.topo.dado;
        this.topo = this.topo.proximo;
        this.tamanho--;
        return dado;
    }

    public T topo() {
        if (vazia()) {
            throw new EmptyStackException();
        }
        return this.topo.dado;
    }

    public boolean vazia() {
        return this.topo == null;
    }

    public int tamanho() {
        return this.tamanho;
    }
}