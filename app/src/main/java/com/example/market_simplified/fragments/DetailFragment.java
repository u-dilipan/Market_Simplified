package com.example.market_simplified.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.market_simplified.R;

import java.util.Objects;

public class DetailFragment extends Fragment {

    ImageView user_pic;
    ImageView user_close;
    TextView user_name;
    TextView user_fullName;
    TextView user_type;
    TextView user_comment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        user_pic = view.findViewById(R.id.profile_pic);
        user_name = view.findViewById(R.id.user_name);
        user_close = view.findViewById(R.id.pro_close);
        user_fullName = view.findViewById(R.id.full_name);
        user_type = view.findViewById(R.id.user_type);
        user_comment = view.findViewById(R.id.detail_comments);

        assert getArguments() != null;
        String name = getArguments().getString("name");
        String pic =  getArguments().getString("user");
        String fullName = getArguments().getString("fullName");
        String userType = getArguments().getString("type");
        String comments = getArguments().getString("comments");

        user_name.setText(name);
        user_fullName.setText(fullName);
        user_type.setText(userType);
        user_comment.setText(comments);

        Glide.with(DetailFragment.this)
                .load(pic)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(user_pic);

        user_close.setOnClickListener(
                view1 -> Objects.requireNonNull(getActivity()).onBackPressed()
        );

        return view;
    }
}
