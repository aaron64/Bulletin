package net.aaronchambers.bulletin.util;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtil {

    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "fasolid900.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

}