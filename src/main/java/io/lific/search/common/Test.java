package io.lific.search.common;

import io.lific.search.common.utils.file.FileUtils;

import java.io.FileNotFoundException;
import java.io.Reader;

public class Test {

    public static void main(String[] args) throws FileNotFoundException {
        String dict = "user.dic";
        Reader reader = FileUtils.getFileReader(dict);
        System.out.println(reader);
    }

}
