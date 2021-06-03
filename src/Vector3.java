import java.awt.*;

// This class represents vectors in a 3D vector space.
public class Vector3 {

    private double x;
    private double y;
    private double z;

    public Vector3 (double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 (Vector3 v){
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector3 (){
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    // Returns the sum of this vector and vector 'v'.
    public Vector3 plus(Vector3 v) {
        Vector3 result = new Vector3 (0, 0, 0);
        result.x = this.x + v.x;
        result.y = this.y + v.y;
        result.z = this.z + v.z;
        return result;
    }

    // Returns the product of this vector and 'd'.
    public Vector3 times(double d) {
        Vector3 result = new Vector3(0, 0 ,0);
        result.x = this.x * d;
        result.y = this.y * d;
        result.z = this.z * d;
        return result;
    }

    // Returns the sum of this vector and -1*v.
    public Vector3 minus(Vector3 v) {
        Vector3 result = new Vector3();
        result.x = (this.x - v.x);
        result.y = (this.y - v.y);
        result.z = (this.z - v.z);

        return result;
    }

    public Vector3 divideBy(double d){
        return times(1/d);
    }

    // Returns the Euclidean distance of this vector
    // to the specified vector 'v'.
    public double distanceTo(Vector3 v) {
        Vector3 resultVector = minus(v);
        return resultVector.length();
    }

    // Returns the length (norm) of this vector.
    public double length() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    // Normalizes this vector: changes the length of this vector such that it becomes 1.
    // The direction and orientation of the vector is not affected.
    public void normalize() {
        double vectorLenght = this.length();
        this.x = x / vectorLenght;
        this.y = y / vectorLenght;
        this.z = z / vectorLenght;
    }

    //calculates distance between vector 1 and 2
    public double distance(Vector3 v){
        return Math.sqrt((this.x - v.x)*(this.x - v.x) + (this.y - v.y)*(this.y - v.y) + (this.y - v.y)*(this.y - v.y));
    }

    // Draws a filled circle with a specified radius centered at the (x,y) coordinates of this vector
    // in the existing StdDraw canvas. The z-coordinate is not used.
    public void drawAsDot(double radius, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(this.x, this.y, 1e9 * Math.log10(radius));
    }

    // Returns the coordinates of this vector in brackets as a string
    // in the form "[x,y,z]", e.g., "[1.48E11,0.0,0.0]".
    public String toString() {
        //converted to  astronomical units
        return "[" + Double.toString(this.x/Simulation.AU) + "AU, "
                + Double.toString(this.y/Simulation.AU) + "AU, "
                + Double.toString(this.z/Simulation.AU) + "AU] ";
    }

    public double getX (){
        return x;
    }

    public double getY (){
        return y;
    }

    public double getZ (){
        return z;
    }
}


