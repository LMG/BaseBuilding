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
    private double turnAngle = Math.PI / 18;
    private double correctionLength;

    public BetterBoatBehaviour(MovingGameItem item) {
        this.item = item;
        correctionLength = 10 * item.getMaxSpeed();
        destination = new Coordinate();
    }

    @Override
    public void step() {
        Coordinate itemCoordinate = item.getCoordinate();
        Vect2D trajToDo;
        Vect2D acceleration = new Vect2D();
        double itemAcceleration = item.getAcceleration();
        Vect2D speed = item.getSpeed();
        double destAngle = Vect2D.createFromCoordinates(item.getCoordinate(), destination).getAngle();
        double turnAngle = this.turnAngle * speed.getLength() / item.getMaxSpeed();
        double angleToDest = compareAngles(item.getSpeed().getAngle(), destAngle);

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

                //if we're facing in the correct direction, go to ACCELERATE and stop turning
                if (angleToDest >= -turnAngle / 2 &&
                        angleToDest <= turnAngle / 2) {
                    state = STATE.ACCELERATING;
                    acceleration = new Vect2D();
                }
                else if(!canReach(destination)){
                    state = STATE.MOVE;
                    acceleration = new Vect2D();
                }
                else {
                    Log.d(TAG, "Orientation " + item.getSpeed().getAngle() * 360 / (2 * Math.PI) + " destAngle " + destAngle * 360 / (2 * Math.PI));
                    if (compareAngles(item.getSpeed().getAngle(), destAngle) < 0) {
                        acceleration = (new Vect2D(item.getSpeed())).turn(-turnAngle).sub(item.getSpeed());
                    } else {
                        acceleration = (new Vect2D(item.getSpeed())).turn(turnAngle).sub(item.getSpeed());
                    }
                    // accelerate while turning
                    acceleration.add(Vect2D.createPolar(item.getAcceleration(), item.getOrientation()));
                }
                break;
            case ACCELERATING://once we are facing the point, accelerate as much as we can
                Log.d(TAG, "ACCELERATING");
                trajToDo = Vect2D.createFromCoordinates(itemCoordinate, destination);
                acceleration = Vect2D.createPolar(itemAcceleration, item.getSpeed().getAngle());

                double decelerationTime = (speed.getLength() / itemAcceleration);
                double decelerationLength = (decelerationTime * (decelerationTime + 1) * itemAcceleration / 2);

                if (trajToDo.getLength() <= correctionLength
                        && (angleToDest >= turnAngle / 2 ||
                        angleToDest <= -turnAngle / 2)) {
                    state = STATE.TURN;
                }
                if (trajToDo.getLength() <= decelerationLength) {
                    state = STATE.DECELERATING;
                }
                break;
            case DECELERATING://we are too close, we need to start slowing down
                Log.d(TAG, "DECELERATING");
                trajToDo = Vect2D.createFromCoordinates(itemCoordinate, destination);
                acceleration = Vect2D.createPolar(itemAcceleration, item.getSpeed().getAngle() + Math.PI);

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

    /*
     * Can we reach the coordinate if we turn at max speed
     */
    private boolean canReach(Coordinate c) {
        double speed = item.getMaxSpeed();//item.getSpeed().getLength()+item.getAcceleration();
        double turningRadius = speed * Math.sin(turnAngle) / (4 * Math.pow(Math.sin(turnAngle / 2), 2));
        Log.d(TAG, "speed " + speed + "turnAngle " + turnAngle + "turnradius " + turningRadius);

        Vect2D vectA = (Vect2D.createCart(item.getSpeed().getX() / 2, item.getSpeed().getY() / 2).turn(Math.PI));
        Coordinate relativeCenterLeft = vectA.add(Vect2D.createPolar(turningRadius, item.getOrientation() - Math.PI / 2))
                .add(Vect2D.createCart(item.getCoordinate().x, item.getCoordinate().y))
                .toCoordinate();
        Coordinate relativeCenterRight = vectA.add(Vect2D.createPolar(turningRadius, item.getOrientation() + Math.PI / 2))
                .add(Vect2D.createCart(item.getCoordinate().x, item.getCoordinate().y))
                .toCoordinate();

        Log.d(TAG, "" + turningRadius);

        return (Vect2D.createFromCoordinates(destination, relativeCenterLeft).getLength() > turningRadius
                && Vect2D.createFromCoordinates(destination, relativeCenterRight).getLength() > turningRadius);

    }

    public void setDestination(Coordinate dest) {
        this.destination = new Coordinate(dest);
        this.state = STATE.START;
    }

    /*
     * returns a signed angled that is the smallest angle from a to b.
     *
     * Note: costly, see https://stackoverflow.com/questions/1878907/the-smallest-difference-between-2-angles for smarter solutions
     *
     * @returns signed angle between -PI and PI
     * @param a a source angle of any value
     * @param b a destination angle of any value
     */
    private double compareAngles(double a, double b) {
        return Math.atan2(Math.sin(b - a), Math.cos(b - a));
    }

    private enum STATE {IDLE, START, MOVE, TURN, ACCELERATING, DECELERATING}
}
