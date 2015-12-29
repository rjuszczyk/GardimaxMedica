package pl.pharmaway.gardmiaxmedica.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Radek on 12/23/2015.
 */
public class QuestionsResponse {
    @SerializedName("questions")
    List<Question> mQuestions;

    public List<Question> getQuestions() {
        return mQuestions;
    }
}
