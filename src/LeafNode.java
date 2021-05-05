public class LeafNode implements  CosmicComponent{
    private Body body;

    public LeafNode(Body body){
        this.body = body;
    }

    public Body getBody(){
        return body;
    }
    public Body getBody(String name){
        if (body.getName().equals(name)){
            return body;
        }
    }
    public Boolean add(Body body){
        return false;
    }
}
