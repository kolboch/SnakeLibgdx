package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.List;

import static model.MyDirection.DOWN;
import static model.MyDirection.LEFT;
import static model.MyDirection.RIGHT;
import static model.MyDirection.UP;

/**
 * Created by Karlo on 2017-05-11.
 */

public class Snake implements MyDrawable {

    private static final Color SNAKE_COLOR;
    private static GameWorld world;

    private final String SNAKE_LOG_TAG = getClass().getSimpleName();

    private Vector2 snakeVelocity;
    private MyDirection currentDirection;
    private Vector2 headPosition;
    private Vector2 tailPosition;
    private List<MyDirection> directions;
    private List<Vector2> turnPoints;
    private float snakeSpeed;
    private float size;
    private float snakeWidth;

    static {
        SNAKE_COLOR = Color.GREEN;
    }

    public Snake(GameWorld world, float speed, float snakeWidth) {
        this.snakeSpeed = Math.abs(speed);
        this.snakeWidth = snakeWidth;
        this.world = world;
        this.directions = new LinkedList<MyDirection>();
        this.turnPoints = new LinkedList<Vector2>();
        setHeadTailPosition();
        initSnake();
    }

    private void initSnake() {
        this.snakeVelocity = new Vector2(1, 0);
        this.currentDirection = RIGHT;
        this.directions.add(currentDirection);
    }

    private void setHeadTailPosition() {
        this.headPosition = new Vector2(world.getWorldStartX() + world.getWorldWidth() / 2,
                world.getWorldStartY() + world.getWorldHeight() / 2);
        this.tailPosition = new Vector2(headPosition.x, headPosition.y);
        this.headPosition.x += world.getWorldWidth() / 10;
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(SNAKE_COLOR);
        drawSnake(renderer);
    }

    private void drawSnake(ShapeRenderer renderer) {
        int i = 0;
        if (turnPoints.size() > 0) {
            Vector2 tailSectionPoint = turnPoints.get(0);
            renderer.rectLine(tailPosition.x, tailPosition.y, tailSectionPoint.x, tailSectionPoint.y, snakeWidth);

            Vector2 adjustment;
            Vector2 beginning;
            Vector2 ending;
            for (; i + 1 < turnPoints.size(); i++) {
                adjustment = getPointAdjustment(directions.get(i + 1));
                beginning = turnPoints.get(i);
                ending = turnPoints.get(i + 1);
                renderer.rectLine(beginning.x - adjustment.x, beginning.y - adjustment.y, ending.x, ending.y, snakeWidth);
            }
        } else {
            renderer.rectLine(tailPosition, headPosition, snakeWidth);
        }

        if (i < turnPoints.size()) {
            Vector2 adjustment = getPointAdjustment(currentDirection);
            Vector2 headSectionPoint = turnPoints.get(i);
            renderer.rectLine(headSectionPoint.x - adjustment.x, headSectionPoint.y - adjustment.y, headPosition.x, headPosition.y, snakeWidth);
        }
        drawHead(renderer);
    }

    private void drawHead(ShapeRenderer renderer) {
        renderer.setColor(Color.RED);
        renderer.circle(headPosition.x, headPosition.y, 3);
    }

    public void update(float delta, Viewport viewport) {
        move(delta);
    }

    private void move(float delta) {
        updateHead(delta);
        updateTail(delta);
    }

    private void updateHead(float delta) {
        headPosition.x += snakeVelocity.x * snakeSpeed * delta;
        headPosition.y += snakeVelocity.y * snakeSpeed * delta;
    }

    private void updateTail(float delta) {
        if (directions.size() > 0) {
            Vector2 directionVector = directions.get(0).getDirectionVector();
            tailPosition.x += directionVector.x * snakeSpeed * delta;
            tailPosition.y += directionVector.y * snakeSpeed * delta;
            if (doesTailNeedUpdate()) {
                Vector2 nextTail = turnPoints.get(0);
                Vector2 tailAdjustment = getPointAdjustment(directions.get(1));
                tailPosition = new Vector2(nextTail.x - tailAdjustment.x, nextTail.y - tailAdjustment.y);
                removeLastTurnAndDirection();
            }
        }
    }

    private boolean doesTailNeedUpdate() {
        if (directions.size() == 0 || turnPoints.size() == 0) {
            return false;
        }
        MyDirection tailDirection = directions.get(0);
        Vector2 nextTailCandidate = turnPoints.get(0);
        Vector2 adjustment = getPointAdjustment(tailDirection);
        //notice that adjustment always extends section
        switch (tailDirection) {
            case UP:
                return tailPosition.y > nextTailCandidate.y - adjustment.y;
            case DOWN:
                return tailPosition.y < nextTailCandidate.y - adjustment.y;
            case RIGHT:
                return tailPosition.x > nextTailCandidate.x - adjustment.x;
            case LEFT:
                return tailPosition.x < nextTailCandidate.x - adjustment.x;
            default:
                return false;
        }
    }

    public void turn(MyDirection direction) {
        boolean wasHandled = false;
        MyDirection previous = currentDirection;
        switch (direction) {
            case UP:
                if (currentDirection != DOWN && currentDirection != UP) {
                    changeSnakeVelocity(0, 1);
                    currentDirection = UP;
                    wasHandled = true;
                }
                break;
            case DOWN:
                if (currentDirection != UP && currentDirection != DOWN) {
                    changeSnakeVelocity(0, -1);
                    currentDirection = DOWN;
                    wasHandled = true;
                }
                break;
            case RIGHT:
                if (currentDirection != LEFT && currentDirection != RIGHT) {
                    changeSnakeVelocity(1, 0);
                    currentDirection = RIGHT;
                    wasHandled = true;
                }
                break;
            case LEFT:
                if (currentDirection != RIGHT && currentDirection != LEFT) {
                    changeSnakeVelocity(-1, 0);
                    currentDirection = LEFT;
                    wasHandled = true;
                }
                break;
        }
        if (wasHandled) {
            directions.add(direction);
            turnPoints.add(adjustTurnPoint(new Vector2(headPosition.x, headPosition.y), previous)); // important, add before head adjustment
            adjustHeadPosition(previous, direction);
        }
    }

    private Vector2 getPointAdjustment(MyDirection direction) {
        Vector2 adjustment;
        switch (direction) {
            case UP:
                adjustment = new Vector2(0, snakeWidth / 2);
                break;
            case DOWN:
                adjustment = new Vector2(0, -(snakeWidth / 2));
                break;
            case RIGHT:
                adjustment = new Vector2(snakeWidth / 2, 0);
                break;
            case LEFT:
                adjustment = new Vector2(-(snakeWidth / 2), 0);
                break;
            default:
                adjustment = new Vector2(0, 0);
        }
        return adjustment;
    }

    private void removeLastTurnAndDirection() {
        if (directions.size() > 0) {
            directions.remove(0);
        }
        if (turnPoints.size() > 0) {
            turnPoints.remove(0);
        }
    }

    private void changeSnakeVelocity(float x, float y) {
        snakeVelocity.x = x;
        snakeVelocity.y = y;
    }

    private Vector2 adjustTurnPoint(Vector2 turnPoint, MyDirection direction) {
        return turnPoint.sub(getPointAdjustment(direction));
    }

    private void adjustHeadPosition(MyDirection previous, MyDirection following) {
        headPosition.sub(getPointAdjustment(previous));
        headPosition.add(getPointAdjustment(following));
    }

    public void increaseSize(float size) {
        this.size += size;
    }
}

