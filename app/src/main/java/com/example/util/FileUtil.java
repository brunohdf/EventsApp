package com.example.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    public static String loadJsonRawResource(Context context, int rawResource) {
        String json;
        try {
            InputStream is = context.getResources().openRawResource(rawResource);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
