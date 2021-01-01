package com.steven.diseoconstrainlayout.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.steven.diseoconstrainlayout.common.Constante;
import com.steven.diseoconstrainlayout.R;
import com.steven.diseoconstrainlayout.data.NuevaNotaDialogViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

public class BotonOpcion extends BottomSheetDialogFragment {
    int idEliminar;
    NuevaNotaDialogViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(NuevaNotaDialogViewModel.class);
    }

    public static BotonOpcion newInstance(int idTweet) {
        BotonOpcion bt = new BotonOpcion();
        Bundle arg = new Bundle();
        arg.putInt(Constante.NOTA_ID, idTweet);
        bt.setArguments(arg);
        return bt;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idEliminar = getArguments().getInt(Constante.NOTA_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.boton_opciones, container, false);
        final NavigationView nav = v.findViewById(R.id.opcionesView);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_delete) {
                    dialogEliminar();
                    getDialog().dismiss();
                    return true;
                }else if (item.getItemId() == R.id.action_modificar) {
                    mostrarDialogoActualizarNota();
                    getDialog().dismiss();
                    return true;
                }
                return false;
            }
        });
        return v;
    }

    private void dialogEliminar() {
        AlertDialog.Builder bilder = new AlertDialog.Builder(getActivity());
        bilder.setMessage("¿Desea eliminar esta nota?")
                .setTitle("Eliminar nota")
                .setPositiveButton("Eliminar", (dialog, i) -> {
                    viewModel.eliminarNota(idEliminar);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog dialog = bilder.create();
        dialog.show();
    }

    private void mostrarDialogoActualizarNota() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ModificarNota dialogFragment = ModificarNota.newInstance(idEliminar);
        dialogFragment.show(fragmentManager, "ModificarNota");
    }
}
