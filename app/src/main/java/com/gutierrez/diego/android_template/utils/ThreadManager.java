package com.gutierrez.diego.android_template.utils;

import android.os.Handler;
import android.os.Message;

import com.gutierrez.diego.android_template.calls.home.GetUsersHomeCall;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HandshakeCompletedListener;

/**
 * Created by diego on 24-10-16.
 */
public class ThreadManager extends Thread {

    Callable call;
    Handler handler;
    ExecutorService executor = Executors.newFixedThreadPool(1);

    public ThreadManager(Callable call, Handler handler) {
        this.call = call;
        this.handler = handler;
    }
    public void run() {

        try {
            Future<String> future = executor.submit(call);
            String response = future.get();

            Message msg = new Message();
            msg.obj = response;
            handler.sendMessage(msg);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
