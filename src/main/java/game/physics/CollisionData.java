package game.physics;

import processing.core.PVector;

public class CollisionData {
    public PVector normal;
    public float penetration;
    public boolean collided;

    public CollisionData(){
        this.normal = new PVector();
        this.penetration = 0.f;
        this.collided = false;
    }
}
