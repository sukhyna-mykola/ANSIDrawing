package mykola.devchallenge.com.ansidrawing.callbacks;

import mykola.devchallenge.com.ansidrawing.models.Preset;

/**
 * Created by mykola on 04.05.17.
 */

public interface CallbackPreset {
    void setSelectedPreset(Preset preset);

    void confirmPreset();

    void cancelPreset();
}
