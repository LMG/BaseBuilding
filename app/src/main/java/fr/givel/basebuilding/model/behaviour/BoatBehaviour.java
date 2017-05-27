package fr.givel.basebuilding.model.behaviour;

import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.utils.Vect2D;


/**
 * Created by lmg on 27/05/17.
 */

public class BoatBehaviour extends Behaviour {
    private double maxSpeed;
    private double maxAcceleration;
    private Coordinate lastCoord;
    private Vect2D speed;
    private Vect2D acceleration;
    private Coordinate destination;
    private Coordinate start;
    private STATE state = STATE.IDLE;
    ;
    public BoatBehaviour(Coordinate coord, double maxSpeed, double acceleration) {
        this.lastCoord = new Coordinate(coord);
        this.destination = new Coordinate(coord);
        this.start = new Coordinate(coord);

        this.speed = new Vect2D();
        this.acceleration = new Vect2D();
        this.maxSpeed = maxSpeed;
        this.maxAcceleration = acceleration;
    }

    public BoatBehaviour(Coordinate coord) {
        this(coord, 1, 0.05);
    }

    @Override
    public Coordinate getNextCoordinate() {
        Vect2D trajToDo = Vect2D.createCart(lastCoord, destination);
        switch (state) {
            case IDLE:
                break;
            case ACCELERATING:
                acceleration = Vect2D.createPolar(maxAcceleration, trajToDo.getAngle());
                moveToPoint();

                trajToDo = Vect2D.createCart(lastCoord, destination);
                acceleration = Vect2D.createPolar(maxAcceleration, trajToDo.getAngle());
                double decelerationTime = (speed.getLength() / maxAcceleration);
                double decelerationLength = (decelerationTime * (decelerationTime + 1) * maxAcceleration / 2);

                if (trajToDo.getLength() <= decelerationLength) {
                    state = STATE.DECELERATING;
                }
                break;
            case DECELERATING:
                acceleration = Vect2D.createPolar(maxAcceleration, trajToDo.getAngle() + Math.PI);
                moveToPoint();

                trajToDo = Vect2D.createCart(lastCoord, destination);
                if (trajToDo.getLength() <= 10) {
                    state = STATE.IDLE;
                    speed.setLength(0);
                    acceleration.setLength(0);
                }
                break;
            default:
                break;
        }
        return lastCoord;
    }

    public void moveToPoint() {
        speed.add(acceleration);
        if (speed.getLength() > maxSpeed) {
            speed.setLength(maxSpeed);
        }

        lastCoord.x += speed.getX();
        lastCoord.y += speed.getY();
        lastCoord.rotation = speed.getAngle();
    }

    public void setDestination(Coordinate dest) {
        this.destination = new Coordinate(dest);
        this.start = new Coordinate(lastCoord);
        this.state = STATE.ACCELERATING;
    }

    private enum STATE {IDLE, ACCELERATING, DECELERATING}
}
