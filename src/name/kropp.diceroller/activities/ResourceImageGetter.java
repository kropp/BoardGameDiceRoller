package name.kropp.diceroller.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class ResourceImageGetter implements Html.ImageGetter {
    private static ResourceImageGetter ourInstance;

    private final Context myContext;

    private ResourceImageGetter(Context context) {
        myContext = context;
    }

    public static ResourceImageGetter getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new ResourceImageGetter(context);
        return ourInstance;
    }

    @Override
    public Drawable getDrawable(String s) {
        Resources resources = myContext.getResources();
        int id = resources.getIdentifier(s, "drawable", myContext.getPackageName());
        Drawable d = resources.getDrawable(id);
        d.setBounds(0, 0, getImageSize(), getImageSize());
        return d;
    }

    private int getImageSize() {
        Resources resources = myContext.getResources();
        int size = resources.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (size >= Configuration.SCREENLAYOUT_SIZE_NORMAL)
            return 42;
        if (size >= Configuration.SCREENLAYOUT_SIZE_SMALL)
            return 36;
        return 24;
    }
}
