package model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;
import java.util.List;

import utils.DrawingUtils;

/**
 * Created by Karlo on 2017-05-11.
 */

public class Snake implements MyDrawable {

    private static final Color SNAKE_COLOR;
    private static GameWorld world;
    private Vector2 headPosition;
    private float size;
    private Vector2 velocity;
    private List<MyDirection> directions;
    private List<Vector2>turnPoints;

    static {
        SNAKE_COLOR = Color.GREEN;
    }

    public Snake(GameWorld world, float speed){
        this.world = world;
        this.directions = new LinkedList<MyDirection>();
        this.turnPoints = new LinkedList<Vector2>();
        this.velocity.x = speed;
        this.velocity.y = 0;
        this.headPosition = new Vector2(world.getWorldStartX() + world.getWorldWidth() / 2,
                                        world.getWorldStartY() + world.getWorldHeight() / 2);
    }

    public void increaseSize(float size){
        this.size += size;
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        //TODO proper drawing
        DrawingUtils.initShapeRendererFilled(renderer);

    }

    public void turn(MyDirection direction){
        //TODO changing velocity
        switch (direction){
            case UP:
                break;
            case DOWN:
                break;
            case RIGHT:
                break;
            case LEFT:
                break;
            default:

        }
    }

}

