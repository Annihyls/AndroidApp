package com.example.simongame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.simongame.battle.Combat;
import com.example.simongame.battle.Equipment;
import com.example.simongame.battle.Inventaire;
import com.example.simongame.battle.Personnage;
import com.example.simongame.saveload.SaveLoad;

import java.io.IOException;
import java.io.InterruptedIOException;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private Button battle;
    private Button erase;
    public static Personnage[] charaTab;
    public static final int CHARA_TAB_TAILLE = 15;
    public static Equipment[] eqTab;
    public static final int EQUIP_TAB_TAILLE = 24;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.start = findViewById(R.id.start);
        this.battle = findViewById(R.id.battle);
        this.erase = findViewById(R.id.erase);

        initChara();
        createEquipment();
        Inventaire.createInventaire();
        Combat.createCombatList();

        try {
            Personnage.getCharaTab(0).loadSimon();
        } catch (IOException e) {
            e.printStackTrace();
        }


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(getApplicationContext(), MainWindow.class);
                startActivity(game);
            }
        });

        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent battle = new Intent(getApplicationContext(), BattleMenuSelection.class);
                startActivity(battle);
            }
        });
        AlertDialog.Builder popup = new AlertDialog.Builder(this);
        Toast saveErased = Toast.makeText(this, "Sauvegarde correctement effacé !", Toast.LENGTH_SHORT);
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setTitle("Effacer la sauvegarde ?");
                popup.setMessage("Voulez vous vraiment effacer la sauvegarde ?");
                popup.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveLoad.eraseSave();
                        saveErased.show();
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
    }

    public void initChara(){
        charaTab = new Personnage[CHARA_TAB_TAILLE];

        Resources resource = this.getResources();
        String[] description = resource.getStringArray(R.array.description_chara_array);
        charaTab[0] = new Personnage(0,"Simon",0,100,1,1,1,1, description[0]);
        charaTab[1] = new Personnage(1,"Macron", 1, 156, -600, -100, -100, -100, description[1]);
        charaTab[2] = new Personnage(2,"Le Roi", 1, 1350, -100, -50, -10, -120, description[2]);
        charaTab[3] = new Personnage(3, "Yazid", 200, 140000, 4000, 8500, 3500, 2000, description[3]);
        charaTab[4] = new Personnage(4, "Staline", 1, 18766, 800, 500, 89, 20, description[4]);
        charaTab[5] = new Personnage(5, "Roméo", 200, 160000, -100000, 10000, 10000, -100000, description[5]);
        charaTab[6] = new Personnage(6, "Brassens", 1, 6922, 169, 255, 199, -1, description[6]);
        charaTab[7] = new Personnage(7, "Sanders", 1, 14793, 340, 877, 356, 290, description[7]);
        charaTab[8] = new Personnage(8, "Jarod", 200, 160000, 1000, 10000, 2000, -80000, description[8]);
        charaTab[9] = new Personnage(9, "Vincent", 1000, 100, 10000, -100000, -100000, 10000, description[9]);
        charaTab[10] = new Personnage(10, "M.Pedoncule le Juge", 1000, 149999, 10000, 5000, 2500, 1000, description[10]);
        charaTab[11] = new Personnage(11, "Jesse", 1000, 69420, 1, 1, 10000, 10000, description[11]);
        charaTab[12] = new Personnage(12, "Samuel", 1000, 115000, 5500, 7000, 4500, 5000, description[12]);
        charaTab[13] = new Personnage(13, "Femme", 1000, 10, -999999, -999999, -999999, -999999, description[13]);
        charaTab[14] = new Personnage(14, "Franzl Lang", 1000, 3455, 297, 89, 165, 13, description[14]);
    }
    public void createEquipment(){
        eqTab = new Equipment[EQUIP_TAB_TAILLE];

        Resources resource = this.getResources();
        String[] description = resource.getStringArray(R.array.description_item_array);

        eqTab[0] = new Equipment(0, "Poings", "poings", 0,50,0,0,20, description[0]);
        eqTab[1] = new Equipment(1, "Corps Musculeux","corps_musculeux", 250,0,100,10,-10, description[1]);
        eqTab[2] = new Equipment(2, "Pièce Chanceuse", "piece_chanceuse",2,2,2,2,2, description[2]);
        eqTab[3] = new Equipment(3, "Grâce d'Aurelion Sol", "grace_aurelion_sol",0, -999999, -999999, -999999, -999999, description[3]);
        eqTab[4] = new Equipment(4, "OTP Sett", "otp_sett",10001, 6500, -1500, 21300, 3110, description[4]);
        eqTab[5] = new Equipment(5, "Paic Citron", "paic_citron",1500, -120, -150, 2300, 56, description[5]);
        eqTab[6] = new Equipment(6, "Armure", "armor",0, -250, 800, -355,-55, description[6]);
        eqTab[7] = new Equipment(7, "Pazuzu destiné à Victor", "pegi18",-1900, 1233, 225, -994,790, description[7]);
        eqTab[8] = new Equipment(8, "Epée", "weapon",-600, 850, -500, -500,200, description[8]);
        eqTab[9] = new Equipment(9, "Sac à PV", "sac_a_pv",12600, 0, 0, 0,0, description[9]);
        eqTab[10] = new Equipment(10, "Communisme négatif", "communisme_negatif",-780, -780, -780, -780,-780, description[10]);
        eqTab[11] = new Equipment(11, "Poudre de Perlimpinpin", "communisme_positif",300, -16, 122, -65,99, description[11]);
        eqTab[12] = new Equipment(12, "Chapeau de Brassens", "chapo_brassens",122, 333, -65, -77,9000, description[12]);
        eqTab[13] = new Equipment(13, "Boite de Tenders", "boite_tenders",0, 455, 223, 499,-102, description[13]);
        eqTab[14] = new Equipment(14, "20 000 hectars d'oliviers", "hectare_olivier",10000, 0, 0, 10000,0, description[14]);
        eqTab[15] = new Equipment(15, "Rose du parrain", "rose",5, 2344,45 , 2,-1111, description[15]);
        eqTab[16] = new Equipment(16, "Tardigrade du bonheur", "tardigrade",-1833, -445, 67989, -45667,-9657, description[16]);
        eqTab[17] = new Equipment(17, "Superbes Babouches", "notre_projet",7000, -16, 6550, -65,100000, description[17]);
        eqTab[18] = new Equipment(18, "Succulent dîner", "diner",8990, 120, 100, 566,-600, description[18]);
        eqTab[19] = new Equipment(19, "Poto de Yazid", "john",1, 10000, 10000, 1,10000, description[19]);
        eqTab[20] = new Equipment(20, "Chapitre 1000 One Piece", "one_piece_1000",0, 1000, 0, 0,0, description[20]);
        eqTab[21] = new Equipment(21, "Voiture d'occasion", "item_samuel",-5000, 2222, 8678, -23722,100000, description[21]);
        eqTab[22] = new Equipment(22, "Une copine", "copine",1, 13, 3, 12,8, description[22]);
        eqTab[23] = new Equipment(23, "Tenue bavaroise", "bavarois",0, 150, 110, 38,-7, description[23]);
    }
}