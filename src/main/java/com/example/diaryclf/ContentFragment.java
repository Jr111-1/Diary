package com.example.diaryclf;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;

public class ContentFragment extends Fragment {

    private TextView meanText;
    private TextView exampleText;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_layout, null);

        meanText = view.findViewById(R.id.meanText);
        exampleText = view.findViewById(R.id.exampleText);
        return view;
    }

    public void setMeanText(String content){
        meanText.setText(content);
    }

    public void setExampleText(String content){

        exampleText.setText(content);
    }


}