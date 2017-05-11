package utils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Karlo on 2017-05-11.
 */

public class DrawingUtils {

    public static ShapeRenderer initShapeRendererFilled(ShapeRenderer renderer){
        if(renderer != null){
            if(!renderer.isDrawing()){
                renderer.begin(ShapeRenderer.ShapeType.Filled);
            }else{
                renderer.end();
                renderer.begin(ShapeRenderer.ShapeType.Filled);
            }
        }else{
            renderer = new ShapeRenderer();
            renderer.begin(ShapeRenderer.ShapeType.Filled);
        }
        return renderer;
    }

}
