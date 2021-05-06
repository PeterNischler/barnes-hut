public interface CosmicComponent {

    public Body getBody();

   public Boolean add(Body body);

   Vector3 calcForceOnBody(Body body);

   public void drawTree2D();

   public double getMass();
   public Vector3 getCentre();

}