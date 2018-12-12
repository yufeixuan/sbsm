package com.snsprj.callback;

/**
 * @author SKH
 * @date 2018-10-26 10:50
 **/
public class People {

    private Printer printer = new Printer();

    public void doPrint(Callback callback, String content){

        new Thread(new Runnable() {
            @Override
            public void run() {

                printer.print(callback, content);
            }
        }).start();
    }
}
