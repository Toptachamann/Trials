package com.chudakov.parcs.java;

import parcs.*;

public class Main {
    public static void main(String[] args) {
        task curtask = new task();
        curtask.addJarFile("Coloring.jar");

        AMInfo info = new AMInfo(curtask, null);
        point p = info.createPoint();
        channel c = p.createChannel();
        p.execute("Runner");
        c.write(15);

        System.out.println("Waiting for result...");
        System.out.println("Result: " + c.readLong());
        curtask.end();
    }

}
