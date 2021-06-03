public interface CosmicComponent {

    public Body getBody();

   public Boolean add(Body body);

   //public void add(BodyStack bodies);

   Vector3 calcForceOnBody(Body body);

   //public void drawTree2D();

   public double getMass();

   public Vector3 getCentre();

   public String toString();

}