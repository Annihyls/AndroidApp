package com.example.simongame.battle;


import java.util.ArrayList;

public class Inventaire {
    private static ArrayList<Equipment> inventaire;

    public static ArrayList<Equipment> getInventaire() {
        return inventaire;
    }

    public static void createInventaire(){Inventaire.inventaire = new ArrayList<>();}

    public static void resetInventory(){
        inventaire.clear();
    }

    public static void setInventaire(ArrayList<Equipment> inventaire) {
        Inventaire.inventaire = inventaire;
    }
}
