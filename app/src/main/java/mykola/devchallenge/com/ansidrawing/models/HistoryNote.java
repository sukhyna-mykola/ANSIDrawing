package mykola.devchallenge.com.ansidrawing.models;

/**
 * Created by mykola on 04.05.17.
 */

public class HistoryNote {
    private String action;
    private Canvas canvas;
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

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public HistoryNote(String action, Canvas canvas) {

        this.action = action;
        this.canvas = canvas;
    }

    public HistoryNote clone() {
        return new HistoryNote(new String(action), canvas.clone());
    }
}
