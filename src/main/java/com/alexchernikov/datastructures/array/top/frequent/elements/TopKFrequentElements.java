package com.alexchernikov.datastructures.array.top.frequent.elements;

import java.util.*;

public class TopKFrequentElements {
    public static void main(String[] args) {
        //int[] nums = {1,1,1,2,2,3};
        int[] nums = {1,1,1,2,2,2,3,3,3,4,4};

        //System.out.println(Arrays.toString(topKFrequent(nums, 2)));
        System.out.println(Arrays.toString(topKFrequentBuckets(nums, 2)));
    }

    public static int [] topKFrequent(int [] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();

        for(int n : nums) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>(
                (a,b) -> freq.get(a) - freq.get(b)
        );

        for (int num : freq.keySet()) {
            heap.add(num);

            if(heap.size() > k) {
                heap.poll();
            }
        }

        int[] result = new int[k];

        for (int i = k-1; i >= 0; i--) {
            result[i] = heap.poll();
        }

        return result;
    }

    public static int [] topKFrequentBuckets(int [] nums, int k) {

        Map<Integer, Integer> freq = new HashMap<>();

        for (int n : nums) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }

        List<Integer>[] buckets = new List[nums.length + 1];

        for (int key : freq.keySet()) {
            int f = freq.get(key);

            if (buckets[f] == null) {
                buckets[f] = new ArrayList<>();
            }

            buckets[f].add(key);
        }

        int[] result = new int[k];
        int index = 0;

        for (int i = buckets.length - 1; i >= 0 && index < k; i--) {
            if (buckets[i] != null) {
                for (int num : buckets[i]) {
                    result[index++] = num;
                    if (index == k) break;
                }
            }
        }

        return result;
    }
}
