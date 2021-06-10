import java.awt.*;

public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    //dimension in all 3 axis of the CosmicCube
    //One cosmicCube has length of diameter * 2
    public static final double Diameter = 50*AU;

    //threshhold to group bodies together. d/r < T, d = diameter of group, r = distance from center of group to body
    public static final double T = 1.0;

    public static void main(String[] args) {
        Body sun = new Body("Sol", 1.989e37, 696340e3, new Vector3(0,0,0), new Vector3(0,0,0), StdDraw.YELLOW);

        int n = 10000; //number of bodies in simulation
        Body[] bodies = new Body[n]; //array containing all bodies in simulation

        bodies[0] = sun;

        double masses[] = new double[]{1e36, 1e35, 1e34, 1e33, 1e33, 1e33, 1e32, 1e32, 1e31, 1e30};
        double radii[] = new double[]{1e5, 1e5, 1e5, 1e4, 1e4, 1e4, 1e4, 1e3, 1e3, 1e3};
        double movements[] = new double[]{30000, -30000};

        //generate random bodies and add them to the array bodies
        for (int i = 1; i < n; i++){
            String name = "a" + i;
            int hochzahl = (int) (10 * Math.random());
            //double mass = Math.random()*1e40 + 1e30; //min = mass of earths moon
            double mass = masses[(int)Math.random()];
            //double radius = (Math.random()*6371e3) + 1737.5; // max = earth , min = radius of earths moon
            double radius = radii[(int) Math.random()];
            Vector3 position = new Vector3(
                    ((Math.random()*Diameter)-(Diameter/2)) * Math.pow(Math.random(),1.1),
                    ((Math.random()*Diameter)-(Diameter/2)) * Math.pow(Math.random(),1.1),
                    ((Math.random()*Diameter)-(Diameter/2)) * Math.pow(Math.random(),1.1));
            /*Vector3 currentMovement = new Vector3(
                    ((Math.random()*30000 - 30000/2)),
                    ((Math.random()*30000 - 30000/2)),
                    ((Math.random()*30000 - 30000/2)));*/
            Vector3 currentMovement = new Vector3(
                    movements[((int) Math.random()) % 2],
                    movements[((int) Math.random()) % 2],
                    movements[((int) Math.random()) % 2]);
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
            if (seconds >= 1){
                //break; // uncomment this line to stop after 5 seconds
            }
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            //create new octree and adds all elements from bodies to it.
            // If one Element cannot be added it is removed from bodies.
            Octree system = new Octree("system");
            for (int i = 0; i < n; i++){
                if (bodies[i] != null){
                   if(system.add(bodies[i]) == false){
                       System.out.println("Body \"" + bodies[i].getName() + "\" removed from the simulation.");
                       //System.out.println(bodies[i].getMass());
                       bodies[i] = null;
                   };
                }
            }

            // calc force on bodies and save result in body
            for (int i = 0; i < n; i++){
                if (bodies[i] != null){
                    bodies[i].setForce(system.calcForceOnBody(bodies[i]));
                }
            }

            //visualize Octree
            //system.drawTree2D();

            // for each body (with index i): move it according to the total force exerted on it.
            for (int i = 0; i < n; i++){
                if (bodies[i] != null) {
                    bodies[i].move();
                }
            }

            // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(10) == 0 || seconds == 1) {
                // clear old positions (exclude the following line if you want to draw orbits).
                StdDraw.clear(StdDraw.BLACK);

                // draw new positions
                for (int i = 0; i < n; i++) {
                    if (bodies[i] != null) {
                        bodies[i].draw();
                    }
                }
                // show new positions
                StdDraw.show();
            }
        }
    }


}
