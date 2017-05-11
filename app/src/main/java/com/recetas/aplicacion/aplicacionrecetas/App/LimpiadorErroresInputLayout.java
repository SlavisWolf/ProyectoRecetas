package com.recetas.aplicacion.aplicacionrecetas.App;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by anton on 06/05/2017.
 */


// general para todos los input edit text que solo pierdne el error al escribir en ellos.
public class LimpiadorErroresInputLayout implements TextWatcher {

    TextInputLayout textInputLayout;


    public LimpiadorErroresInputLayout(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textInputLayout.setError(null);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
