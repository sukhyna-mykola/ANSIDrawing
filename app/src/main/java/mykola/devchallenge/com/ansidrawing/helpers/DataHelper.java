package mykola.devchallenge.com.ansidrawing.helpers;

import android.content.Context;

import mykola.devchallenge.com.ansidrawing.R;

/**
 * Created by mykola on 04.05.17.
 */

public class DataHelper {
    private Context context;
    private int[] colors;
    private int[] symbols;

    public  static  DataHelper dataHelper;

    public static DataHelper get(Context context){
        if(dataHelper==null)
            dataHelper = new DataHelper(context);
        return dataHelper;
    }

    private DataHelper(Context context) {
        this.context = context;
        loadResourses();
    }

    public int[] getColors() {
        return colors;
    }

    public int[] getSymbols() {
        return symbols;
    }

    public void loadResourses() {
        colors = context.getResources().getIntArray(R.array.colors);

        symbols = context.getResources().getIntArray(R.array.symbols);

    }
}
