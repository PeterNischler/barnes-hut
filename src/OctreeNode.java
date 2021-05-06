public class OctreeNode implements CosmicComponent {
    private Vector3 centre;
    private double diameter; //diameter of octant = edge length
    //ungerade bei x minus, gerade x immer plus
    //1-4 bei y immer plus, 5-8 bei y immer minus
    //1,2,5,6 bei z plus, 3,4,7,8 bei z minus
    private double mass;
    private Vector3[] centreNodes;
    private CosmicComponent[] nodes = new CosmicComponent[8];
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
            if (body.insideOfBoundary(diameter / 2, centreNodes[i])) {
                if (nodes[i] == null) {
                    nodes[i] = new LeafNode();
                } else if (nodes[i] instanceof LeafNode) {
                    Body otherBody = nodes[i].getBody();
                    nodes[i] = new OctreeNode(diameter / 2, centreNodes[i]);
                    nodes[i].add(otherBody);
                }
                return nodes[i].add(body);
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
            if (nodes[i] != null){
                if ((diameter / centreNodes[i].distance(body.getMassCenter())) < Simulation.T){
                    //gruppe zsm fassen
                }
                v = v.plus(nodes[i].calcForceOnBody(body));;
            }
        }
        return v;
    }

    public void drawTree2D(){
        for (int i = 0; i < 8; i++){
            if (nodes[i] != null){
                StdDraw.setPenColor(StdDraw.WHITE);
                System.out.println(centreNodes[i].getX());
                StdDraw.square(centreNodes[i].getX(), centreNodes[i].getY(), diameter/4);
            }
        }
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
