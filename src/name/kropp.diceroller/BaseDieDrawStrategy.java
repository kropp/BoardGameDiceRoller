package name.kropp.diceroller;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public abstract class BaseDieDrawStrategy implements DieDrawStrategy {
    @Override
    public View draw(Die die, int size, int height, Context context) {
        Drawable drawable = getDieDrawable(context, die, size);

        Bitmap canvasBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);

        drawable.setBounds(0, 0, size, size);
        drawable.draw(canvas);

        drawFace(size, size, canvas, die, context);

        ImageView image = new ImageView(context);
        image.setImageBitmap(canvasBitmap);

        return image;
    }

    protected abstract Drawable getDieDrawable(Context context, Die die, int size);

    protected void drawFace(int width, int height, Canvas canvas, Die die, Context context) {
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(24);
        paint.setFakeBoldText(true);
        paint.setColor(die.getColor());

        String s = String.valueOf(die.getCurrentValue());
        Rect bounds = new Rect();
        paint.getTextBounds(s, 0, s.length(), bounds);
        canvas.drawText(s, width / 2, (height - (bounds.top - bounds.bottom)) / 2, paint);
    }

    protected void setPaint(Die die, ShapeDrawable result) {
        Paint paint = result.getPaint();
        paint.setColor(die.getDieColor());
        paint.setStrokeWidth(2);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }
}
