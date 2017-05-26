package fr.givel.basebuilding.model;

import java.util.ArrayList;
import java.util.List;

import fr.givel.basebuilding.view.Camera;

/**
 * Created by lmg on 25/05/17.
 * A world is a contains a number of 3D objects
 */

public class World {
    List<GameItem> gameItems;
    List<Camera> cameras;

    public World(List<GameItem> gameItems, Camera camera) {
        this.gameItems = gameItems;
        this.cameras = new ArrayList<Camera>();
        cameras.add(camera);
    }

    public List<GameItem> getGameItems() {
        return gameItems;
    }

    public void setGameItems(List<GameItem> gameItems) {
        this.gameItems = gameItems;
    }

    public Camera getCamera(int i) {
        return cameras.get(0);
    }
}
