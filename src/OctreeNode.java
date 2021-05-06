public class OctreeNode implements CosmicComponent {
    private Vector3 centre;
    private double diameter; //diameter of octant = edge length
    //ungerade bei x minus, gerade x immer plus
    //1-4 bei y immer plus, 5-8 bei y immer minus
    //1,2,5,6 bei z plus, 3,4,7,8 bei z minus
    private double mass;
    private Vector3[] centreNodes;
    private CosmicComponent[] Nodes = new CosmicComponent[8];
    private Vector3 centreOfMass;

    public OctreeNode(double diameter, Vector3 centre) {
        this.diameter = diameter;
        this.centre = centre;
        centreNodes = new Vector3[]{new Vector3(new Vector3(-diameter / 4, diameter / 4, diameter / 4)), centre.plus(new Vector3(diameter / 4, diameter / 4, diameter / 4)),
                centre.plus(new Vector3(-diameter / 4, diameter / 4, -diameter / 4)),
                centre.plus(new Vector3(diameter / 4, diameter / 4, -diameter / 4)),
                centre.plus(new Vector3(-diameter / 4, -diameter / 4, diameter / 4)),
                centre.plus(new Vector3(diameter / 4, -diameter / 4, diameter / 4)),
                centre.plus(new Vector3(-diameter / 4, -diameter / 4, -diameter / 4)),
                centre.plus(new Vector3(diameter / 4, -diameter / 4, -diameter / 4)),};
    }

    @Override
    public Body getBody() {
        return null;
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
                    Nodes[i] = new OctreeNode(diameter / 2, centreNodes[i]);
                    Nodes[i].add(otherBody);
                }
                return Nodes[i].add(body);
            }
        }
        return false;
    }
 

    public Vector3 calcForceOnBody(Body body){
        if ((diameter / centre.distance(body.getMassCenter())) < Simulation.T) {
            return centre.times(mass);
        }
        Vector3 v = new Vector3();
        for (int i = 0; i < 8; i++){
            if (Nodes[i] != null){
                if ((diameter / centreNodes[i].distance(body.getMassCenter())) < Simulation.T){
                    //gruppe zsm fassen
                }
                v = v.plus(Nodes[i].calcForceOnBody(body));;
            }
        }
        return v;
    }

    /*public Body getBody(String name) {
        for (int i = 0; i < 8; i++) {
            if (Nodes[i] != null) {
                Body b = Nodes[i].getBody(name);
                if (b != null) {
                    return b;
                }
            }
        }
        return null;
    }*/
}
