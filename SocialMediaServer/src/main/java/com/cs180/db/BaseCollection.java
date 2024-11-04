package com.cs180.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Predicate;

/**
 * BaseCollection
 * 
 * This class is the base class for all the collections in the database. It
 * provides the basic functionality for adding, updating, removing, and finding
 * elements in the collection.
 * 
 * @param <T>
 * @version November 2nd, 2024
 * 
 */
abstract class BaseCollection<T extends Serializable> implements Collection<T> {
    protected RwLockArrayList<T> records;

    private final ScheduledThreadPoolExecutor scheduler;
    private boolean needWrite = false;

    /**
     * Constructor for BaseCollection
     * 
     * @param fileName
     * @param scheduler
     */
    public BaseCollection(String fileName, ScheduledThreadPoolExecutor scheduler) {
        T[] arr = this.readData(fileName);
        if (arr == null) {
            this.records = new RwLockArrayList<>();
        } else {
            this.records = new RwLockArrayList<>(Arrays.asList(arr));
        }

        this.scheduler = scheduler;
        this.scheduler.scheduleAtFixedRate(() -> {
            if (!this.needWrite)
                return;

            this.writeRecords();
            this.needWrite = false;
        }, 0, Collection.ASYNC_WRITE_FREQ, java.util.concurrent.TimeUnit.SECONDS);
    }

    /**
     * Saves the collection to disk.
     */
    public void save() {
        if (!this.needWrite)
            return;
        this.writeRecords();
    }

    /**
     * Wrapper method for the Collection interface's persistToDisk method.
     * 
     * @return exitCode
     */
    abstract boolean writeRecords();

    /**
     * @implNote: Not Thread Safe, Needs Locking (Meant for internal use)
     *
     * @param T
     * @return index
     *         Return the index of the record if found, else -1
     */
    abstract int indexOf(T record);

    @Override
    public boolean addElement(T record) {
        boolean exitCode = false;

        this.records.lockWrite();
        this.records.add(record);
        this.records.unlockWrite();

        this.needWrite = true;
        exitCode = true;

        return exitCode;
    }

    @Override
    public boolean updateElement(T target, T record) {
        boolean exitCode = false;

        this.records.lockWrite();

        int index = this.indexOf(target);
        if (index == -1) {
            this.records.unlockWrite();
            return exitCode;
        }

        this.records.set(index, record);
        this.records.unlockWrite();

        this.needWrite = true;
        exitCode = true;

        return exitCode;
    }

    @Override
    public boolean removeElement(T record) {
        boolean exitCode = false;

        this.records.lockWrite();

        int index = this.indexOf(record);
        if (index == -1) {
            this.records.unlockWrite();
            return exitCode;
        }

        this.records.remove(index);
        this.records.unlockWrite();

        this.needWrite = true;
        exitCode = true;

        return exitCode;
    }

    @Override
    public int count() {
        this.records.lockRead();
        int size = this.records.size();
        this.records.unlockRead();

        return size;
    }

    @Override
    public T findOne(Predicate<T> predicate) {
        this.records.lockRead();

        for (T t : this.records.getList()) {
            if (predicate.test(t)) {
                this.records.unlockRead();
                return t;
            }
        }

        this.records.unlockRead();
        return null;
    }

    @Override
    public List<T> findAll(Predicate<T> predicate) {
        this.records.lockRead();

        List<T> result = new ArrayList<>();
        for (T t : this.records.getList()) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }

        this.records.unlockRead();
        return result;
    }

    @Override
    public List<T> findAll(Predicate<T> predicate, int limit) {
        this.records.lockRead();

        List<T> result = new ArrayList<>();
        for (T t : this.records.getList()) {
            if (predicate.test(t) && result.size() < limit) {
                result.add(t);
            }
        }

        this.records.unlockRead();
        return result;
    }
}
