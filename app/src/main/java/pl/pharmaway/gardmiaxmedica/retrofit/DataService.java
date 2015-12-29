package pl.pharmaway.gardmiaxmedica.retrofit;

import pl.pharmaway.gardmiaxmedica.model.AgentsResponse;
import pl.pharmaway.gardmiaxmedica.model.QuestionsResponse;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Radek on 12/21/2015.
 */
public interface  DataService {
    @GET("MedycynaRodzinna/gardimax/dane.json")
    Call<AgentsResponse> agentsIndex();
    @GET("MedycynaRodzinna/gardimax/questions.json")
    Call<QuestionsResponse> questionsIndex();
}
