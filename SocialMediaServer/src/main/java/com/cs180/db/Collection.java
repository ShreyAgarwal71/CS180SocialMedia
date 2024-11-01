package com.cs180.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A Collection interface to help manage our program's database
 * 
 * @author Ates Isfendiyaroglu, L17
 *
 * @version 30 October 2024
 */
public interface Collection {
	/**
	 * Reads Object data from the specified file. Can be used from 
	 * all Collection classes thanks to polymorphism
	 *
	 * @param fileName
	 * @return data
	 */
	default Object[] readData(String fileName) {
		File f = new File(fileName);

		Object[] data = null;
		try (ObjectInputStream os = new ObjectInputStream(new FileInputStream(f))) {
			Object o = os.readObject();
			data = (Object[]) o;
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
	 * @param fileName, data
	 * @return exitCode -> true = success, false = failure
	 */
	default boolean writeData(String fileName, Object[] data) {
		boolean exitCode = true;
		File f = new File(fileName);

		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
			os.writeObject((Object) os);
		} catch (Exception e) {
			e.printStackTrace();
			exitCode = false;
		}

		return exitCode;
	} 

	/**
	 * Finds the index of the given Object in the Collection's ArrayList.
	 * Returns -1 if the Object's type doesn't match the expected type or
	 * if the targeted Object doesn't exist in the ArrayList.
	 *
	 * @param obj
	 * @return index
	 */
	abstract int indexOf(Object obj);

	/**
	 * Updates the targeted Object to match the newly given Object.
	 * Returns false if the Object's type doesn't match the expected type or
	 * if the targeted Object doesn't exist in the Collection's ArrayList.
	 *
	 * @param target, newObj
	 * @return exitCode
	 */
	abstract boolean updateElement(Object target, Object newObj);

	/**
	 * Updates the targeted Object to match the newly given Object.
	 * Returns false if the Object's type doesn't match the expected type or
	 * if the targeted Object doesn't exist in the Collection's ArrayList.
	 *
	 * @param index, newObj
	 * @return exitCode
	 */
	abstract boolean updateElement(int index, Object newObj);

	/**
	 * Deletes the specified Object from the Collection.
	 * Returns false if the Object's type doesn't match the expected type or
	 * if the targeted Object doesn't exist in the Collection's ArrayList.
	 *
	 * @param obj
	 * @return exitCode
	 */
	abstract boolean removeElement(Object obj);

	/**
	 * Deletes the specified Object from the Collection.
	 * Returns false if the index if out of bounds or
	 * if the targeted Object doesn't exist in the Collection's ArrayList.
	 *
	 * @param index
	 * @return exitCode
	 */
	abstract boolean removeElement(int index);
}
