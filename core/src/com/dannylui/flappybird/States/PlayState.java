package com.dannylui.flappybird.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dannylui.flappybird.FlappyBird;
import com.dannylui.flappybird.sprites.Bird;
import com.dannylui.flappybird.sprites.Tube;
import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by Danny on 10/22/2016.
 */

public class PlayState extends State {
    private Bird bird;
    private Texture bg;

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private Array<Tube> tubes;

    //using a single ground texture, reposition it again and again
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private static final int GROUND_Y_OFFSET = -50;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);

        //set the camera for what we see as the game view, bottom left and half the screen size
        cam.setToOrtho(false, FlappyBird.WIDTH / 2, FlappyBird.HEIGHT / 2);

        bg = new Texture("bg.png");

        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<Tube>();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        //need to handleInput everytime
        handleInput();
        updateGround();
        bird.update(dt);

        //camera will follow the bird, offset 80
        cam.position.x = bird.getPosition().x + 80;

        //logic when we reposition a tube when its not in the camera viewport
        for (int i = 0; i < tubes.size; i++) {
            Tube tube = tubes.get(i);
            //if a tube is off to the left side of the screen
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())){
                gsm.set(new PlayState(gsm));
            }
        }

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            gsm.set(new PlayState(gsm));

        cam.update(); //tells libGDX that the cam has been repositioned
    }

    @Override
    public void render(SpriteBatch sb) {
        //need to adjust the sprite batch so it knows the coordinates
        //that we're working with in relation to our camera
        //where it needs to draw things on the screen in relation to our camera
        //it won't draw things that are outside the camera
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        sb.end();

    }

    private void updateGround(){
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube : tubes) {
            tube.dispose();
        }
        System.out.println("Play State Disposed");
    }
}
