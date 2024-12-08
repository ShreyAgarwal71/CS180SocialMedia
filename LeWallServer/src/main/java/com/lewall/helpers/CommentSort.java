package com.lewall.helpers;

import java.util.List;

import com.lewall.db.models.Comment;

/**
 * A class to implement Quicksort for Comments
 *
 * @author Shrey Agarwal
 * @version 17 November 2024
 */
public class CommentSort {
    /**
     * The quicksort method
     *
     * @param Comments
     * @param low
     * @param high
     */
    public static void quickSort(List<Comment> comments, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(comments, low, high);
            quickSort(comments, low, pivotIndex - 1);
            quickSort(comments, pivotIndex + 1, high);
        }
    }

    private static int partition(List<Comment> comments, int low, int high) {
        Comment pivot = comments.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comments.get(j).compareTo(pivot) > 0) {
                i++;
                swap(comments, i, j);
            }
        }

        swap(comments, i + 1, high);
        return i + 1;
    }

    private static void swap(List<Comment> comments, int i, int j) {
        Comment temp = comments.get(i);
        comments.set(i, comments.get(j));
        comments.set(j, temp);
    }
}
