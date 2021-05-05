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

    public OctreeNode(double d, Vector3 p) {
        diameter = d;
        position = p;
    }

    @Override
    public boolean add(Body body) {
        double positionX = body.getMassCenter().getX();
        double positionY = body.getMassCenter().getY();
        double positionZ = body.getMassCenter().getZ();
        //ungerade bei x minus, gerade x immer plus
        //1-4 bei y immer plus, 5-8 bei y immer minus
        //1,2,5,6 bei z plus, 3,4,7,8 bei z minus
        Vector3 firstNodeCentre = new Vector3(position.getX()-diameter/4, position.getY()+diameter/4, position.getZ()+diameter/4);
        Vector3 secondNodeCentre = new Vector3(position.getX()+diameter/4, position.getY()+diameter/4, position.getZ()+diameter/4);
        Vector3 thirdNodeCentre = new Vector3(position.getX()-diameter/4, position.getY()+diameter/4, position.getZ()-diameter/4);
        Vector3 fourthNodeCentre = new Vector3(position.getX()+diameter/4, position.getY()+diameter/4, position.getZ()-diameter/4);
        Vector3 fifthNodeCentre = new Vector3(position.getX()-diameter/4, position.getY()-diameter/4, position.getZ()+diameter/4);
        Vector3 sixthNodeCentre = new Vector3(position.getX()+diameter/4, position.getY()-diameter/4, position.getZ()+diameter/4);
        Vector3 seventhNodeCentre = new Vector3(position.getX()-diameter/4, position.getY()-diameter/4, position.getZ()-diameter/4);
        Vector3 eigthNodeCentre = new Vector3(position.getX()+diameter/4, position.getY()-diameter/4, position.getZ()-diameter/4);

        if (body.insideOfBoundary(diameter/2, firstNodeCentre){
            if (firstNode == null){
                firstNode = new OctreeNode(diameter/2, firstNodeCentre);
                firstNode.add(body);
            } else if{
                firstNode.add(body);
            }
        } else if ())

    }

    @Override
    public String getName() {
        return "n";
    }
}
