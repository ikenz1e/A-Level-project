package handlers;

import utils.State;

public class StateHandler {

    private State currentState = State.GAME;

    public void changeState(State newState){
        if(currentState != newState){
            currentState = newState;
        }
    }

    public State getCurrentState(){
        return currentState;
    }
    
}
