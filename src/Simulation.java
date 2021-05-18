import java.awt.*;
import java.sql.SQLOutput;

public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    //dimension in all 3 axis of the CosmicCube
    //One cosmicCube has length of diameter * 2
    public static final double Diameter = 50*AU;

    //threshhold to group bodies together. d/r < T, d = diameter of group, r = distance from center of group to body
    public static final double T = 5;

    public static void main(String[] args) {
        System.out.println(Diameter/2);
        Body sun = new Body("Sol", 1.989e30, 696340e3, new Vector3(0,0,0), new Vector3(0,0,0), StdDraw.YELLOW);
        Body earth = new Body("Earth", 5.972e24, 6371e3, new Vector3(148e9, 0, 0), new Vector3(0, 29.29e3, 0), StdDraw.BLUE);
        Body mercury = new Body("Mercury", 3.301e23, 2.4397e3, new Vector3(-46.0e9, 0, 0), new Vector3(0, -47.87e3, 0), StdDraw.ORANGE);
        Body venus = new Body("Venus",4.86747e24,6052e3,new Vector3(-1.707667e10,1.066132e11,2.450232e9),new Vector3(-34446.02,-5567.47,2181.10),StdDraw.PINK);
        Body mars = new Body("Mars",6.41712e23,3390e3,new Vector3(-1.010178e11,-2.043939e11,-1.591727E9),new Vector3(20651.98,-10186.67,-2302.79),StdDraw.RED);


        int n = 1000; //number of bodies in simulation
        Body[] bodies = new Body[n]; //array containing all bodies in simulation

        bodies[0] = sun;
        bodies[1] = earth;
        bodies[2] = venus;
        bodies[3] = mercury;
        bodies[4] = mars;

        //generate random bodies and add them to the array bodies
        for (int i = 5; i < n; i++){
            String name = "a" + i;
            double mass = Math.random()*10e26 + 7.348e2; //min = mass of earths moon
            double radius = (Math.random()*100000e3) + 1737.5e2; // max = earth , min = radius of earths moon
            Vector3 position = new Vector3(
                    ((Math.random()*Diameter)-(Diameter/2)) * Math.pow(Math.random(),1.1),
                    ((Math.random()*Diameter)-(Diameter/2)) * Math.pow(Math.random(),1.1),
                    ((Math.random()*Diameter)-(Diameter/2))* Math.pow(Math.random(),1.1));
            Vector3 currentMovement = new Vector3(
                    ((Math.random()*300000 - 300000/2)),
                    ((Math.random()*300000 - 300000/2)),
                    ((Math.random()*300000 - 300000/2)));
            bodies[i] = new Body(name, mass, radius, position, currentMovement, StdDraw.LIGHT_GRAY);

        }

        //StdDraw setup
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(-Diameter/2,Diameter/2);
        StdDraw.setYscale(-Diameter/2,Diameter/2);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        double seconds = 0;

        // simulation loop
        while(true) {
            //long startTime = System.nanoTime();
            if (seconds >= 1){
                //break; // uncomment this line to stop after 5 seconds
            }
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            //create new octree and add bodies
            Octree system = new Octree("system");
            for (int i = 0; i < n; i++){
                system.add(bodies[i]);
            }

            //long octreeTime = System.nanoTime();
            //System.out.println("Octree:");
            //System.out.println(octreeTime - startTime);

            // calc force on bodies and save result in body
            for (int i = 0; i < n; i++){
                bodies[i].setForce(system.calcForceOnBody(bodies[i]));
            }

            //long forceTime = System.nanoTime();
            //System.out.println("Force:");
            //System.out.println(forceTime - octreeTime);


            // for each body (with index i): move it according to the total force exerted on it.
            for (int i = 0; i < n; i++){
                bodies[i].move();
            }

            //long moveTime = System.nanoTime();
            //System.out.println("Move:");
            //System.out.println(moveTime - forceTime);

            // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(3*3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                StdDraw.clear(StdDraw.BLACK);

                // draw new positions
                for (int i = 0; i < n; i++) {
                    bodies[i].draw();
                }
                // show new positions
                StdDraw.show();
            }

            //long restTime = System.nanoTime();
            //System.out.println("Rest:");
            //System.out.println(restTime - moveTime);
        }
    }


}
