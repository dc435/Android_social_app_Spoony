package com.finalproject.comp90018;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyApp extends ApplicationAdapter {

	private SpriteBatch batch;
	private boolean pickUp;

	private TextureRegion campassTextureRegion;
	private int campassX;
	private int campassY;

	private TextureRegion pointerTextureRegion;
	private int pointerX;
	private int pointerY;
    private float pointerRotation;

    private TextureRegion pickUpTextureRegion;
    private int pickUpX;
    private int pickUpY;

	@Override
	public void create () {
		batch = new SpriteBatch();
		pickUp = false;

		Texture campassTexture = new Texture(Gdx.files.internal("campass.png"));
		campassTextureRegion = new TextureRegion(campassTexture);
		campassX = (Gdx.graphics.getWidth() - campassTexture.getWidth()) / 2;
		campassY = (Gdx.graphics.getHeight() - campassTexture.getHeight()) / 2;

		Texture pointerTexture = new Texture(Gdx.files.internal("pointer.png"));
		pointerTextureRegion = new TextureRegion(pointerTexture);
		pointerX = (Gdx.graphics.getWidth() - pointerTexture.getWidth()) / 2;
		pointerY = (Gdx.graphics.getHeight() - pointerTexture.getHeight()) / 2;
		pointerRotation = 0;

		Texture pickUpTexture = new Texture(Gdx.files.internal("pick_up.png"));
		pickUpTextureRegion = new TextureRegion(pickUpTexture);
		pickUpX = (Gdx.graphics.getWidth() - pickUpTexture.getWidth()) / 2;
		pickUpY = 200;
	}

	@Override
	public void render () {

		float azimuth = Gdx.input.getAzimuth();
		float pitch = Gdx.input.getPitch();

		pointerRotation = azimuth;
		pickUp = pitch < -45;

		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(campassTextureRegion, campassX, campassY);
		batch.draw(pointerTextureRegion, pointerX, pointerY,
				(float)pointerTextureRegion.getRegionWidth()/2,
				(float)pointerTextureRegion.getRegionHeight()/2,
				pointerTextureRegion.getRegionWidth(),
				pointerTextureRegion.getRegionHeight(),
				1, 1, pointerRotation);
		if (pickUp) {
			batch.draw(pickUpTextureRegion, pickUpX, pickUpY);
		}
		batch.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {

	}
}
