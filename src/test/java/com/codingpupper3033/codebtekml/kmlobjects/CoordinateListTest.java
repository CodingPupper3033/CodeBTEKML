package com.codingpupper3033.codebtekml.kmlobjects;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joshua Miller
 */
public class CoordinateListTest {
    @Test
    public void containsWithList() throws IOException {

        List<Coordinate> coordinates = new ArrayList<>();


        coordinates.add(new Coordinate(50,50,0));
        coordinates.add(new Coordinate(5,20,0));

        assert coordinates.contains(new Coordinate(5,20,69));
    }

    @Test
    public void containsWithHashMap() {

        Map<Coordinate, Integer> coordinates = new HashMap<>();


        coordinates.put(new Coordinate(50,50,0), 5);
        coordinates.put(new Coordinate(5,20,0), 5);

        assert coordinates.get(new Coordinate(5,20,69)) == 5;
    }
}