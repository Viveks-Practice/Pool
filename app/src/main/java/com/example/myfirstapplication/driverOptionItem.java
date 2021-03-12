package com.example.myfirstapplication;

public class driverOptionItem {
    private int mDistanceImage;
    private int mTimeElapsedImage;
    private int mTimeArrivalImage;
    private String mDistanceText;
    private String mTimeElapsedText;
    private String mTimeArrivalText;

    public driverOptionItem(int distanceImage, int timeElapsedImage,
                            int timeArrivalImage, String distanceText,
                            String timeElapsedText, String timeArrivalText){

        mDistanceImage = distanceImage;
        mTimeElapsedImage = timeElapsedImage;
        mTimeArrivalImage = timeArrivalImage;
        mDistanceText = distanceText;
        mTimeElapsedText = timeElapsedText;
        mTimeArrivalText = timeArrivalText;
    }

    public int getmDistanceImage() {
        return mDistanceImage;
    }

    public void setmDistanceImage(int mDistanceImage) {
        this.mDistanceImage = mDistanceImage;
    }

    public int getmTimeElapsedImage() {
        return mTimeElapsedImage;
    }

    public void setmTimeElapsedImage(int mTimeElapsedImage) {
        this.mTimeElapsedImage = mTimeElapsedImage;
    }

    public int getmTimeArrivalImage() {
        return mTimeArrivalImage;
    }

    public void setmTimeArrivalImage(int mTimeArrivalImage) {
        this.mTimeArrivalImage = mTimeArrivalImage;
    }

    public String getmDistanceText() {
        return mDistanceText;
    }

    public void setmDistanceText(String mDistanceText) {
        this.mDistanceText = mDistanceText;
    }

    public String getmTimeElapsedText() {
        return mTimeElapsedText;
    }

    public void setmTimeElapsedText(String mTimeElapsedText) {
        this.mTimeElapsedText = mTimeElapsedText;
    }

    public String getmTimeArrivalText() {
        return mTimeArrivalText;
    }

    public void setmTimeArrivalText(String mTimeArrivalText) {
        this.mTimeArrivalText = mTimeArrivalText;
    }
}
