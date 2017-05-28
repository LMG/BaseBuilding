package fr.givel.basebuilding.model.behaviour;

import android.util.Log;

import fr.givel.basebuilding.model.MovingGameItem;
import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.utils.Vect2D;


/**
 * Created by lmg on 27/05/17.
 */

public class BetterBoatBehaviour extends Behaviour {
    private static final String TAG = "BetterBoatBehaviour";
    private final MovingGameItem item;
    private STATE state = STATE.IDLE;
    private Coordinate destination;
    private double turnAngle = Math.PI / 4;

    public BetterBoatBehaviour(MovingGameItem item) {
        this.item = item;
    }

    @Override
    public void step() {
        Coordinate itemCoordinate = item.getCoordinate();
        Vect2D trajToDo;
        Vect2D acceleration = new Vect2D();
        double itemAcceleration = item.getAcceleration();
        Vect2D speed = item.getSpeed();
        switch (state) {
            case START: //see if we can reach the point
                Log.d(TAG, "START");
                if (canReach(destination))
                    state = STATE.TURN;
                else if (!canReach(destination))
                    state = STATE.MOVE;
                break;
            case MOVE: //we can't reach the point, move forward accelerating until we can
                Log.d(TAG, "MOVE");
                if (canReach(destination)) {
                    acceleration = new Vect2D();
                    state = STATE.TURN;
                    break;
                } else {
                    acceleration = Vect2D.createPolar(itemAcceleration, item.getOrientation());
                    break;
                }
            case TURN://we can reach the point, make sure we are facing the correct direction
                Log.d(TAG, "TURN");
                if (Vect2D.createFromCoordinates(item.getCoordinate(), destination).getAngle() == item.getSpeed().getAngle()) {
                    state = STATE.ACCELERATING;
                    acceleration = new Vect2D();
                    break;
                } else if (item.getSpeed().getLength() > 0.1) {
                    //TODO: turn using the correct side
                    acceleration = (new Vect2D(item.getSpeed())).turn(Math.PI / 2);
                    break;
                } else // if the speed is too slow, accelerate a little bit before turning
                {
                    acceleration = Vect2D.createPolar(item.getAcceleration(), item.getOrientation());
                    break;
                }
            case ACCELERATING://once we are facing the point, accelerate as much as we can
                Log.d(TAG, "ACCELERATING");
                trajToDo = Vect2D.createFromCoordinates(itemCoordinate, destination);
                acceleration = Vect2D.createPolar(itemAcceleration, item.getSpeed().getAngle());

                double decelerationTime = (speed.getLength() / itemAcceleration);
                double decelerationLength = (decelerationTime * (decelerationTime + 1) * itemAcceleration / 2);

                if (trajToDo.getLength() <= decelerationLength) {
                    state = STATE.DECELERATING;
                }
                break;
            case DECELERATING://we are too close, we need to start slowing down
                Log.d(TAG, "DECELERATING");
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
                Log.d(TAG, "IDLE");
                acceleration = new Vect2D();
                break;
        }

        item.computeMovement(acceleration);
    }

    private boolean canReach(Coordinate c) {
        double turningRadius = -item.getSpeed().getLength() * Math.sin(turnAngle) / (4 * Math.pow(Math.sin(turnAngle / 2), 2));
        Log.d(TAG, "speed " + item.getSpeed().getLength() + "turnAngle " + turnAngle + "turnradius " + turningRadius);
        Vect2D vectA = (new Vect2D(item.getSpeed())).add(Vect2D.createCart(item.getSpeed().getX() / 2, item.getSpeed().getY() / 2));
        Coordinate relativeCenterLeft = vectA.add(Vect2D.createPolar(turningRadius, item.getSpeed().getAngle() + Math.PI / 2)).toCoordinate();
        Coordinate relativeCenterRight = vectA.add(Vect2D.createPolar(turningRadius, item.getSpeed().getAngle() - Math.PI / 2)).toCoordinate();

        return !(Vect2D.createFromCoordinates(item.getCoordinate(), relativeCenterLeft).getLength() < turningRadius
                || Vect2D.createFromCoordinates(item.getCoordinate(), relativeCenterRight).getLength() < turningRadius);

    }

    public void setDestination(Coordinate dest) {
        this.destination = new Coordinate(dest);
        this.state = STATE.START;
    }

    private enum STATE {IDLE, START, MOVE, TURN, ACCELERATING, DECELERATING}
}
