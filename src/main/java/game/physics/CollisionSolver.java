package game.physics;

import processing.core.PVector;

public class CollisionSolver {

    public CollisionSolver(){}

    public void resolve(CollisionData m, PolygonCollider a, PolygonCollider b){
        PVector velA = a.getVelocity();
        PVector velB = b.getVelocity();
        PVector rv = new PVector(velB.x - velA.x, velB.y - velA.y);

        float aInvMass = a.getMass() == 0 ? 0 : a.getMass();
        float bInvMass = b.getMass() == 0 ? 0 : b.getMass();

        float velAlongNormal = PVector.dot(m.normal,rv);
        if(velAlongNormal >= 0) return;

        float e = Math.min(a.getRestitution(), b.getRestitution());

        float j = -(1 + e) * velAlongNormal;
        j /= (aInvMass + bInvMass);

        PVector impulse = new PVector(m.normal.x * j, m.normal.y * j);

        a.addVelocity(impulse.mult(-aInvMass));
        b.addVelocity(impulse.mult(bInvMass));
        correctPosition(m,a,b);
    }

    public void correctPosition(CollisionData m, PolygonCollider a, PolygonCollider b){
        //float factor = a.getMass() == 0 || b.getMass() == 0? 1 : 0.5f;
        if(a.getMass() != 0) a.position.add(m.normal.mult(-m.penetration * 0.5f));
        if(b.getMass() != 0) b.position.add(m.normal.mult(m.penetration * 0.5f));
    }

}
