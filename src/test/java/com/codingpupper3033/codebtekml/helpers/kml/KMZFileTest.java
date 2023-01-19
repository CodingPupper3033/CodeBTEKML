package com.codingpupper3033.codebtekml.helpers.kml;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * @author Joshua Miller
 */
public class KMZFileTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @Test
    public void test() throws IOException {
        KMZFile file = new KMZFile("C:\\Users\\codin\\Documents\\Temp\\Center.kmz");

        System.out.println(file.getMainKMLZipEntry());
        assertEquals("hello", outContent.toString());
    }
}