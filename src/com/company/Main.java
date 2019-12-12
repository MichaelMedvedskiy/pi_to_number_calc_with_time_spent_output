package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private static final int SCALE = 10000;
    private static final int ARRINIT = 2000;

    private static int nFinishedThreads=0;

    private static int instanceCounter=1;

    private static long tsStart;
    private static long tsFinish;

    private static void markFinished(int nThreads){

        if(++nFinishedThreads >=nThreads){
            allFinishedGetTime();
        }

    }

    public static void allFinishedGetTime(){
        tsFinish = new Date().getTime();
        long strDate = tsFinish-tsStart;
        System.out.println("Calculation took : "+strDate + " milliseconds!");
    }

    public String pi_digits(int digits){
        StringBuffer pi = new StringBuffer();
        int[] arr = new int[digits + 1];
        int carry = 0;

        for (int i = 0; i <= digits; ++i)
            arr[i] = ARRINIT;

        for (int i = digits; i > 0; i-= 14) {
            int sum = 0;
            for (int j = i; j > 0; --j) {
                sum = sum * j + SCALE * arr[j];
                arr[j] = sum % (j * 2 - 1);
                sum /= j * 2 - 1;
            }

            pi.append(String.format("%04d", carry + sum / SCALE));
            carry = sum % SCALE;
        }
        return pi.toString();
    }

    private  void threadPi(int howManyTimes,int nThreads){


        for (int i = 0; i < nThreads ; i++) {
            new Thread(()-> {
                //System.out.println( howManyTimes/nThreads);
                for (int j = 0; j < howManyTimes/nThreads; j++) {

                    calcAndPrintPi(Main.instanceCounter++);
                }
                markFinished(nThreads);

            }).start();
        }
    }

    public  void calcAndPrintPi(int callN){
        //int n = args.length > 0 ? Integer.parseInt(args[0]) : 100;
        int n=100;
        //so it's heavier, there will be 2 calculations
        String s = "This is the call #"+callN+" ; "+ String.valueOf( new Main().pi_digits(n)).substring(0,1)+"."+String.valueOf(new  Main().pi_digits(n)).substring(1,32);
        //System.out.println("This is the call #"+callN+" ; "+ String.valueOf( Main.pi_digits(n)).substring(0,1)+"."+String.valueOf( Main.pi_digits(n)).substring(1,32));
    }

    public static void main(String[] args) {
        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        Main.tsStart = new Date().getTime();
        //calcAndPrintPi(1);
        new Main().threadPi(Integer.parseInt( args[0]),Integer.parseInt( args[1]));
        //new Main().threadPi(100000,32);

    }
}