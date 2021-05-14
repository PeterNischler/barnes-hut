public class OctreeNode implements CosmicComponent {
    private Vector3 centre;
    private double diameter; //diameter of octant = edge length
    //ungerade bei x minus, gerade x immer plus
    //1-4 bei y immer plus, 5-8 bei y immer minus
    //1,2,5,6 bei z plus, 3,4,7,8 bei z minus
    private Vector3[] centreNodes;
    private CosmicComponent[] nodes = new CosmicComponent[8];
    private Vector3 centreOfMass;
    private double mass;

    public OctreeNode(double diameter, Vector3 centre) {
        this.diameter = diameter;
        this.centre = centre;
        centreNodes = new Vector3[]{new Vector3(new Vector3(-diameter / 4, diameter / 4, diameter / 4)), centre.plus(new Vector3(diameter / 4, diameter / 4, diameter / 4)),
                centre.plus(new Vector3(-diameter / 4, diameter / 4, -diameter / 4)),
                centre.plus(new Vector3(diameter / 4, diameter / 4, -diameter / 4)),
                centre.plus(new Vector3(-diameter / 4, -diameter / 4, diameter / 4)),
                centre.plus(new Vector3(diameter / 4, -diameter / 4, diameter / 4)),
                centre.plus(new Vector3(-diameter / 4, -diameter / 4, -diameter / 4)),
                centre.plus(new Vector3(diameter / 4, -diameter / 4, -diameter / 4)),};
    }


    @Override
    public Boolean add(Body body) {
        //System.out.println(body.getName());
        for (int i = 0; i < 8; i++) {
            if (body.insideOfBoundary(diameter / 2, centreNodes[i])) {
                if (nodes[i] == null) {
                    nodes[i] = new LeafNode();
                } else if (nodes[i] instanceof LeafNode) {
                    Body otherBody = nodes[i].getBody();
                    nodes[i] = new OctreeNode(diameter / 2, centreNodes[i]);
                    nodes[i].add(otherBody);
                }
                boolean returnValue = nodes[i].add(body);
                calculateCentreOfMass(body);
                calculateMass(body);
                return returnValue;
            }
        }
        return false;
        // slightly mor efficient implementation
        /*Vector3 position = body.getMassCenter().minus(centre);
        int cubeNumber;
        if (position.getX() >= 0) {
            if (position.getY() >= 0) {
                if (position.getZ() >= 0) {
                    cubeNumber = 1;
                } else {
                    cubeNumber = 3;
                }
            } else {
                if (position.getZ() >= 0) {
                    cubeNumber = 5;
                } else {
                    cubeNumber = 7;
                }
            }
        } else {
            if (position.getY() >= 0) {
                if (position.getZ() >= 0) {
                    cubeNumber = 0;
                } else {
                    cubeNumber = 2;
                }
            } else {
                if (position.getZ() >= 0) {
                    cubeNumber = 4;
                } else {
                    cubeNumber = 6;
                }
            }
        }
        if (nodes[cubeNumber] == null) {
            nodes[cubeNumber] = new LeafNode();
        } else if (nodes[cubeNumber] instanceof LeafNode) {
            Body otherBody = nodes[cubeNumber].getBody();
            nodes[cubeNumber] = new OctreeNode( diameter / 2, centreNodes[cubeNumber]);
            nodes[cubeNumber].add(otherBody);
        }
        boolean returnValue = nodes[cubeNumber].add(body);
        calculateCentreOfMass(body);
        calculateMass(body);
        return returnValue;*/

    }

    // calculates new Mass of the OctreeNode after adding body
    public void calculateMass(Body body) {
        /*double newMass = 0;
        for (int i = 0; i < 8; i++) {
            if (nodes[i] != null){
                newMass += nodes[i].getMass();
            }
        }*/
        mass = mass + body.getMass();
    }

    // calculate new calculateCentreOfMass
    public void calculateCentreOfMass(Body body) {
        if (centreOfMass != null) {
            centreOfMass = centreOfMass.times(mass).plus(body.getMassCenter().times(body.getMass())).times(1 / (mass * body.getMass()));
        } else {
            centreOfMass = body.getMassCenter();
        }
    }

    public Vector3 calcForceOnBody(Body body) {
        //System.out.println("new rec " +body.getName());
        Vector3 force = new Vector3();
        //iterates over all 8 nodes. if d/r<T there is no recursive call and the
        for (int i = 0; i < 8; i++) {
            //System.out.println(i);
            if (nodes[i] != null) {
                //System.out.println(nodes[i]);

                if ((diameter / centreNodes[i].distance(body.getMassCenter())) < Simulation.T) {
                    Vector3 v = body.gravitationalForce(nodes[i].getMass(), nodes[i].getCentre());
                    //System.out.println( "." + v);
                    force = force.plus(v);
                }
                force = force.plus(nodes[i].calcForceOnBody(body));
                ;
            }
        }
        return force;
    }

    public void drawTree2D() {
        for (int i = 0; i < 8; i++) {
            if (nodes[i] instanceof LeafNode) {
                StdDraw.setPenColor(StdDraw.WHITE);
                System.out.println(centreNodes[i].getX());
                StdDraw.square(centreNodes[i].getX(), centreNodes[i].getY(), diameter / 4);
            }
            nodes[i].drawTree2D();
        }
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public Vector3 getCentre() {
        return centreOfMass;
    }

    /*public Body getBody(String name) {
        for (int i = 0; i < 8; i++) {
            if (Nodes[i] != null) {
                Body b = Nodes[i].getBody(name);
                if (b != null) {
                    return b;
                }
            }
        }
        return null;
    }*/
    @Override
    public Body getBody() {
        return null;
    }
}
