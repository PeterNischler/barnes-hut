public class LeafNode implements  CosmicComponent{
    private Body body;

    public LeafNode(){
    }

    public Body getBody(){
        return body;
    }

    public Body getBody(String name){
        if (body.getName().equals(name)){
            return body;
        }
        else return null;
    }

    //calculates the gravitational force exerted by this body on body
    public Vector3 calcForceOnBody(Body body){
        return body.gravitationalForce(this.body);
    }

    public Boolean add(Body body){
        this.body = body;
        return true;
    }

    /*public void add(BodyStack bodies){
        this.body = bodies.pop();
        //return true;
    }*/

    public void drawTree2D(){

    }

    @Override
    public double getMass() {
        return body.getMass();
    }

    @Override
    public Vector3 getCentre() {
        return body.getMassCenter();
    }

    public String toString() { return body.toString();}
}
