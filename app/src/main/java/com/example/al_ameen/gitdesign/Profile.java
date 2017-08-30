package com.example.al_ameen.gitdesign;

import java.util.ArrayList;

/**
 * Created by Al-avatar on 09/08/2017.
 */
public class Profile extends ArrayList {
    private String mProfileResourceId;
    private String mFullName;
    private String mUserName;
    private String mBio;
    private String mTotalRepo;
    private String mFollowers;
    private String mFollowing;
    private String mTotalGist;
    private String mProfileUrl;

    public Profile(String profileResourceId, String fullName, String userName, String bio, String totalRepo, String followers, String following, String totalGist, String profileUrl){
        mProfileResourceId = profileResourceId;
        mFullName = fullName;
        mUserName = userName;
        mBio = bio;
        mTotalRepo = totalRepo;
        mFollowers = followers;
        mFollowing = following;
        mTotalGist = totalGist;
        mProfileUrl = profileUrl;
    }
    /*
    public Profile(String profileResourceId, String fullName, String userName, String bio, String totalRepo){
        mProfileResourceId = profileResourceId;
        mFullName = fullName;
        mUserName = userName;
        mBio = bio;
        mTotalRepo = totalRepo;
    }
    */

    public String getMProfileResourceId(){
        return mProfileResourceId;
    }
    public String getMFullName(){
        return mFullName;
    }
    public String getMUserName(){
        return mUserName;
    }
    public String getMBio(){
        return mBio;
    }
    public String getMTotalRepo(){
        return mTotalRepo;
    }
    public String getMFollowers(){return mFollowers;}
    public String getMFollowing(){return mFollowing;}
    public String getMTotalGist(){return mTotalGist;}
    public String getMProfileUrl(){return mProfileUrl;}
}
