package name.kropp.diceroller;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class ResourceImageGetter implements Html.ImageGetter {
    private static ResourceImageGetter ourInstance;

    private Resources myResources;

    private ResourceImageGetter(Resources resources) {
        this.myResources = resources;
    }

    public static ResourceImageGetter getInstance(Resources resources) {
        if (ourInstance == null)
            ourInstance = new ResourceImageGetter(resources);
        return ourInstance;
    }

    @Override
    public Drawable getDrawable(String s) {
        int id;
        try {
            id = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            id = R.drawable.w6;
        }
        Drawable d = myResources.getDrawable(id);
        d.setBounds(0, 0, 24, 24);
        return d;
    }
}
