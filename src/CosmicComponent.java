public interface CosmicComponent {

    public Body getBody();

   public Boolean add(Body body);

   Vector3 calcForceOnBody(Body body);


}