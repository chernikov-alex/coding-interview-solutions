package com.alexchernikov.datastructures.graph;

import java.util.*;

// Example according https://bytebytego.com/courses/coding-patterns/graphs/prerequisites
public class TestPrerequisites {
    public static void main(String[] args) {
        testOne();
        testTwo();
        testThree();
    }

    private static void testOne() {
        int n = 3; // Number of courses

        ArrayList<ArrayList<Integer>> prerequisites = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(0, 1)),
                new ArrayList<>(Arrays.asList(1, 2)),
                new ArrayList<>(Arrays.asList(2, 1))
        ));

        boolean result = prerequisites(n, prerequisites);

        System.out.println("Can all courses be enrolled (example 1)? " + result); // Expected: False
    }

    private static void testTwo() {
        int n = 4;
        ArrayList<ArrayList<Integer>> prerequisites2 = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 0)),
                new ArrayList<>(Arrays.asList(2, 0)),
                new ArrayList<>(Arrays.asList(3, 1)),
                new ArrayList<>(Arrays.asList(3, 2))
        ));
        boolean result2 = prerequisites(n, prerequisites2);
        System.out.println("Can all courses be enrolled (example 2)? " + result2); // Expected: True
    }

    private static void testThree() {
        int n = 6;
        ArrayList<ArrayList<Integer>> prerequisites3 = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(0, 1)),
                new ArrayList<>(Arrays.asList(0, 2)),
                new ArrayList<>(Arrays.asList(3, 2)),
                new ArrayList<>(Arrays.asList(1, 4)),
                new ArrayList<>(Arrays.asList(2, 4)),
                new ArrayList<>(Arrays.asList(4, 5))
        ));
        boolean result3 = prerequisites(n, prerequisites3);
        System.out.println("Can all courses be enrolled (example 3)? " + result3); // Expected: True
    }

    public static boolean prerequisites(int n, ArrayList<ArrayList<Integer>> prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] inDegrees = new int[n];
        // Represent the graph as an adjacency list and record the in-degree of each course.
        for (ArrayList<Integer> pair : prerequisites) {
            int prerequisite = pair.get(0);
            int course = pair.get(1);
            graph.computeIfAbsent(prerequisite, k -> new ArrayList<>()).add(course);
            inDegrees[course]++;
        }
        Deque<Integer> queue = new LinkedList<>();
        // Add all courses with an in-degree of 0 to the queue.
        for (int i = 0; i < n; i++) {
            if (inDegrees[i] == 0) {
                queue.add(i);
            }
        }
        int enrolledCourses = 0;
        List<Integer> executionOrder = new ArrayList<>();
        // Perform topological sort.
        while (!queue.isEmpty()) {
            int node = queue.poll();
            executionOrder.add(node);
            enrolledCourses++;

            if (graph.containsKey(node)) {
                for (int neighbor : graph.get(node)) {
                    inDegrees[neighbor]--;
                    // If the in-degree of a neighboring course becomes 0, add it to the queue.
                    if (inDegrees[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }
        if (enrolledCourses == n) {
            System.out.println("Order of courses: " + executionOrder);
        }
        // Return true if we've successfully enrolled in all courses.
        return enrolledCourses == n;
    }
}
