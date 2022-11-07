import java.util.Random;
import java.util.function.Consumer;

public class Sorting {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        double timeForBubbleSort = timeForSorting(5, Sorting::sortBubble);
        System.out.println("Среднее время сортировки пузырьком: " + timeForBubbleSort + " мс.");
        double timeForSelectionSort = timeForSorting(5, Sorting::sortSelection);
        System.out.println("Среднее время сортировки выбором: " + timeForSelectionSort + " мс.");
        double timeForInsertionSort = timeForSorting(5, Sorting::sortInsertion);
        System.out.println("Среднее время сортировки вставками: " + timeForInsertionSort + " мс.");
    }

    private static double timeForSorting(int iteration, Consumer<int[]> arrayConsumer) {
        double sum = 0;
        for (int i = 0; i < iteration; i++) {
            int[] array = generateArray(100000);
            long start = System.currentTimeMillis();
            arrayConsumer.accept(array);
            long end = System.currentTimeMillis() - start;
            sum += end;
        }
        return sum / iteration;
    }

    private static void sortBubble(int[] arr) { //21419.2 мс
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swapElements(arr, j, j + 1);
                }
            }
        }
    }

    private static void sortSelection(int[] arr) { // 10737.2 мс
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] < arr[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(arr, i, minElementIndex);
        }
    }

    private static void sortInsertion(int[] arr) { // 976.6 мс
        for (int i = 0; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

    private static int[] generateArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = RANDOM.nextInt(100);
        }
        return array;
    }

    private static void swapElements(int[] arr, int indexOne, int indexTwo) {
        int tmp = arr[indexOne];
        arr[indexOne] = arr[indexTwo];
        arr[indexTwo] = tmp;
    }

}