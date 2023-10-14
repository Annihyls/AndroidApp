package com.example.simongame.saveload;

import android.os.Environment;

import com.example.simongame.MainActivity;
import com.example.simongame.battle.Equipment;
import com.example.simongame.battle.Inventaire;
import com.example.simongame.battle.Personnage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveLoad {
    private static FileReader fr;
    private static BufferedReader br;
    private static FileWriter fw;
    private static BufferedWriter bw;
    private static final String EXTERNAL_STORAGE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();

    public static void save(long[] statSimon, ArrayList<Equipment> iv, int taille) throws IOException {
        File directory = new File(EXTERNAL_STORAGE+"/save_simon_game");
        if(!directory.exists()){
            directory.mkdir();
        }
        File f = new File(EXTERNAL_STORAGE + "/save_simon_game/save.simon");
        if(!f.exists()){
            f.createNewFile();
            f.setWritable(true);
        }
        fw = new FileWriter(EXTERNAL_STORAGE + "/save_simon_game/save.simon");
        bw = new BufferedWriter(fw);
        String str;
        int i;
        for(i = 0; i<taille; i++){
            str = String.valueOf(statSimon[i]);
            bw.write(str);
            bw.newLine();
        }
        int j = 0;
        for(i = taille; i<(Inventaire.getInventaire().size() + taille); i++){
            str = String.valueOf(iv.get(j).getId());
            bw.write(str);
            bw.newLine();
            j++;
        }
        bw.close(); //on ferme d'abord l'écriveur avant de fermer le fichier !
        fw.close();
    }

    public static long[] load(int taille) throws IOException {
        long[] statSimon = new long[taille];
        ArrayList<Equipment> inventaire = new ArrayList<>();
        File directory = new File(EXTERNAL_STORAGE+"/save_simon_game");
        if(!directory.exists()){
            directory.mkdir();
        }
        File f = new File(EXTERNAL_STORAGE + "/save_simon_game/save.simon");
        if(!f.exists()){
            statSimon[0] = 0;   //lvl
            statSimon[1] = 0;   //nbrClick
            statSimon[2] = 100; //pv
            statSimon[3] = 1;   //attck
            statSimon[4] = 1;   //def
            statSimon[5] = 1;   //soin
            statSimon[6] = 1;   //vitesse
            statSimon[7] = 0;   //x de la fct grow
            statSimon[8] = 0;   //comparatif actuel
            statSimon[9] = -1;   //pas d'equipement1
            statSimon[10] = -1;  //pas d'equipement2
            Inventaire.setInventaire(inventaire);
            return statSimon;
        }

        fr = new FileReader(EXTERNAL_STORAGE + "/save_simon_game/save.simon");
        br = new BufferedReader(fr);
        for(int i = 0; i<taille; i++){
            statSimon[i] = Long.parseLong(br.readLine());
        }
        String str;
        while((str = br.readLine()) != null){
            inventaire.add(Equipment.getEqTab(Integer.parseInt(str)));
        }
        br.close();
        fr.close();
        Inventaire.setInventaire(inventaire);
        return statSimon;
    }

    public static void eraseSave() {
        File directory = new File(EXTERNAL_STORAGE+"/save_simon_game");
        if(!directory.exists()){
            directory.mkdir();
        }
        File f = new File(EXTERNAL_STORAGE + "/save_simon_game/save.simon");
        if(f.exists()){
            f.delete();
            Personnage.getCharaTab(0).reset();
            Inventaire.resetInventory();
        }
    }
}
