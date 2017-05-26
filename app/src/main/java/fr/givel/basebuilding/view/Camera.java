package fr.givel.basebuilding.view;

import fr.givel.basebuilding.utils.Coordinate;

/**
 * Created by lmg on 25/05/17.
 */

public class Camera {
    Coordinate coordinate;
    private float zoom;

    public Camera(float zoom) {
        this.zoom = zoom;
    }

    public float getZoom() {
        return zoom;
    }
}
