public class OctreeNode implements CosmicComponent {
    private CosmicComponent firstNode;
    private CosmicComponent secondNode;
    private CosmicComponent thirdNode;
    private CosmicComponent fourthNode;
    private CosmicComponent fifthNode;
    private CosmicComponent sixthNode;
    private CosmicComponent seventhNode;
    private CosmicComponent eigthNode;

    public OctreeNode() {

    }
    @Override
    public boolean add(Body body) {
        return false;
    }

    @Override
    public String getName() {
        return n;
    }
}
