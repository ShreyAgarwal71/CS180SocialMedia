package com.cs180.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A thread-safe ArrayList that uses a ReadWriteLock to manage access to the
 * list. This class is used to store data in the database. It provides methods
 * to add, remove, and get elements from the list. It also provides methods to
 * lock and unlock the read and write locks. This class is used by the database
 * to store user, post, and comment data. This class is a generic class. The
 * type of the elements in the list is specified by the type parameter T.
 * 
 * @param <T> The type of the elements in the list
 * 
 * @version November 2nd, 2024
 * 
 */
public class RwLockArrayList<T extends Serializable> {
    private final List<T> list = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    /**
     * Constructor for RwLockArrayList
     */
    public RwLockArrayList() {
    }

    /**
     * Constructs a new RwLockArrayList with the specified list.
     * 
     * @param arr
     */
    public RwLockArrayList(List<T> arr) {
        writeLock.lock();
        try {
            list.addAll(arr);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Locks the read lock.
     *
     */
    public void lockRead() {
        readLock.lock();
    }

    /**
     * Unlocks the read lock.
     *
     */
    public void unlockRead() {
        readLock.unlock();
    }

    /**
     * Locks the write lock.
     * 
     */
    public void lockWrite() {
        writeLock.lock();
    }

    /**
     * Unlocks the write lock.
     * 
     */
    public void unlockWrite() {
        writeLock.unlock();
    }

    /**
     * Returns the size of the list
     * 
     * @return size
     */
    public int size() {
        return list.size();
    }

    /**
     * Converts the list to an array.
     * 
     * @param arr
     *            the array
     * 
     */
    public void toArray(T[] arr) {
        list.toArray(arr);
    }

    /**
     * Sets the element at the specified index.
     * 
     * @param index
     *            the index
     * @param o
     *            the element
     * 
     */
    public void set(int index, T o) {
        list.set(index, o);
    }

    /**
     * Removes the element at the specified index.
     * 
     * @param index
     *            the index
     * 
     */
    public void remove(int index) {
        list.remove(index);
    }

    /**
     * Adds an item to the list.
     * 
     * @param item
     * 
     */
    public void add(T item) {
        list.add(item);
    }

    /**
     * Gets the list.
     * 
     * @return the list
     * 
     */
    public List<T> getList() {
        return list;
    }

    /**
     * Gets the element at the specified index.
     * 
     * @param index
     *
     * @return the element at the specified index
     * 
     */
    public T get(int index) {
        return list.get(index);
    }

    /**
     * Clears the list.
     * 
     */
    public void clear() {
        list.clear();
    }
}