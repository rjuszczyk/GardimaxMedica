package pl.pharmaway.gardmiaxmedica.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Radek on 12/21/2015.
 */
public class AgentsResponse {
    @SerializedName("farmaceutyczni")
    List<Agent> mMedicalAgents;

    @SerializedName("medyczni")
    List<Agent> mPharmaceuticalAgents;

    public List<Agent> getPharmaceuticalAgents() {
        return mPharmaceuticalAgents;
    }

    public List<Agent> getMedicalAgents() {
        return mMedicalAgents;
    }
}
