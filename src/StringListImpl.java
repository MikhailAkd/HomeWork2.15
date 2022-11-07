import java.util.Objects;

public class StringListImpl implements StringList {

    private static final int SIZE = 15;

    private final String[] storage;

    private int capacity;

    public StringListImpl() {
        storage = new String[SIZE];
        capacity = 0;
    }

    public StringListImpl(int a) {
        if (a <= 0) {
            throw new IllegalArgumentException("Размер списка должен быть положительным");
        }
        storage = new String[a];
        capacity = 0;
    }

    @Override
    public String add(String item) {
        return add(capacity, item);
    }

    @Override
    public String add(int index, String item) {
        if (capacity >= storage.length) {
            throw new IllegalArgumentException("Список полон");
        }
        checkNotNull(item);
        checkNonNegativeIndex(index);
        checkIndex(index, false);
        System.arraycopy(storage, index, storage, index + 1, capacity - index);
        storage[index] = item;
        capacity++;
        return item;
    }

    @Override
    public String set(int index, String item) {
        checkNotNull(item);
        checkNonNegativeIndex(index);
        checkIndex(index, true);
        return storage[index] = item;
    }

    @Override
    public String remove(String item) {
        int indexForRemoving = indexOf(item);
        if (indexForRemoving == -1) {
            throw new IllegalArgumentException("Элемент не найден");
        }
        return remove(indexForRemoving);
    }

    @Override
    public String remove(int index) {
        checkNonNegativeIndex(index);
        checkIndex(index, true);
        String removed = storage[index];
        System.arraycopy(storage, index + 1, storage, index, capacity - 1 - index);
        storage[--capacity] = null;
        return removed;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        checkNotNull(item);
        int index = -1;
        for (int i = 0; i < capacity; i++) {
            if (storage[i].equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(String item) {
        checkNotNull(item);
        int index = -1;
        for (int i = capacity; i >= 0; i--) {
            if (storage[i].equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public String get(int index) {
        checkNonNegativeIndex(index);
        checkIndex(index, true);
        return storage[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if (size() != otherList.size()) {
            return false;
        }
        for (int i = 0; i < capacity; i++) {
            if (!storage[i].equals(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            storage[i] = null;
        }
        capacity = 0;
    }

    @Override
    public String[] toArray() {
        String[] result = new String[capacity];
        System.arraycopy(storage, 0, result, 0, capacity);
        return result;
    }

    private void checkNotNull(String item) {
        if (Objects.isNull(item)) {
            throw new IllegalArgumentException("Нельзя хранить null в списке");
        }
    }

    private void checkNonNegativeIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс должен быть неотрицительный");
        }
    }

    private void checkIndex(int index, boolean includeEquality) {
        boolean expression = includeEquality ? index >= capacity : index > capacity;
        if (expression) {
            throw new IllegalArgumentException("Индекс: " + index + ", Размер: " + capacity);
        }
    }
}