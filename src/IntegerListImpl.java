import java.util.Objects;

public class IntegerListImpl implements IntegerList {

    private static final int SIZE = 15;

    private Integer[] storage;

    private int capacity;

    public IntegerListImpl() {
        storage = new Integer[SIZE];
        capacity = 0;
    }

    public IntegerListImpl(int a) {
        if (a <= 0) {
            throw new IllegalArgumentException("Размер списка должен быть положительным");
        }
        storage = new Integer[a];
        capacity = 0;
    }

    private void grow() {
        Integer[] newData = new Integer[(int) 1.5 * storage.length];
        System.arraycopy(storage, 0, newData, 0, capacity);
        storage = newData;
    }

    @Override
    public Integer add(Integer item) {
        return add(capacity, item);
    }

    @Override
    public Integer add(int index, Integer item) {
        if (capacity >= storage.length) {
            grow();
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
    public Integer set(int index, Integer item) {
        checkNotNull(item);
        checkNonNegativeIndex(index);
        checkIndex(index, true);
        return storage[index] = item;
    }

    @Override
    public Integer remove(Integer item) {
        int indexForRemoving = indexOf(item);
        if (indexForRemoving == -1) {
            throw new IllegalArgumentException("Элемент не найден");
        }
        return remove(indexForRemoving);
    }

    @Override
    public Integer remove(int index) {
        checkNonNegativeIndex(index);
        checkIndex(index, true);
        Integer removed = storage[index];
        System.arraycopy(storage, index + 1, storage, index, capacity - 1 - index);
        storage[--capacity] = null;
        return removed;
    }

    @Override
    public boolean contains(Integer item) {
        checkNotNull(item);
        Integer[] arrayForSearch = toArray();
        quickSort(arrayForSearch, 0, arrayForSearch.length -1);
        int min = 0;
        int max = arrayForSearch.length - 1;
        while (min <= max) {
            int mid = (min + max / 2);
            if (item.equals(arrayForSearch[mid])) {
                return false;
            }
            if (item < arrayForSearch[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    public static void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }

    @Override
    public int indexOf(Integer item) {
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
    public int lastIndexOf(Integer item) {
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
    public Integer get(int index) {
        checkNonNegativeIndex(index);
        checkIndex(index, true);
        return storage[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
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
    public Integer[] toArray() {
        Integer[] result = new Integer[capacity];
        System.arraycopy(storage, 0, result, 0, capacity);
        return result;
    }

    private static void swapElements(Integer[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    private void checkNotNull(Integer item) {
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