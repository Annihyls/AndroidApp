package com.example.simongame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simongame.battle.Combat;
import com.example.simongame.battle.Equipment;
import com.example.simongame.battle.Personnage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Battle extends AppCompatActivity {
    private static Combat combat;
    private Button attack, guard, escape;
    private int nbTap = 0;
    private TextView tw, pvBoss, etatJeu, comboView, comboSoinView, pvSimon;
    private ImageView bossView, simonView, eclatViewBoss, eclatViewSimon, background;



    public static void setCombat(Combat c) {
        Battle.combat = c;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);


        combat.getBoss().setPvActuel(combat.getBoss().getPvMax());
        combat.getSimon().setPvActuel(combat.getSimon().getPvMax());

        this.tw = findViewById(R.id.nameBoss);
        tw.setText(combat.getBoss().getNom());

        this.pvSimon = findViewById(R.id.pvSimon);
        pvSimon.setText(combat.getSimon().getPvActuel()+" / "+combat.getSimon().getPvMax());

        this.pvBoss = findViewById(R.id.pvBoss);
        pvBoss.setText(combat.getBoss().getPvActuel()+" / "+combat.getBoss().getPvMax());

        this.etatJeu = findViewById(R.id.etat_du_jeu);
        this.etatJeu.setVisibility(View.GONE);

        this.comboView = findViewById(R.id.combo_view);
        comboView.setText("Combo : 0");
        this.comboView.setVisibility(View.GONE);

        this.comboSoinView = findViewById(R.id.combo_view_soin);
        comboSoinView.setText("Combo : 0");
        this.comboSoinView.setVisibility(View.GONE);

        this.eclatViewBoss = findViewById(R.id.view_boss_eclat);
        this.eclatViewBoss.setVisibility(View.GONE);

        this.eclatViewSimon = findViewById(R.id.view_eclat_simon);
        this.eclatViewSimon.setVisibility(View.GONE);

        this.bossView = findViewById(R.id.view_boss);
        String resourceName = "id_"+combat.getIdImageBoss()+"_boss";
        int idRessource = this.getResources().getIdentifier(resourceName, "drawable", this.getPackageName());
        this.bossView.setImageResource(idRessource);

        this.background = findViewById(R.id.backgroundCombat);
        String nameBackground = "environnement"+combat.getIdImageBoss();
        int idRessourceBg = this.getResources().getIdentifier(nameBackground, "drawable", this.getPackageName());
        System.out.println(idRessourceBg);
        this.background.setImageResource(idRessourceBg);

        this.simonView = findViewById(R.id.view_simon);

        this.attack = findViewById(R.id.attack_button);
        this.guard = findViewById(R.id.guard_button);
        this.escape = findViewById(R.id.escape_button);

        bossView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nbTap++;
                comboView.setText("Combo : "+nbTap);
            }
        });
        bossView.setEnabled(false);

        simonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nbTap++;
                comboSoinView.setText("Combo : "+nbTap);
            }
        });
        simonView.setEnabled(false);

        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attackAction();
            }
        });

        guard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soinAction();
            }
        });
        escape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active_Button(false);

                etatJeu.setText("Vous prenez la fuite !");
                etatJeu.setVisibility(View.VISIBLE);

                Timer t = new Timer(false);
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                //ce code sera exécuté après 2 secondes
                                etatJeu.setVisibility(View.GONE);
                                finish();
                            }
                        });
                    }
                }, 2000);

            }
        });

    }



    public void active_Button(boolean b){
        if(b){
            attack.setVisibility(View.VISIBLE);
            guard.setVisibility(View.VISIBLE);
            escape.setVisibility(View.VISIBLE);
        } else {
            attack.setVisibility(View.GONE);
            guard.setVisibility(View.GONE);
            escape.setVisibility(View.GONE);
        }
    }
    public void soinAction(){
        active_Button(false);
        Personnage p = combat.quiCommence();
        Timer simonTurn = new Timer(false);
        Timer bossTurn = new Timer(false);
        Timer resultBossTurn = new Timer(false);
        Timer endOfGame = new Timer(false);

        if(p == combat.getSimon()) {
            eclatViewSimon.setVisibility(View.VISIBLE);
            etatJeu.setText("Tapez vous vous-même pour augmenter l'efficacité du soin !");
            etatJeu.setVisibility(View.VISIBLE);
            comboSoinView.setVisibility(View.VISIBLE);
            simonView.setEnabled(true);

            simonTurn.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //ce code sera exécuté après 5 secondes
                            simonView.setEnabled(false);
                            eclatViewSimon.setVisibility(View.GONE);
                            long degat = combat.getSimon().seSoigne(nbTap);
                            pvSimon.setText(combat.getSimon().getPvActuel() + " / " + combat.getSimon().getPvMax());
                            etatJeu.setText("Vous vous êtes soigné de "+degat+" PV !");
                            bossTurn.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            //ce code sera exécuté après 2 secondes
                                            comboSoinView.setVisibility(View.GONE);
                                            comboSoinView.setText("Combo : 0");
                                            nbTap = 0;
                                            long[] l = combat.queFaitBoss();
                                            if (l[0] == 0) {
                                                etatJeu.setText(combat.getBoss().getNom() + " s'est soigné de "+l[1]+" PV");
                                            } else {
                                                etatJeu.setText(combat.getBoss().getNom() + " vous a infligé "+l[1]+" dommages");
                                            }
                                            pvSimon.setText(combat.getSimon().getPvActuel()+" / "+combat.getSimon().getPvMax());
                                            pvBoss.setText(combat.getBoss().getPvActuel() + " / " + combat.getBoss().getPvMax());
                                            resultBossTurn.schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    runOnUiThread(new Runnable() {
                                                        public void run() {
                                                            //ce code sera exécuté après 2 secondes
                                                            if (combat.gameover()) {
                                                                simonView.setVisibility(View.GONE);
                                                                etatJeu.setText("Vous êtes mort ! GAME OVER !");
                                                                endOfGame.schedule(new TimerTask() {
                                                                    @Override
                                                                    public void run() {
                                                                        runOnUiThread(new Runnable() {
                                                                            public void run() {
                                                                                //ce code sera exécuté après 2 secondes
                                                                                finish();
                                                                            }
                                                                        });
                                                                    }
                                                                    }, 3000);
                                                            } else {
                                                                active_Button(true);
                                                                etatJeu.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });
                                                }
                                                }, 2000);
                                            }
                                        });
                                    }
                                    }, 2000);
                        }
                    });
                }
            }, 5000);
        } else {
            long[] l = combat.queFaitBoss();
            if (l[0] == 0) {
                etatJeu.setText(combat.getBoss().getNom() + " s'est soigné de "+l[1]+" PV");
            } else {
                etatJeu.setText(combat.getBoss().getNom() + " vous a infligé "+l[1]+" dommages");
            }
            etatJeu.setVisibility(View.VISIBLE);
            pvSimon.setText(combat.getSimon().getPvActuel()+" / "+combat.getSimon().getPvMax());
            pvBoss.setText(combat.getBoss().getPvActuel() + " / " + combat.getBoss().getPvMax());
            resultBossTurn.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //ce code sera exécuté après 2 secondes
                            if (combat.gameover()) {
                                simonView.setVisibility(View.GONE);
                                etatJeu.setText("Vous êtes mort ! GAME OVER !");
                                endOfGame.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                //ce code sera exécuté après 2 secondes
                                                finish();
                                            }
                                        });
                                    }
                                }, 3000);
                            } else {
                                eclatViewSimon.setVisibility(View.VISIBLE);
                                etatJeu.setText("Tapez vous vous-même pour augmenter l'efficacité du soin !");
                                etatJeu.setVisibility(View.VISIBLE);
                                comboSoinView.setVisibility(View.VISIBLE);
                                simonView.setEnabled(true);
                                simonTurn.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                //ce code sera exécuté après 5 secondes
                                                simonView.setEnabled(false);
                                                eclatViewSimon.setVisibility(View.GONE);
                                                long degat = combat.getSimon().seSoigne(nbTap);
                                                pvSimon.setText(combat.getSimon().getPvActuel() + " / " + combat.getSimon().getPvMax());
                                                etatJeu.setText("Vous vous êtes soigné de "+degat+" PV !");
                                                endOfGame.schedule(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        runOnUiThread(new Runnable() {
                                                            public void run() {
                                                                //ce code sera exécuté après 3 secondes
                                                                comboSoinView.setVisibility(View.GONE);
                                                                comboSoinView.setText("Combo : 0");
                                                                nbTap = 0;
                                                                active_Button(true);
                                                                etatJeu.setVisibility(View.GONE);
                                                            }
                                                        });
                                                    }
                                                }, 3000);
                                            }
                                        });
                                    }
                                }, 5000);
                            }
                        }
                    });
                }
            }, 2000);
        }
    }


    public void attackAction(){
        active_Button(false);
        Personnage p = combat.quiCommence();
        Timer simonTurn = new Timer(false);
        Timer bossTurn = new Timer(false);
        Timer resultBossTurn = new Timer(false);
        Timer endOfGame = new Timer(false);

        if(p == combat.getSimon()) {
            etatJeu.setText("Tapez " + combat.getBoss().getNom() + " pour plus de dégât !");
            etatJeu.setVisibility(View.VISIBLE);
            comboView.setVisibility(View.VISIBLE);
            bossView.setEnabled(true);
            eclatViewBoss.setVisibility(View.VISIBLE);

            simonTurn.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //ce code sera exécuté après 5 secondes
                            bossView.setEnabled(false);
                            eclatViewBoss.setVisibility(View.GONE);
                            long degat = combat.getBoss().coupSubitPar(combat.getSimon(), nbTap);
                            pvBoss.setText(combat.getBoss().getPvActuel() + " / " + combat.getBoss().getPvMax());
                            etatJeu.setText("Vous avez infligé " + degat + " dommages à " + combat.getBoss().getNom());

                            if(combat.bossMort()){
                                bossTurn.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @SuppressLint("SetTextI18n")
                                            public void run() {
                                                //ce code sera exécuté après 2 secondes
                                                bossView.setVisibility(View.GONE);
                                                comboView.setVisibility(View.GONE);
                                                Equipment e = null;
                                                try {
                                                    e = combat.getRecompense();
                                                } catch (IOException ex) {
                                                    ex.printStackTrace();
                                                }
                                                if(e == null){
                                                    etatJeu.setText("Félicitation ! Vous avez vaincu "
                                                            +combat.getBoss().getNom()+
                                                            ", mais vous avez déjà reçu la récompense !");
                                                } else {
                                                    etatJeu.setText("Félicitation ! Vous avez vaincu "
                                                            +combat.getBoss().getNom()+", et vous recevez en récompense "
                                                            +e.getNom());
                                                }
                                                endOfGame.schedule(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        runOnUiThread(new Runnable() {
                                                            public void run() {
                                                                //ce code sera exécuté après 3 secondes
                                                                finish();
                                                            }
                                                        });
                                                    }
                                                }, 3000);
                                            }
                                        });
                                    }
                                }, 2000);
                            } else {
                                bossTurn.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                //ce code sera exécuté après 2 secondes
                                                comboView.setVisibility(View.GONE);
                                                comboView.setText("Combo : 0");
                                                nbTap = 0;
                                                long[] l = combat.queFaitBoss();
                                                if (l[0] == 0) {
                                                    etatJeu.setText(combat.getBoss().getNom() + " s'est soigné de "+l[1]+" PV");
                                                } else {
                                                    etatJeu.setText(combat.getBoss().getNom() + " vous a infligé "+l[1]+" dommages");
                                                }
                                                pvSimon.setText(combat.getSimon().getPvActuel()+" / "+combat.getSimon().getPvMax());
                                                pvBoss.setText(combat.getBoss().getPvActuel() + " / " + combat.getBoss().getPvMax());
                                                resultBossTurn.schedule(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        runOnUiThread(new Runnable() {
                                                            public void run() {
                                                                //ce code sera exécuté après 2 secondes
                                                                if (combat.gameover()) {
                                                                    simonView.setVisibility(View.GONE);
                                                                    etatJeu.setText("Vous êtes mort ! GAME OVER !");
                                                                    endOfGame.schedule(new TimerTask() {
                                                                        @Override
                                                                        public void run() {
                                                                            runOnUiThread(new Runnable() {
                                                                                public void run() {
                                                                                    //ce code sera exécuté après 2 secondes
                                                                                    finish();
                                                                                }
                                                                            });
                                                                        }
                                                                    }, 3000);
                                                                } else {
                                                                    active_Button(true);
                                                                    etatJeu.setVisibility(View.GONE);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }, 2000);
                                            }
                                        });
                                    }
                                }, 2000);
                            }
                        }
                    });
                }
            }, 5000);
        } else {
            long[] l = combat.queFaitBoss();
            if (l[0] == 0) {
                etatJeu.setText(combat.getBoss().getNom() + " s'est soigné de "+l[1]+" PV");
            } else {
                etatJeu.setText(combat.getBoss().getNom() + " vous a infligé "+l[1]+" dommages");
            }
            etatJeu.setVisibility(View.VISIBLE);
            pvSimon.setText(combat.getSimon().getPvActuel()+" / "+combat.getSimon().getPvMax());
            pvBoss.setText(combat.getBoss().getPvActuel() + " / " + combat.getBoss().getPvMax());
            resultBossTurn.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //ce code sera exécuté après 2 secondes
                            if (combat.gameover()) {
                                simonView.setVisibility(View.GONE);
                                etatJeu.setText("Vous êtes mort ! GAME OVER !");
                                endOfGame.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                //ce code sera exécuté après 2 secondes
                                                finish();
                                            }
                                        });
                                    }
                                }, 3000);
                            } else {
                                etatJeu.setText("Tapez " + combat.getBoss().getNom() + " pour plus de dégât !");
                                comboView.setVisibility(View.VISIBLE);
                                bossView.setEnabled(true);
                                eclatViewBoss.setVisibility(View.VISIBLE);
                                simonTurn.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                //ce code sera exécuté après 5 secondes
                                                bossView.setEnabled(false);
                                                eclatViewBoss.setVisibility(View.GONE);
                                                long degat = combat.getBoss().coupSubitPar(combat.getSimon(), nbTap);
                                                pvBoss.setText(combat.getBoss().getPvActuel() + " / " + combat.getBoss().getPvMax());
                                                etatJeu.setText("Vous avez infligé " + degat + " dommages à " + combat.getBoss().getNom());

                                                if(combat.bossMort()){
                                                    bossTurn.schedule(new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            runOnUiThread(new Runnable() {
                                                                @SuppressLint("SetTextI18n")
                                                                public void run() {
                                                                    //ce code sera exécuté après 2 secondes
                                                                    bossView.setVisibility(View.GONE);
                                                                    comboView.setVisibility(View.GONE);
                                                                    Equipment e = null;
                                                                    try {
                                                                        e = combat.getRecompense();
                                                                    } catch (IOException ex) {
                                                                        ex.printStackTrace();
                                                                    }
                                                                    if(e == null){
                                                                        etatJeu.setText("Félicitation ! Vous avez vaincu "
                                                                                +combat.getBoss().getNom()+
                                                                                ", mais vous avez déjà reçu la récompense !");
                                                                    } else {
                                                                        etatJeu.setText("Félicitation ! Vous avez vaincu "
                                                                                +combat.getBoss().getNom()+", et vous recevez en récompense "
                                                                                +e.getNom());
                                                                    }
                                                                    endOfGame.schedule(new TimerTask() {
                                                                        @Override
                                                                        public void run() {
                                                                            runOnUiThread(new Runnable() {
                                                                                public void run() {
                                                                                    //ce code sera exécuté après 3 secondes
                                                                                    finish();
                                                                                }
                                                                            });
                                                                        }
                                                                    }, 3000);
                                                                }
                                                            });
                                                        }
                                                    }, 2000);
                                                } else {
                                                    bossTurn.schedule(new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            runOnUiThread(new Runnable() {
                                                                public void run() {
                                                                    //ce code sera exécuté après 2 secondes
                                                                    comboView.setVisibility(View.GONE);
                                                                    comboView.setText("Combo : 0");
                                                                    nbTap = 0;
                                                                    active_Button(true);
                                                                    etatJeu.setVisibility(View.GONE);
                                                                }
                                                            });
                                                        }
                                                    }, 2000);
                                                }
                                            }
                                        });
                                    }
                                }, 5000);
                            }
                        }
                    });
                }
            }, 2000);
        }
    }
}