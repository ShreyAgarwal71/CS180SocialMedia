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
	// It will be crazy if these actualy work
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

	// true -> success, false -> failure
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
}
