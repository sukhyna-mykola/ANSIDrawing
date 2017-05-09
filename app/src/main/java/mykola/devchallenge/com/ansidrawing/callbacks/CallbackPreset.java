package mykola.devchallenge.com.ansidrawing.callbacks;

import mykola.devchallenge.com.ansidrawing.models.Preset;


public interface CallbackPreset {
    void setSelectedPreset(Preset preset);

    void confirmPreset();

    void cancelPreset();
}
