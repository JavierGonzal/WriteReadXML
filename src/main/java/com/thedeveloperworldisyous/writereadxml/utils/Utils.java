package com.thedeveloperworldisyous.writereadxml.utils;

import android.os.Environment;

/**
 * Created by javiergonzalezcabezas on 8/10/15.
 */
public class Utils {

    public static boolean isExternalStorageReadOnly() {

        String extStorageState = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {

            return true;

        }

        return false;

    }

    public static boolean isExternalStorageAvailable() {

        String extStorageState = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {

            return true;

        }

        return false;

    }
}
