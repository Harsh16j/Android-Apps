package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Progress extends AppCompatActivity {

    LinearLayout linearLayout;

    ///////////////////////////////////////////////////
    //Components of linear layout
    //tv:text view , tr:table row
    TextView tvThread1;
    //Variables for table 1
    TableLayout p1Table;
    TableRow p1_tr[];
    TextView p1tv[][];

    TextView tvThread2;
    //Variables for table 2
    TableLayout p2Table;
    TableRow p2_tr[];
    TextView p2tv[][];

    Button stopbtn;
    Button backbtn;
    /////////////////////////////////////////View variables of linear layout declared

    //gopher location variables
    int gpos;  //absolute value out of 100
    int gposi; //row number
    int gposj; //column number

    //Random variable
    Random rand;

    //Threading variables
    private final Handler mHandler = new Handler (Looper.getMainLooper());

    Thread t1;
    Handler t1Handler;
    Thread t2;
    Handler t2Handler;

    //UI thread response variables
    protected final int SUCCESS=0;
    protected final int NEAR_MISS=1;
    protected final int CLOSE_GUESS=2;
    protected final int COMPLETE_MISS=3;

    //No_of_guesses variable
    protected int p1Guess=1;
    protected int p2Guess=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        linearLayout=findViewById(R.id.main_layout);

        //Creating linear layout programmatically
        tvThread1=createTextView(tvThread1,"Thread 1");
        createTable1();
        tvThread2=createTextView(tvThread1,"Thread 2");
        createTable2();
        stopbtn=createButton(stopbtn,"Stop");
        backbtn=createButton(backbtn,"Back");
        ///////////////////////////////////////////////View Created

        //Choosing the location of the gopher
        rand=new Random();
        gpos=rand.nextInt(100)+1;
        gposi=posi(gpos);
        gposj=posj(gpos);

        // Filling the gopher cell with green color
        p1tv[gposi][gposj].setBackgroundColor(Color.parseColor("#7cfc00"));
        p2tv[gposi][gposj].setBackgroundColor(Color.parseColor("#7cfc00"));


        //Starting the threads
        t1=new Thread(new T1Runnable());
        t1.start();
        t2=new Thread(new T2Runnable());
        t2.start();

        //Stop button
        stopbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //stopping worker thread 1 looper
                t1Handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //this code will run in worker thread 1
                        Looper.myLooper().quit();
                    }
                });
                //stopping worker thread 2 looper
                t2Handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //this code will run in worker thread 1
                        Looper.myLooper().quit();
                    }
                });
            }
        });

        //Back button
        backbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    // t#Guess : random guess by thread #
    //prev_i, prev_j : i and j coordinates of the previous guess
    //new_i, new_j : i and j coordinates of the new guess
    //di, dj: range by which i and j coordinates can change, d stands for delta
    public class T1Runnable implements Runnable{
        int t1Guess;
        int prev_i;
        int prev_j;
        int new_i;
        int new_j;
        int di,dj;
        @Override
        public void run() {
            Looper.prepare();

            t1Handler=new Handler(Looper.myLooper()){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    // running on worker thread
                    super.handleMessage(msg);
                    switch(msg.what){
                        case SUCCESS:
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //below code runs on UI thread
                                    tvThread1.setText("Thread 1 Wins!!!");
                                    tvThread2.setText("Thread 2 Loses!!!");
                                }
                            });
                            //Below code runs on the worker thread 1
                            Looper.myLooper().quit();
                            t2Handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Below code runs on worker thread 2
                                    Looper.myLooper().quit();
                                }
                            });
                            break;
                        case NEAR_MISS:
                            prev_i=posi(t1Guess);
                            prev_j=posj(t1Guess);
                            di=2; dj=2;  //delta i and delta j
                            if (prev_i==0)
                                di=1;
                            if (prev_i==9)
                                di=-1;
                            if (prev_j==0)
                                dj=1;
                            if (prev_j==9)
                                dj=-1;
                            while(p1tv[posi(t1Guess)][posj(t1Guess)].getText().equals(" ")==false){
                                if (Math.abs(di)==2)
                                    new_i= prev_i + ( rand.nextInt(Math.abs(di)+1) - 1 );
                                else
                                    new_i= prev_i + ( rand.nextInt(Math.abs(di)+1) * di);
                                if (Math.abs(dj)==2)
                                    new_j= prev_j + ( rand.nextInt(Math.abs(dj)+1) - 1 );
                                else
                                    new_j= prev_j + ( rand.nextInt(Math.abs(dj)+1) * dj);
                                t1Guess=guessNumber(new_i,new_j);
                            }
                            sleepThread();// sleeping the thread
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //below code running on UI thread
                                    Message msg=Message.obtain();
                                    messagePrepareForWorker(t1Guess,msg);
                                    p1tv[posi(t1Guess)][posj(t1Guess)].setText(Integer.toString(p1Guess++));
                                    t1Handler.sendMessage(msg);
                                }
                            });

                            break;
                        case CLOSE_GUESS:
                            prev_i=posi(t1Guess);
                            prev_j=posj(t1Guess);
                            int max_di=2,max_dj=2;  //max delta i and delta j
                            int min_di=-2,min_dj=-2;  //min delta i and delta j
                            if (prev_i==0)
                                min_di=0;
                            if (prev_i==1)
                                min_di=-1;
                            if (prev_i==9)
                                max_di=0;
                            if (prev_i==8)
                                max_di=1;
                            if (prev_j==0)
                                min_dj=0;
                            if (prev_j==1)
                                min_dj=-1;
                            if (prev_j==9)
                                max_dj=0;
                            if (prev_j==8)
                                max_dj=1;
                            while(p1tv[posi(t1Guess)][posj(t1Guess)].getText().equals(" ")==false){
                                new_i=prev_i + (rand.nextInt(max_di-min_di+1) + min_di) ;
                                new_j=prev_j + (rand.nextInt(max_dj-min_dj+1) + min_dj) ;
                                t1Guess=guessNumber(new_i,new_j);
                            }
                            sleepThread();// sleeping the thread
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Below code running in UI thread
                                    Message msg=Message.obtain();
                                    messagePrepareForWorker(t1Guess,msg);
                                    p1tv[posi(t1Guess)][posj(t1Guess)].setText(Integer.toString(p1Guess++));
                                    t1Handler.sendMessage(msg);
                                }
                            });
                            break;
                        case COMPLETE_MISS:
                            t1Guess=rand.nextInt(100)+1;
                            while(p1tv[posi(t1Guess)][posj(t1Guess)].getText().equals(" ")==false){
                                t1Guess= rand.nextInt(100)+1;
                            }
                            sleepThread();// sleeping the thread
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Message msg=Message.obtain();
                                    messagePrepareForWorker(t1Guess,msg);
                                    p1tv[posi(t1Guess)][posj(t1Guess)].setText(Integer.toString(p1Guess++));
                                    t1Handler.sendMessage(msg);
                                }
                            });
                            break;
                    }
                }
            };

            //Guessing for the first time
            t1Guess=rand.nextInt(100)+1;

            sleepThread();// sleeping the thread

            //displaying the first guess on the table
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Message msg=Message.obtain();
                    messagePrepareForWorker(t1Guess,msg);
                    p1tv[posi(t1Guess)][posj(t1Guess)].setText(Integer.toString(p1Guess++));
                    t1Handler.sendMessage(msg);
                }
            });

            Looper.loop();
        }
        // To sleep the thread for 2 seconds
        void sleepThread(){
            try{
                Thread.sleep(2000);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    public class T2Runnable implements Runnable{
        int t2Guess;
        int prev_i;
        int prev_j;
        int new_i;
        int new_j;
        int di,dj;
        @Override
        public void run() {
            Looper.prepare();

            t2Handler=new Handler(Looper.myLooper()){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    // running on worker thread
                    super.handleMessage(msg);
                    switch(msg.what){
                        case SUCCESS:
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tvThread2.setText("Thread 2 Wins!!!");
                                    tvThread1.setText("Thread 1 Loses!!!");
                                }
                            });
                            //Below code runs on the worker thread 2
                            Looper.myLooper().quit();
                            t1Handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Below code runs on worker thread 21
                                    Looper.myLooper().quit();
                                }
                            });
                            break;
                        case NEAR_MISS:
                            prev_i=posi(t2Guess);
                            prev_j=posj(t2Guess);
                            di=2; dj=2;  //delta i and delta j
                            if (prev_i==0)
                                di=1;
                            if (prev_i==9)
                                di=-1;
                            if (prev_j==0)
                                dj=1;
                            if (prev_j==9)
                                dj=-1;
                            while(p2tv[posi(t2Guess)][posj(t2Guess)].getText().equals(" ")==false){
                                if (Math.abs(di)==2)
                                    new_i= prev_i + ( rand.nextInt(Math.abs(di)+1) - 1 );
                                else
                                    new_i= prev_i + ( rand.nextInt(Math.abs(di)+1) * di);
                                if (Math.abs(dj)==2)
                                    new_j= prev_j + ( rand.nextInt(Math.abs(dj)+1) - 1 );
                                else
                                    new_j= prev_j + ( rand.nextInt(Math.abs(dj)+1) * dj);
                                t2Guess=guessNumber(new_i,new_j);
                            }
                            sleepThread();// sleeping the thread
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //below code running on UI thread
                                    Message msg=Message.obtain();
                                    messagePrepareForWorker(t2Guess,msg);
                                    p2tv[posi(t2Guess)][posj(t2Guess)].setText(Integer.toString(p2Guess++));
                                    t2Handler.sendMessage(msg);
                                }
                            });
                            break;
                        case CLOSE_GUESS:
                            prev_i=posi(t2Guess);
                            prev_j=posj(t2Guess);
                            int max_di=2,max_dj=2;  //max delta i and delta j
                            int min_di=-2,min_dj=-2;  //min delta i and delta j
                            if (prev_i==0)
                                min_di=0;
                            if (prev_i==1)
                                min_di=-1;
                            if (prev_i==9)
                                max_di=0;
                            if (prev_i==8)
                                max_di=1;
                            if (prev_j==0)
                                min_dj=0;
                            if (prev_j==1)
                                min_dj=-1;
                            if (prev_j==9)
                                max_dj=0;
                            if (prev_j==8)
                                max_dj=1;
                            while(p2tv[posi(t2Guess)][posj(t2Guess)].getText().equals(" ")==false){
                                new_i=prev_i + (rand.nextInt(max_di-min_di+1) + min_di) ;
                                new_j=prev_j + (rand.nextInt(max_dj-min_dj+1) + min_dj) ;
                                t2Guess=guessNumber(new_i,new_j);
                            }
                            sleepThread();// sleeping the thread
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Below code running in UI thread
                                    Message msg=Message.obtain();
                                    messagePrepareForWorker(t2Guess,msg);
                                    p2tv[posi(t2Guess)][posj(t2Guess)].setText(Integer.toString(p2Guess++));
                                    t2Handler.sendMessage(msg);
                                }
                            });
                            break;
                        case COMPLETE_MISS:
                            t2Guess=rand.nextInt(100)+1;
                            while(p2tv[posi(t2Guess)][posj(t2Guess)].getText().equals(" ")==false){
                                t2Guess= rand.nextInt(100)+1;
                            }
                            sleepThread();// sleeping the thread
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Message msg=Message.obtain();
                                    messagePrepareForWorker(t2Guess,msg);
                                    p2tv[posi(t2Guess)][posj(t2Guess)].setText(Integer.toString(p2Guess++));
                                    t2Handler.sendMessage(msg);
                                }
                            });
                            break;
                    }
                }
            };

            //Guessing for the first time
            t2Guess=rand.nextInt(100)+1;

            sleepThread();// sleeping the thread

            //displaying the first guess on the table
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Message msg=Message.obtain();
                    messagePrepareForWorker(t2Guess,msg);
                    p2tv[posi(t2Guess)][posj(t2Guess)].setText(Integer.toString(p2Guess++));
                    t2Handler.sendMessage(msg);
                }
            });

            Looper.loop();
        }
        // To sleep the thread for 2 seconds
        void sleepThread(){
            try{
                Thread.sleep(2000);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    //adds the what field inside the message based on the distance of the guess from the gopher
    void messagePrepareForWorker(int tGuess,Message msg){
        // below code running on UI thread
        int d=distFromGopher(posi(tGuess),posj(tGuess));
        switch (d){
            case SUCCESS:
                msg.what=SUCCESS;
                break;
            case NEAR_MISS:
                msg.what=NEAR_MISS;
                break;
            case CLOSE_GUESS:
                msg.what=CLOSE_GUESS;
                break;
            case COMPLETE_MISS:
                msg.what=COMPLETE_MISS;
                break;
        }
    }
    // calculates the distance of the guess from the gopher
    int distFromGopher(int i,int j){
        int dist_x=Math.abs(gposi-i);
        int dist_y=Math.abs(gposj-j);
        if(dist_x==0 && dist_y==0){
            return SUCCESS;
        }
        if(dist_x<=1 && dist_y<=1){
            return NEAR_MISS;
        }
        if(dist_x<=2 && dist_y<=2){
            return CLOSE_GUESS;
        }
        return COMPLETE_MISS;
    }

    //returns the i coordinate of a guess
    int posi(int pos){
        return (pos-1)/10;
    }

    //returns the j coordinate of a guess
    int posj(int pos){
        return (pos-1)%10;
    }

    //returns the guess number from the i and j coordinates
    int guessNumber(int posi,int posj){
        return posi*10 + posj +1 ;
    }

    //creates and adds a button to the linear layout
    Button createButton(Button btn,String str){
        btn=new Button(this);
        btn.setText(str);
        btn.setTextSize(15);
        btn.setGravity(Gravity.CENTER_HORIZONTAL);
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        // setting margins
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(30, 10, 30, 0);
        ///////////////////////////////////
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(btn,layoutParams);

        return btn;
    }

    //creates and adds a text view to the linear layout
    TextView createTextView(TextView tv,String str){
        tv=new TextView(this);
        tv.setText(str);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setPadding(20,20,20,20);
        tv.setTextSize(40);
        linearLayout.addView(tv);
        return tv;
    }

    //creates and adds worker thread 1 progress table to the linear layout
    void createTable1(){
        p1Table=new TableLayout(this);
        p1_tr=new TableRow[10];
        p1tv=new TextView[10][10];

        for(int i=0;i<10;i++){
            p1_tr[i]=new TableRow(Progress.this);
            for(int j=0;j<10;j++){
                p1tv[i][j]=new TextView(Progress.this);
                p1tv[i][j].setText(" ");
                p1tv[i][j].setBackground(ContextCompat.getDrawable(this, R.drawable.table_border));
                p1tv[i][j].setWidth(80);
                p1tv[i][j].setHeight(60);
                p1tv[i][j].setGravity(Gravity.CENTER_HORIZONTAL);
                p1_tr[i].addView(p1tv[i][j]);
                p1_tr[i].setGravity(Gravity.CENTER_HORIZONTAL);
            }
            p1Table.addView(p1_tr[i]);
        }
        linearLayout.addView(p1Table);
    }

    //creates and adds worker thread 2 progress table to the linear layout
    void createTable2(){
        p2Table=new TableLayout(this);
        p2_tr=new TableRow[10];
        p2tv=new TextView[10][10];

        for(int i=0;i<10;i++){
            p2_tr[i]=new TableRow(Progress.this);
            for(int j=0;j<10;j++){
                p2tv[i][j]=new TextView(Progress.this);
                p2tv[i][j].setText(" ");
                p2tv[i][j].setBackground(ContextCompat.getDrawable(this, R.drawable.table_border));
                p2tv[i][j].setWidth(80);
                p2tv[i][j].setHeight(60);
                p2tv[i][j].setGravity(Gravity.CENTER_HORIZONTAL);
                p2_tr[i].addView(p2tv[i][j]);
                p2_tr[i].setGravity(Gravity.CENTER_HORIZONTAL);
            }
            p2Table.addView(p2_tr[i]);
        }
        linearLayout.addView(p2Table);
    }

}