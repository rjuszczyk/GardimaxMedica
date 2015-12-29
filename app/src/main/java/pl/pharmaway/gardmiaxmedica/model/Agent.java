package pl.pharmaway.gardmiaxmedica.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Radek on 12/21/2015.
 */
public class Agent implements Serializable {
    @SerializedName("id")
    Long mId;

    @SerializedName("name")
    String mName;

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return getName();
    }
}
