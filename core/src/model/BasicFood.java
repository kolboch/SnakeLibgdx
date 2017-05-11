package model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

import utils.DrawingUtils;

/**
 * Created by Karlo on 2017-05-11.
 */

public class BasicFood extends Food {

    private static final Color FOOD_COLOR = Color.RED;
    private static Random random;
    private static GameWorld world;
    private float radius;

    static {
        random = new Random();
    }

    public BasicFood(GameWorld world, float radius) {
        if(this.world == null){
            this.world = world;
        }
        this.radius = radius;
        this.position = generateRandomPosition();
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer = DrawingUtils.initShapeRendererFilled(renderer);
        renderer.setColor(FOOD_COLOR);
        renderer.circle(this.position.x, this.position.y, this.radius);
    }

    private Vector2 generateRandomPosition() {
        float x = world.getWorldWidth() * random.nextFloat() + world.getWorldStartX();
        float y = world.getWorldHeight() * random.nextFloat() + world.getWorldStartY();
        return new Vector2(x, y);
    }
}
