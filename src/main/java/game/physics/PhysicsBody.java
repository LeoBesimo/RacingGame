package game.physics;

import processing.core.PVector;

public class PhysicsBody {
    protected PVector position = new PVector();
    private PVector velocity = new PVector();
    private PVector force = new PVector();

    private float angle = 0;
    private float omega = 0;
    private float torque = 0;

    private float mass = 1;
    private float inertia = 1;
    private float restitution = 1;

    public PhysicsBody(){}
    public PhysicsBody(PVector position){
        this.position = position;
        //System.out.println(position.x);
    }

    public PhysicsBody(PVector position, PVector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public PhysicsBody(PVector position, PVector velocity, float mass){
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }

    public PhysicsBody(PVector position, PVector velocity, float mass, float restitution){
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.restitution = restitution;
    }

    public PVector getPosition() {return this.position;}
    public float getAngle() {return this.angle;}
    public PVector getVelocity() {return this.velocity;}
    public float getOmega() {return this.omega;}
    public PVector getForce() {return this.force;}
    public float getTorque() {return this.torque;}
    public float getMass() {return this.mass;}
    public float getInertia() {return this.inertia;}
    public float getRestitution() {return this.restitution;}

    public void setPosition(PVector position) {this.position = position;}
    public void setAngle(float angle){this.angle = angle;}

    public void setVelocity(PVector velocity) {this.velocity = velocity;}
    public void setOmega(float omega) {this.omega = omega;}
    public void setForce(PVector force) {this.force = force;}
    public void setTorque(float torque) {this.torque = torque;}
    public void setMass(float mass) {this.mass = mass;}
    public void setInertia(float inertia) {this.inertia = inertia;}
    public void setRestitution(float restitution) {this.restitution = restitution;}

    public void addAngle(float angle) {this.angle += angle;}
    public void addVelocity(PVector velocity) {this.velocity.add(velocity);}
    public void addOmega(float omega) {this.omega += omega;}
    public void addForce(PVector force) {this.force.add(force);}
    public void addTorque(float torque) {this.torque += torque;}
    public void update(float dt){
        if(dt <= 0 ) return;
        if(this.mass == 0) return;
        this.omega += this.torque * dt / this.inertia;
        this.torque = 0;
        this.angle += omega * dt;

        this.velocity.add(this.force.x * dt / this.mass, this.force.y * dt / this.mass);
        this.force.mult(0);
        this.position.add(this.velocity.x * dt, this.velocity.y * dt);
    }
}
