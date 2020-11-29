package com.example.academy.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.academy.data.CourseEntity;
import com.example.academy.data.ModuleEntity;

@Database(entities = {CourseEntity.class, ModuleEntity.class},
        version = 1,
        exportSchema = false)
public abstract class AcademyDatabase extends RoomDatabase {
    public abstract AcademyDao academyDao();

    private static volatile AcademyDatabase INSTANCE;

    public static AcademyDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AcademyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AcademyDatabase.class, "Academies.db")
                            .build();
                }

            }
        }
        return INSTANCE;
    }
}
