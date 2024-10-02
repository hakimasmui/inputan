package com.hakimasmui.inputan;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Inputan extends LinearLayout {

    TextView text1;
    EditText edt1;

    public Inputan(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Inputan, 0, 0);
        String label = a.getString(R.styleable.Inputan_label);
        String hint = a.getString(R.styleable.Inputan_hint);
        int inputType = a.getInt(R.styleable.Inputan_inputType, 1);

        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.inputan_view, this, true);

        text1 = v.findViewById(R.id.text1);
        edt1 = v.findViewById(R.id.edt1);

        text1.setText(label);
        edt1.setHint(hint);
        if (inputType == 0) {
            edt1.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (inputType == 1) {
            edt1.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (inputType == 2) {
            edt1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public String getText() {
        return edt1.getText().toString().trim();
    }

    public void setText(String text) {
        edt1.setText(text);
    }
}
