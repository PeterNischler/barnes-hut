import java.awt.*;
import java.util.Vector;

// This class represents celestial bodies like stars, planets, asteroids, etc..
public class Body {

    //TODO: change modifiers.
    private String name;
    private double mass;
    private double radius;
    private Vector3 position; // position of the center.
    private Vector3 currentMovement;
    private Color color; // for drawing the body.
    private Vector3 forceOnBody;

    //constructor for standard body
    public Body(String name, double mass, double radius, Vector3 position, Vector3 currentMovement, Color color) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.currentMovement = currentMovement;
        this.color = color;
    }

    //constructor for random body with unique number key
    public Body(int uniqueNumber, double maxMass, double maxRadius, Vector3 maxPosition, Vector3 maxCurrentMovement) {
        this.name = "body " + Integer.toString(uniqueNumber);
        this.mass = maxMass * Math.random();
        this.radius = maxRadius * Math.random();
        this.position = maxPosition.times(Math.random());
        this.currentMovement = maxCurrentMovement.times(Math.random());
        this.color = Color.cyan;
    }


    // Returns the distance between this body and the specified 'body'.
    public double distanceTo(Body body) {
        return this.position.minus(body.position).length();
    }

    //Returns a vector representing the gravitational force exerted by 'body' on this body.
    //The gravitational Force F is calculated by F = G*(m1*m2)/(r*r), with m1 and m2 being the masses of the objects
    //interacting, r being the distance between the centers of the masses and G being the gravitational constant.
    //To calculate the force exerted on b1, simply multiply the normalized vector pointing from b1 to b2 with the
    //calculated force
    public Vector3 gravitationalForce(Body body) {
        return gravitationalForce(body.mass, body.position);
    }

    //Returns a vector representing the gravitational force exerted by the entity with 'mass' and 'position' on this body.
    //The gravitational Force F is calculated by F = G*(m1*m2)/(r*r), with m1 and m2 being the masses of the objects
    //interacting, r being the distance between the centers of the masses and G being the gravitational constant.
    //To calculate the force exerted on b1, simply multiply the normalized vector pointing from b1 to b2 with the
    //calculated force
    public Vector3 gravitationalForce(double mass, Vector3 position) {
        if (this.position == position) {
            return new Vector3(0, 0, 0);
        }
        Vector3 direction = position.minus(this.position);
        double distance = direction.length();
        if (distance < radius){
            distance = radius;
        }
        direction.normalize();
        double force = 0;
        /*if (distance >= 1) {
            force = Simulation.G * this.mass * mass / (distance * distance);
        } else {
            force = Simulation.G * this.mass * mass;
        }*/
        // alternativ stellen wir die distanz auf mindestens den radius, da wir keine Kollisionsberechnung durchf??hren
        // und so das entstheen von zu gro??en Kr??ften vermeiden k??nnen;
        if (distance < radius){
            distance = radius;
        }
        force = Simulation.G * this.mass * mass / (distance * distance);
        return direction.times(force);
    }

    // Moves this body to a new position, according to the specified force vector 'force' exerted
    // on it, and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and the exerted force)
    // Hint: see simulation loop in Simulation.java to find out how this is done
    public void move(Vector3 force) {
        Vector3 newPosition = this.currentMovement.plus(this.position.plus(force.times(1 / this.mass)));
        Vector3 newMovement = newPosition.minus(this.position);
        this.position = newPosition;
        this.currentMovement = newMovement;
    }

    public void move() {
        Vector3 newPosition = this.currentMovement.plus(this.position.plus(forceOnBody.times(1 / this.mass)));
        Vector3 newMovement = newPosition.minus(this.position);
        this.position = newPosition;
        this.currentMovement = newMovement;
    }

    // Returns a string with the information about this body including
    // name, mass, radius, position and current movement. Example:
    // "Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
    public String toString() {
        return "<" + this.name + ">"
                /*+ ", " + Double.toString(this.mass) + " kg, radius: " + Double.toString(this.radius) +
                " m, position: " + this.position.toString() + " m, movement: " + this.currentMovement.toString() +
                " m/s."*/;
    }

    // Draws the body to the current StdDraw canvas as a dot using 'color' of this body.
    // The radius of the dot is in relation to the radius of the celestial body
    // (use a conversion based on the logarithm as in 'Simulation.java').
    // Hint: use the method drawAsDot implemented in Vector3 for this
    public void draw() {
        this.position.drawAsDot(this.radius, this.color);
    }

    public void drawMovementDirection() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.line(this.position.getX(), this.position.getY(), (this.position.getX() + this.currentMovement.getX() * 1E6), (this.position.getY() + this.currentMovement.getY() * 1E6));

    }

    public String getName() {
        return name;
    }

    public int numberOfBodies() {
        return 1;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Vector3 getMassCenter() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setForce(Vector3 forceOnBody) {
        this.forceOnBody = forceOnBody;
    }

    // checks if Body is inside of an cubical boundary
    public boolean insideOfBoundary(double diameter, Vector3 centre) {
        Vector3 boundaryVector = position.minus(centre);
        if (Math.abs(boundaryVector.getX()) > diameter / 2 || Math.abs(boundaryVector.getY()) > diameter / 2
                || Math.abs(boundaryVector.getY()) > diameter / 2) {
            return false;
        }
        return true;
    }

    public Vector3 getForceOnBody(){
        return forceOnBody;
    }
}

