package controller;

import model.MyDirection;
import model.Snake;

/**
 * Created by Karlo on 2017-05-16.
 */

public class SnakeController {

    private Snake snake;

    public SnakeController(Snake snake){
        this.snake = snake;
    }

    public void turnRight(){
        snake.turn(MyDirection.RIGHT);
    }

    public void turnLeft(){
        snake.turn(MyDirection.LEFT);
    }

    public void turnUp(){
        snake.turn(MyDirection.UP);
    }

    public void turnDown(){
        snake.turn(MyDirection.DOWN);
    }
}
