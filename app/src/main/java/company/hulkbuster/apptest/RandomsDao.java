package company.hulkbuster.apptest;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RandomsDao {


    @Query("SELECT * FROM Randoms")
    List<Randoms> allFromRandoms();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Randoms... randoms);

    @Query("SELECT listR FROM Randoms WHERE ID = (:selectId)")
    String getByID(int selectId);
}
