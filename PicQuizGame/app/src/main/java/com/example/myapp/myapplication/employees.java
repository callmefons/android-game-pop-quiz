package com.example.myapp.myapplication;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Fon on 2/28/2015.
 */
public class employees extends ListFragment {

    Activity mActivity;
    private CustomArrayAdapter myCustomArrayAdapter;

    public static employees newInstance() {
        employees fragment = new employees();
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_page1, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TypedArray values_imgs = getResources().obtainTypedArray(R.array.employeesimgs);
        int len = values_imgs.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++)
            resIds[i] = values_imgs.getResourceId(i, 0);

        values_imgs.recycle();

        String[] values = getResources().getStringArray(R.array.employees);
        String[] values_description = getResources().getStringArray(R.array.employeesimgsdescrition);
        String[] values_description_2 = getResources().getStringArray(R.array.employeesimgsdescrition2);

        myCustomArrayAdapter = new CustomArrayAdapter(getActivity(), R.layout.list_row, values, values_description,values_description_2, resIds, 1);
        setListAdapter(myCustomArrayAdapter);
    }

}
