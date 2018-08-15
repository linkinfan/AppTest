package company.hulkbuster.apptest;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static java.lang.String.valueOf;

class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.ViewHolder> {
    List<Randoms> randomsList;

    public RandomAdapter(List<Randoms> randoms) {
        this.randomsList = randoms;
    }

    @Override
    public RandomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RandomAdapter.ViewHolder holder, final int position) {
        holder.nameRandom.setText(randomsList.get(position).getNameR());
        holder.listRandoms.setText(randomsList.get(position).getListR().replace("|"," "));
        holder.rowLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AppDatabase db = Room.databaseBuilder(holder.itemView.getContext(), AppDatabase.class, "production")
                        .allowMainThreadQueries()
                        .build();

                AlertDialog.Builder ad;
                ad = new AlertDialog.Builder(holder.itemView.getContext());
                ad.setTitle(R.string.TitleRandom);
                ad.setMessage(R.string.MessageAlert);
                ad.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("RANDOM SIZE",valueOf(getItemCount()));
                        String element = db.randomsDao().getByID(position+1);
                        Log.d("ELEMENT",element);
                        String[] separeted = element.split("|");
                        int rand = (int)(Math.random() * getItemCount());
                        Log.d("Random number",valueOf(rand));
                        String getRandom = separeted[rand];
                        for (int i = 0; i < separeted.length; i++) {
                            Log.d("Element #" + i,separeted[i]);

                        }
                        Toast.makeText(holder.itemView.getContext(), getRandom, Toast.LENGTH_LONG).show();
                    }

                });
                ad.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = ad.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return randomsList.size();   }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameRandom;
        TextView listRandoms;
        LinearLayout rowLinear;
        public ViewHolder(View itemView) {
            super(itemView);
            nameRandom = itemView.findViewById(R.id.nameRandom);
            listRandoms = itemView.findViewById(R.id.listRandoms);
            rowLinear = itemView.findViewById(R.id.rowLinear);
        }

    }
}
