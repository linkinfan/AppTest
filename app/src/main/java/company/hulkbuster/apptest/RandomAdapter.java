package company.hulkbuster.apptest;
import android.arch.persistence.room.Room;
import android.content.Context;
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
import java.util.Random;
import java.util.regex.Pattern;

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
        final Context context = holder.itemView.getContext();
        holder.nameRandom.setText(randomsList.get(position).getNameR());
        holder.listRandoms.setText(randomsList.get(position).getListR().replace("|"," "));
        holder.rowLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "production")
                        .allowMainThreadQueries()
                        .build();

                AlertDialog.Builder ad;
                ad = new AlertDialog.Builder(context);
                ad.setTitle(R.string.TitleRandom);
                ad.setMessage(R.string.MessageAlert);
                ad.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String element = db.randomsDao().getByID(position+1);
                        String[] separeted = element.split(Pattern.quote("|"));
                        Random rnd = new Random();
                        int rand = rnd.nextInt(separeted.length-1);
                        String getRandom = separeted[rand];


                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(R.string.TitleResultRandom)
                                .setMessage(getRandom.toUpperCase())
                                .setCancelable(false)
                                .setNegativeButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();


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
        holder.rowLinear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder ad;
                ad = new AlertDialog.Builder(context);
                ad.setTitle(R.string.AlertTitleDelete);
                ad.setMessage(R.string.MessageAlert);
                ad.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "production")
                                .allowMainThreadQueries()
                                .build();

                        db.randomsDao().deleteByUserId(position+1);
                    }

                });
                ad.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = ad.create();
                alertDialog.show();
                return true;
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
