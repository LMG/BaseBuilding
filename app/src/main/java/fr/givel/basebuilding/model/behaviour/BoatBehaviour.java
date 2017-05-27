package fr.givel.basebuilding.model.behaviour;

import android.util.Log;

import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.utils.Vect2D;


/**
 * Created by lmg on 27/05/17.
 */

public class BoatBehaviour extends Behaviour {
    private final String TAG = "BoatBehaviour";
    private double maxSpeed = 0.5;
    private double maxAcceleration = 0.01;
    private Coordinate lastCoord;
    //private Vect2D movement;
    private Vect2D speed;
    private Vect2D acceleration;
    private Coordinate destination;
    private Coordinate start;

    public BoatBehaviour() {
        this.lastCoord = new Coordinate();
        //this.movement = new Vect2D();
        this.speed = new Vect2D();
        this.acceleration = new Vect2D();
        this.destination = new Coordinate(100, 100, 0, 0);
        this.start = new Coordinate(0, 0, 0, 0);
    }

    @Override
    public Coordinate getNextCoordinate() {
        update();

        Log.d(TAG, "" + lastCoord.x + " " + lastCoord.y + "spe" + speed + "acc" + acceleration);

        return lastCoord;
    }

    public void update() {
        if (!lastCoord.vincinityOf(destination, 10)) {
            moveToPoint(destination);
        }
    }

    public void moveToPoint(Coordinate destination) {
        lastCoord.x += speed.getX();//movement.getX();
        lastCoord.y += speed.getY();//movement.getY();
        lastCoord.rotation = speed.getAngle();

        //movement.add(speed);

        speed.add(acceleration);
        if (speed.getLength() > maxSpeed) {
            speed.setLength(maxSpeed);
        }

        Vect2D traj = Vect2D.createCart(destination.x, destination.y);
        traj.sub(Vect2D.createCart(start.x, start.y));
        Log.d(TAG, "traj " + traj);

        Vect2D trajToDo = Vect2D.createCart(destination.x, destination.y);
        trajToDo.sub(Vect2D.createCart(lastCoord.x, lastCoord.y));
        Log.d(TAG, "trajToDo " + trajToDo);

        if (trajToDo.getLength() < traj.getLength() / 3) {
            acceleration = Vect2D.createPolar(-maxAcceleration, trajToDo.getAngle());
            Log.d(TAG, "trajend");
        } else if (trajToDo.getLength() > 2 * (traj.getLength() / 3)) {
            acceleration = Vect2D.createPolar(maxAcceleration, trajToDo.getAngle());
            Log.d(TAG, "trajbeg " + acceleration);
        } else {
            acceleration.setLength(0);
            Log.d(TAG, "trajmid");
        }
    }
}
