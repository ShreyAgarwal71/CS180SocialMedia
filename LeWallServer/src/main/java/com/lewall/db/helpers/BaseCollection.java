package com.lewall.db.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Predicate;

import com.lewall.db.models.Model;

/**
 * BaseCollection
 * 
 * This class is the base class for all the collections in the database. It
 * provides the basic functionality for adding, updating, removing, and finding
 * elements in the collection.
 * 
 * @param <T>
 * @author Mahit Mehta
 * @version 2024-11-03
 */
public abstract class BaseCollection<T extends Model> implements Collection<T> {
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
     * Set the write flag to true
     */
    public void setWriteFlag(boolean value) {
        this.needWrite = value;
    }

    /**
     * Save the records to disk
     */
    public void save() {
        if (!this.needWrite)
            return;
        this.writeRecords();
    }

    /**
     * Wrapper method for the Collection interface's persistToDisk method.
     * 
     * @return true if the records were written to disk, false otherwise
     */
    public abstract boolean writeRecords();

    private int indexOf(UUID id) {
        int index = -1;
        for (int i = 0; i < this.records.size(); i++) {
            if (this.records.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }

        return index;
    }

    @Override
    public boolean addElement(T record, Predicate<T> dedupPredicate) {
        boolean exitCode = false;

        if (record == null)
            return exitCode;

        this.records.lockWrite();

        for (T t : this.records.getList()) {
            if (dedupPredicate.test(t)) {
                this.records.unlockWrite();
                return exitCode;
            }
        }

        this.records.add(record);
        this.records.unlockWrite();

        this.needWrite = true;
        exitCode = true;

        return exitCode;
    }

    /**
     * Add a record to the collection if it does not already exist
     * 
     * @return true if the record was added, false otherwise
     */
    @Override
    public boolean addElement(T record) {
        return this.addElement(record, (t) -> t.getId().equals(record.getId()));
    }

    @Override
    public boolean updateElement(UUID id, T record) {
        boolean exitCode = false;

        if (record == null)
            return exitCode;

        // If the id of the updated record doesn't match the id of the record to update
        // return false
        if (!id.equals(record.getId()))
            return exitCode;

        this.records.lockWrite();

        int index = this.indexOf(id);
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
    public boolean removeElement(UUID id) {
        boolean exitCode = false;

        this.records.lockWrite();

        int index = this.indexOf(id);
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
    public void clear() {
        this.records.lockWrite();
        this.records.clear();
        this.records.unlockWrite();

        this.needWrite = true;
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

    @Override
    public T findById(UUID id) {
        this.records.lockRead();

        for (T t : this.records.getList()) {
            if (t.getId().equals(id)) {
                this.records.unlockRead();
                return t;
            }
        }

        this.records.unlockRead();
        return null;
    }
}
