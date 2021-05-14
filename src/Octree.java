import jdk.dynalink.linker.support.SimpleLinkRequest;

import javax.management.ValueExp;
import java.awt.*;

public class Octree {
    private String name;
    private double diameter;
    private Vector3 centre = new Vector3(0, 0, 0);
    private Vector3[] centreNodes = new Vector3[]{centre.plus(new Vector3(-Simulation.Diameter / 4, Simulation.Diameter / 4, Simulation.Diameter / 4)),
            centre.plus(new Vector3(Simulation.Diameter / 4, Simulation.Diameter / 4, Simulation.Diameter / 4)),
            centre.plus(new Vector3(-Simulation.Diameter / 4, Simulation.Diameter / 4, -Simulation.Diameter / 4)),
            centre.plus(new Vector3(Simulation.Diameter / 4, Simulation.Diameter / 4, -Simulation.Diameter / 4)),
            centre.plus(new Vector3(-Simulation.Diameter / 4, -Simulation.Diameter / 4, Simulation.Diameter / 4)),
            centre.plus(new Vector3(Simulation.Diameter / 4, -Simulation.Diameter / 4, Simulation.Diameter / 4)),
            centre.plus(new Vector3(-Simulation.Diameter / 4, -Simulation.Diameter / 4, -Simulation.Diameter / 4)),
            centre.plus(new Vector3(Simulation.Diameter / 4, -Simulation.Diameter / 4, -Simulation.Diameter / 4)),};
    private CosmicComponent[] nodes = new CosmicComponent[8];

    public Octree(String name) {
        this.name = name;
        this.diameter = Simulation.Diameter;
    }

    // adds body to the octree. the body must be inside of a cube with lenght Diameter, if it is not false is returned.
    // the body will be added to one of the 8 octreenodes, representing the 8 cubes into which the larger cube is divided.
    public Boolean add(Body body) {
        //System.out.println(body.getName());
        if (!body.insideOfBoundary(diameter, centre)) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (body.insideOfBoundary(diameter/ 2, centreNodes[i])) {
                if (nodes[i] == null) {
                    nodes[i] = new LeafNode();
                } else if (nodes[i] instanceof LeafNode) {
                    Body otherBody = nodes[i].getBody();
                    nodes[i] = new OctreeNode( diameter / 2, centreNodes[i]);
                    nodes[i].add(otherBody);
                }
                return nodes[i].add(body);
            }
        }
        return false;
        //slightly more efficient implementation
        /*Vector3 position = body.getMassCenter();
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
        return nodes[cubeNumber].add(body);*/
    }

    public Vector3 calcForceOnBody(Body body) {
        Vector3 force = new Vector3();
        for (int i = 0; i < 8; i++) {
            if (nodes[i] != null) {
                force = force.plus(nodes[i].calcForceOnBody(body));
            }
        }
        body.setForce(force);
        return force;
    }

    public void drawTree2D() {
        for (int i = 0; i < 8; i++) {
            if (nodes[i] != null) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.square(centreNodes[i].getX(), centreNodes[i].getY(), diameter / 4);
            }
        }
    }

    public Boolean remove(Body body) {
        return false;
    }

    public String getName() {
        return name;
    }

    public int size() {
        return 0;
    }

}
