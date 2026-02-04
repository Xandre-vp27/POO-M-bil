package com.example.pt71_vilchez_alexandre.sampledata;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {
        Tasca.class,
        Tag.class,
        TascaTag.class
}, version = 1)
@TypeConverters({Converter.class})
public abstract class DataBase extends RoomDatabase {

    private static DataBase INSTANCE;

    // DAOs
    public abstract TascaDAO tascaDAO();
    public abstract TascaTagDAO tascaTagDAO();
    public abstract TagDAO tagDAO();


    // Singleton
    public static DataBase getDbInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataBase.class, "db_tasques")
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Esto solo se ejecuta la PRIMERA vez que se crea la base de datos
                            Executors.newSingleThreadExecutor().execute(() -> {
                                TagDAO dao = INSTANCE.tagDAO();
                                dao.insertTag(new Tag("Casa"));
                                dao.insertTag(new Tag("Feina"));
                            });
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }
}