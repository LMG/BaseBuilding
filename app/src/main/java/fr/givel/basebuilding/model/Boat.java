package fr.givel.basebuilding.model;

import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.view.GameItemView;

/**
 * Created by lmg on 28/05/17.
 */

public class Boat extends MovingGameItem {
    public Boat(Coordinate coordinate, GameItemView view) {
        this(coordinate, view, 10, 0.01);
    }

    public Boat(Coordinate coordinate, GameItemView view, double maxSpeed, double acceleration) {
        super(coordinate, view, maxSpeed, acceleration);
    }
}
