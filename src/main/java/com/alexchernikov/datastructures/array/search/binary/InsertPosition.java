package com.alexchernikov.datastructures.array.search.binary;

public class InsertPosition {

    public static void main(String[] args) {
        int[] nums = {1,3,5,6};
        int target = 2;
        System.out.println("Index: " + searchInsertPosition(nums, target));
        nums = new int[] {11, 30, 45, 67, 89};
        target = 56;
        System.out.println("Index: " + searchInsertPosition(nums, target));
        target = 67;
        System.out.println("Index: " + searchInsertPosition(nums, target));
    }

    public static int searchInsertPosition(int[] nums, int target) {
        int lowerBound = 0;
        int upperBound = nums.length;
        while (lowerBound < upperBound) {
            int mid = (lowerBound + upperBound) / 2;
            if(nums[mid] >= target) {
                upperBound = mid; // need to decrease the upperBound
            }
            else {
                lowerBound = mid + 1; // need to increase the lowerBound
            }
        }

        return lowerBound;
    }
}
