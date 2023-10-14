package com.example.simongame.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.simongame.MainActivity;
import com.example.simongame.R;
import com.example.simongame.battle.Equipment;
import com.example.simongame.battle.Inventaire;
import com.example.simongame.battle.Personnage;

import java.io.IOException;
import java.util.ArrayList;

public class IventaireAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Equipment> inventaire;
    private LayoutInflater inflator;

    public IventaireAdapter(Context c, ArrayList<Equipment> e){
        this.context = c;
        this.inventaire = e;
        this.inflator = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return inventaire.size();
    }

    @Override
    public Object getItem(int position) {
        return inventaire.get(position);
    }

    @Override
    public long getItemId(int i) {
        return inventaire.get(i).getId();
    }

    @SuppressLint({"ViewHolder", "InflateParams", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflator.inflate(R.layout.adapter_inventory, null);
        Equipment currentEquip = inventaire.get(i);
        String name = currentEquip.getNom();
        String desc = currentEquip.getDescription();
        long power = currentEquip.getPower();
        long pvBonus = currentEquip.getPvBonus();
        long defenseBonus = currentEquip.getDefenseBonus();
        long vitessebonus = currentEquip.getVitesseBonus();
        long soin = currentEquip.getSoinBonus();

        TextView nameView = view.findViewById(R.id.item_name);
        nameView.setText(name);

        TextView powerView = view.findViewById(R.id.item_power);
        powerView.setText(String.valueOf(power));

        TextView defenseView = view.findViewById(R.id.item_defense);
        defenseView.setText(String.valueOf(defenseBonus));

        TextView pvView = view.findViewById(R.id.item_pv);
        pvView.setText(String.valueOf(pvBonus));

        TextView soinView = view.findViewById(R.id.item_soin);
        soinView.setText(String.valueOf(soin));

        TextView vitesseView = view.findViewById(R.id.item_vitesse);
        vitesseView.setText(String.valueOf(vitessebonus));

        TextView descView = view.findViewById(R.id.item_description);
        descView.setText(desc);

        TextView equip2View = view.findViewById(R.id.item_equip2);
        TextView equip1View = view.findViewById(R.id.item_equip1);
        if(Personnage.getCharaTab(0).getE1() != null) {
            if (currentEquip == Personnage.getCharaTab(0).getE1()) {
                equip1View.setText("Eq 1");
            }
        }
        if(Personnage.getCharaTab(0).getE2() != null) {
            if (currentEquip == Personnage.getCharaTab(0).getE2()) {
                equip2View.setText("Eq 2");
            }
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder popup = new AlertDialog.Builder(context);
                popup.setTitle("Sur quel emplacement voulez vous équiper "+name+" ?");
                popup.setMessage(desc);
                popup.setPositiveButton("Emplacement 2", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Personnage.getCharaTab(0).setE2(currentEquip);

                            equip1View.setText("");
                            equip2View.setText("Eq 2");


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                popup.setNegativeButton("Emplacement 1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Personnage.getCharaTab(0).setE1(currentEquip);

                            equip2View.setText("");
                            equip1View.setText("Eq 1");


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                popup.show();
            }
        });
        this.notifyDataSetChanged();       //permet de maj les tag !!!!

        ImageView icon = view.findViewById(R.id.weapon_icon);
        String resourceName = currentEquip.getMnemonique();
        int idRessource = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
        icon.setImageResource(idRessource);

        return view;
    }
}
