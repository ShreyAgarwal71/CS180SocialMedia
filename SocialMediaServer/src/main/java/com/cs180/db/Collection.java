package com.cs180.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cs180.AppServer;
import com.cs180.db.models.Model;
import com.cs180.resolvers.ResolverTools;

/**
 * 
 * A Collection interface to help manage our program's database collections.
 * This interface provides methods to read and write data to and from the disk,
 * add, update, and remove elements from the collection, and find elements in
 * the collection that match a given predicate.
 * 
 * @param <T>
 * @author Mahit Mehta and Ates Isfendiyaroglu
 * @version 2024-11-03
 */
public interface Collection<T extends Model> {
	static Logger logger = LogManager.getLogger(Collection.class);

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
			data = (T[]) os.readObject();
		} catch (FileNotFoundException e) {
			logger.debug("Will create new file: " + fileName);
			return data;
		} catch (Exception e) {
			logger.error("Failed to read from db.", e);
		}

		return data;
	}

	static Path getOSDataBasePath() {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			return Paths.get(System.getenv("APPDATA"), AppServer.ID);
		} else if (os.contains("mac")) {
			return Paths.get(System.getProperty("user.home"), "/Library/Application Support", AppServer.ID);
		} else if (os.contains("nix") || os.contains("nux")) {
			return Paths.get(System.getProperty("user.home"), String.format(".%s", AppServer.ID.toLowerCase()));
		} else {
			return Paths.get(System.getProperty("user.home"), AppServer.ID);
		}
	}

	static String getCollectionAbsolutePath(String fileName) {
		return getOSDataBasePath().resolve(fileName).toString();
	}

	/**
	 * Writes Object data to the specified file. Can be used from
	 * all Collection classes thanks to polymorphism.
	 * Returns true if successfull, false if unsuccessfull.
	 *
	 * @param fileName,
	 *            data
	 * @return exitCode -> true = success, false = failure
	 */
	default boolean persistToDisk(String fileName, T[] data) {
		boolean exitCode = true;

		File basePath = new File(getOSDataBasePath().toString());
		File path = null;

		if (basePath.exists() == false) {
			boolean success = basePath.mkdirs();
			if (success == false) {
				logger.error("Failed to persist Database Collection.");
				exitCode = false;

				return exitCode;
			}

			path = new File(fileName);
		} else {
			path = new File(fileName);
		}

		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path))) {
			os.writeObject(data);
		} catch (Exception e) {
			logger.error("Failed to write to db.", e);
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
	 * Updates the Object with the given id in the Collection's ArrayList.
	 *
	 * @param target,
	 *            newObj
	 * @return exitCode
	 */
	abstract boolean updateElement(UUID id, T newObj);

	/**
	 * Removes the Object with the given id from the Collection's ArrayList.
	 *
	 * @param id
	 * @return exitCode
	 */
	abstract boolean removeElement(UUID id);

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
	 * Clears the Collection's ArrayList.
	 */
	abstract void clear();

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

	/**
	 * Returns the index of the element with the given id.
	 *
	 * @param id
	 * @return index
	 */
	abstract T findById(UUID id);
}
