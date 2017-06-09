package com.recetas.aplicacion.aplicacionrecetas.Dialogos;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import com.recetas.aplicacion.aplicacionrecetas.Dialogos.Interfaces.AcceptOrCancelDialogListener;
import com.recetas.aplicacion.aplicacionrecetas.R;

/**
 * Created by anton on 08/06/2017.
 */

public class DialogoNuevoComentario extends DialogFragment implements View.OnClickListener {


    private MultiAutoCompleteTextView texto;
    AcceptOrCancelDialogListener listener;

    public DialogoNuevoComentario() {
    }

    public static DialogoNuevoComentario newInstance() {
        DialogoNuevoComentario fragment = new DialogoNuevoComentario();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogoComentario = new AlertDialog.Builder(getActivity());

        //dialogoComentario.setTitle("Create Project");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        dialogoComentario.setView(inflater.inflate(R.layout.dialogo_nuevo_comentario, null))

                .setPositiveButton(R.string.comment, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onAcceptDialog(texto.getText().toString().trim());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCancelDialog();
                    }
                });
        return dialogoComentario.create();
    }*/


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.dialogo_nuevo_comentario,null);
            Button aceptar = (Button) v.findViewById(R.id.comentar);
            aceptar.setOnClickListener(this);

            texto = (MultiAutoCompleteTextView) v.findViewById(R.id.textoDialogoComentario);

            Button botonCancel = (Button) v.findViewById(R.id.cancelar);
            botonCancel.setOnClickListener(this);

            return v;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try {
                listener = (AcceptOrCancelDialogListener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(
                        context.toString() +
                                " no implementó AcceptOrCancelDialogListener");
            }
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cancelar: {
                this.dismiss();
                break;
            }

            case R.id.comentar: {
                listener.onAcceptDialog(texto.getText().toString().trim());
                this.dismiss();
                break;
            }
        }
    }
}
