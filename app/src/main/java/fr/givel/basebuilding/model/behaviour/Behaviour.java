package fr.givel.basebuilding.model.behaviour;

import fr.givel.basebuilding.model.MovingGameItem;

/**
 * Created by lmg on 27/05/17.
 */

public abstract class Behaviour {
    protected MovingGameItem item;

    public abstract void step();

    public void setItem(MovingGameItem item) {
        this.item = item;
    }
}
