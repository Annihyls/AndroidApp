package com.example.simongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.simongame.adapter.CombatAdapter;

import com.example.simongame.battle.Combat;

import java.util.ArrayList;

public class BattleMenuSelection extends AppCompatActivity {
    private ArrayList<Combat> combats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_menu_selection);

        combats = Combat.getCombats();

        ListView combatList = findViewById(R.id.battleSelectionMenu);
        combatList.setAdapter(new CombatAdapter(this, combats));
    }


}