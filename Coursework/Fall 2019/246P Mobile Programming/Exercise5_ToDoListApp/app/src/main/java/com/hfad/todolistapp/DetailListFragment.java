package com.hfad.todolistapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailListFragment extends Fragment {
    private long itemID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            TextView title = (TextView) view.findViewById(R.id.textTitle);
            String item = Items.itemsList.get((int) itemID);
//            title.setText(item.getName());
            TextView description = (TextView) view.findViewById(R.id.textDescription);
//            description.setText(item.getDescription());
        }
    }

    public void setItemID(long id) {
        this.itemID = id;
    }

}
