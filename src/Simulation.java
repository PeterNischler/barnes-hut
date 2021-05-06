public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    //dimension in all 3 axis of the CosmicCube
    //One cosmicCube has length of diameter * 2
    public static final double Diameter = 8e12;

    //threshhold to group bodies together. d/r < T, d = diameter of group, r = distance from center of group to body
    public static final double T = 1.0;

    public static void main(String[] args) {
        Body sun = new Body("Sol", 1.989e30, 696340e3, new Vector3(0,0,0), new Vector3(0,0,0), StdDraw.YELLOW);
        Body earth = new Body("Earth", 5.972e24, 6371e3, new Vector3(148e9, 0, 0), new Vector3(0, 29.29e3, 0), StdDraw.BLUE);
        Body mercury = new Body("Mercury", 3.301e23, 2.4397e3, new Vector3(-46.0e9, 0, 0), new Vector3(0, -47.87e3, 0), StdDraw.ORANGE);
        Body venus = new Body("Venus",4.86747e24,6052e3,new Vector3(-1.707667e10,1.066132e11,2.450232e9),new Vector3(-34446.02,-5567.47,2181.10),StdDraw.PINK);
        Body mars = new Body("Mars",6.41712e23,3390e3,new Vector3(-1.010178e11,-2.043939e11,-1.591727E9),new Vector3(20651.98,-10186.67,-2302.79),StdDraw.RED);

        Body[] bodies = new Body[]{sun, earth, mercury, venus, mars};


        if (mercury.insideOfBoundary(Diameter, new Vector3(0,0,0)) == false){
            System.exit(0);
        }

        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(-2*AU,2*AU);
        StdDraw.setYscale(-2*AU,2*AU);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        double seconds = 0;

        // simulation loop
        while(seconds < 1) {
            //create new octree and add bodies
            Octree system = new Octree("system");
            for (int i = 0; i < bodies.length; i++){
                System.out.println(system.add(bodies[i]));
            }

            // calc force on bodies
            Vector3[] forceOnBody = new Vector3[bodies.length];
            for (int i = 0; i < bodies.length; i++){

                forceOnBody[i] = system.calcForceOnBody(bodies[i]);
                System.out.println(forceOnBody[i]);
            }



            seconds++; // each iteration computes the movement of the celestial bodies within one second.

 /*           // for each body (with index i): compute the total force exerted on it.
            for (int i = 0; i < system.size(); i++) {
                forceOnBody[i] = new Vector3(); // begin with zero

                for (int j = 0; j < system.size(); j++) {
                    if (i == j) continue;

                    Vector3 forceToAdd = system.get(i).gravitationalForce(system.get(j));
                    forceOnBody[i] = forceOnBody[i].plus(forceToAdd);
                }
            }
            // now forceOnBody[i] holds the force vector exerted on body with index i.

            // for each body (with index i): move it according to the total force exerted on it.
            for (int i = 0; i < system.size(); i++) {
                system.get(i).move(forceOnBody[i]);
            }

            // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(3*3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                StdDraw.clear(StdDraw.BLACK);

                // draw new positions
                for (int i = 0; i < system.size(); i++) {
                    system.get(i).draw();
                }

                // show new positions
                StdDraw.show();
            }*/

        }
    }


}
