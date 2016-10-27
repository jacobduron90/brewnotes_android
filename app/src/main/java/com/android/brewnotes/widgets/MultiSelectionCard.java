package com.android.brewnotes.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.brewnotes.R;

/**
 * Created by jacobduron on 10/27/16.
 */

public class MultiSelectionCard extends CardView {


    private int selectionType;
    private LinearLayout selectionContainer;

    private static final String[] AROMA = new String[]{
            "flowers",
            "citrus fruits",
            "caramel",
            "fresh breads"
    };
    private static final String[] BODY = new String[]{
            "heavy",
            "light",
            "buttery",
            "thick",
            "syrupy",
            "watery",
            "winy"
    };

    private static final String[] FLAVOR = new String[]{
            "berries",
            "fruits",
            "flowers",
            "chocolate",
            "spice"
    };

    private static final String[] FINISH = new String[]{
            "spicy",
            "smoky",
            "woody"
    };


    public MultiSelectionCard(Context context) {
        super(context);
        initSelections();
    }

    public MultiSelectionCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiSelectionCard);
        initFromAttrs(a);
        a.recycle();
        initSelections();


    }

    public MultiSelectionCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiSelectionCard, defStyleAttr, 0);
        initFromAttrs(a);
        a.recycle();
        initSelections();
    }




    private void initSelections(){
        LayoutInflater.from(getContext()).inflate(R.layout.content_selection_widget, this, true);
        selectionContainer = (LinearLayout)findViewById(R.id.selection_container);

        String[] selections = getSelections(selectionType);
        loadSelections(selections);


    }

    private void initFromAttrs(TypedArray a){
        if(a == null || isInEditMode()){
            return;
        }

        selectionType = a.getInt(R.styleable.MultiSelectionCard_selectionType, 0);
    }
    private String[] getSelections(int selection){
        switch(selection){
            case 0:
                return AROMA;
            case 1:
                return BODY;
            case 2:
                return FLAVOR;
            case 3:
            default:
                return FINISH;
        }

    }
    private View[] views;
    private static final int LETTER_THRESHOLD = 35;


    private void loadSelections(String[] selections){
        views = new View[selections.length];

        LinearLayout row = null;
        int letterCount = 0;
        for(int i = 0; i < selections.length; i++){
            if(row == null){
                row = getNewRow();
                selectionContainer.addView(row);

            }
            String text = selections[i];

            TextView selection = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.widget_selection_view, row, false);
            row.addView(selection);
            selection.setText(text);
            views[i] = selection;
            selection.setOnClickListener(selectedListener);

            letterCount += text.length();
            if(letterCount > LETTER_THRESHOLD){
                row = null;
            }
        }

    }

    private LinearLayout getNewRow(){
        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        return row;
    }

    private OnClickListener selectedListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setSelected(!view.isSelected());
        }
    };
}
