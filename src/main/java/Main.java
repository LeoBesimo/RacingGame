import game.physics.CollisionData;
import game.physics.CollisionDetection;
import game.physics.CollisionSolver;
import game.physics.PolygonCollider;
import game.player.InputHandler;
import game.player.Player;
import game.track.Track;
import game.track.segments.StraightSegment;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Main extends PApplet{

    public void settings(){
        size(600,600);
    }
    float previous = 0;
    CollisionDetection detector = new CollisionDetection();
    CollisionSolver solver = new CollisionSolver();
    Player player = new Player(this,new PVector(300,300),new PVector(19 * 2.5f,12 * 2.5f),sketchPath("assets/sprites/player/car.png"));

    ArrayList<PolygonCollider> walls = new ArrayList<>();

    public InputHandler handler = new InputHandler();

    PolygonCollider box;

    public Track track;

    public void setup(){
        rectMode(CENTER);
        textAlign(CENTER,CENTER);
        imageMode(CENTER);

        track = new Track(this);

        PolygonCollider wall1 = new PolygonCollider(this, new PVector(0,height/2));
        PolygonCollider wall2 = new PolygonCollider(this, new PVector(width/2,0));
        PolygonCollider wall3 = new PolygonCollider(this, new PVector(width,height/2));
        PolygonCollider wall4 = new PolygonCollider(this, new PVector(width/2,height));
        PolygonCollider wall5 = new PolygonCollider(this,new PVector(150,150));
        float wallWidth = 10;
        wall1.addCornerPoint(new PVector(-wallWidth,-height/2));
        wall1.addCornerPoint(new PVector(wallWidth,-height/2));
        wall1.addCornerPoint(new PVector(wallWidth,height/2));
        wall1.addCornerPoint(new PVector(-wallWidth,height/2));
        wall1.setMass(0);

        wall2.addCornerPoint(new PVector(-width/2,-wallWidth));
        wall2.addCornerPoint(new PVector(width/2,-wallWidth));
        wall2.addCornerPoint(new PVector(width/2,wallWidth));
        wall2.addCornerPoint(new PVector(-width/2,wallWidth));
        wall2.setMass(0);

        wall3.addCornerPoint(new PVector(-wallWidth,-height/2));
        wall3.addCornerPoint(new PVector(wallWidth,-height/2));
        wall3.addCornerPoint(new PVector(wallWidth,height/2));
        wall3.addCornerPoint(new PVector(-wallWidth,height/2));
        wall3.setMass(0);

        wall4.addCornerPoint(new PVector(-width/2,-wallWidth));
        wall4.addCornerPoint(new PVector(width/2,-wallWidth));
        wall4.addCornerPoint(new PVector(width/2,wallWidth));
        wall4.addCornerPoint(new PVector(-width/2,wallWidth));
        wall4.setMass(0);

        wall5.addCornerPoint(new PVector(-50,-50));
        wall5.addCornerPoint(new PVector(50,50));
        wall5.setMass(0);

        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);
        walls.add(wall4);
        //walls.add(wall5);

        //TODO: change PolygonCollider Transform point generation to generate them new after a new Cornerpoint is added

        for(PolygonCollider wall : walls) wall.update(1);

        box = new PolygonCollider(this,new PVector(width/2,height/2));

        box.addCornerPoint(new PVector(-60,-2));
        box.addCornerPoint(new PVector(60,-2));
        box.addCornerPoint(new PVector(60,2));
        box.addCornerPoint(new PVector(-60,2));
        box.setMass(0);

        player.setRestitution(0.75f);

        handler.addKey('w', new Runnable() {
            @Override
            public void run() {
                player.addForce(PVector.fromAngle(player.getAngle()).mult(200));
            }
        });
        handler.addKey('s', new Runnable() {
            @Override
            public void run() {
                player.addForce(PVector.fromAngle(player.getAngle()).mult(-200));
            }
        });
        handler.addKey('a', new Runnable() {
            @Override
            public void run() {
                player.addTorque(-PI * 2.5f);
            }
        });
        handler.addKey('d', new Runnable() {
            @Override
            public void run() {
                player.addTorque(PI * 2.5f);
            }
        });
    }

    public void draw(){
        float deltaTime = (millis() - previous) / 1000;
        background(64);
        handler.update();
        player.update(deltaTime);

        track.display();


        ArrayList<PolygonCollider> boundaries = track.getBoundaries(0);
        for(PolygonCollider b : boundaries){
            CollisionData collisionData = detector.PolygonCollisionSatManifold(player, b);
            solver.resolve(collisionData,player,b);
            b.display();
        }

        CollisionData bm = detector.PolygonCollisionSatManifold(player,box);
        solver.resolve(bm,player,box);
        box.display();

        for(PolygonCollider wall: walls) {
            CollisionData m = detector.PolygonCollisionSatManifold(player, wall);
            solver.resolve(m, player, wall);
            //solver.correctPosition(m,player,wall);
            wall.display();
        }
        player.display();

        previous = millis();
    }

    public void mousePressed(){
    }

    public void keyPressed(){
        handler.onKeyPressed(key);
    }

    public void keyReleased(){
        handler.onKeyReleased(key);
    }

    public static void main(String[] args){
        PApplet.main("Main");
    }

}
