package fr.givel.basebuilding.model.behaviour;

import fr.givel.basebuilding.model.MovingGameItem;
import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.utils.Vect2D;


/**
 * Created by lmg on 27/05/17.
 */

public class BoatBehaviour extends Behaviour {
    private final MovingGameItem item;
    private STATE state = STATE.IDLE;
    private Coordinate destination;

    public BoatBehaviour(MovingGameItem item) {
        this.item = item;
    }

    @Override
    public void step() {
        Coordinate itemCoordinate = item.getCoordinate();
        Vect2D trajToDo;
        Vect2D acceleration;
        double itemAcceleration = item.getAcceleration();
        Vect2D speed = item.getSpeed();
        switch (state) {
            case ACCELERATING:
                trajToDo = Vect2D.createFromCoordinates(itemCoordinate, destination);
                acceleration = Vect2D.createPolar(itemAcceleration, trajToDo.getAngle());

                double decelerationTime = (speed.getLength() / itemAcceleration);
                double decelerationLength = (decelerationTime * (decelerationTime + 1) * itemAcceleration / 2);

                if (trajToDo.getLength() <= decelerationLength) {
                    state = STATE.DECELERATING;
                }
                break;
            case DECELERATING:
                trajToDo = Vect2D.createFromCoordinates(itemCoordinate, destination);
                acceleration = Vect2D.createPolar(itemAcceleration, trajToDo.getAngle() + Math.PI);

                if (trajToDo.getLength() <= 10) {
                    state = STATE.IDLE;
                    speed.setLength(0);
                    acceleration.setLength(0);
                }
                break;
            case IDLE:
            default:
                acceleration = new Vect2D();
                break;
        }

        item.computeMovement(acceleration);
    }

    public void setDestination(Coordinate dest) {
        this.destination = new Coordinate(dest);
        this.state = STATE.ACCELERATING;
    }

    private enum STATE {IDLE, ACCELERATING, DECELERATING}
}
