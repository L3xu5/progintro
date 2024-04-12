class IntList {
    private int[] array;
    private int lastFilled = 0;
    void add(int index, int value) {
        while (index > array.length - 1) {
            int[] tempArray = new int[array.length * 2 + 2];
            System.arraycopy(array, 0, tempArray, 0, array.length);
            array = tempArray;
        }
        array[index] = value;
        lastFilled = Math.max(lastFilled, index);
    }

    void add(int value) {
        lastFilled++;
        while (lastFilled > array.length - 1) {
            int[] tempArray = new int[array.length * 2 + 2];
            System.arraycopy(array, 0, tempArray, 0, array.length);
            array = tempArray;
        }
        array[lastFilled] = value;
    }

    int get(int index) {
        return array[index];
    }

    int length() {
        return lastFilled + 1;
    }

    IntList() {
        this.array = new int[1];
    }
} 