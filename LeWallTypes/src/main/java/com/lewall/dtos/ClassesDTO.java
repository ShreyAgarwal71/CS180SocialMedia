package com.lewall.dtos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassesDTO {
    private List<String> classes;

    public ClassesDTO() {
        this.classes = new ArrayList<>(Arrays.asList(
                "Mathematics 101",
                "Computer Science 101",
                "Biology 201",
                "Chemistry 102",
                "Physics 301",
                "History 101",
                "English Literature",
                "Calculus",
                "Data Structures",
                "Machine Learning",
                "Organic Chemistry",
                "World History",
                "Linear Algebra",
                "Artificial Intelligence",
                "Discrete Mathematics"));
    }

    public List<String> getClasses() {
        return classes;
    }
}