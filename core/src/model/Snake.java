package model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.List;

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
    private List<Vector2> turnPoints;
    private float snakeSpeed;

    static {
        SNAKE_COLOR = Color.GREEN;
    }

    public Snake(GameWorld world, float speed, float width) {
        this.snakeSpeed = Math.abs(speed);
        this.width = width;
        this.world = world;
        this.directions = new LinkedList<MyDirection>();
        this.turnPoints = new LinkedList<Vector2>();
        setHeadTailPosition();
        initSnake();
    }

    private void initSnake() {
        this.velocity = new Vector2(1, 0);
        this.currentDirection = RIGHT;
        this.directions.add(currentDirection);
//        this.turnPoints.add(new Vector2(headPosition.x, headPosition.y));
    }

    private void setHeadTailPosition() {
        this.headPosition = new Vector2(world.getWorldStartX() + world.getWorldWidth() / 2,
                world.getWorldStartY() + world.getWorldHeight() / 2);
        this.tailPosition = new Vector2(headPosition.x, headPosition.y);
        this.headPosition.x += 30; // just for TESTING, must delete this
    }

    public void increaseSize(float size) {
        this.size += size;
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(SNAKE_COLOR);
        drawSnake(renderer);
    }

    private void drawSnake(ShapeRenderer renderer) {
        int i = 0;
        if (turnPoints.size() > 0) {
            renderer.rectLine(tailPosition, turnPoints.get(0), width);
        } else {
            renderer.rectLine(tailPosition, headPosition, width);
        }
        for (; i + 1 < turnPoints.size(); i++) {
            renderer.rectLine(turnPoints.get(i), turnPoints.get(i + 1), width);
        }
        if (i < turnPoints.size()) {
            renderer.rectLine(turnPoints.get(i), headPosition, width);
        }
    }

    public void update(float delta, Viewport viewport) {
        move(delta);
    }

    private void move(float delta) {
        updateHead(delta);
        updateTail(delta);
    }

    private void updateHead(float delta) {
        headPosition.x += velocity.x * snakeSpeed * delta;
        headPosition.y += velocity.y * snakeSpeed * delta;
    }

    private void updateTail(float delta) {
        if (directions.size() > 0) {
            Vector2 directionVector = directions.get(0).getDirectionVector();
            tailPosition.x += directionVector.x * snakeSpeed * delta;
            tailPosition.y += directionVector.y * snakeSpeed * delta;
            if (doesTailNeedUpdate()) {
                Vector2 nextTail = turnPoints.get(0);
                tailPosition = new Vector2(nextTail.x, nextTail.y);
                removeLastTurnAndDirection();
            }
        }
    }

    private void removeLastTurnAndDirection() {
        if (directions.size() > 0) {
            directions.remove(0);
        }
        if (turnPoints.size() > 0) {
            turnPoints.remove(0);
        }
    }

    private boolean doesTailNeedUpdate() {
        if (directions.size() == 0 || turnPoints.size() == 0) {
            return false;
        }
        MyDirection tailDirection = directions.get(0);
        Vector2 nextTailCandidate = turnPoints.get(0);
        switch (tailDirection) {
            case UP:
                return tailPosition.y > nextTailCandidate.y;
            case DOWN:
                return tailPosition.y < nextTailCandidate.y;
            case RIGHT:
                return tailPosition.x > nextTailCandidate.x;
            case LEFT:
                return tailPosition.x < nextTailCandidate.x;
            default:
                return false;
        }
    }

    public void turn(MyDirection direction) {
        boolean wasHandled = false;
        switch (direction) {
            case UP:
                if (currentDirection != MyDirection.DOWN) {
                    velocity.x = 0;
                    velocity.y = 1;
                    currentDirection = MyDirection.UP;
                    wasHandled = true;
                }
                break;
            case DOWN:
                if (currentDirection != MyDirection.UP) {
                    velocity.x = 0;
                    velocity.y = -1;
                    currentDirection = MyDirection.DOWN;
                    wasHandled = true;
                }
                break;
            case RIGHT:
                if (currentDirection != MyDirection.LEFT) {
                    velocity.x = 1;
                    velocity.y = 0;
                    currentDirection = MyDirection.RIGHT;
                    wasHandled = true;
                }
                break;
            case LEFT:
                if (currentDirection != MyDirection.RIGHT) {
                    velocity.x = -1;
                    velocity.y = 0;
                    currentDirection = MyDirection.LEFT;
                    wasHandled = true;
                }
                break;
        }
        if (wasHandled) {
            directions.add(direction);
            turnPoints.add(new Vector2(headPosition.x, headPosition.y));
        }
    }

}

