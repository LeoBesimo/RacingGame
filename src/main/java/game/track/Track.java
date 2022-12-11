package game.track;

import game.physics.PolygonCollider;
import game.track.segments.StraightSegment;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Track {

    private PApplet parent;

    private ArrayList<TrackSegment> segments;

    public Track(PApplet parent){
        this.parent = parent;
        segments = new ArrayList<>();
        this.segments.add(new StraightSegment(parent, new PVector(300,100), 120));
    }

    public void addSegment(TrackSegment segment) {
        this.segments.add(segment);
    }

    public ArrayList<PolygonCollider> getBoundaries(int sector){
        for(TrackSegment segment : this.segments){
            if(sector == segment.getSector()) return segment.getBoundarys();
        }
        return new ArrayList<>();
    }

    public void display(){
        for(TrackSegment segment: this.segments) segment.display();
    }
}
