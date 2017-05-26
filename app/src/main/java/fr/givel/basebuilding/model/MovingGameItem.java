package fr.givel.basebuilding.model;

import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.view.GameItemView;

/**
 * Created by lmg on 27/05/17.
 */

public class MovingGameItem extends GameItem {
    private Behaviour behaviour;

    public MovingGameItem(Coordinate coordinate, GameItemView view, Behaviour behaviour) {
        super(coordinate, view);

        this.behaviour = behaviour;
    }

    public void setBehaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
    }

    public void updatePosition() {
        this.coordinate = behaviour.getNextCoordinate();
    }
}
