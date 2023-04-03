package com.codingpupper3033.codebtekml.helpers.kml;

import com.codingpupper3033.codebtekml.kml.MalformedKMZException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * @author Joshua Miller
 */
public class KMZFile extends ZipFile {
    // KML File Type
    public static final String[] KML_FILE_EXTENSIONS = {"kml"}; // only KML, not also kmz

    /**
     * Opens a KMZ file for reading.
     *
     * <p>First, if there is a security manager, its <code>checkRead</code>
     * method is called with the <code>name</code> argument as its argument
     * to ensure the read is allowed.
     *
     * <p>The UTF-8 {@link Charset charset} is used to
     * decode the entry names and comments.
     *
     * @param name the name of the kmz file
     * @throws ZipException      if a ZIP format error has occurred
     * @throws IOException       if an I/O error has occurred
     * @throws SecurityException if a security manager exists and its
     *                           <code>checkRead</code> method doesn't allow read access to the file.
     * @see SecurityManager#checkRead(String)
     */
    public KMZFile(String name) throws IOException {
        super(name);
    }

    /**
     * Opens a new <code>KMZFile</code> to read from the specified
     * <code>File</code> object in the specified mode.  The mode argument
     * must be either <tt>OPEN_READ</tt> or <tt>OPEN_READ | OPEN_DELETE</tt>.
     *
     * <p>First, if there is a security manager, its <code>checkRead</code>
     * method is called with the <code>name</code> argument as its argument to
     * ensure the read is allowed.
     *
     * <p>The UTF-8 {@link Charset charset} is used to
     * decode the entry names and comments
     *
     * @param file the KMZ file to be opened for reading
     * @param mode the mode in which the file is to be opened
     * @throws ZipException             if a ZIP format error has occurred
     * @throws IOException              if an I/O error has occurred
     * @throws SecurityException        if a security manager exists and
     *                                  its <code>checkRead</code> method
     *                                  doesn't allow read access to the file,
     *                                  or its <code>checkDelete</code> method doesn't allow deleting
     *                                  the file when the <tt>OPEN_DELETE</tt> flag is set.
     * @throws IllegalArgumentException if the <tt>mode</tt> argument is invalid
     * @see SecurityManager#checkRead(String)
     * @since 1.3
     */
    public KMZFile(File file, int mode) throws IOException {
        super(file, mode);
    }

    /**
     * Opens a KMZ file for reading given the specified File object.
     *
     * <p>The UTF-8 {@link Charset charset} is used to
     * decode the entry names and comments.
     *
     * @param file the KMZ file to be opened for reading
     * @throws ZipException if a ZIP format error has occurred
     * @throws IOException  if an I/O error has occurred
     */
    public KMZFile(File file) throws ZipException, IOException {
        super(file);
    }

    /**
     * Opens a new <code>ZipFile</code> to read from the specified
     * <code>File</code> object in the specified mode.  The mode argument
     * must be either <tt>OPEN_READ</tt> or <tt>OPEN_READ | OPEN_DELETE</tt>.
     *
     * <p>First, if there is a security manager, its <code>checkRead</code>
     * method is called with the <code>name</code> argument as its argument to
     * ensure the read is allowed.
     *
     * @param file    the KMZ file to be opened for reading
     * @param mode    the mode in which the file is to be opened
     * @param charset the {@linkplain Charset charset} to
     *                be used to decode the KMZ entry name and comment that are not
     *                encoded by using UTF-8 encoding (indicated by entry's general
     *                purpose flag).
     * @throws ZipException             if a ZIP format error has occurred
     * @throws IOException              if an I/O error has occurred
     * @throws SecurityException        if a security manager exists and its <code>checkRead</code>
     *                                  method doesn't allow read access to the file,or its
     *                                  <code>checkDelete</code> method doesn't allow deleting the
     *                                  file when the <tt>OPEN_DELETE</tt> flag is set
     * @throws IllegalArgumentException if the <tt>mode</tt> argument is invalid
     * @see SecurityManager#checkRead(String)
     * @since 1.7
     */
    public KMZFile(File file, int mode, Charset charset) throws IOException {
        super(file, mode, charset);
    }

    /**
     * Opens a kmz file for reading.
     *
     * <p>First, if there is a security manager, its <code>checkRead</code>
     * method is called with the <code>name</code> argument as its argument
     * to ensure the read is allowed.
     *
     * @param name    the name of the kmz file
     * @param charset the {@linkplain Charset charset} to
     *                be used to decode the KMZ entry name and comment that are not
     *                encoded by using UTF-8 encoding (indicated by entry's general
     *                purpose flag).
     * @throws ZipException      if a ZIP format error has occurred
     * @throws IOException       if an I/O error has occurred
     * @throws SecurityException if a security manager exists and its <code>checkRead</code>
     *                           method doesn't allow read access to the file
     * @see SecurityManager#checkRead(String)
     * @since 1.7
     */
    public KMZFile(String name, Charset charset) throws IOException {
        super(name, charset);
    }

    /**
     * Opens a KMZ file for reading given the specified File object.
     *
     * @param file    the KMZ file to be opened for reading
     * @param charset The {@linkplain Charset charset} to be
     *                used to decode the KMZ entry name and comment (ignored if
     *                the <a href="package-summary.html#lang_encoding"> language
     *                encoding bit</a> of the KMZ entry's general purpose bit
     *                flag is set).
     * @throws ZipException if a ZIP format error has occurred
     * @throws IOException  if an I/O error has occurred
     * @since 1.7
     */
    public KMZFile(File file, Charset charset) throws IOException {
        super(file, charset);
    }

    public ZipEntry getMainKMLZipEntry() throws MalformedKMZException {
        // Loop though files to find main file
        Enumeration<? extends ZipEntry> entries = entries(); // Get top level entries in Zip File
        ZipEntry foundEntry = null;
        while (entries.hasMoreElements()) {
            ZipEntry currentEntry = entries.nextElement();
            if (currentEntry.isDirectory()) continue; // Will not recursively look

            for (int i = 0; i < KML_FILE_EXTENSIONS.length; i++) { // Does it end with one of the KML file extensions?
                // Not this extension
                if (!currentEntry.getName().endsWith("." + KML_FILE_EXTENSIONS[i])) continue;

                // Already found one
                if (foundEntry != null) throw new MalformedKMZException("Multiple main KML files found. Unable to choose."); // Can't have multiple main


                foundEntry = currentEntry;
            }
        }

        // Didn't find a main file
        if (foundEntry == null) throw new MalformedKMZException("No main KML file");

        return foundEntry;
    }

    public InputStream getMainKMLZInputStream() throws IOException {
        return getInputStream(getMainKMLZipEntry());
    }
}
