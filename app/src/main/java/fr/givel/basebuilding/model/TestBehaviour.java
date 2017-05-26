package fr.givel.basebuilding.model;

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
    Coordinate getNextCoordinate() {
        return new Coordinate(++(lastCoord.x), ++(lastCoord.y), 0, ++(lastCoord.rotation));
    }
}
