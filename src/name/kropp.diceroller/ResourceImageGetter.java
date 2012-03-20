package name.kropp.diceroller;

import android.content.Context;
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
        int id = resources.getIdentifier(s, "drawable", myContext.getPackageName());;
        Drawable d = resources.getDrawable(id);
        d.setBounds(0, 0, 24, 24);
        return d;
    }
}
