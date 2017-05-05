package mykola.devchallenge.com.ansidrawing.helpers;

import java.util.ArrayList;
import java.util.List;

import mykola.devchallenge.com.ansidrawing.models.Canvas;
import mykola.devchallenge.com.ansidrawing.models.HistoryNote;

/**
 * Created by mykola on 04.05.17.
 */

public class HistoryHelper {
    private List<HistoryNote> notes;
    private int positionNote;
    public static HistoryHelper helper;

    public static HistoryHelper get() {
        if (helper == null)
            helper = new HistoryHelper();
        return helper;
    }

    private HistoryHelper() {
        notes = new ArrayList<>();
    }

    public void addNote(HistoryNote note) {
        //Якщо було натиснуто Undo і потім щось намальвано
        if (positionNote - notes.size() != -1 && notes.size() >= 1) {

            for (int i = positionNote + 1; i < notes.size();) {
                notes.remove(i);
            }
        }
        positionNote = notes.size();
        notes.add(note);
    }

    public void undo(Canvas canvas) {
        if (positionNote - 1 >= 0) {
            HistoryNote note = notes.get(--positionNote);

            for (int i = 0; i < canvas.getWidth(); i++) {
                for (int j = 0; j < canvas.getHeight(); j++) {
                    canvas.setPixel(i, j, note.getCanvas().getPixel(i, j));
                }

            }
        }
        if (positionNote < 0)
            positionNote = 0;

    }

    public void redo(Canvas canvas) {
        if (positionNote + 1 < notes.size()) {
            HistoryNote note = notes.get(++positionNote);

            for (int i = 0; i < canvas.getWidth(); i++) {
                for (int j = 0; j < canvas.getHeight(); j++) {
                    canvas.setPixel(i, j, note.getCanvas().getPixel(i, j));
                }

            }
        }
        if (positionNote > notes.size() - 1)
            positionNote = notes.size() - 1;

    }
}
