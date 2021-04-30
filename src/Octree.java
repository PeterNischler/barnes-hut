public class Octree {
    private Body head;
    private Octree firstNode;
    private Octree secondNode;
    private Octree thirdNode;
    private Octree fourthNode;


    public Octree (Body head){
        this.head = head;
    }

    public Boolean add(Body body){
        return false;
    }

}
