package com.cs180.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RwLockArrayList<T extends Serializable> {
    private final List<T> list = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public RwLockArrayList() {
    }

    public void lockRead() {
        readLock.lock();
    }

    public void unlockRead() {
        readLock.unlock();
    }

    public void lockWrite() {
        writeLock.lock();
    }

    public void unlockWrite() {
        writeLock.unlock();
    }

    public RwLockArrayList(List<T> arr) {
        writeLock.lock();
        try {
            list.addAll(arr);
        } finally {
            writeLock.unlock();
        }
    }

    public int size() {
        return list.size();
    }

    public void toArray(T[] arr) {
        list.toArray(arr);
    }

    public void set(int index, T o) {
        list.set(index, o);
    }

    public void remove(T o) {
        list.remove(o);
    }

    public void remove(int index) {
        list.remove(index);
    }

    public void add(T item) {
        list.add(item);
    }

    public T get(int index) {
        return list.get(index);
    }
}