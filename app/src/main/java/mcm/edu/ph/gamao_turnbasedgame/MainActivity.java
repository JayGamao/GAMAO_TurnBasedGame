package mcm.edu.ph.gamao_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView ak47MagCapacity, ak47FireRate, ak47Gun, m1a4Gun, m1a4FireRate, m1a4MagCapacity, m1a4Hp, ak47Hp, txtcombatLog;
    ImageButton btnFlashBang;
    Button btnActivate;

    String hp = "HP: ";
    String gunName = "Gun: ";
    String fireRate = "Fire Rate: ";
    String magCap = "Mag Cap: ";
    String heroGunName = "Ak47";


    int Ak47FireRate = 600;
    int Ak47MagCap = 30;
    int Ak47MinDmg = 50;
    int Ak47MaxDmg = 200;
    int heroHp = 2000;


    String enemyGunName = "M1A4";
    int M1A4FireRate = 700;
    int M1A4MagCap = 30;
    int M1A4MinDmg = 35;
    int M1A4MaxDmg = 150;
    int enemyHp = 2000;
    int magDown = 1;
    int minMag = 5;
    int maxMag = 7;

    int turnNumber = 1;

    boolean disabledstatus = false;
    int statuscounter = 0;
    int buttoncounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActivate = findViewById(R.id.btnActivate);

        ak47Gun = findViewById(R.id.heroGun);
        ak47FireRate = (findViewById(R.id.heroFireRate));
        ak47MagCapacity = findViewById(R.id.heroMagCapacity);
        ak47Hp = findViewById(R.id.heroHp);
        btnFlashBang = findViewById(R.id.flashBang);

        m1a4Gun = findViewById(R.id.enemyGun);
        m1a4FireRate = findViewById(R.id.enemyFireRate);
        m1a4MagCapacity = findViewById(R.id.enemyMagCapacity);
        m1a4Hp = findViewById(R.id.enemyHp);

        txtcombatLog = findViewById(R.id.combatLog);


        ak47Gun.setText(String.valueOf(gunName) + (String.valueOf(heroGunName)));
        ak47FireRate.setText(String.valueOf(fireRate) + (String.valueOf(Ak47FireRate)));
        ak47MagCapacity.setText(String.valueOf(magCap) + (String.valueOf(Ak47MagCap)));
        ak47Hp.setText(String.valueOf(hp) + (String.valueOf(heroHp)));


        m1a4Gun.setText(String.valueOf(gunName) + (String.valueOf(enemyGunName)));
        m1a4FireRate.setText(String.valueOf(fireRate) + (String.valueOf(M1A4FireRate)));
        m1a4MagCapacity.setText(String.valueOf(magCap) + (String.valueOf(M1A4MagCap)));
        m1a4Hp.setText(String.valueOf(hp) + (String.valueOf(enemyHp)));


        btnActivate.setOnClickListener(this);
        btnFlashBang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        Random randomizer = new Random();

        int magprobability = randomizer.nextInt(maxMag - minMag) + minMag;
        int ak47probability = randomizer.nextInt(Ak47MaxDmg - Ak47MinDmg) + Ak47MinDmg;
        int m1a4probability = randomizer.nextInt(M1A4MaxDmg - M1A4MinDmg) + M1A4MinDmg;

        if (turnNumber % 2 != 1){
            btnFlashBang.setEnabled(false);
        }
        else if (turnNumber % 2 == 1) {
            btnFlashBang.setEnabled(true);
        }
        if(buttoncounter>0){
            btnFlashBang.setEnabled(false);
            buttoncounter--;
        }
        else if(buttoncounter==0) {
            btnFlashBang.setEnabled(true);
        }

        switch (v.getId()) {
            case R.id.flashBang:

                turnNumber++;
                txtcombatLog.setText("Blind enemy for 4 shots");
                btnActivate.setText("Use Flash Bang");
                disabledstatus = true;
                statuscounter = 4;

                if (enemyHp < 0) {
                    txtcombatLog.setText(" Headshot!, you've done well soldier, your gun : " + (String.valueOf(heroGunName) + " have leveled up and has now become stronger!, continue the adventure"));
                    heroHp = 2000;
                    enemyHp = 2000;
                    Ak47MagCap = 30;
                    turnNumber = 1;
                    btnActivate.setText("Kill some more");
                }
                buttoncounter=12;


                break;

            case R.id.btnActivate:

                if (turnNumber % 2 == 1) {

                    enemyHp = enemyHp - ak47probability;
                    turnNumber++;
                    m1a4Hp.setText(String.valueOf(hp) + (String.valueOf(enemyHp)));
                    Ak47MagCap = Ak47MagCap - magprobability;
                    ak47MagCapacity.setText(String.valueOf(magCap) + (String.valueOf(Ak47MagCap)));
                    btnActivate.setText("Defend");
                    txtcombatLog.setText(String.valueOf(heroGunName) + " dealt " + ak47probability + " damage to the enemy.");


                    if (enemyHp <= 0) {
                        txtcombatLog.setText(" Headshot!, you've done well soldier, your gun : " + (String.valueOf(heroGunName) + " have leveled up and has now become stronger!, continue the adventure"));
                        heroHp = 2000;
                        enemyHp = 2000;
                        Ak47MagCap = 30;
                        turnNumber = 1;
                        statuscounter = 0;
                        btnActivate.setText("Kill some more");

                    }
                    if (Ak47MagCap <= 0) {
                        txtcombatLog.setText("You are out of Ammo, Reload Now! ");
                        Ak47MagCap = 30;
                        btnActivate.setText("Reload and Defend!");

                    }
                    if (statuscounter>0) {
                        statuscounter--;
                        if (statuscounter == 0) {
                            disabledstatus = false;
                        }
                    }
                    buttoncounter--;
                }
                else if (turnNumber % 2 != 1) {


                    if (disabledstatus== true) {
                        txtcombatLog.setText("Enemy is blinded for " + String.valueOf(statuscounter)+" shots and dealt" + (String.valueOf(heroGunName) + " dealt " + ak47probability + " damage to the enemy."));
                        statuscounter--;
                        enemyHp = enemyHp - ak47probability;
                        m1a4Hp.setText(String.valueOf(hp) + (String.valueOf(enemyHp)));
                        Ak47MagCap = Ak47MagCap - magprobability;

                        if (statuscounter==0){
                            disabledstatus=false;
                        }
                    }
                    else{

                        heroHp = heroHp - m1a4probability;
                        turnNumber++;
                        ak47Hp.setText(String.valueOf(hp) + (String.valueOf(heroHp)));
                        M1A4MagCap = M1A4MagCap - magprobability;
                        m1a4MagCapacity.setText(String.valueOf(magCap) + (String.valueOf(M1A4MagCap)));
                        txtcombatLog.setText(String.valueOf(enemyGunName) + " dealt " + m1a4probability + " damage to the enemy.");
                        btnActivate.setText("Fire Again");

                        if (heroHp <= 0) {
                            txtcombatLog.setText(" Do you copy? I repeat do you copy? ERROR! ERROR! ERROR! ");
                            heroHp = 2000;
                            enemyHp = 2000;
                            turnNumber = 1;
                            statuscounter = 0;
                            btnActivate.setText("Take Revenge");
                        }
                        if (M1A4MagCap <= 0) {
                            M1A4MagCap = 30;
                        }
                        buttoncounter--;
                    }
                    break;


                }
        }
    }
}
