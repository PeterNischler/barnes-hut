import jdk.dynalink.linker.support.SimpleLinkRequest;

import java.awt.*;

public class Octree{
    private String name;
    private Vector3 pos; //center of octant
    private double d; //diameter of octant = edge length
    private CosmicComponent firstNode;
    private CosmicComponent secondNode;
    private CosmicComponent thirdNode;
    private CosmicComponent fourthNode;
    private CosmicComponent fifthNode;
    private CosmicComponent sixthNode;
    private CosmicComponent seventhNode;
    private CosmicComponent eigthNode;


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
        if (Math.abs(positionX) > Simulation.Cubeside || Math.abs(positionY) > Simulation.Cubeside || Math.abs(positionZ) > Simulation.Cubeside) {
            return false;
        }
        //firstNode = body;
        firstNode = new OctreeNode();
        firstNode.add(body);

        if (positionX >= 0) {
            if (positionY >= 0) {
                if (positionZ >= 0) {

                    }
                } else {

                }
            } else {

            }


            return false;
        }

        public Boolean remove (Body body){
            return false;
        }

        public String getName () {
            return name;
        }

        public int size () {
            return 0;
        }

        public CosmicComponent getFirstNode () {
            return firstNode;
        }

        public void setFirstNode (CosmicComponent firstNode){
            this.firstNode = firstNode;
        }

        public CosmicComponent getSecondNode () {
            return secondNode;
        }

        public void setSecondNode (CosmicComponent secondNode){
            this.secondNode = secondNode;
        }

        public CosmicComponent getThirdNode () {
            return thirdNode;
        }

        public void setThirdNode (CosmicComponent thirdNode){
            this.thirdNode = thirdNode;
        }

        public CosmicComponent getFourthNode () {
            return fourthNode;
        }

        public void setFourthNode (CosmicComponent fourthNode){
            this.fourthNode = fourthNode;
        }

        public CosmicComponent getFifthNode () {
            return fifthNode;
        }

        public void setFifthNode (CosmicComponent fifthNode){
            this.fifthNode = fifthNode;
        }

        public CosmicComponent getSixthNode () {
            return sixthNode;
        }

        public void setSixthNode (CosmicComponent sixthNode){
            this.sixthNode = sixthNode;
        }

        public CosmicComponent getSeventhNode () {
            return seventhNode;
        }

        public void setSeventhNode (CosmicComponent seventhNode){
            this.seventhNode = seventhNode;
        }

        public CosmicComponent getEigthNode () {
            return eigthNode;
        }

        public void setEigthNode (CosmicComponent eigthNode){
            this.eigthNode = eigthNode;
        }
    }
