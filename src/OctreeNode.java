public class OctreeNode implements CosmicComponent {
    private Body body;
    private CosmicComponent firstNode;
    private CosmicComponent secondNode;
    private CosmicComponent thirdNode;
    private CosmicComponent fourthNode;
    private CosmicComponent fifthNode;
    private CosmicComponent sixthNode;
    private CosmicComponent seventhNode;
    private CosmicComponent eigthNode;
    private Vector3 position; //center of octant
    private Vector3 centreOfMass;
    private double diameter; //diameter of octant = edge length

    public OctreeNode(double d) {
        diameter = d;
    }

    @Override
    public boolean add(Body body) {
        double positionX = body.getMassCenter().getX();
        double positionY = body.getMassCenter().getY();
        double positionZ = body.getMassCenter().getZ();

        Vector3 firstNodeCentre = new Vector3(position.getX()+diameter/2, );
        if (body.insideOfBoundary(diameter, position){

        }

    }

    @Override
    public String getName() {
        return "n";
    }
}
