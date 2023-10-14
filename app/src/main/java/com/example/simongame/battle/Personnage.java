package com.example.simongame.battle;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.example.simongame.MainActivity;
import com.example.simongame.MainWindow;
import com.example.simongame.R;
import com.example.simongame.saveload.SaveLoad;

import java.io.IOException;

public class Personnage extends Application {
    private int id;
    private String nom;
    private long level;
    private long pvActuel;
    private long pvMax;
    private long attaque;
    private long defense;
    private long soin;
    private long vitesse;
    private Equipment e1;
    private Equipment e2;
    private final String description;


    public Personnage(int id, String n, long level, long pvMax, long a, long d, long s, long v, String description){
        this.id = id;
        this.nom = n;
        this.level = level;
        this.pvMax = pvMax;
        this.pvActuel = pvMax;
        this.attaque = a;
        this.defense = d;
        this.soin = s;
        this.vitesse = v;
        this.description = description;
    }
    /*getter*/
    public int getId(){return this.id;}
    public long getLevel() {return level;}
    public long getPvActuel() {
        return pvActuel;
    }
    public long getPvMax() {
        return pvMax;
    }
    public long getAttaque(){return this.attaque;}
    public String getNom(){return this.nom;}
    public long getDefense(){return this.defense;}
    public long getSoin(){return this.soin;}
    public long getVitesse(){return this.vitesse;}
    public Equipment getE1() {
        return e1;
    }
    public Equipment getE2() {
        return e2;
    }
    public String getDescription() {
        return description;
    }
    /*setter*/

    public void setPvMax(long pvMax) {
        this.pvMax = pvMax;
    }
    public void setAttaque(long attaque) { this.attaque = attaque; }
    public void setDefense(long defense) {this.defense = defense;}
    public void setNom(String nom) {this.nom = nom;}
    public void setSoin(long soin) {this.soin = soin;}
    public void setVitesse(long vitesse) {this.vitesse = vitesse;}

    public void setE1(Equipment e) throws IOException {
        long[] statSimonTemp = SaveLoad.load(MainWindow.TAILLE);
        if(this.e1 != e){
            if (this.e2 == e && this.e1 != null) {
                Equipment temp = e2;
                this.e2 = e1;
                this.e1 = temp;
                statSimonTemp[9] = e1.getId();
                statSimonTemp[10] = e2.getId();
            } else if(this.e2 == e && this.e1 == null){
                this.e1 = this.e2;
                this.e2 = null;
                statSimonTemp[9] = e1.getId();
                statSimonTemp[10] = -1;
            } else {
                if (this.e1 != null) {
                    this.pvMax = pvMax - e1.getPvBonus();
                    this.attaque = attaque - e1.getPower();
                    this.defense = defense - e1.getDefenseBonus();
                    this.soin = soin - e1.getSoinBonus();
                    this.vitesse = vitesse - e1.getVitesseBonus();
                }
                this.e1 = e;
                this.pvMax = pvMax + e.getPvBonus();
                this.attaque = attaque + e.getPower();
                this.defense = defense + e.getDefenseBonus();
                this.soin = soin + e.getSoinBonus();
                this.vitesse = vitesse + e.getVitesseBonus();

                statSimonTemp[2] = this.pvMax; //pv
                statSimonTemp[3] = this.attaque;   //attck
                statSimonTemp[4] = this.defense;   //def
                statSimonTemp[5] = this.soin;   //soin
                statSimonTemp[6] = this.vitesse;   //vitesse
                statSimonTemp[9] = e1.getId();
            }
        } else {
            this.pvMax = pvMax - e1.getPvBonus();
            this.attaque = attaque - e1.getPower();
            this.defense = defense - e1.getDefenseBonus();
            this.soin = soin - e1.getSoinBonus();
            this.vitesse = vitesse - e1.getVitesseBonus();
            this.e1 = null;
            statSimonTemp[2] = this.pvMax; //pv
            statSimonTemp[3] = this.attaque;   //attck
            statSimonTemp[4] = this.defense;   //def
            statSimonTemp[5] = this.soin;   //soin
            statSimonTemp[6] = this.vitesse;   //vitesse
            statSimonTemp[9] = -1;
        }
        SaveLoad.save(statSimonTemp, Inventaire.getInventaire(), MainWindow.TAILLE);
    }

