package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    protected final Arkanoids game;
    protected Texture platformImage = new Texture(Gdx.files.internal("block.png"));;
    protected OrthographicCamera camera = new OrthographicCamera();
    protected Rectangle platform  = new Rectangle();
    protected Vector3 touchPos = new Vector3();

    public GameScreen(final Arkanoids game) {
        this.game = game;

        camera.setToOrtho(false, Parameters.windowsWidth, Parameters.windowsHeight);

        platform.x = (Parameters.windowsWidth - Parameters.platformWidth) / 2;
        platform.y = Parameters.platformBottom;

        platform.width = Parameters.platformWidth;
        platform.height = Parameters.platformHeight;
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(127, 255, 212,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(platformImage, platform.x, platform.y, platform.width, platform.height);
        game.batch.end();

        // управление мышкой

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            platform.x = (touchPos.x - Parameters.platformWidth) / 2;
        }

        // управлением с клавиатуры
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            platform.x -= Parameters.speedPlatform * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            platform.x += Parameters.speedPlatform * Gdx.graphics.getDeltaTime();
        }

        // граница
        if (platform.x < 0) {
            platform.x = 0;
        }
        if (platform.x > Parameters.windowsWidth - Parameters.platformWidth) {
            platform.x = Parameters.windowsWidth - Parameters.platformWidth;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        platformImage.dispose();
    }

}