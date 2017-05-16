package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import model.MyDirection;
import model.Snake;

/**
 * Created by Karlo on 2017-05-16.
 */

public class BaseGameController extends InputAdapter {

    private SnakeController snakeController;
    private FoodController foodController;

    public BaseGameController(SnakeController snakeController, FoodController foodController){
        super();
        this.snakeController = snakeController;
        this.foodController = foodController;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean wasInputHandled = false;
        switch (keycode) {
            case Input.Keys.DPAD_UP:
                snakeController.turnUp();
                wasInputHandled = true;
                break;
            case Input.Keys.DPAD_DOWN:
                snakeController.turnDown();
                wasInputHandled = true;
                break;
            case Input.Keys.DPAD_RIGHT:
                snakeController.turnRight();
                wasInputHandled = true;
                break;
            case Input.Keys.DPAD_LEFT:
                snakeController.turnLeft();
                wasInputHandled = true;
                break;
        }
        return wasInputHandled;
    }
}
