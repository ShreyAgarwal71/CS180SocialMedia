package com.lewall.dtos;

import java.util.ArrayList;
import java.util.List;

public class ClassesDTO {
    private List<String> classes;

    public ClassesDTO() {
        classes = new ArrayList<>();
        classes.add("CS 180");
        classes.add("CS 240");
        classes.add("CS 250");
        classes.add("CS 251");
    }
}
