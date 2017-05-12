package model;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Karlo on 2017-05-11.
 */

public enum MyDirection {
    RIGHT(1, 0),
    LEFT(-1, 0),
    UP(0, 1),
    DOWN(0, -1);

    private float x;
    private float y;

    MyDirection(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 getDirectionVector() {
        return new Vector2(x, y);
    }
}
