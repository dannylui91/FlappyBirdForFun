package com.dannylui.flappybird.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Danny on 10/22/2016.
 */

public class GameStateManager {
    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop().dispose();
    }

    //sometimes we'll need to pop and instantly push a new state, call this set
    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    //update and render the top object of our stack
    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
