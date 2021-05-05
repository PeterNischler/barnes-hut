import jdk.dynalink.linker.support.SimpleLinkRequest;

import javax.management.ValueExp;
import java.awt.*;

public class Octree {
    private String name;
    private Vector3 centre = new Vector3(0,0,0);
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
    }

    // adds planet to the Octree, unless the planet is already present
    // planets must be inside the given bounds of a cube, the length of which is Cubeside, if it is outside of bounds return false
    // this cube is divided into 8 smaller cubes, and the body is added to node representative of its position in the cube
    public Boolean add(Body body) {
        if (body.insideOfBoundary(Simulation.Diameter, centre) == false) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (body.insideOfBoundary(Simulation.Diameter / 2, centreNodes[i])) {
                if (Nodes[i] == null){
                    Nodes[i] = new LeafNode(body);
                    break;
                } else if (Nodes[i] instanceof LeafNode){
                    Body otherBody = Nodes[i].getBody();
                    Nodes[i] = new OctreeNode();
                    Nodes[i].add(body);
                    Nodes[i].add(otherBody);
                }

            }
        }


        //firstNode = body;
        firstNode = new OctreeNode();
        firstNode.add(body);


        return false;
    }

    public Boolean get(String name) {
        return false;
    }

    public Boolean get(int index) {
        return false;
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
