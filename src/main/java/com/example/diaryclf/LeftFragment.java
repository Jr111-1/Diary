package com.example.diaryclf;



import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;//

import java.util.ArrayList;
import java.util.Map;


public class LeftFragment extends Fragment {



    private SimpleAdapter listAdapter2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View left = inflater.inflate(R.layout.left_layout, container, false);



        return left;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        FragmentManager nManager = getFragmentManager();
//        //如果有多个就删除多个fragment
//        LeftFragment fragment =(LeftFragment)nManager.findFragmentById(R.id.left_fragment);
//        if (f1 != null) nManager.beginTransaction().remove(fragment).commit();
//    }










}
