package com.steven.diseoconstrainlayout.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.steven.diseoconstrainlayout.R;
import com.steven.diseoconstrainlayout.db.entity.NotaEntity;
import com.steven.diseoconstrainlayout.data.NuevaNotaDialogViewModel;

public class NuevaNotaDialogFragment extends DialogFragment {

    private View view;
    private EditText edtTitulo, edtContenido;
    private RadioGroup rdgColores;
    private Switch switchFavorito;

    public static NuevaNotaDialogFragment newInstance() {
        return new NuevaNotaDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.titulo_dialog);
        builder.setMessage(R.string.llenar_datos)
                .setPositiveButton(R.string.guardar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String titulo = edtTitulo.getText().toString();
                        String contenido = edtContenido.getText().toString();
                        String color;

                        switch (rdgColores.getCheckedRadioButtonId()) {
                            case R.id.rdbColorAzul:
                                color = "azul";
                                break;
                            case R.id.rdbColorRojo:
                                color = "rojo";
                                break;
                            default:
                                color = "verde";
                                break;
                        }

                        boolean favorito = switchFavorito.isChecked();

                        //comunicar al ViewModel el nuevo dato.
                        NuevaNotaDialogViewModel mViewModel = new ViewModelProvider(getActivity()).get(NuevaNotaDialogViewModel.class);
                        mViewModel.insertarNota(new NotaEntity(titulo, contenido, favorito, color));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.nueva_nota_dialog_fragment, null);
        builder.setView(view);
        edtTitulo = view.findViewById(R.id.edtTitulo);
        edtContenido = view.findViewById(R.id.edtContenido);
        rdgColores = view.findViewById(R.id.rdbColores);
        switchFavorito = view.findViewById(R.id.switchNotaFavorita);

        // Create the AlertDialog object and return it
        return builder.create();
    }
}