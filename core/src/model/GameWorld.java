package model;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Karlo on 2017-05-11.
 */

public class GameWorld {

    private Vector2 worldDimensions;
    private Vector2 worldStart;

    public GameWorld(Vector2 worldDimensions, Vector2 worldStart){
        this.worldDimensions = worldDimensions;
        this.worldStart = worldStart;
    }

    public float getWorldWidth() {
        return worldDimensions.x;
    }

    public float getWorldHeight() {
        return worldDimensions.y;
    }

    public float getWorldStartX() {
        return worldStart.x;
    }

    public float getWorldStartY() {
        return worldStart.y;
    }
}
