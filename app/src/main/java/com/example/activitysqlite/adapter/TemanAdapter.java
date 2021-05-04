package com.example.activitysqlite.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitysqlite.MainActivity;
import com.example.activitysqlite.EditData;
import com.example.activitysqlite.R;
import com.example.activitysqlite.database.DBController;
import com.example.activitysqlite.database.Teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listData;
    private Context c;
    private Context a;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData = listData;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c = parent.getContext();
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String nm, tlp, id;

        id = listData.get(position).getId();
        nm = listData.get(position).getNama();
        tlp = listData.get(position).getTelpon();
        DBController db = new DBController(c);



        holder.namaTxt.setTextColor(Color.BLUE);
        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nm);
        holder.telponTxt.setText(tlp);


        holder.cardku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(c, holder.cardku);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

                            case R.id.mnedit:
                                Intent i = new Intent(c, EditData.class);
                                i.putExtra("id",id);
                                i.putExtra("nama",nm);
                                i.putExtra("telpon",tlp);
                                c.startActivity(i);
                                break;
                            case R.id.mndelete:
                                HashMap<String,String > values = new HashMap<>();
                                values.put("id",id);
                                db.deleteData(values);
                                Intent intent = new Intent(c, MainActivity.class);
                                c.startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namaTxt, telponTxt;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt = (TextView) view.findViewById(R.id.textTelpon);
        }
    }
}
