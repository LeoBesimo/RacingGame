package game.player;

import game.physics.PolygonCollider;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Player extends PolygonCollider {

    //TODO: Increase backwards drag if there backwards acceleration

    private final PImage sprite;
    private PVector size = new PVector();
    public Player(PApplet parent, PVector position, PVector size, String spritePath) {
        super(parent, position);
        this.size = size;
        this.sprite = parent.loadImage(spritePath);
        this.addCornerPoint(new PVector(-size.x / 2, - size.y / 2));
        this.addCornerPoint(new PVector(size.x / 2, - size.y / 2));
        this.addCornerPoint(new PVector(size.x / 2, size.y / 2));
        this.addCornerPoint(new PVector(-size.x / 2, size.y / 2));
    }

    public void update(float dt){
        super.update(dt);
        PVector newVel = this.getVelocity().mult(0.998f);
        if(newVel.mag() < 1) newVel = new PVector();
        this.setVelocity(newVel);
        float newOmega = this.getOmega() * 0.95f;
        if(Math.abs(newOmega) < 0.01f) newOmega = 0;
        this.setOmega(newOmega);

        this.position.x = PApplet.constrain(this.position.x, 0, parent.width);
        this.position.y = PApplet.constrain(this.position.y, 0, parent.height);
    }

    public void display(){
        super.display();
        this.parent.pushMatrix();
        this.parent.translate(this.position.x,this.position.y);
        this.parent.rotate(this.getAngle());
        this.parent.image(sprite,0,0,this.size.x,this.size.y);
        this.parent.popMatrix();
    }
}
