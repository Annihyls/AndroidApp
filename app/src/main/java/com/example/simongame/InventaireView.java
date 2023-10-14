package com.example.simongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.simongame.adapter.IventaireAdapter;
import com.example.simongame.battle.Equipment;
import com.example.simongame.battle.Inventaire;

import java.util.ArrayList;

public class InventaireView extends AppCompatActivity {
    private ArrayList<Equipment> itemInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventaire_view);

        itemInventory = Inventaire.getInventaire();

        ListView inventaire = findViewById(R.id.inventoryView);
        inventaire.setAdapter(new IventaireAdapter(this, itemInventory));
    }

}