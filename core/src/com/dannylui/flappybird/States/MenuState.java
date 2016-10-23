package com.dannylui.flappybird.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dannylui.flappybird.FlappyBird;

/**
 * Created by Danny on 10/22/2016.
 */

public class MenuState extends State {
    //variables to hold resources from assets folder
    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyBird.WIDTH / 2, FlappyBird.HEIGHT / 2); //for android port (end)
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        //while game is running menu always check for input
        handleInput();
    }

    //a spritebatch needs to open and close, open the box containing all the sprites and close the box afterwards
    //the sprites will be rendered onto the screen
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined); //for android port (end)
        //open the box
        sb.begin();
        //now we can start drawing/rendering onto the screen
        //0,0 is the bottom left corner of the screen where it will start to draw the image
        //specify the width and height of how much the bg will take up
        //sb.draw(background, 0, 0, FlappyBird.WIDTH, FlappyBird.HEIGHT);
        sb.draw(background, 0, 0); // for android port (end) otherwise use above
        //then we'll need to draw the play button, will default the width and height if not specified
        //sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y); //for android port (end) otherwise use above
        //close the box
        sb.end();

    }

    //when you transition states, dispose bg and play button to clean up memory
    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }
}
