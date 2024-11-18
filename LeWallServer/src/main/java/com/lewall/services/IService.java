package com.lewall.services;

import com.lewall.db.Database;

/**
 * An interface for all Service classes
 * 
 * @author Shrey Agarwal
 * @version 14 November 2024
 */
public interface IService {
    /**
     * The database instance
     */
    static Database db = new Database();
}
