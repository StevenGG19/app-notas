package com.steven.diseoconstrainlayout.data;

import android.app.Application;
import android.os.AsyncTask;

import com.steven.diseoconstrainlayout.db.NotaRoomDatabase;
import com.steven.diseoconstrainlayout.db.dao.NotaDao;
import com.steven.diseoconstrainlayout.db.entity.NotaEntity;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NotaRepository {
    private NotaDao notaDao;
    private LiveData<List<NotaEntity>> allNotas;
    private LiveData<List<NotaEntity>> allNotasFavoritas;

    public NotaRepository(Application application) {
        NotaRoomDatabase db = NotaRoomDatabase.getDatabase(application);
        notaDao = db.notaDao();
        allNotas = notaDao.getAll();
        allNotasFavoritas = notaDao.getAllFavorita();
    }

    public LiveData<List<NotaEntity>> getAll() {
        return allNotas;
    }

    public NotaEntity getNota(int id) {
        NotaEntity notaEntity = null;
        for (int i = 0; i < allNotas.getValue().size(); i++) {
            if (allNotas.getValue().get(i).getId() == id) {
                notaEntity = allNotas.getValue().get(i);
            }
        }
        return notaEntity;
    }

    public LiveData<List<NotaEntity>> getAllFavs() {
        return allNotasFavoritas;
    }

    public void eliminar(int id) {
        new eliminarAsyncTask(notaDao).execute(id);
    }

    public void insert(NotaEntity notaEntity) {
        new insertAsyncTask(notaDao).execute(notaEntity);
    }

    public void update(NotaEntity notaEntity) {
        new updateAsyncTask(notaDao).execute(notaEntity);
    }

    private static class insertAsyncTask extends AsyncTask<NotaEntity, Void, Void> {
        private NotaDao notaDaoAsyncTask;

        insertAsyncTask(NotaDao dao) {
            notaDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDaoAsyncTask.insert(notaEntities[0]);
            return null;
        }
    }

    private static class eliminarAsyncTask extends AsyncTask<Integer, Void, Void> {
        private NotaDao dao;
        public eliminarAsyncTask(NotaDao notaDao) {
            dao = notaDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            dao.deleteById(integers[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<NotaEntity, Void, Void> {
        private NotaDao notaDaoAsyncTask;

        updateAsyncTask(NotaDao dao) {
            notaDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDaoAsyncTask.update(notaEntities[0]);
            return null;
        }
    }
}
