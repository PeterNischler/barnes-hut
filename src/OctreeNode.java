public class OctreeNode implements CosmicComponent {
    private Body body;
    private Vector3 centre;
    private double diameter; //diameter of octant = edge length
    //ungerade bei x minus, gerade x immer plus
    //1-4 bei y immer plus, 5-8 bei y immer minus
    //1,2,5,6 bei z plus, 3,4,7,8 bei z minus
    private Vector3[] centreNodes = new Vector3[]{        new Vector3(new Vector3(-diameter / 4, diameter / 4, diameter / 4)),
            centre.plus(new Vector3(diameter / 4, diameter / 4, diameter / 4)),
            centre.plus(new Vector3(-diameter / 4, diameter / 4, -diameter / 4)),
            centre.plus(new Vector3(diameter / 4, diameter / 4, -diameter / 4)),
            centre.plus(new Vector3(-diameter / 4, -diameter / 4, diameter / 4)),
            centre.plus(new Vector3(diameter / 4, -diameter / 4, diameter / 4)),
            centre.plus(new Vector3(-diameter / 4, -diameter / 4, -diameter / 4)),
            centre.plus(new Vector3(diameter / 4, -diameter / 4, -diameter / 4)),};
    private CosmicComponent[] Nodes = new CosmicComponent[8];
    private Vector3 centreOfMass;

    public OctreeNode(double diameter, Vector3 centre) {
        this.diameter = diameter;
        this.centre = centre;
    }

    @Override
    public Boolean add(Body body) {
        if (body.insideOfBoundary(diameter, centre) == false) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (body.insideOfBoundary(Simulation.Diameter / 2, centreNodes[i])) {
                if (Nodes[i] == null) {
                    Nodes[i] = new LeafNode(body);
                } else if (Nodes[i] instanceof LeafNode) {
                    Body otherBody = Nodes[i].getBody();
                    Nodes[i] = new OctreeNode();
                    Nodes[i].add(otherBody);
                }
                return Nodes[i].add(body);
            }
        }
        return false;
    }

    public Body getBody(String name) {
        for (int i = 0; i < 8; i++){
            if (Nodes[i] instanceof LeafNode){
                if (Nodes[i].getBody().getName().equals(name)){
                    return Nodes[i].getBody();
                }
            } else {
                return Nodes[i].get(name);
            }
        }
        while
        return false;
    }

    @Override
    public String getName() {
        return "n";
    }
    @Override
    public String getName() {
        return "n";
    }
}
