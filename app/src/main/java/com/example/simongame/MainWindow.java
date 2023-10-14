package com.example.simongame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simongame.battle.Equipment;
import com.example.simongame.battle.Inventaire;
import com.example.simongame.battle.Personnage;
import com.example.simongame.saveload.SaveLoad;

import java.io.IOException;
import java.util.Random;

public class MainWindow extends AppCompatActivity {


    private long[] statSimon = new long[TAILLE];
    private Personnage charaSimon = Personnage.getCharaTab(0);
    private long comparatif;
    public static final int TAILLE = 11;

    private ImageView simon, equipView1, equipView2;
    private TextView click, lvlDisplay, attackDisplay, defenseDisplay, soinDisplay, vitesseDisplay,
            pvDisplay, nameEquip1, nameEquip2;

    private Random random = new Random();
    private Button equip;

    @SuppressLint({"WrongViewCast", "SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        this.simon = findViewById(R.id.imageView);
        this.equip = findViewById(R.id.equip);
        this.equipView1 = findViewById(R.id.equip_1_view);
        this.equipView2 = findViewById(R.id.equip_2_view);

        this.click = findViewById(R.id.numberClick);
        this.lvlDisplay = findViewById(R.id.numberNiveau);
        this.attackDisplay = findViewById(R.id.numberAttack);
        this.defenseDisplay = findViewById(R.id.numberDefense);
        this.soinDisplay = findViewById(R.id.numberSoin);
        this.vitesseDisplay = findViewById(R.id.numberVitesse);
        this.pvDisplay = findViewById(R.id.numberPv);

        this.nameEquip1 = findViewById(R.id.equip_1_name);
        this.nameEquip2 = findViewById(R.id.equip_2_name);

        try {
            charaSimon.loadSimon();
            statSimon = SaveLoad.load(TAILLE);


            click.setText(""+ statSimon[1]);
            pvDisplay.setText(""+charaSimon.getPvMax());
            lvlDisplay.setText(""+statSimon[0]);
            attackDisplay.setText(""+charaSimon.getAttaque());
            defenseDisplay.setText(""+charaSimon.getDefense());
            soinDisplay.setText(""+charaSimon.getSoin());
            vitesseDisplay.setText(""+charaSimon.getVitesse());
            if(statSimon[9] != -1) {
                nameEquip1.setText(charaSimon.getE1().getNom());
                int idRessource1 = this.getResources().getIdentifier(charaSimon.getE1().getMnemonique(), "drawable", this.getPackageName());
                equipView1.setImageResource(idRessource1);
            }
            if(statSimon[10] != -1){
                nameEquip2.setText(charaSimon.getE2().getNom());
                int idRessource2 = this.getResources().getIdentifier(charaSimon.getE2().getMnemonique(), "drawable", this.getPackageName());
                equipView2.setImageResource(idRessource2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        simon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                statSimon[1]++;
                try {
                    lvlUP();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                charaSimon.setLevel(statSimon[0]);
                charaSimon.setPvMax(statSimon[2]);
                charaSimon.setAttaque(statSimon[3]);
                charaSimon.setDefense(statSimon[4]);
                charaSimon.setSoin(statSimon[5]);
                charaSimon.setVitesse(statSimon[6]);

                click.setText(""+ statSimon[1]);
                pvDisplay.setText("" + charaSimon.getPvMax());
                lvlDisplay.setText("" + charaSimon.getLevel());
                attackDisplay.setText("" + charaSimon.getAttaque());
                defenseDisplay.setText("" + charaSimon.getDefense());
                soinDisplay.setText("" + charaSimon.getSoin());
                vitesseDisplay.setText("" + charaSimon.getVitesse());

            }
        });
        equip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent equip = new Intent(getApplicationContext(), InventaireView.class);
                startActivity(equip);
                finish();
            }
        });
    }



    private void lvlUP() throws IOException {

        if(statSimon[7] < 34) {
            comparatif = fctGrow(statSimon[7]);
            statSimon[8] = comparatif;
        } else {
            comparatif = statSimon[8];
        }
        if(statSimon[1] >= comparatif){
            if(statSimon[7] < 34) {
                statSimon[7]++;
                comparatif = fctGrow(statSimon[7]);
            } else {
                comparatif += (fctGrow(35) - fctGrow(34));
            }
            statSimon[8] = comparatif;
            statSimon[0]++;
            statSimon[2] = statSimon[2] + (random.nextInt((((int)statSimon[0])/(1+(int)Math.log((double)statSimon[0])) + 100) - 3) + 1);
            statSimon[3] = statSimon[3] + (random.nextInt((((int)statSimon[0])/(1+(int)Math.log((double)statSimon[0])) + 2) - 1) + 1);
            statSimon[4] = statSimon[4] + (random.nextInt((((int)statSimon[0])/(1+(int)Math.log((double)statSimon[0])) + 2) - 1) + 1);
            statSimon[5] = statSimon[5] + (random.nextInt((((int)statSimon[0])/(1+(int)Math.log((double)statSimon[0])) + 2) - 1) + 1);
            statSimon[6] = statSimon[6] + (random.nextInt((((int)statSimon[0])/(1+(int)Math.log((double)statSimon[0])) + 2) - 1) + 1);


            lvlUpGainItem();
        }
        SaveLoad.save(statSimon, Inventaire.getInventaire(), TAILLE);
    }


    private long fctGrow(long i){
        long resultat;
        resultat = (long) Math.pow(1.2, i) + i;
        return resultat;
    }

    private void lvlUpGainItem(){

        Toast item = new Toast(getApplicationContext());
        View toast_view = LayoutInflater.from(this).inflate(R.layout.toast_text, null);
        item.setView(toast_view);
        item.setDuration(Toast.LENGTH_SHORT);
        item.setGravity(Gravity.CENTER, 0, 250);

        switch ((int) statSimon[0]){
            case(10):
                Inventaire.getInventaire().add(Equipment.getEqTab(2));  //pièce chanceuse
                item.show();
                break;
            case(20):
                Inventaire.getInventaire().add(Equipment.getEqTab(0));  //poings
                item.show();
                break;
            case(30):
                Inventaire.getInventaire().add(Equipment.getEqTab(1));  //corps musculeux
                item.show();
                break;
            case(40):
                Inventaire.getInventaire().add(Equipment.getEqTab(5));  //paic citron
                item.show();
                break;
            case(50):
                Inventaire.getInventaire().add(Equipment.getEqTab(6));  //armor
                item.show();
                break;
            case(60):
                Inventaire.getInventaire().add(Equipment.getEqTab(8));  //weapon
                item.show();
                break;
            case(70):
                Inventaire.getInventaire().add(Equipment.getEqTab(9));  //sac à pv
                item.show();
                break;
            case(80):
                Inventaire.getInventaire().add(Equipment.getEqTab(15));  //Rose du parrain
                item.show();
                break;
            case(90):
                Inventaire.getInventaire().add(Equipment.getEqTab(16));  //tardigrade
                item.show();
                break;
            case(100):
                Inventaire.getInventaire().add(Equipment.getEqTab(4));  //sett OTP
                item.show();
                break;
            default:
                try {
                    SaveLoad.save(statSimon, Inventaire.getInventaire(), TAILLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}