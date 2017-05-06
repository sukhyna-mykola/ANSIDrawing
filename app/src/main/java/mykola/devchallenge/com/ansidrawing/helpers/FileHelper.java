package mykola.devchallenge.com.ansidrawing.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Surface;

/**
 * Created by mykola on 06.05.17.
 */

public class FileHelper {

    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String PIXELS = "pixels";

    public static final String X = "x";
    public static final String Y = "y";
    public static final String SIZE = "size";
    public static final String COLOR = "color";
    public static final String SYMBOL = "symbol";

    private static final String DIR = "ANSI";
    public static final String TXT = ".txt";
    public static final int PERMISSION_REQUEST_CODE = 14;


    private static FileHelper fileHelper;

    private Context context;

    public FileHelper(Context context) {
        this.context = context;
    }

    public static FileHelper getInstance(Context context) {
        if (fileHelper == null)
            fileHelper = new FileHelper(context);
        return fileHelper;
    }


    public List<String> getFilesInDirecory() {

        File sdPath = Environment.getExternalStorageDirectory();
        File directory = new File(sdPath.getAbsolutePath() + "/" + DIR);
        directory.mkdirs();
        List<String> result = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (!file.isDirectory() && file.getName().endsWith(TXT))
                result.add(file.getName());

        }
        return result;


    }

    public boolean checkPermissions(String pesmission) {
        if (ContextCompat.checkSelfPermission(context, pesmission)
                == PackageManager.PERMISSION_GRANTED) return true;
        else return false;
    }


    public void requestMultiplePermissions() {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                PERMISSION_REQUEST_CODE);
    }


    public Surface load(String fileName) throws JSONException {
        String data = readFileSD(fileName);
        JSONObject object = new JSONObject(data);
        return surfaceFromJson(object);
    }

    public void save(Surface surface, String fileName) throws JSONException {
        writeFileSD(fileName, surfaceToJson(surface).toString());
    }

    private void writeFileSD(String fileName, String data) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        File sdPath = Environment.getExternalStorageDirectory();

        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR);

        sdPath.mkdirs();

        File sdFile = new File(sdPath, fileName + TXT);
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFileSD(String fileName) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File sdPath = Environment.getExternalStorageDirectory();

        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR);

        File sdFile = new File(sdPath, fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";
            String result = new String();

            while ((str = br.readLine()) != null) {
                result += str;
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private JSONObject surfaceToJson(Surface surface) throws JSONException {
        JSONObject o = new JSONObject();
        o.put(WIDTH, surface.getWidth());
        o.put(HEIGHT, surface.getHeight());
        JSONArray array = new JSONArray();
        for (int i = 0; i < surface.getWidth(); i++) {
            for (int j = 0; j < surface.getHeight(); j++) {
                Pixel pixel = surface.getPixels()[i][j];
                if (pixel != null) {
                    array.put(pixelToJson(pixel));
                }

            }
        }
        o.put(PIXELS, array);

        return o;
    }


    private JSONObject pixelToJson(Pixel pixel) throws JSONException {
        JSONObject o = new JSONObject();
        o.put(X, pixel.getX());
        o.put(Y, pixel.getY());
        o.put(SIZE, pixel.getSizeSymbol());
        o.put(COLOR, pixel.getColor());
        o.put(SYMBOL, pixel.getSymbol());
        return o;
    }


    private Surface surfaceFromJson(JSONObject o) throws JSONException {
        int width = o.getInt(WIDTH);
        int height = o.getInt(HEIGHT);
        Pixel[][] pixels = new Pixel[width][height];

        JSONArray a = o.getJSONArray(PIXELS);
        for (int i = 0; i < a.length(); i++) {
            JSONObject object = a.getJSONObject(i);

            Pixel p = pixelFromJson(object);

            pixels[p.getX()][p.getY()] = p;
        }
        return new Surface(pixels);

    }

    private Pixel pixelFromJson(JSONObject o) throws JSONException {

        int x = o.getInt(X);
        int y = o.getInt(Y);
        int size = o.getInt(SIZE);
        int symbol = o.getInt(SYMBOL);
        int color = o.getInt(COLOR);

        return new Pixel(size, color, symbol, x, y);


    }
}
