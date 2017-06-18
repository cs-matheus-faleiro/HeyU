package com.matheusfaleiro.heyu.activities.heyu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matheusfaleiro.heyu.R;


public class ChatsFragment extends Fragment {


    public ChatsFragment() {}

    public static ChatsFragment newInstance() {
        return new ChatsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

}
