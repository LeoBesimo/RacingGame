package game.track.segments;

import game.physics.PolygonCollider;
import game.track.TrackSegment;
import game.track.TrackSegmentType;
import processing.core.PApplet;
import processing.core.PVector;

public class StraightSegment extends TrackSegment {

    float length;

    private void calculateBoundaries(){
        PolygonCollider b1 = new PolygonCollider(this.parent, this.position);
        PolygonCollider b2 = new PolygonCollider(this.parent, this.position);

        byte r = (byte) (this.rotation % 2);

        switch (r) {
            case 0 -> {
                b1.addCornerPoint(new PVector(-this.length / 2, -this.trackWidth / 2 - 5));
                b1.addCornerPoint(new PVector(this.length / 2, -this.trackWidth / 2 -5));
                b1.addCornerPoint(new PVector(this.length / 2, -this.trackWidth / 2 + 5));
                b1.addCornerPoint(new PVector(-this.length / 2, -this.trackWidth / 2 + 5));

                //b2.addCornerPoint(new PVector(-this.length / 2, this.trackWidth / 2));
                //b2.addCornerPoint(new PVector(this.length / 2, this.trackWidth / 2));
            }
            case 1 -> {
                //b1.addCornerPoint(new PVector(-this.trackWidth / 2, -this.length / 2));
                //b1.addCornerPoint(new PVector(-this.trackWidth / 2, this.length / 2));
                //b2.addCornerPoint(new PVector(this.trackWidth / 2, -this.length / 2));
                //b2.addCornerPoint(new PVector(this.trackWidth / 2, this.length / 2));
            }
        }

        b1.setMass(0);
        b2.setMass(0);

        b1.update(0);

        this.boundarys.add(b1);
        //this.boundarys.add(b2);
    }

    public StraightSegment(PApplet parent, PVector position, float segmentLength) {
        super(parent, TrackSegmentType.STRAIGHT, position);
        int nSegments = (int) (segmentLength % this.trackWidth);
        System.out.println(nSegments);
        if(nSegments == 0) this.length = segmentLength;
        else this.length = this.trackWidth * nSegments;
        this.calculateBoundaries();
    }

    public void display(){
        this.parent.pushMatrix();

        this.parent.translate(this.position.x,this.position.y);
        this.parent.rotate(PApplet.HALF_PI * rotation);
        this.parent.fill(128);
        this.parent.noStroke();
        this.parent.rect(0,0,this.length, this.trackWidth);

        this.parent.stroke(0);
        this.parent.line(-this.length / 2, -this.trackWidth / 2f, this.length / 2, -this.trackWidth / 2f);
        this.parent.line(-this.length / 2, this.trackWidth / 2f, this.length / 2, this.trackWidth / 2f);
        this.parent.popMatrix();
    }
}
