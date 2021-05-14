import java.awt.*;

public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    //dimension in all 3 axis of the CosmicCube
    //One cosmicCube has length of diameter * 2
    public static final double Diameter = 4*AU;

    //threshhold to group bodies together. d/r < T, d = diameter of group, r = distance from center of group to body
    public static final double T = 0.0;

    public static void main(String[] args) {

        Body sun = new Body("Sol", 1.989e30, 696340e3, new Vector3(0,0,0), new Vector3(0,0,0), StdDraw.YELLOW);
        Body earth = new Body("Earth", 5.972e24, 6371e3, new Vector3(148e9, 0, 0), new Vector3(0, 29.29e3, 0), StdDraw.BLUE);
        Body mercury = new Body("Mercury", 3.301e23, 2.4397e3, new Vector3(-46.0e9, 0, 0), new Vector3(0, -47.87e3, 0), StdDraw.ORANGE);
        Body venus = new Body("Venus",4.86747e24,6052e3,new Vector3(-1.707667e10,1.066132e11,2.450232e9),new Vector3(-34446.02,-5567.47,2181.10),StdDraw.PINK);
        Body mars = new Body("Mars",6.41712e23,3390e3,new Vector3(-1.010178e11,-2.043939e11,-1.591727E9),new Vector3(20651.98,-10186.67,-2302.79),StdDraw.RED);
        CosmicSystem SolarSystem = new CosmicSystem("SolarSystem");
        SolarSystem.add(sun);
        SolarSystem.add(earth);
        SolarSystem.add(venus);
        SolarSystem.add(mercury);
        SolarSystem.add(mars);

        //Body[] bodies = new Body[];


        for (int i = 0; i < 10; i++){
            String name = "a" + i;
            double mass = Math.random()*1.989e30 + 7.348e22; //max = mass of sun, min = mass of earths moon
            double radius = (Math.random()*696340e3) + 1737.5e3; // max = radius of sun, min = radius of earths moon
            Vector3 position = new Vector3(
                    ((Math.random()*Diameter/2)-(Diameter/2)),
                    ((Math.random()*Diameter/2)-(Diameter/2)),
                    ((Math.random()*Diameter/2)-(Diameter/2)));
            Vector3 currentMovement = new Vector3(
                    ((Math.random()*30000/2)),
                    ((Math.random()*30000/2)),
                    ((Math.random()*30000/2)));
            //SolarSystem.add(new Body(name, mass, radius, position, currentMovement, StdDraw.YELLOW));
            SolarSystem.add(new Body(name, mass, radius, position, currentMovement, StdDraw.YELLOW));

        }
        System.out.println(SolarSystem);

        if (!earth.insideOfBoundary(Diameter, new Vector3(0,0,0))){
            System.exit(0);
        }


        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(-Diameter/2,Diameter/2);
        StdDraw.setYscale(-Diameter/2,Diameter/2);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        double seconds = 0;

        // simulation loop
        while(true) {
            if (seconds >= 2){
                //break; // uncomment this line to stop after 5 seconds
            }
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            //create new octree and add bodies
            Octree system = new Octree("system");
            for (int i = 0; i < SolarSystem.size(); i++){
                //system.add(bodies[i]);
                system.add(SolarSystem.get(i));
                //System.out.println(SolarSystem.get(i).getName());
                //System.out.println(system.add(SolarSystem.get(i)));
            }

            // calc force on bodies and put results in array
            Vector3[] forceOnBody = new Vector3[SolarSystem.size()];
            for (int i = 0; i < SolarSystem.size(); i++){
                //forceOnBody[i] = system.calcForceOnBody(bodies[i]);
                forceOnBody[i] = system.calcForceOnBody(SolarSystem.get(i));

            }

            //visualize Octree
            //system.drawTree2D();

            // for each body (with index i): move it according to the total force exerted on it.
            for (int i = 0; i < SolarSystem.size(); i++){
                //bodies[i].move(forceOnBody[i]);
                SolarSystem.get(i).move(forceOnBody[i]);
            }

            // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(3*3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                StdDraw.clear(StdDraw.BLACK);

                // draw new positions
                for (int i = 0; i < SolarSystem.size(); i++) {
                    //bodies[i].draw();
                    SolarSystem.get(i).draw();

                }
                // show new positions
                StdDraw.show();
            }
        }
    }


}
