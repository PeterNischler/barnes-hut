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
    private CosmicComponent[] Nodes = new CosmicComponent[8];

    public Octree(String name) {
        this.name = name;
        this.diameter = Simulation.Diameter;
    }

    // adds planet to the Octree, unless the planet is already present
    // planets must be inside the given bounds of a cube, the length of which is Cubeside, if it is outside of bounds return false
    // this cube is divided into 8 smaller cubes, and the body is added to node representative of its position in the cube
    public Boolean add(Body body) {
        if (body.insideOfBoundary(Simulation.Diameter, centre) == false) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (body.insideOfBoundary(diameter/ 2, centreNodes[i])) {
                if (Nodes[i] == null) {
                    Nodes[i] = new LeafNode();
                } else if (Nodes[i] instanceof LeafNode) {
                    Body otherBody = Nodes[i].getBody();
                    Nodes[i] = new OctreeNode( diameter / 2, centreNodes[i]);
                    Nodes[i].add(otherBody);
                }
                return Nodes[i].add(body);
            }
        }
        return false;
    }

    public Vector3 calcForceOnBody(Body body){
        Vector3 v = new Vector3();
        for (int i = 0; i < 8; i++){
            if (Nodes[i] != null){
                v = v.plus(Nodes[i].calcForceOnBody(body));;
            }
        }
        return v;
    }

    /*public Body getBody(String name) {
        for (int i = 0; i < 8; i++){
            if (Nodes[i] != null){
                Body b =  Nodes[i].getBody(name);
                if (b != null){
                    return b;
                }
            }
        }
        return null;
    }*/

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
