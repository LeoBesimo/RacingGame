package game.player;

import java.util.HashMap;
import java.util.Set;

public class InputHandler {

    private HashMap<Character,Boolean> inputs = new HashMap<Character, Boolean>();
    private Set<Character> keys;
    private HashMap<Character, Runnable> executable = new HashMap<Character, Runnable>();
    public InputHandler(){
        keys = inputs.keySet();
    }

    public void addKey(char key, Runnable executionFunction){
        inputs.put(key, false);
        executable.put(key, executionFunction);
        keys = inputs.keySet();
    }

    public void onKeyPressed(char key){
        if(keys.contains(key))
            inputs.put(key,true);
    }

    public void onKeyReleased(char key){
        if(keys.contains(key))
            inputs.put(key, false);
    }

    public void onMousePressed(int button){

    }

    public void update(){
        for(char key : keys){
            if(inputs.get(key)) executable.get(key).run();
        }
    }
}
