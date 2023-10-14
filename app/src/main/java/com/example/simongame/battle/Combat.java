package com.example.simongame.battle;

import com.example.simongame.MainWindow;
import com.example.simongame.saveload.SaveLoad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Combat {
    private int idImageBoss;
    private Personnage simon;
    private Personnage boss;
    private final Equipment recompense;
    private static ArrayList<Combat> combats;

    public Combat(int idImageBoss, Personnage boss, Equipment recompense){
        this.idImageBoss = idImageBoss;
        this.simon = Personnage.getCharaTab(0);
        this.boss = boss;
        this.recompense = recompense;
    }
    public static void createCombatList(){
        combats = new ArrayList<>();
        combats.add(new Combat(Personnage.getCharaTab(13).getId(), Personnage.getCharaTab(13), Equipment.getEqTab(22))); //Macron perlin
        combats.add(new Combat(Personnage.getCharaTab(1).getId(), Personnage.getCharaTab(1), Equipment.getEqTab(11))); //Macron perlin
        combats.add(new Combat(Personnage.getCharaTab(2).getId(), Personnage.getCharaTab(2), Equipment.getEqTab(18))); //Roi diner
        combats.add(new Combat(Personnage.getCharaTab(14).getId(), Personnage.getCharaTab(14), Equipment.getEqTab(23))); //Franzl Lang bavarois
        combats.add(new Combat(Personnage.getCharaTab(6).getId(), Personnage.getCharaTab(6), Equipment.getEqTab(12)));  //Brassens CHAPO
        combats.add(new Combat(Personnage.getCharaTab(4).getId(), Personnage.getCharaTab(4), Equipment.getEqTab(10)));  //Staline comNeg
        combats.add(new Combat(Personnage.getCharaTab(7).getId(), Personnage.getCharaTab(7), Equipment.getEqTab(13)));  //sanders tenders
        combats.add(new Combat(Personnage.getCharaTab(12).getId(), Personnage.getCharaTab(12), Equipment.getEqTab(21)));  //Samuel son item
        combats.add(new Combat(Personnage.getCharaTab(11).getId(), Personnage.getCharaTab(11), Equipment.getEqTab(20)));//Jesse > one piece
        combats.add(new Combat(Personnage.getCharaTab(5).getId(), Personnage.getCharaTab(5), Equipment.getEqTab(17)));  //Roméo projet
        combats.add(new Combat(Personnage.getCharaTab(3).getId(), Personnage.getCharaTab(3), Equipment.getEqTab(19)));   //Yazid John
        combats.add(new Combat(Personnage.getCharaTab(10).getId(), Personnage.getCharaTab(10), Equipment.getEqTab(7)));   //Axel BAzuzu
        combats.add(new Combat(Personnage.getCharaTab(8).getId(), Personnage.getCharaTab(8), Equipment.getEqTab(14)));//Jarod > oliviers
        combats.add(new Combat(Personnage.getCharaTab(9).getId(), Personnage.getCharaTab(9), Equipment.getEqTab(3)));//Vincent > aurelion

    }

    public static ArrayList<Combat> getCombats() {return combats;}

    public boolean gameover(){
        if(simon.getPvActuel() == 0){
            return true;
        }
        return false;
    }

    public boolean bossMort(){
        if(boss.getPvActuel() == 0){
            return true;
        }
        return false;
    }

    public Personnage getBoss() {return boss;}
    public int getIdImageBoss() {return idImageBoss;}
    public Personnage getSimon() {return simon;}
    public Equipment getRecompense() throws IOException {
        for(int i=0; i<Inventaire.getInventaire().size(); i++){
            if(Inventaire.getInventaire().get(i) == recompense){
                return null;
            }
        }
        long[] statSimonTemp = SaveLoad.load(MainWindow.TAILLE);
        Inventaire.getInventaire().add(recompense);
        SaveLoad.save(statSimonTemp,Inventaire.getInventaire(), MainWindow.TAILLE);
        return recompense;
    }

    public Personnage quiCommence(){
        if(simon.getVitesse() > boss.getVitesse()){
            return simon;
        } else if(simon.getVitesse() < boss.getVitesse()){
            return boss;
        }
        Random r = new Random();
        boolean b;
        b = r.nextBoolean();
        if(b){
            return simon;
        }
        return boss;
    }
    public long[] queFaitBoss() {
        Random r = new Random();
        Random rint = new Random();
        long[] l = new long[2];
        int combo;
        int choix;
        choix = r.nextInt(2);
        combo = 10 + rint.nextInt(40 - 10);
        if (choix == 0) {
            l[0] = 0;
            l[1] = boss.seSoigne(combo);
            return l;
        }
        l[1] = simon.coupSubitPar(boss, combo);
        l[0] = 1;
        return l;
    }
}
