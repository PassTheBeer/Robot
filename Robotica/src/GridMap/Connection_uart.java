package GridMap;

import java.util.concurrent.Semaphore;

import com.pi4j.io.serial.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;

import java.io.IOException;
import java.util.Date;

public class Connection_uart {

    private static Connection_uart instance = new Connection_uart();
    private int connections;
    private Semaphore sem = new Semaphore(1);

    private Connection_uart() {

    }

    public static Connection_uart getInstance() {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doConnect();
        } finally {
            sem.release();
        }

    }

    private void doConnect() {

        synchronized (this) {
            connections++;
            System.out.println("Amount of connections: " + connections);
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connections--;
        }

    }

    public int getConnections() {
        return connections;


    }
}
