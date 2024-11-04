package com.cs180.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

/**
 * A Collection interface to help manage our program's database collections.
 * This interface provides methods to read and write data to and from the disk,
 * add, update, and remove elements from the collection, and find elements in
 * the collection that match a given predicate.
 * 
 * @author Ates Isfendiyaroglu and Mahit Mehta, L17
 *
 * @version November 2nd, 2024
 */
public interface Collection<T extends Serializable> {
	int ASYNC_WRITE_FREQ = 5; // seconds

	/**
	 * Reads Object data from the specified file. Can be used from
	 * all Collection classes thanks to polymorphism
	 *
	 * @param fileName
	 * @return data
	 */
	@SuppressWarnings("unchecked")
	default T[] readData(String fileName) {
		File f = new File(fileName);

		T[] data = null;
		try (ObjectInputStream os = new ObjectInputStream(new FileInputStream(f))) {
			// TODO: Perform this unchecked cast safely
			data = (T[]) os.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("Creating new file: " + fileName);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * Writes Object data to the specified file. Can be used from
	 * all Collection classes thanks to polymorphism.
	 * Returns true if successfull, false if unsuccessfull.
	 *
	 * @param fileName,
	 *                  data
	 * @return exitCode -> true = success, false = failure
	 */
	default boolean persistToDisk(String fileName, T[] data) {
		boolean exitCode = true;
		File f = new File(fileName);
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
			os.writeObject(data);
		} catch (Exception e) {
			e.printStackTrace();
			exitCode = false;
		}

		return exitCode;
	}

	/**
	 * Adds the given Object to the Collection's ArrayList.
	 * Returns false if the Object's type doesn't match the expected type.
	 *
	 * @param obj
	 * @return exitCode
	 */
	abstract boolean addElement(T obj);

	/**
	 * Updates the targeted Object to match the newly given Object.
	 * Returns false if the Object's type doesn't match the expected type or
	 * if the targeted Object doesn't exist in the Collection's ArrayList.
	 *
	 * @param target,
	 *                newObj
	 * @return exitCode
	 */
	abstract boolean updateElement(T target, T newObj);

	/**
	 * Deletes the specified Object from the Collection.
	 * Returns false if the Object's type doesn't match the expected type or
	 * if the targeted Object doesn't exist in the Collection's ArrayList.
	 *
	 * @param obj
	 * @return exitCode
	 */
	abstract boolean removeElement(T obj);

	/**
	 * Saves the Collection's data to the file.
	 */
	abstract void save();

	/**
	 * Returns the number of elements in the Collection.
	 *
	 * @return count
	 */
	abstract int count();

	/**
	 * Returns all the elements in the Collection that match the given predicate.
	 *
	 * @param predicate
	 * @return data
	 */
	abstract List<T> findAll(Predicate<T> predicate);

	/**
	 * Returns all the elements in the Collection that match the given predicate up
	 * to the given limit.
	 *
	 * @param predicate
	 * @return data
	 */
	abstract List<T> findAll(Predicate<T> predicate, int limit);

	/**
	 * Returns the first element that matches the given predicate.
	 *
	 * @param predicate
	 * @return data
	 */
	abstract T findOne(Predicate<T> predicate);
}
