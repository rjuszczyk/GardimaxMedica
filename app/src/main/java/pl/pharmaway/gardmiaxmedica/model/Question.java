package pl.pharmaway.gardmiaxmedica.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Radek on 12/23/2015.
 */
public class Question implements Serializable{
    @SerializedName("correct")
    Long mCorrect;

    @SerializedName("question")
    String mQuestion;

    @SerializedName("ans1")
    String mAns1;

    @SerializedName("ans2")
    String mAns2;

    @SerializedName("ans3")
    String mAns3;

    public String getQuestion() {
        return mQuestion;
    }

    public String getAnswer1() {
        return mAns1;
    }

    public String getAnswer2() {
        return mAns2;
    }

    public String getAnswer3() {
        return mAns3;
    }

    public int getCorrectAnswer() {
        return mCorrect.intValue();
    }
}
