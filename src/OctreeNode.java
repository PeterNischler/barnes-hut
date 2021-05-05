public class OctreeNode implements CosmicComponent {
    private Body body;
    private Vector3 centre;
    private double diameter; //diameter of octant = edge length
    //ungerade bei x minus, gerade x immer plus
    //1-4 bei y immer plus, 5-8 bei y immer minus
    //1,2,5,6 bei z plus, 3,4,7,8 bei z minus
    private Vector3[] centreNodes = new Vector3[]{        new Vector3(centre.getX()-diameter/4, centre.getY()+diameter/4, centre.getZ()+diameter/4),
            new Vector3(centre.getX()+diameter/4, centre.getY()+diameter/4, centre.getZ()+diameter/4),
            new Vector3(centre.getX()-diameter/4, centre.getY()+diameter/4, centre.getZ()-diameter/4),
            new Vector3(centre.getX()+diameter/4, centre.getY()+diameter/4, centre.getZ()-diameter/4),
            new Vector3(centre.getX()-diameter/4, centre.getY()-diameter/4, centre.getZ()+diameter/4),
            new Vector3(centre.getX()+diameter/4, centre.getY()-diameter/4, centre.getZ()+diameter/4),
            new Vector3(centre.getX()-diameter/4, centre.getY()-diameter/4, centre.getZ()-diameter/4),
            new Vector3(centre.getX()+diameter/4, centre.getY()-diameter/4, centre.getZ()-diameter/4),};
    private CosmicComponent[] Nodes = new CosmicComponent[8];
    private Vector3 centreOfMass;

    public OctreeNode(double d, Vector3 p) {
        diameter = d;
        position = p;
    }

    @Override
    public boolean add(Body body) {
        double positionX = body.getMassCenter().getX();
        double positionY = body.getMassCenter().getY();
        double positionZ = body.getMassCenter().getZ();


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
