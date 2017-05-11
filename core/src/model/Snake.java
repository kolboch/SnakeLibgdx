package model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.List;

import utils.DrawingUtils;

import static model.MyDirection.RIGHT;

/**
 * Created by Karlo on 2017-05-11.
 */

public class Snake implements MyDrawable {

    private static final Color SNAKE_COLOR;
    private static GameWorld world;
    private float size;
    private float width;
    private Vector2 velocity;
    private MyDirection currentDirection;
    private Vector2 headPosition;
    private Vector2 tailPosition;
    private List<MyDirection> directions;
    private List<Vector2>turnPoints;
    private float snakeSpeed;

    static {
        SNAKE_COLOR = Color.GREEN;
    }

    public Snake(GameWorld world, float speed, float width){
        this.snakeSpeed = Math.abs(speed);
        this.width = width;
        this.world = world;
        this.directions = new LinkedList<MyDirection>();
        this.turnPoints = new LinkedList<Vector2>();
        this.velocity = new Vector2(snakeSpeed, 0);
        this.currentDirection = RIGHT;
        setHeadTailPosition();
    }

    private void setHeadTailPosition(){
        this.headPosition = new Vector2(world.getWorldStartX() + world.getWorldWidth() / 2,
                world.getWorldStartY() + world.getWorldHeight() / 2);
        this.tailPosition = headPosition;
    }

    public void increaseSize(float size){
        this.size += size;
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(SNAKE_COLOR);
        renderer.rect(headPosition.x, headPosition.y, width, width); // single square for tests
    }

    public void update(float delta, Viewport viewport){
        move(delta);
    }

    private void move(float delta){
        headPosition.x += velocity.x * snakeSpeed * delta;
        headPosition.y += velocity.y * snakeSpeed * delta;
    }

    public void turn(MyDirection direction){
        switch (direction){
            case UP:
                if(currentDirection != MyDirection.DOWN){
                    velocity.x = 0;
                    velocity.y = 1;
                }
                break;
            case DOWN:
                if(currentDirection != MyDirection.UP){
                    velocity.x = 0;
                    velocity.y = -1;
                }
                break;
            case RIGHT:
                if(currentDirection != MyDirection.LEFT){
                    velocity.x = 1;
                    velocity.y = 0;
                }
                break;
            case LEFT:
                if(currentDirection != MyDirection.RIGHT){
                    velocity.x = -1;
                    velocity.y = 0;
                }
                break;
        }
    }

}

