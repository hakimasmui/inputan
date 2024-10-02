package com.hakimasmui.inputan;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class Inputan extends LinearLayout {

    Locale locale = new Locale("in", "ID");
    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);
    String thousandSeparator = String.valueOf(decimalFormatSymbols.getGroupingSeparator());
    int before = 0, length = 0;

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
            edt1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    before = edt1.getSelectionStart();

                    length = charSequence.length();
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String cleanStr = editable.toString();
                    if (cleanStr.isEmpty()) {
                        return;
                    }
                    cleanStr = cleanStr.startsWith(".") ? cleanStr.substring(1) : cleanStr;

                    cleanStr = cleanStr.replace("Rp", "");
                    cleanStr = cleanStr.replace(thousandSeparator, "").replaceAll(",,", ",");

                    if (cleanStr.length() > 9) {
                        cleanStr = cleanStr.substring(0, cleanStr.length() - 1);
                    }

                    String formattedString = formatInteger(cleanStr);
                    edt1.removeTextChangedListener(this);
                    edt1.setText("Rp "+formattedString);
                    if (before == length)
                        edt1.setSelection(edt1.getText().length());
                    else {
                        if (edt1.getText().length() > length){
                            edt1.setSelection(before+1);
                        } else {
                            if (before-1 == 0) {
                                edt1.setSelection(0);
                            }else if (before > 0) {
                                edt1.setSelection(before-1);
                            }
                        }
                    }

                    edt1.addTextChangedListener(this);
                }
            });
        }
    }

    public String getText() {
        return edt1.getText().toString().trim();
    }

    public void setText(String text) {
        edt1.setText(text);
    }

    private String formatInteger(String str) {
        try {
            BigDecimal parsed = new BigDecimal(str);
            DecimalFormat formatter =
                    new DecimalFormat("#,###",
                            new DecimalFormatSymbols(locale));
            return formatter.format(parsed);
        } catch (Exception e) {
            str = str.replaceAll("\\D+", "");
            if (str.isEmpty()) return  str;
            BigDecimal parsed = new BigDecimal(str);
            DecimalFormat formatter =
                    new DecimalFormat("#,###",
                            new DecimalFormatSymbols(locale));
            return formatter.format(parsed);
        }
    }
}
