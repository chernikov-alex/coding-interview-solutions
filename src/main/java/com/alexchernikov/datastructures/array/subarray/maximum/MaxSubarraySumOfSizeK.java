package com.alexchernikov.datastructures.array.subarray.maximum;

import java.util.Arrays;

public class MaxSubarraySumOfSizeK {
    public static void main(String[] args) {
        int[] arr = {2, 1, 5, 1, 3, 2};
        int k = 3;
        System.out.println(findMaxSubArraySum(arr, k));
        System.out.println(Arrays.toString(findMaxSubArray(arr, k)));
    }

    public static int findMaxSubArraySum(int[] arr, int k) {
        if (arr == null || arr.length < k || k <=0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int windowSum = 0;
        int maxSum;

        // Build the first window
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        maxSum = windowSum;

        // Slide the window
        for (int right = k; right < arr.length; right++) {
            windowSum += arr[right];    // add new element
            windowSum -= arr[right - k];// remove element leaving window
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    // If we need the actual subarray
    public static int[] findMaxSubArray(int[] arr, int k) {
        if (arr == null || arr.length < k || k <=0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int windowSum = 0;

        // Build the first window
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        int maxSum = windowSum;
        int maxStartIndex = 0;

        // Slide the window
        for (int right = k; right < arr.length; right++) {
            windowSum += arr[right];    // add new element
            windowSum -= arr[right - k];// remove element leaving window
            if (windowSum > maxSum) {
                maxSum = windowSum;
                maxStartIndex = right - k + 1;
            }
        }
        // Return the actual subarray
        return Arrays.copyOfRange(arr, maxStartIndex, maxStartIndex + k);
    }
}
