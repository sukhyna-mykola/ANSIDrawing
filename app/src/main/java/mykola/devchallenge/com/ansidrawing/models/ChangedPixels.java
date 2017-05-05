package mykola.devchallenge.com.ansidrawing.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mykola on 04.05.17.
 */

public class ChangedPixels {
    private List<Pixel> oldPixels, newPixels;

    public List<Pixel> getOldPixels() {
        return oldPixels;
    }

    public void setOldPixels(List<Pixel> oldPixels) {
        this.oldPixels = oldPixels;
    }

    public List<Pixel> getNewPixels() {
        return newPixels;
    }

    public void setNewPixels(List<Pixel> newPixels) {
        this.newPixels = newPixels;
    }

    public ChangedPixels() {
        oldPixels = new ArrayList<>();
        newPixels = new ArrayList<>();
    }

    public ChangedPixels(List<Pixel> oldPixels, List<Pixel> newPixels) {

        this.oldPixels = oldPixels;
        this.newPixels = newPixels;
    }

    public void addOldPixels(List<Pixel> oldPixels) {
        this.oldPixels.addAll(oldPixels);
    }

    public void addNewPixels(List<Pixel> newPixels) {
        this.newPixels.addAll(newPixels);
    }
}
