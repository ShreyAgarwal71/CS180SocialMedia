package com.cs180.services;

import com.cs180.db.Database;

public interface Service {
    static Database db = new Database();
}
