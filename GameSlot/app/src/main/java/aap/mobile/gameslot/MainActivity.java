package aap.mobile.gameslot;

import static aap.mobile.gameslot.R.drawable.slot_1;
import static aap.mobile.gameslot.R.drawable.slot_2;
import static aap.mobile.gameslot.R.drawable.slot_3;
import static aap.mobile.gameslot.R.drawable.slot_4;
import static aap.mobile.gameslot.R.drawable.slot_5;
import static aap.mobile.gameslot.R.drawable.slot_6;
import static aap.mobile.gameslot.R.drawable.slot_7;
import static aap.mobile.gameslot.R.drawable.slot_8;
import static aap.mobile.gameslot.R.drawable.slot_9;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivSlot1;
    private ImageView ivSlot2;
    private ImageView ivSlot3;
    private ArrayList<Integer> arImage;
    private Thread thread1;
    private Thread thread2;
    private Thread thread3;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btStartStop).setOnClickListener(this);

        arImage = new ArrayList<Integer>();
        arImage.add(slot_1);
        arImage.add(slot_2);
        arImage.add(slot_3);
        arImage.add(slot_4);
        arImage.add(slot_5);
        arImage.add(slot_6);
        arImage.add(slot_7);
        arImage.add(slot_8);
        arImage.add(slot_9);

        this.ivSlot1 = (ImageView) findViewById(R.id.ivSlot1);
        this.ivSlot2 = (ImageView) findViewById(R.id.ivSlot2);
        this.ivSlot3 = (ImageView) findViewById(R.id.ivSlot3);
        this.handler = new Handler(Looper.getMainLooper());

        // Set initial background resource for each ImageView
        ivSlot1.setBackgroundResource(slot_6);
        ivSlot2.setBackgroundResource(slot_6);
        ivSlot3.setBackgroundResource(slot_6);

        createThread(1, ivSlot1);
        createThread(2, ivSlot2);
        createThread(3, ivSlot3);
    }

    private void createThread(final int slotNumber, final ImageView imageView) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Random rand = new Random();
                    while (!Thread.currentThread().isInterrupted()) {
                        int index = rand.nextInt(9);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setBackgroundResource(arImage.get(index));
                            }
                        });
                        Thread.sleep(200);
                    }
                } catch (InterruptedException e) {
                    // Thread interrupted, exit gracefully
                }
            }
        });

        if (slotNumber == 1) {
            thread1 = thread;
        } else if (slotNumber == 2) {
            thread2 = thread;
        } else if (slotNumber == 3) {
            thread3 = thread;
        }
    }

    @Override
    public void onClick(View view) {
        if (thread1 != null && thread1.isAlive()) {
            thread1.interrupt();
        } else if (thread2 != null && thread2.isAlive()) {
            thread2.interrupt();
        } else if (thread3 != null && thread3.isAlive()) {
            thread3.interrupt();
        } else {
            createThread(1, ivSlot1);
            createThread(2, ivSlot2);
            createThread(3, ivSlot3);

            thread1.start();
            thread2.start();
            thread3.start();
        }
    }
}
