package com.jaksic.nikola.expandablerecycler;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {
    private static final String TAG = "RecAdapter";

    private List<Movie> list;
    public int height = 0;
    Context mContext;

    public RecAdapter(List<Movie> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        Movie movie = list.get(position);
        holder.bind(movie);
        holder.itemView.setOnClickListener(v -> {
            boolean expanded = movie.isExpanded();
            movie.setExpanded(!expanded);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView genre;
        private TextView year;
        public LinearLayout subItem;
        public View item_divider;
        public ImageView ivArrow;
        private TextView description;

        public RecViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_title);
            genre = itemView.findViewById(R.id.sub_item_genre);
            year = itemView.findViewById(R.id.sub_item_year);
            subItem = itemView.findViewById(R.id.sub_item);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            description = itemView.findViewById(R.id.sub_item_description);
            item_divider = itemView.findViewById(R.id.item_divider);
        }

        private void bind(Movie movie) {
            boolean expanded = movie.isExpanded();
            Log.d(TAG, "expanded: " + expanded);
//            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            title.setText(movie.getTitle());
            genre.setText("Genre: " + movie.getGenre());
            year.setText("Year: " + movie.getYear());
            description.setText("Description" + movie.getDesciption());
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) item_divider.getLayoutParams();

            if (height == 0) {
                runOneTime(subItem);
                marginParams.setMargins(0, 16, 0, 0);
            } else {
                animationDown(subItem, height, ivArrow, expanded);
                marginParams.setMargins(0, 0, 0, 0);
            }
        }

    }

    //Animation for devices with kitkat and below
    public void animationDown(LinearLayout subItem, int originalHeight, ImageView ivArrow, boolean expanded) {

        // Declare a ValueAnimator object
        ValueAnimator valueAnimator;
        if (expanded) {
            subItem.setVisibility(View.VISIBLE);

            subItem.setEnabled(true);
            Animation a = new AlphaAnimation(0.00f, 1.00f); // Fade in
            subItem.startAnimation(a);
            valueAnimator = ValueAnimator.ofInt(0, originalHeight); // These values in this method can be changed to expand however much you like

            ivArrow.startAnimation(animate(expanded));

        } else {
            Animation a = new AlphaAnimation(1.00f, 0.00f); // Fade out

            a.setDuration(800);
            // Set a listener to the animation and configure onAnimationEnd
            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    subItem.setVisibility(View.INVISIBLE);
                    subItem.setEnabled(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            // Set the animation on the custom view
            subItem.startAnimation(a);
            valueAnimator = ValueAnimator.ofInt(originalHeight, 0);

            ivArrow.startAnimation(animate(expanded));

        }
        valueAnimator.setDuration(800);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                subItem.getLayoutParams().height = value.intValue();
                subItem.requestLayout();
            }
        });

        valueAnimator.start();

        //

    }

    public void runOneTime(LinearLayout layout) {
        layout.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                // do something...
                height = layout.getHeight();
                Log.d(TAG, "height: " + height);
                layout.setVisibility(View.GONE);
            }
        }, 100);
    }

    private Animation animate(boolean up) {
        Animation anim = AnimationUtils.loadAnimation(mContext, up ? R.anim.arrow_rotate_down : R.anim.arrow_rotate_up);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        return anim;
    }
}
