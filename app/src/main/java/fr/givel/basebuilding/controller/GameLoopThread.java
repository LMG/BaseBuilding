package fr.givel.basebuilding.controller;

import fr.givel.basebuilding.view.View3D;

/**
 * Created by lmg on 25/05/17.
 */

public class GameLoopThread extends Thread {
    private boolean running;

    public GameLoopThread(View3D view) {

    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
