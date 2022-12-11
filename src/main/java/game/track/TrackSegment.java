package game.track;

import game.physics.PhysicsBody;
import game.physics.PolygonCollider;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class TrackSegment {

    protected PApplet parent;
    protected PVector position;
    protected TrackSegmentType type;

    private int sector;
    protected ArrayList<PolygonCollider> boundarys;

    protected final int trackWidth = 120;

    protected byte rotation = 0;

    public TrackSegment(PApplet parent, TrackSegmentType type, PVector position){
        this.parent = parent;
        this.type = type;
        this.position = position;
        this.boundarys = new ArrayList<>();
    }

    public void setRotation(byte rotation) { this.rotation = (byte) (rotation % 4);}
    public void setSector(int sector) {this.sector = sector;}
    public int getSector() {return this.sector;}
    public byte getRotation() {return  this.rotation;}
    public ArrayList<PolygonCollider> getBoundarys() {return boundarys;}

    private void calculateBoundaries() {}
    public void display() {}
}
