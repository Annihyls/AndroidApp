package com.example.simongame.battle;


import static android.provider.Settings.Global.getString;

import android.content.Context;
import android.content.res.Resources;

import com.example.simongame.MainActivity;
import com.example.simongame.R;

public class Equipment {

    private int id;
    private String nom;
    String mnemonique;
    private String description;
    private long pvBonus;
    private long power;
    private long defenseBonus;
    private long soinBonus;
    private long vitesseBonus;


    public Equipment(int id, String n, String mnemonique, long pv, long p, long d, long s, long v, String description){
        this.id = id;
        this.nom = n;
        this.mnemonique = mnemonique;
        this.pvBonus = pv;
        this.power = p;
        this.defenseBonus = d;
        this.soinBonus = s;
        this.vitesseBonus = v;
        this.description = description;
    }

    public long getPower() {
        return power;
    }
    public long getDefenseBonus() {
        return defenseBonus;
    }
    public long getPvBonus() {
        return pvBonus;
    }
    public long getSoinBonus() {
        return soinBonus;
    }
    public long getVitesseBonus() {
        return vitesseBonus;
    }
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getDescription() {
        return description;
    }
    public String getMnemonique() {
        return mnemonique;
    }



    public static Equipment getEqTab(int id) {
        if(id >= MainActivity.EQUIP_TAB_TAILLE || id < 0){
            throw new IllegalArgumentException();
        }
        return MainActivity.eqTab[id];
    }
}
