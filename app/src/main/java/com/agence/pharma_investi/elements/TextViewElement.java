package com.agence.pharma_investi.elements;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by diego on 21-10-16.
 */
public class TextViewElement extends TextView {

    public TextViewElement(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setType(context);
    }

    public TextViewElement(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(context);
    }

    public TextViewElement(Context context) {
        super(context);
        setType(context);
    }

    private void setType(Context context){
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Regular.ttf"));

    }
}
