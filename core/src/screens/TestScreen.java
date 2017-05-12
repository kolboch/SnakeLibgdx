package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.List;

import controller.MyInputAdapter;
import model.Food;
import model.GameWorld;
import model.Snake;

/**
 * Created by Karlo on 2017-05-11.
 */

public class TestScreen implements Screen {

    private static final float WORLD_SIZE = 400;
    private static final float SNAKE_SPEED = 50f;
    private static final float SNAKE_WIDTH = 20f;

    private ShapeRenderer shapeRenderer;
    private ExtendViewport viewport;

    private Snake snake;
    private List<Food> food;
    private GameWorld world;

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        world = new GameWorld(new Vector2(WORLD_SIZE, WORLD_SIZE), new Vector2(0, 0));
        snake = new Snake(world, SNAKE_SPEED, SNAKE_WIDTH);
        viewport = new ExtendViewport(WORLD_SIZE, WORLD_SIZE);
        Gdx.input.setInputProcessor(new MyInputAdapter(snake));
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setColor(Color.YELLOW);

        snake.update(delta, viewport);
        snake.draw(shapeRenderer);

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
