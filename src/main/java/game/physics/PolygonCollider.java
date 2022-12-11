package game.physics;

import com.jogamp.graph.geom.SVertex;
import com.sun.source.tree.ParenthesizedTree;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class PolygonCollider extends PhysicsBody{

    private ArrayList<PVector> cornerPoints;
    private ArrayList<PVector> transformedCornerPoints;
    protected PApplet parent;

    public PolygonCollider(PApplet parent){
        super();
        this.parent = parent;
        this.cornerPoints = new ArrayList<>();
        this.transformedCornerPoints = new ArrayList<>();
    }

    public PolygonCollider(PApplet parent, PVector position){
        super(position);
        //this.position = position;
        //System.out.println(position.x);
        this.parent = parent;
        this.cornerPoints = new ArrayList<>();
        this.transformedCornerPoints = new ArrayList<>();
    }

    public PolygonCollider(PApplet parent, PVector position, ArrayList<PVector> cornerPoints){
        super(position);
        this.position = position;
        this.parent = parent;
        this.cornerPoints = cornerPoints;
    }

    public void addCornerPoint(PVector corner){
        this.cornerPoints.add(corner);
        this.update(0);
    }

    public ArrayList<PVector> getOriginalCornerPoints() {return this.cornerPoints;}
    public ArrayList<PVector> getTransformedCornerPoints() {return this.transformedCornerPoints;}

    @Override
    public void update(float dt){
        super.update(dt);
        float s = PApplet.sin(this.getAngle());
        float c = PApplet.cos(this.getAngle());

        transformedCornerPoints.clear();

        for(PVector p : cornerPoints){
            float newX = this.position.x + (p.x * c) + (p.y * -s);
            float newY = this.position.y + (p.x * s) + (p.y * c);
            PVector transformed = new PVector(newX, newY);
            transformedCornerPoints.add(transformed);
        }
    }

    public void display()
    {
        parent.stroke(255,0,255);
        parent.noFill();
        parent.beginShape();
        for(PVector p : transformedCornerPoints) {
            parent.vertex(p.x,p.y);
        }
        parent.endShape(PApplet.CLOSE);
    }
}
