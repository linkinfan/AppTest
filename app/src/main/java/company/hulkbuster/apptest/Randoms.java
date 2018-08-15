package company.hulkbuster.apptest;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Randoms {

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameR() {
        return nameR;
    }

    public void setNameR(String nameR) {
        this.nameR = nameR;
    }

    public String getListR() {
        return listR;
    }

    public void setListR(String listR) {
        this.listR = listR;
    }

    public Randoms(String nameR, String listR) {
        this.nameR = nameR;

        this.listR = listR;
    }

    @PrimaryKey(autoGenerate = true)
    int ID;

    @ColumnInfo
    String nameR;

    @ColumnInfo
    String listR;
}
