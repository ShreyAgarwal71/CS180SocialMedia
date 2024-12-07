package com.lewall.db.collections;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.lewall.db.helpers.BaseCollection;
import com.lewall.db.models.User;

/**
 * A Collection class to manage users in the database of our program. This class
 * is responsible for reading and writing user data to and from the disk. It
 * also provides methods to find users by their username.
 * 
 * @author Ates Isfendiyaroglu and Mahit Mehta
 * @version 2024-11-03
 * 
 */
public class UserCollection extends BaseCollection<User> {
    private final String fileName;

    public UserCollection(String fileName,
            ScheduledThreadPoolExecutor scheduler) {
        super(fileName, scheduler);
        this.fileName = fileName;
    }

    /**
     * Wrapper method for the Collection interface's persistToDisk method.
     * 
     * @return exitCode
     */
    @Override
    public boolean writeRecords() {
        this.records.lockRead();
        User[] arr = new User[this.records.size()];
        this.records.toArray(arr);
        this.records.unlockRead();

        return this.persistToDisk(fileName, arr);
    }

    /**
     * Add a user to the collection while ensuring that the user is unique
     * based on the username, email, and id
     * 
     * @param user
     * @return
     */
    public boolean addUser(User user) {
        return this.addElement(user, (u) -> {
            return u.getUsername().equals(user.getUsername()) ||
                    u.getEmail().equals(user.getEmail()) ||
                    u.getId().equals(user.getId());
        });
    }

    /**
     * Find a user by username
     * 
     * @param username
     * @return user
     *         Return the user with the same username
     *         Returns null if none of the users have the same username
     */
    public User findByUsername(String username) {
        return this.findOne(user -> user.getUsername().equals(username));
    }

    /**
     * Search a user by username
     * 
     * @param username
     * @return List<User>
     *         Return all the users with the similar username
     *         Returns empty list if none of the users have similar usernames
     */
    public List<User> searchByUsername(String username) {
        return this.findAll(user -> {
            return user.getDisplayName().toLowerCase().contains(username.toLowerCase());
        });
    }
}
