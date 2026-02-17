package com.alexchernikov.datastructures.array;

public class CountingSequences {
    public static void main(String[] args) {
        int[] arr = {2, 2, 3, 1, 6, 5, 5, 5, 5, 5};
        countSequences(arr);
    }

    private static void countSequences(int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println("Empty array");
            return;
        }
        int[] countingArray = new int[arr.length];
        int j = 0;
        countingArray[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                countingArray[j]++;
            } else {
                j++;
                countingArray[j] = 1;
            }
        }
        for (int k = 0; k <= j; k++) {
            System.out.println(countingArray[k]);
        }
    }
}
