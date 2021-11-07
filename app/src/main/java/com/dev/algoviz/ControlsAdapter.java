package com.dev.algoviz;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class ControlsAdapter extends ArrayAdapter<String> {

    public ControlsAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }
}
