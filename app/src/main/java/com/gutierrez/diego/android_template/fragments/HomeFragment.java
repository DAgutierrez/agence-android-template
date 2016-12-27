package com.gutierrez.diego.android_template.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.gutierrez.diego.android_template.R;


/**
 * Created by diego on 31-10-16.
 */
public class HomeFragment  extends Fragment{

    Context context;
    FragmentManager fragmentManager;

    FloatingActionButton btnAddTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        fragmentManager = getActivity().getSupportFragmentManager();


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAddTask =  (FloatingActionButton) view.findViewById(R.id.btnAddTask);

        btnAddTask.setOnClickListener(actionAddTask);


    }


    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(actionBar != null) {
            actionBar.show();
        }

    }


    private View.OnClickListener actionAddTask  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .addToBackStack("Task Fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.main_container, new AddTaskFragment())
                    .commit();

        }
    };


}
