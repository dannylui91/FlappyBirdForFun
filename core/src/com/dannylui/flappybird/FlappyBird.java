package com.dannylui.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dannylui.flappybird.States.GameStateManager;
import com.dannylui.flappybird.States.MenuState;

public class FlappyBird extends ApplicationAdapter {
	//width and height of screen
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Flappy Bird";

	private GameStateManager gsm;
	private SpriteBatch batch;//should have only one sb in the game

	//in libGDX sound clips are loaded in ram, long background music are loaded in the disk
	//this will be the background song on a loop
	//bird flap sound will be in bird class
	private Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1); //wipes the screen clean and spritebatch will redraw everything fresh
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		super.dispose();
		music.dispose();
	}
}
