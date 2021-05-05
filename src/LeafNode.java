public class LeafNode implements  CosmicComponent{
    private Body body;

    public LeafNode(Body body){
        this.body = body;
    }

    public Body getBody(){
        return body;
    }

    public Boolean add(){
        return false;
    }
}
