package com.lewall.helpers;

import java.util.List;

import com.lewall.db.models.Post;

/**
 * A class to implement Quicksort for posts
 *
 * @author Shrey Agarwal
 * @version 17 November 2024
 */
public class PostSort {
	/**
	 * The quicksort method
	 *
	 * @param posts
	 * @param low
	 * @param high
	 */
    public static void quickSort(List<Post> posts, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(posts, low, high);
            quickSort(posts, low, pivotIndex - 1);
            quickSort(posts, pivotIndex + 1, high);
        }
    }

    private static int partition(List<Post> posts, int low, int high) {
        Post pivot = posts.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (posts.get(j).compareTo(pivot) > 0) {
                i++;
                swap(posts, i, j);
            }
        }

        swap(posts, i + 1, high);
        return i + 1;
    }

    private static void swap(List<Post> posts, int i, int j) {
        Post temp = posts.get(i);
        posts.set(i, posts.get(j));
        posts.set(j, temp);
    }
}
