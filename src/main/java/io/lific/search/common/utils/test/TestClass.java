package io.lific.search.common.utils.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


public abstract class TestClass {

    protected static final Path resourceDirectory = Paths.get("src", "test", "resources");

    protected static final Path targetDirectory = Paths.get("target");

    protected static File getResourceFile(String fileName) {
        return new File(resourceDirectory.toFile(), fileName);
    }

    protected static File getTargetFile(String fileName) {
        return new File(targetDirectory.toFile(), fileName);
    }

    protected static File getResourceDirectory() {
        return resourceDirectory.toFile();
    }

    protected static File getTargetDirectory() {
        return targetDirectory.toFile();
    }

    protected static URL toURL(File file) {
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
