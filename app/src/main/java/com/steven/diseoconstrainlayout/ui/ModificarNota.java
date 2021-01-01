package com.steven.diseoconstrainlayout.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.steven.diseoconstrainlayout.common.Constante;
import com.steven.diseoconstrainlayout.R;
import com.steven.diseoconstrainlayout.data.NuevaNotaDialogViewModel;
import com.steven.diseoconstrainlayout.db.entity.NotaEntity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class ModificarNota extends DialogFragment {

    private View view;
    private EditText edtTitulo, edtContenido;
    private RadioGroup rdgColores;
    private Switch switchFavorito;
    int idNota;
    private RadioButton rbColor;
    private NotaEntity nota;
    private NuevaNotaDialogViewModel mViewModel;

    public static ModificarNota newInstance(int id) {
        ModificarNota dialogFragment = new ModificarNota();
        Bundle arg = new Bundle();
        arg.putInt(Constante.NOTA_ID, id);
        dialogFragment.setArguments(arg);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(NuevaNotaDialogViewModel.class);
        if (getArguments() != null) {
            idNota = getArguments().getInt(Constante.NOTA_ID);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.modificar_nota);
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
                        nota.setFavorita(favorito);
                        nota.setTitulo(titulo);
                        nota.setContenido(contenido);
                        nota.setColor(color);
                        mViewModel.updateNota(nota);
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

        nota = mViewModel.getNota(idNota);
        edtTitulo.setText(nota.getTitulo());
        edtContenido.setText(nota.getContenido());
        switchFavorito.setChecked(nota.isFavorita());
        colorSeleccionado(view);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void colorSeleccionado(View view) {
        if (nota.getColor().equals("azul")) {
            rbColor = view.findViewById(R.id.rdbColorAzul);
        }else if (nota.getColor().equals("rojo")) {
            rbColor = view.findViewById(R.id.rdbColorRojo);
        }else {
            rbColor = view.findViewById(R.id.rdbColorVerde);
        }
        rbColor.setChecked(true);
    }
}