    public void setE2(Equipment e) throws IOException {
        long[] statSimonTemp = SaveLoad.load(MainWindow.TAILLE);
        if(this.e2 != e) {
            if (this.e1 == e && this.e2 != null) {
                Equipment temp = e1;
                this.e1 = e2;
                this.e2 = temp;
                statSimonTemp[9] = e1.getId();
                statSimonTemp[10] = e2.getId();
            } else if(this.e1 == e && this.e2 == null){
                this.e2 = this.e1;
                this.e1 = null;
                statSimonTemp[9] = -1;
                statSimonTemp[10] = e2.getId();
            } else {
                if (this.e2 != null) {
                    this.pvMax = pvMax - e2.getPvBonus();
                    this.attaque = attaque - e2.getPower();
                    this.defense = defense - e2.getDefenseBonus();
                    this.soin = soin - e2.getSoinBonus();
                    this.vitesse = vitesse - e2.getVitesseBonus();
                }
                this.e2 = e;
                this.pvMax = pvMax + e.getPvBonus();
                this.attaque = attaque + e.getPower();
                this.defense = defense + e.getDefenseBonus();
                this.soin = soin + e.getSoinBonus();
                this.vitesse = vitesse + e.getVitesseBonus();

                statSimonTemp[2] = this.pvMax; //pv
                statSimonTemp[3] = this.attaque;   //attck
                statSimonTemp[4] = this.defense;   //def
                statSimonTemp[5] = this.soin;   //soin
                statSimonTemp[6] = this.vitesse;   //vitesse
                statSimonTemp[10] = e2.getId();
            }
        } else {
            this.pvMax = pvMax - e2.getPvBonus();
            this.attaque = attaque - e2.getPower();
            this.defense = defense - e2.getDefenseBonus();
            this.soin = soin - e2.getSoinBonus();
            this.vitesse = vitesse - e2.getVitesseBonus();
            this.e2 = null;
            statSimonTemp[2] = this.pvMax; //pv
            statSimonTemp[3] = this.attaque;   //attck
            statSimonTemp[4] = this.defense;   //def
            statSimonTemp[5] = this.soin;   //soin
            statSimonTemp[6] = this.vitesse;   //vitesse
            statSimonTemp[10] = -1;
        }
        SaveLoad.save(statSimonTemp, Inventaire.getInventaire(), MainWindow.TAILLE);
    }
    public void setLevel(long level) {
        this.level = level;
    }
    public void setPvActuel(long pvActuel) {
        this.pvActuel = pvActuel;
    }

    public long coupSubitPar(Personnage p1, long combo){
        long temp = this.getPvActuel();
        this.setPvActuel(temp - formuleDegat(p1, this, combo));
        if(pvActuel < 0){
            pvActuel = 0;
        }
        return (formuleDegat(p1, this, combo));
    }

    public long seSoigne(long combo){
        long temp = pvActuel;
        this.setPvActuel(temp + formuleSoin(this, combo));
        if(pvActuel > pvMax){
            pvActuel = pvMax;
        }
        return (formuleSoin(this, combo));
    }

    public long formuleDegat(Personnage p1, Personnage p2, long combo){
        long resultat;

        if(p2.defense <= 0 && p1.attaque > 0){
            resultat = (long) ((p1.attaque * combo + Math.abs((p2.defense + 3))));
        } else if (p2.defense > 0 && p1.attaque > 0) {
            resultat = (long) ((p1.attaque * combo) / Math.log((p2.defense + 3)) + 1);
        } else {
            if(p2.defense <= 0){
                resultat = (long) ((((Math.abs(p2.defense)) / (p1.attaque)) * combo) / (-1 / (Math.log(Math.abs((p2.defense + 3))) + 1)));
            }
            else {
                resultat = (long) (((1/Math.abs(p1.attaque)) * combo) + (1/(p2.defense + 3)));
            }
        }
        if(resultat < 1){
            return 1;
        }
        return resultat;
    }
    public long formuleSoin(Personnage p1, long combo){
        long resultat;
        resultat = (long) ((p1.soin * combo)/Math.log(p1.pvMax)+1);
        if(resultat < 1){
            return 1;
        }
        return resultat;
    }


    public static Personnage getCharaTab(int i){
        if(i >= MainActivity.CHARA_TAB_TAILLE || i < 0){
            throw new IllegalArgumentException();
        }
        return MainActivity.charaTab[i];
    }

    public void reset(){
        this.pvMax = 100;
        this.level = 0;
        this.attaque = 1;
        this.defense = 1;
        this.soin = 1;
        this.vitesse = 1;
        this.e1 = null;
        this.e2 = null;
    }
    public void loadSimon() throws IOException {
        long[] statSimonTemp = SaveLoad.load(MainWindow.TAILLE);
        this.level   = statSimonTemp[0];
        this.pvMax   = statSimonTemp[2];
        this.attaque = statSimonTemp[3];
        this.defense = statSimonTemp[4];
        this.soin    = statSimonTemp[5];
        this.vitesse = statSimonTemp[6];
        if(statSimonTemp[9] != -1) {
            this.e1 = Equipment.getEqTab((int) statSimonTemp[9]);
        }
        if(statSimonTemp[10] != -1) {
            this.e2 = Equipment.getEqTab((int) statSimonTemp[10]);
        }
    }
}
