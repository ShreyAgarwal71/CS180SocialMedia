package com.lewall.services;

import com.lewall.db.Database;

public interface Service {
    static Database db = new Database();
}
