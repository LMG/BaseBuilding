package fr.givel.basebuilding.model.behaviour;

import fr.givel.basebuilding.utils.Coordinate;

/**
 * Created by lmg on 27/05/17.
 */

public class TestBehaviour extends Behaviour {
    private Coordinate lastCoord;

    public TestBehaviour() {
        this.lastCoord = new Coordinate();
    }

    @Override
    public void step() {
        item.setCoordinate(new Coordinate(++(lastCoord.x), ++(lastCoord.y), 0, ++(lastCoord.rotation)));
    }
}
