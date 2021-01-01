package com.steven.diseoconstrainlayout.db;

import android.content.Context;

import com.steven.diseoconstrainlayout.db.dao.NotaDao;
import com.steven.diseoconstrainlayout.db.entity.NotaEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NotaEntity.class}, version = 1, exportSchema = false)
public abstract class NotaRoomDatabase extends RoomDatabase {
    public abstract NotaDao notaDao();
    private static volatile NotaRoomDatabase INSTANCE;

    public static NotaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NotaRoomDatabase.class,
                            "nota_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
