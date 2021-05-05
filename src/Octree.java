import jdk.dynalink.linker.support.SimpleLinkRequest;

import javax.management.ValueExp;
import java.awt.*;

public class Octree {
    private String name;
    private CosmicComponent firstNode;
    private CosmicComponent secondNode;
    private CosmicComponent thirdNode;
    private CosmicComponent fourthNode;
    private CosmicComponent fifthNode;
    private CosmicComponent sixthNode;
    private CosmicComponent seventhNode;
    private CosmicComponent eighthNode;


    public Octree(String name) {
        this.name = name;
    }

    // adds planet to the Octree, unless the planet is already present
    // planets must be inside the given bounds of a cube, the length of which is Cubeside, if it is outside of bounds return false
    // this cube is divided into 8 smaller cubes, and the body is added to node representative of its position in the cube
    public Boolean add(Body body) {
        double positionX = body.getMassCenter().getX();
        double positionY = body.getMassCenter().getY();
        double positionZ = body.getMassCenter().getZ();
        if (Math.abs(positionX) > Simulation.Diameter || Math.abs(positionY) > Simulation.Diameter || Math.abs(positionZ) > Simulation.Diameter) {
            return false;
        }

        //firstNode = body;
        firstNode = new OctreeNode();
        firstNode.add(body);

        if (positionX >= 0) {
            if (positionY >= 0) {
                if (positionZ >= 0) {
                    if (secondNode == null) {
                        secondNode = body;
                    }
                    if (secondNode instanceof Body){
                        CosmicComponent otherBody = secondNode;
                    }
                }
            } else {

            }
        } else {

        }


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
