package com.steven.diseoconstrainlayout.data;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.steven.diseoconstrainlayout.db.entity.NotaEntity;
import com.steven.diseoconstrainlayout.ui.BotonOpcion;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NuevaNotaDialogViewModel extends AndroidViewModel {
    private LiveData<List<NotaEntity>> allNotas;
    private LiveData<List<NotaEntity>> favNotas;
    private NotaRepository notaRepository;

    public NuevaNotaDialogViewModel(@NonNull Application application) {
        super(application);
        notaRepository = new NotaRepository(application);
        allNotas = notaRepository.getAll();
    }

    //El fragment que necesita recibir la nueva lista de datos
    public LiveData<List<NotaEntity>> getAllNotas() {
        return allNotas;
    }

    public LiveData<List<NotaEntity>> getFavNotas() {
        favNotas = notaRepository.getAllFavs();
        return favNotas;
    }

    //El fragment que inserte una nueva nota, debera comunicarlo al ViewModel
    public void insertarNota(NotaEntity nuevaNota) {
        notaRepository.insert(nuevaNota);
    }

    public void updateNota(NotaEntity NotaUpdate) { notaRepository.update(NotaUpdate);}

    public void eliminarNota(int id) {
        notaRepository.eliminar(id);
    }

    public void abrirOpciones(Context ctx, int nota) {
        BotonOpcion frag = BotonOpcion.newInstance(nota);
        frag.show(((AppCompatActivity)ctx).getSupportFragmentManager(), "BottomModalViewFragment");
    }

    public NotaEntity getNota(int id) {
        return notaRepository.getNota(id);
    }
}