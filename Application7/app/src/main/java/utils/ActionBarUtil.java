package utils;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ActionBarUtil {



    public Drawable getDrawableDIY(){
        Drawable drawable = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawRGB(241,69,127);
            }

            @Override
            public void setAlpha(int alpha) {
                alpha = 5;
            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 2;
            }
        };
                return drawable;
    }

}
