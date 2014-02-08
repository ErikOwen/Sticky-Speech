package com.spinninggangstaz.stickyspeech;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created with IntelliJ IDEA.
 * User: Kevin
 * Date: 2/8/14
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class SerializeObject {
    private final static String TAG = "SerializeObject";

    /**
     * Read data from file and put it into a string
     * @param context
     * @param filename - fully qualified string name
     * @return
     */

    public static String ReadSettings(Context context, String filename){
        StringBuffer dataBuffer = new StringBuffer();
        try{
            // open the file for reading
            InputStream instream = context.openFileInput(filename);
            // if file the available for reading
            if (instream != null) {
                // prepare the file for reading
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);

                String newLine;
                // read every line of the file into the line-variable, on line at the time
                while (( newLine = buffreader.readLine()) != null) {
                    // do something with the settings from the file
                    dataBuffer.append(newLine);
                }
                // close the file again
                instream.close();
            }

        } catch (java.io.FileNotFoundException f) {
            // do something if the myfilename.txt does not exits
            Log.e(TAG, "FileNot Found in ReadSettings filename = " + filename);
            try {
                context.openFileOutput(filename, Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            Log.e(TAG, "IO Error in ReadSettings filename = " + filename);
        }

        return dataBuffer.toString();
    }
    public static void setStringArrayPref(Context context, String key, ArrayList<Integer> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.commit();
    }

    public static ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}
