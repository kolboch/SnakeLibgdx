package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import model.MyDirection;
import model.Snake;

/**
 * Created by Karlo on 2017-05-12.
 */

public class MyInputAdapter extends InputAdapter {

    private Snake snakeToControl;

    public MyInputAdapter(Snake snake){
        super();
        this.snakeToControl = snake;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean wasInputHandled = false;
        switch (keycode) {
            case Input.Keys.DPAD_UP:
                snakeToControl.turn(MyDirection.UP);
                wasInputHandled = true;
                break;
            case Input.Keys.DPAD_DOWN:
                snakeToControl.turn(MyDirection.DOWN);
                wasInputHandled = true;
                break;
            case Input.Keys.DPAD_RIGHT:
                snakeToControl.turn(MyDirection.RIGHT);
                wasInputHandled = true;
                break;
            case Input.Keys.DPAD_LEFT:
                snakeToControl.turn(MyDirection.LEFT);
                wasInputHandled = true;
                break;
        }
        return wasInputHandled;
    }
}
