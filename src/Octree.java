public class Octree implements CosmicComponent{
    private Body body;
    private double mass;
    private Vector3 position;
    private CosmicComponent firstNode;
    private CosmicComponent secondNode;
    private CosmicComponent thirdNode;
    private CosmicComponent fourthNode;
    private CosmicComponent fifthNode;
    private CosmicComponent sixthNode;
    private CosmicComponent seventhNode;
    private CosmicComponent eigthNode;



    public Octree (Body body){
        this.body = body;
    }

    public Boolean add(Body body){
        return false;
    }

    public Boolean remove(Body body){
        return false;
    }

    public String getName(){
        return body.getName();
    }
}
