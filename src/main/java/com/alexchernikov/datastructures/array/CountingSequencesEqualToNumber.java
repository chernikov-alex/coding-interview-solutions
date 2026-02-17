package com.alexchernikov.datastructures.array;

public class CountingSequencesEqualToNumber {

    public static void main(String[] args) {
        //int[] arr = {2, 2, 3, 1, 6, 5, 5, 5, 5, 5};
        int[] arr = {2, 2, 3, 1, 6, 5, 5, 5, 5, 5, 4, 4, 4, 4, 7};
        int[] result = countSequences(arr);
        for (int k = 0; k < result.length; k++) {
            if (result[k] != 0) {
                System.out.println(result[k]);
            }
        }
    }

    private static int[] countSequences(int[] arr) {

        int[] countingArray = new int[arr.length];
        int j = 0;
        int counter = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                counter++;
            } else {
                if(arr[i - 1] == counter) {
                    countingArray[j] = counter;
                    j++;
                }
                counter = 1;
            }
        }
        if(arr[arr.length - 1] == counter) {
            countingArray[j] = counter;
        }
        return countingArray;
    }
}


