package gui;

import processing.core.PApplet;
import processing.core.PVector;

import gui.Button;

import java.util.ArrayList;

public class Scene {

    private PApplet parent;
    private ArrayList<Button> buttons = new ArrayList<>();

    public void addButton(Button button){
        this.buttons.add(button);
    }

    public void display(){
        //Scene Content

        for(Button button : this.buttons) button.display();
    }

    public void onClick(){
        for(Button button : this.buttons) button.onClick();
    }
}
