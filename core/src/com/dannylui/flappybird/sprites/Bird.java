package com.dannylui.flappybird.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Danny on 10/22/2016.
 */
//what does a bird class need?
//needs to know it's position
//where the bird is in the game world
//needs to have a texture
//needs to be drawn to the screen
//needs a velocity and which direction it's going(up,down,right,left)
public class Bird {
    //vector3 holds a xyz position, since we're only in 2d we'll only use xy
    private Vector3 position;
    private Vector3 velocity;
    //private Texture bird; //no longer need since we have animated bird

    //gravity on our bird only
    private static final int GRAVITY = -15;

    private static final int MOVEMENT = 100;

    //collision detection, using an invisible bird
    private Rectangle bounds;

    //animating the bird to fly
    private Animation birdAnimation;
    private Texture texture;

    //bird flap sound
    private Sound flap;

    //starting position x and y
    public Bird(int x, int y){
        position = new Vector3(x, y, 0);
        //since we're starting and not moving, 0,0,0
        velocity = new Vector3(0, 0, 0);
        //bird = new Texture("bird.png"); //no longer need since we have animated bird
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight()); //bird.getWidth() changed to texture.getWidth()
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    //send the delta time to our bird class that will allow
    //it to do the math that will reset it's position in the game world
    public void update(float dt) {
        birdAnimation.update(dt);
        if (position.y > 0) { //if above ground add gravity
            //everytime the bird is updated we want to add gravity to it's velocity
            //only adding gravity to the y axis
            velocity.add(0, GRAVITY, 0);
        }
        //because we're doing this in relation to a change in time we need to scale
        //it's velocity by it's change in time
        //this will multiply everything by a delta time
        velocity.scl(dt);
        //set it's position
        position.add(MOVEMENT * dt, velocity.y, 0);

        if (position.y < 0) { //if below ground stop falling
            position.y = 0;
        }

        //reverses what we scaled previously
        velocity.scl(1/dt);

        //everyime bird moves need to update bounds
        bounds.setPosition(position.x, position.y);
    }

    public void jump() {
        //because there is gravity acting on the bird's y axis, it's velocity is
        //negative over time, we'll need to add a positive velocity for it to hop up
        velocity.y = 250;
        flap.play(0.5f); //50% volume
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
        flap.dispose();
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }
}
