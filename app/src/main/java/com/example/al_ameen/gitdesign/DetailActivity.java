package com.example.al_ameen.gitdesign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;
    private TextView mTitle;
    private LinearLayout mTitleContainer;
    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    /**My data viewa**/

    CircleImageView mAvatar;
    //TextView mUsername;
    TextView mFullname;
    TextView mBio;
    TextView mRepo;
    TextView mFollowers;
    TextView mFollowing;
    TextView mTotalGist;
    TextView mProfileUrl;
    Button mShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);

        mAvatar = (CircleImageView) findViewById(R.id.mainImage);
        mFullname = (TextView) findViewById(R.id.txt_d_full_name);
        mBio = (TextView) findViewById(R.id.txt_about);
        mRepo = (TextView) findViewById(R.id.txt_repo);
        mFollowers = (TextView) findViewById(R.id.txt_followers);
        mFollowing = (TextView) findViewById(R.id.txt_following);
        mTotalGist = (TextView) findViewById(R.id.txt_gists);
        mProfileUrl = (TextView) findViewById(R.id.txt_url);
        mShare = (Button) findViewById(R.id.btn_share);

        String avatar = getIntent().getExtras().getString("iAvatar");
        final String username = getIntent().getExtras().getString("iUsername");
        String fullname = getIntent().getExtras().getString("iFullname");
        String bio = getIntent().getExtras().getString("iBio");
        String repo = getIntent().getExtras().getString("iTotalRepo");
        String followers = getIntent().getExtras().getString("iFollowers");
        String following = getIntent().getExtras().getString("iFollowing");
        String gists = getIntent().getExtras().getString("iTotalGists");
        final String profileUrl = getIntent().getExtras().getString("iProfileUrl");



        mTitle.setText(username);
        mFullname.setText(fullname);
        mBio.setText(bio);
        mRepo.setText(repo);
        mFollowers.setText(followers);
        mFollowing.setText(following);
        mTotalGist.setText(gists);
        mProfileUrl.setText(profileUrl);

        Linkify.addLinks(mProfileUrl,Linkify.WEB_URLS);

        Picasso.with(this)
                .load(avatar)
                .placeholder(R.drawable.avatar)
                .into(mAvatar);

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Check out this awesome developer "+username+", "+profileUrl);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Github User");
                startActivity(Intent.createChooser(shareIntent,"Share...."));
            }
        });




        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.flexible_example_appbar);
        appbar.addOnOffsetChangedListener(this);

        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
    public static void start(Context c) {
        c.startActivity(new Intent(c, DetailActivity.class));
    }
}

