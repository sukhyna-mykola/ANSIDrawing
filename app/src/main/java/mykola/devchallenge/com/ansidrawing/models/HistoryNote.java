package mykola.devchallenge.com.ansidrawing.models;

/**
 * Created by mykola on 04.05.17.
 */

public class HistoryNote {
    private String action;
    private Surface surface;
    // private ChangedPixels changedPixels;

    public HistoryNote(String action) {
        this.action = action;
        //changedPixels = new ChangedPixels();
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public HistoryNote(String action, ChangedPixels changedPixels) {
        this.action = action;
        //  this.changedPixels = changedPixels;
    }


   /* public ChangedPixels getChangedPixels() {
        return changedPixels;
    }

    public void addChanged(ChangedPixels changedPixels) {
        this.changedPixels.addNewPixels(changedPixels.getNewPixels());
        this.changedPixels.addOldPixels(changedPixels.getOldPixels());
    }*/

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public HistoryNote(String action, Surface surface) {

        this.action = action;
        this.surface = surface;
    }

    public HistoryNote clone() {
        return new HistoryNote(new String(action), surface.clone());
    }
}
