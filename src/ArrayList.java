package src;

/**
 * Implementação personalizada de ArrayList
 * @param <T> Tipo genérico dos elementos armazenados
 */
public class ArrayList<T> implements java.lang.Iterable<T> {
    private T[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Construtor padrão com capacidade inicial de 10
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Construtor com capacidade inicial especificada
     * @param initialCapacity capacidade inicial do array
     */
    @SuppressWarnings("unchecked")
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Capacidade inicial não pode ser negativa: " + initialCapacity);
        }
        this.array = (T[]) new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Adiciona um elemento ao final da lista
     * @param element elemento a ser adicionado
     * @return true sempre (para compatibilidade com Collection)
     */
    public boolean add(T element) {
        ensureCapacity();
        array[size++] = element;
        return true;
    }

    /**
     * Adiciona um elemento em uma posição específica
     * @param index posição onde inserir o elemento
     * @param element elemento a ser inserido
     */
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Índice: " + index + ", Tamanho: " + size);
        }
        
        ensureCapacity();
        
        // Move elementos para a direita
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        
        array[index] = element;
        size++;
    }

    /**
     * Obtém um elemento em uma posição específica
     * @param index índice do elemento
     * @return elemento na posição especificada
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice: " + index + ", Tamanho: " + size);
        }
        return array[index];
    }

    /**
     * Define um elemento em uma posição específica
     * @param index posição do elemento
     * @param element novo elemento
     * @return elemento anterior na posição
     */
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice: " + index + ", Tamanho: " + size);
        }
        T oldElement = array[index];
        array[index] = element;
        return oldElement;
    }

    /**
     * Remove um elemento de uma posição específica
     * @param index posição do elemento a ser removido
     * @return elemento removido
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice: " + index + ", Tamanho: " + size);
        }
        
        T removedElement = array[index];
        
        // Move elementos para a esquerda
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        
        array[--size] = null; // Remove referência para ajudar GC
        return removedElement;
    }

    /**
     * Remove a primeira ocorrência do elemento especificado
     * @param element elemento a ser removido
     * @return true se o elemento foi removido, false caso contrário
     */
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if ((element == null && array[i] == null) || 
                (element != null && element.equals(array[i]))) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se a lista contém um elemento específico
     * @param element elemento a ser procurado
     * @return true se o elemento existe, false caso contrário
     */
    public boolean contains(T element) {
        return indexOf(element) >= 0;
    }

    /**
     * Encontra o índice da primeira ocorrência do elemento
     * @param element elemento a ser procurado
     * @return índice do elemento ou -1 se não encontrado
     */
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if ((element == null && array[i] == null) || 
                (element != null && element.equals(array[i]))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Retorna o tamanho da lista
     * @return número de elementos na lista
     */
    public int size() {
        return size;
    }

    /**
     * Verifica se a lista está vazia
     * @return true se a lista está vazia, false caso contrário
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Remove todos os elementos da lista
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    /**
     * Converte a lista para um array
     * @return array contendo todos os elementos da lista
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Garante que há capacidade suficiente no array interno
     */
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size >= array.length) {
            int newCapacity = array.length * 2;
            if (newCapacity < DEFAULT_CAPACITY) {
                newCapacity = DEFAULT_CAPACITY;
            }
            
            T[] newArray = (T[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
    }

    /**
     * Implementa um iterador para usar em for-each loops
     * @return Iterator para a lista
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                return array[currentIndex++];
            }
        };
    }

    /**
     * Ordena a lista usando o algoritmo bubble sort
     * Requer que T implemente Comparable
     */
    @SuppressWarnings("unchecked")
    public void sort() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                try {
                    Comparable<T> current = (Comparable<T>) array[j];
                    if (current.compareTo(array[j + 1]) > 0) {
                        T temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                } catch (ClassCastException e) {
                    throw new IllegalArgumentException("Elementos devem implementar Comparable para serem ordenados");
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}