package company.hulkbuster.flukeapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Randoms.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RandomsDao randomsDao();

}
