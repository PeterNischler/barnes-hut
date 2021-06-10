public class Octree {
    private String name;
    private final double diameter;
    private Vector3 centre = new Vector3(0, 0, 0);
    private double c = Simulation.Diameter/4;
    private Vector3[] centreNodes = new Vector3[]{centre.plus(new Vector3(-c, c, c)),
            centre.plus(new Vector3(c, c, c)),
            centre.plus(new Vector3(-c, c, -c)),
            centre.plus(new Vector3(c, c, -c)),
            centre.plus(new Vector3(-c, -c, c)),
            centre.plus(new Vector3(c, -c, c)),
            centre.plus(new Vector3(-c, -c, -c)),
            centre.plus(new Vector3(c, -c, -c)),};
    private CosmicComponent[] nodes = new CosmicComponent[8];

    public Octree(String name) {
        this.name = name;
        this.diameter = Simulation.Diameter;
    }

    // adds a body to the octree, which must be inside of the parameter Diameter*2 in all directions (the cube).
    // If that is not the case false is returned.
    // the body will be added to one of the 8 octreenodes, representing the 8 cubes into which the larger cube is divided.
    public Boolean add(Body body) {
        if (!body.insideOfBoundary(diameter, centre)) {
            return false;
        }
        /*for (int i = 0; i < 8; i++) {
            if (body.insideOfBoundary(diameter / 2, centreNodes[i])) {
                if (nodes[i] == null) {
                    nodes[i] = new LeafNode();
                } else if (nodes[i] instanceof LeafNode) {
                    Body otherBody = nodes[i].getBody();
                    nodes[i] = new OctreeNode(diameter / 2, centreNodes[i]);
                    nodes[i].add(otherBody);
                }
                return nodes[i].add(body);
            }
        }
        return false;*/
        //slightly more efficient implementation
        Vector3 position = body.getMassCenter();
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
        return nodes[cubeNumber].add(body);
    }

    // fügt ein Array an bodies auf einmal hinzu
    /*public void add2(Body[] bodies) {
        // noch gescheiter wäre es eine array zu machen, welches dann nach xyz sortiert wird, sodass man das array einfach immer entsprechend der position teilen muss
        BodyStack cube0 = new BodyStack();
        BodyStack cube1 = new BodyStack();
        BodyStack cube2 = new BodyStack();
        BodyStack cube3 = new BodyStack();
        BodyStack cube4 = new BodyStack();
        BodyStack cube5 = new BodyStack();
        BodyStack cube6 = new BodyStack();
        BodyStack cube7 = new BodyStack();

        for (int i = 0; i < bodies.length; i++) {
            Vector3 position = bodies[i].getMassCenter();
            if (position.getX() >= 0) {
                if (position.getY() >= 0) {
                    if (position.getZ() >= 0) {
                        cube1.push(bodies[i]);
                    } else {
                        cube3.push(bodies[i]);
                    }
                } else {
                    if (position.getZ() >= 0) {
                        cube5.push(bodies[i]);
                    } else {
                        cube7.push(bodies[i]);
                    }
                }
            } else {
                if (position.getY() >= 0) {
                    if (position.getZ() >= 0) {
                        cube0.push(bodies[i]);
                    } else {
                        cube2.push(bodies[i]);
                    }
                } else {
                    if (position.getZ() >= 0) {
                        cube4.push(bodies[i]);
                    } else {
                        cube6.push(bodies[i]);
                    }
                }
            }
        }
        BodyStack cubes[] = new BodyStack[]{cube0, cube1, cube2, cube3, cube4, cube5, cube6, cube7};
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

    //returns vector of force exerted by all other bodies on the input body
    public Vector3 calcForceOnBody(Body body) {
        Vector3 v = new Vector3();
        for (int i = 0; i < 8; i++) {
            if (nodes[i] != null) {
                v = v.plus(nodes[i].calcForceOnBody(body));
            }
        }
        return v;
    }

    public void drawTree2D() {
        //TODO
        for (int i = 0; i < 8; i++) {
            if (nodes[i] != null) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.square(centreNodes[i].getX(), centreNodes[i].getY(), diameter / 4);
            }
        }
    }

    public String getName() {
        return name;
    }

    public String toString() {
        String returnValue = "<Octree: ";
        for (int i = 0; i < 8; i++) {
            if (nodes[i] == null) {
                returnValue = returnValue + i + "empty\n";
            } else {
                returnValue = returnValue + nodes[i].toString() + "\n";
            }

        }

        return returnValue + ">";
    }


    //calculates the depth of the octree
    public int getDepth(){
        int depth = 0;
        for (int i = 0; i < 8; i++){
            if (nodes[i] != null){
                int depthNode = nodes[i].getDepth();
                if(depthNode > depth){
                    depth = depthNode;
                }
            }
        }
        return depth;
    }

}
