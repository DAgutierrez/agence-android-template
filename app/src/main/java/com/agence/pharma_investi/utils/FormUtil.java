package com.agence.pharma_investi.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.agence.pharma_investi.R;

/**
 * Created by diego on 15-11-16.
 */
public class FormUtil {

    public static View.OnFocusChangeListener validateRequiredField (final Context context,final View viewLayout, final AutoCompleteTextView textView) {

        return   new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(textView.getText().length() == 0) {
                    textView.setError(context.getString(R.string.required_field));
                }

                final RelativeLayout formLayout = (RelativeLayout) viewLayout.findViewById(R.id.add_task_container);
                final Button btnSave = (Button) viewLayout.findViewById(R.id.btn_save);


                recursiveLoopChildren(formLayout, btnSave);

            }
        };
    };

    public static void recursiveLoopChildren (ViewGroup formLayout, Button btnSave) {

        for (int i = 0; i < formLayout.getChildCount(); i++) {
            View v = formLayout.getChildAt(i);

            if (v instanceof LinearLayout) {

                recursiveLoopChildren((LinearLayout) v, btnSave);

            } else if (v instanceof AutoCompleteTextView ){

                if(((AutoCompleteTextView) v).getError() != null) {
                    System.out.println("Error " + ((AutoCompleteTextView) v).getError().toString());
                } else {
                    btnSave.setEnabled(true);
                }
            }
        }

    }

//    public static View.OnFocusChangeListener checkAllValidField (View view) {
//
//
//        return   new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//
//
//
//
//
//            }
//        };
//
////
//
//    };
}
