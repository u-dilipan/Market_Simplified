package com.example.market_simplified.pagination;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.market_simplified.DBAdapter;
import com.example.market_simplified.fragments.FirstFragment;
import com.example.market_simplified.models.UserData;
import com.google.android.material.snackbar.Snackbar;
import com.example.market_simplified.R;

import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<UserData> userResults;
    private Context context;

    private boolean isLoadingAdded = false;
    private FirstFragment firstFragment;

    public PaginationAdapter(Context context, FirstFragment firstFragment) {
        this.context = context;
        this.firstFragment = firstFragment;
        userResults = new ArrayList<>();
    }

    public List<UserData> getData() {
        return userResults;
    }

    public void setUserData(List<UserData> userResults) {
        this.userResults = userResults;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        assert viewHolder != null;
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list, parent, false);
        viewHolder = new Profile(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        UserData userData = userResults.get(position);
        if (getItemViewType(position) == ITEM) {
            final Profile profile = (Profile) holder;

            profile.mUserName.setText(userData.getName());
            profile.mComments.setText(new DBAdapter(context).getComment(userData.getId()));

            Glide.with(context)
                    .load(userData.getOwner().getImageUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(
                                @Nullable GlideException e, Object model, Target<Drawable> target,
                                boolean isFirstResource) {
                            profile.mProgress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(
                                Drawable resource, Object model, Target<Drawable> target,
                                DataSource dataSource, boolean isFirstResource) {
                            profile.mProgress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(profile.mProfilePic);

            profile.mPost.setOnClickListener(view -> {
                String comments = profile.mComments.getText().toString();
                if(!comments.equals("")) {
                    firstFragment.insertComments(userData.getId(), comments);
                    Snackbar.make(view.getRootView(), "Comment posted!", Snackbar.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Enter comments", Toast.LENGTH_SHORT).show();
                }
            });

        }

        holder.itemView.setOnClickListener(view -> firstFragment.openDetailFragment(userData));

    }

    @Override
    public int getItemCount() {
        return userResults == null ? 0 : userResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == userResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
    */

    public void add(UserData r) {
        userResults.add(r);
        notifyItemInserted(userResults.size() - 1);
    }

    public void addAll(List<UserData> moveResults) {
        for (UserData result : moveResults) {
            add(result);
        }
    }

    public void remove(UserData r) {
        int position = userResults.indexOf(r);
        if (position > -1) {
            userResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new UserData());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = userResults.size() - 1;
        UserData result = getItem(position);

        if (result != null) {
            userResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public UserData getItem(int position) {
        return userResults.get(position);
    }

    protected static class Profile extends RecyclerView.ViewHolder {
        private TextView mUserName;
        private ImageView mProfilePic;
        private ProgressBar mProgress;
        private EditText mComments;
        private Button mPost;

        public Profile(View itemView) {
            super(itemView);

            mUserName = itemView.findViewById(R.id.user_name);
            mProfilePic = itemView.findViewById(R.id.user_pic);
            mProgress = itemView.findViewById(R.id.user_progress);
            mComments = itemView.findViewById(R.id.comments);
            mPost = itemView.findViewById(R.id.postButton);
        }
    }

    protected static class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}