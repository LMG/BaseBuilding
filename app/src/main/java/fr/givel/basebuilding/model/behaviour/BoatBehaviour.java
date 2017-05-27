package fr.givel.basebuilding.model.behaviour;

import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.utils.Vect2D;


/**
 * Created by lmg on 27/05/17.
 */

public class BoatBehaviour extends Behaviour {
    private final String TAG = "BoatBehaviour";
    private double maxSpeed;
    private double maxAcceleration;
    private Coordinate lastCoord;
    private Vect2D speed;
    private Vect2D acceleration;
    private Coordinate destination;
    private Coordinate start;

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
        if (!lastCoord.vincinityOf(destination, 2)) {
            moveToPoint(destination);
        }
        return lastCoord;
    }

    public void moveToPoint(Coordinate destination) {
        lastCoord.x += speed.getX();
        lastCoord.y += speed.getY();
        lastCoord.rotation = speed.getAngle();

        speed.add(acceleration);
        if (speed.getLength() > maxSpeed) {
            speed.setLength(maxSpeed);
        }

        Vect2D traj = Vect2D.createCart(destination.x, destination.y);
        traj.sub(Vect2D.createCart(start.x, start.y));

        Vect2D trajToDo = Vect2D.createCart(destination.x, destination.y);
        trajToDo.sub(Vect2D.createCart(lastCoord.x, lastCoord.y));


        double decelerationTime = (speed.getLength() / acceleration.getLength());
        double decelerationLength = decelerationTime * (decelerationTime + 1) / 2;

        if (trajToDo.getLength() > decelerationLength) {
            acceleration = Vect2D.createPolar(maxAcceleration, trajToDo.getAngle());
        } else {
            acceleration = Vect2D.createPolar(-maxAcceleration, trajToDo.getAngle());
        }
    }

    public void setDestination(Coordinate dest) {
        this.destination = new Coordinate(dest);
        this.start = new Coordinate(lastCoord);
    }
}
