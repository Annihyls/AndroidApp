package com.example.simongame.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.simongame.Battle;
import com.example.simongame.R;
import com.example.simongame.battle.Combat;

import java.util.ArrayList;

public class CombatAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Combat> combats;
    private LayoutInflater inflator;

    public CombatAdapter(Context c, ArrayList<Combat> combats){
        this.context = c;
        this.combats = combats;
        this.inflator = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return combats.size();
    }

    @Override
    public Object getItem(int i) {
        return combats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return combats.get(i).getIdImageBoss();
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflator.inflate(R.layout.adapter_boss_selection, null);
        Combat currentBoss = combats.get(i);
        String name = currentBoss.getBoss().getNom();
        String description = currentBoss.getBoss().getDescription();
        long attack = currentBoss.getBoss().getAttaque();
        long pv = currentBoss.getBoss().getPvMax();
        long defense = currentBoss.getBoss().getDefense();
        long soin = currentBoss.getBoss().getSoin();
        long vitesse = currentBoss.getBoss().getVitesse();

        TextView nameView = view.findViewById(R.id.menu_name_boss);
        nameView.setText(name);

        TextView attackView = view.findViewById(R.id.menu_attack_boss);
        attackView.setText(String.valueOf(attack));

        TextView pvView = view.findViewById(R.id.menu_pv_boss);
        pvView.setText(String.valueOf(pv));

        TextView defView = view.findViewById(R.id.menu_defense_boss);
        defView.setText(String.valueOf(defense));

        TextView soinView = view.findViewById(R.id.menu_soin_boss);
        soinView.setText(String.valueOf(soin));

        TextView vitesseView = view.findViewById(R.id.menu_vitesse_boss);
        vitesseView.setText(String.valueOf(vitesse));

        TextView descView = view.findViewById(R.id.menu_description_boss);
        descView.setText(description);


        ImageView icon = view.findViewById(R.id.menu_view_boss);
        String resourceName = "id_"+currentBoss.getIdImageBoss()+"_boss";
        int idRessource = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
        icon.setImageResource(idRessource);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder popup = new AlertDialog.Builder(context);
                popup.setTitle("Engager un combat ?");
                popup.setMessage("Voulez vous vraiment engager un combat contre "+name);
                popup.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent battle = new Intent(context.getApplicationContext(), Battle.class);
                        Battle.setCombat(currentBoss);
                        context.startActivity(battle);


                    }
                });
                popup.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                popup.show();
            }
        });

        return view;
    }
}
