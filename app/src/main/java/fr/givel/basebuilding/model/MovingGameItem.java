package fr.givel.basebuilding.model;

import fr.givel.basebuilding.model.behaviour.Behaviour;
import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.utils.Vect2D;
import fr.givel.basebuilding.view.GameItemView;

/**
 * Created by lmg on 27/05/17.
 */

public class MovingGameItem extends GameItem {
    private double acceleration;
    private Behaviour behaviour;
    private Vect2D speed = new Vect2D();
    private double maxSpeed;


    public MovingGameItem(Coordinate coordinate, GameItemView view, double maxSpeed, double acceleration) {
        super(coordinate, view);
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
        behaviour.setItem(this);
    }

    public void computeMovement(Vect2D acceleration) {
        speed.add(acceleration);
        if (speed.getLength() > maxSpeed) {
            speed.setLength(maxSpeed);
        }
        computeMovement();
    }

    public void computeMovement() {
        coordinate.x += speed.getX();
        coordinate.y += speed.getY();
        if (speed.getLength() > 0)
            coordinate.rotation = speed.getAngle();
    }

    public Vect2D getSpeed() {
        return speed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void step() {
        behaviour.step();
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
}
