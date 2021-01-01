package com.steven.diseoconstrainlayout.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steven.diseoconstrainlayout.common.Constante;
import com.steven.diseoconstrainlayout.data.NuevaNotaDialogViewModel;
import com.steven.diseoconstrainlayout.R;
import com.steven.diseoconstrainlayout.db.entity.NotaEntity;

import java.util.ArrayList;
import java.util.List;

public class NotaListaFragment extends Fragment {
    private List<NotaEntity> notaEntityList;
    private MyItemRecyclerViewAdapter adapterNotas;
    private NuevaNotaDialogViewModel viewModel;
    private int tipoNotas = 1;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NotaListaFragment newInstance(int columnCount) {
        NotaListaFragment fragment = new NotaListaFragment();
        Bundle args = new Bundle();
        args.putInt(Constante.TIPOS_NOTAS, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotaListaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipoNotas = getArguments().getInt(Constante.TIPOS_NOTAS);
        }
        //indicamos que el fragmento tiene un menu propio.
        //setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(getActivity()).get(NuevaNotaDialogViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nota_lista_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            //nos sirve para conocer informacion relacionda con el tamaño de la pantalla
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            //para obtener los pixeles dependientes que tenemos en la pantalla (ancho)
            float width = displayMetrics.widthPixels / displayMetrics.density;
            int columnas = (int) width / 180;
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(columnas, StaggeredGridLayoutManager.VERTICAL));

            notaEntityList = new ArrayList<>();
            adapterNotas = new MyItemRecyclerViewAdapter(notaEntityList, getActivity());
            recyclerView.setAdapter(adapterNotas);

            if (tipoNotas == 1) {
                lanzarViewModel();
            }else if (tipoNotas == 2) {
                lanzarNotasFav();
            }

        }
        return view;
    }

    private void lanzarNotasFav() {
        viewModel.getFavNotas().observe(getActivity(), notaEntities ->
                adapterNotas.setNuevasNotas(notaEntities));
    }

    private void lanzarViewModel() {
        viewModel.getAllNotas().observe(getActivity(), notaEntities ->
                adapterNotas.setNuevasNotas(notaEntities));
    }
}