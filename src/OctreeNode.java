public class OctreeNode implements CosmicComponent {
    private Vector3 centre;
    private final double diameter; //diameter of octant = edge length
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
        double c = diameter/4;
        centreNodes = new Vector3[]{centre.plus(new Vector3(-c, c, c)), centre.plus(new Vector3(c, c, c)),
                centre.plus(new Vector3(-c, c, -c)),
                centre.plus(new Vector3(c, c, -c)),
                centre.plus(new Vector3(-c, -c, c)),
                centre.plus(new Vector3(c, -c, c)),
                centre.plus(new Vector3(-c, -c, -c)),
                centre.plus(new Vector3(c, -c, -c)),};
    }


    // adds a body to one of the 8 octreenodes. After the body is added both centreOfMass and mass of the current node is
    @Override
    public Boolean add(Body body) {
        //System.out.println(body.getName());
        /*for (int i = 0; i < 8; i++) {
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
        return false;*/
        // slightly mor efficient implementation
        Vector3 position = body.getMassCenter().minus(centre);
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
            nodes[cubeNumber] = new OctreeNode(diameter / 2, centreNodes[cubeNumber]);
            nodes[cubeNumber].add(otherBody);
        }
        boolean returnValue = nodes[cubeNumber].add(body);
        calculateCentreOfMass(body);
        calculateMass(body);
        return returnValue;
    }

    /*public void add(BodyStack bodies) {
        // noch gescheiter w??re es eine array zu machen, welches dann nach xyz sortiert wird, sodass man das array einfach immer entsprechend der position teilen muss
        BodyStack[] cubes = new BodyStack[8];

        while (bodies.peek() != null) {
            Vector3 position = bodies.peek().getMassCenter();
            if (position.getX() >= 0) {
                if (position.getY() >= 0) {
                    if (position.getZ() >= 0) {
                        cubes[1].push(bodies.pop());
                    } else {
                        cubes[3].push(bodies.pop());
                    }
                } else {
                    if (position.getZ() >= 0) {
                        cubes[5].push(bodies.pop());
                    } else {
                        cubes[7].push(bodies.pop());
                    }
                }
            } else {
                if (position.getY() >= 0) {
                    if (position.getZ() >= 0) {
                        cubes[0].push(bodies.pop());
                    } else {
                        cubes[2].push(bodies.pop());
                    }
                } else {
                    if (position.getZ() >= 0) {
                        cubes[4].push(bodies.pop());
                    } else {
                        cubes[6].push(bodies.pop());
                    }
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            if (cubes[i].moreThanOneBody() == false && cubes[i].peek() != null) {
                nodes[i] = new LeafNode();
                nodes[i].add(cubes[i]);
            } else if (cubes[i].peek() != null) {
                nodes[i] = new OctreeNode(diameter / 2, centreNodes[i]);
                nodes[i].add(cubes[i]);
            }
        }
    }*/


    // calculates new Mass of the OctreeNode after a body was added
    public void calculateMass(Body body) {
        mass = mass + body.getMass();
    }

    // calculate new centreOfMass after a body was added
    public void calculateCentreOfMass(Body body) {
        if (centreOfMass != null) {
            centreOfMass = (centreOfMass.times(mass).plus(body.getMassCenter().times(body.getMass()))).times(1 / (mass + body.getMass()));
        } else {
            centreOfMass = body.getMassCenter();
        }
    }

    public Vector3 calcForceOnBody(Body body) {
        //System.out.println("new rec " + body.getName());
        Vector3 force = new Vector3();
        //iterates over all 8 nodes. if d/r<T there is no recursive call and the
        for (int i = 0; i < 8; i++) {
            if (nodes[i] != null) {
                if (((diameter / 2) / centreNodes[i].distance(body.getMassCenter())) < Simulation.T) {
                    Vector3 v = body.gravitationalForce(nodes[i].getMass(), nodes[i].getCentre());
                    force = force.plus(v);
                } else {
                    force = force.plus(nodes[i].calcForceOnBody(body));
                }
            }
        }
        return force;
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public Vector3 getCentre() {
        return centreOfMass;
    }

    @Override
    public Body getBody() {
        return null;
    }

    public String toString() {
        String returnValue = "<nodeMass: " + mass + ", nodeCentreOfMass: " + centreOfMass + ";: ";
        for (int i = 0; i < 8; i++) {
            if (nodes[i] == null) {
                returnValue = returnValue + i + ":empty;\n";
            } else {
                returnValue = returnValue + i + ":" + nodes[i].toString() + "; ";
            }
        }
        return returnValue + ">\n";
    }

    public int getDepth(){
        int depth = 0;
        for (int i = 0; i < 8; i++){
            if (nodes[i] != null){
                int depthNode = nodes[i].getDepth() + 1;
                if(depthNode > depth){
                    depth = depthNode;
                }
            }
        }
        return depth;
    }
}
