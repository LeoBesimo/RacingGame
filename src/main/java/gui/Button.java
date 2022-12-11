package gui;

import processing.core.PApplet;
import processing.core.PVector;

public class Button {

    private Runnable onClickEvent;
    public PApplet parent;

    private PVector position = new PVector();
    private PVector size = new PVector();

    private String buttonText = "";

    public Button(PApplet parent, Runnable onClickEvent) {
        this.parent = parent;
        this.onClickEvent = onClickEvent;
    }

    public Button(PApplet parent, Runnable onClickEvent, PVector position, PVector size){
        this.parent = parent;
        this.onClickEvent = onClickEvent;
        this.position = position;
        this.size = size;
    }

    public Button(PApplet parent, Runnable onClickEvent, PVector position, PVector size, String buttonText){
        this.parent = parent;
        this.onClickEvent = onClickEvent;
        this.position = position;
        this.size = size;
        this.buttonText = buttonText;
    }

    public void setSize(PVector size) {this.size = size;}
    public void setPosition(PVector position) {this.position = position;}

    public PVector getPosition() {
        return this.position;
    }
    public PVector getSize(){
        return this.size;
    }

    public boolean onClick(){
        if(this.parent.mouseX > this.position.x - this.size.x / 2 &&
        this.parent.mouseX < this.position.x + this.size.x / 2 &&
        this.parent.mouseY > this.position.y - this.size.y / 2 &&
        this.parent.mouseY < this.position.y + this.size.y / 2){
            onClickEvent.run();
            return true;
        }
        return false;
    }

    public void display(){
        this.parent.stroke(0);
        this.parent.fill(255);
        this.parent.rect(this.position.x, this.position.y, this.size.x, this.size.y);
        this.parent.fill(0);
        this.parent.text(this.buttonText, this.position.x, this.position.y);
    }


}
