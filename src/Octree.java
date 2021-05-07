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
        if (body.insideOfBoundary(diameter, centre) == false) {
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
    }

    public Vector3 calcForceOnBody(Body body){
        Vector3 v = new Vector3();
        for (int i = 0; i < 8; i++){
            if (nodes[i] != null){
                v = v.plus(nodes[i].calcForceOnBody(body));;
            }
        }
        return v;
    }

    public void drawTree2D(){
        for (int i = 0; i < 8; i++){
            if (nodes[i] != null){
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.square(centreNodes[i].getX(), centreNodes[i].getY(), diameter/4);
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
