package pl.pharmaway.gardmiaxmedica.model;

import android.os.Bundle;

import com.hannesdorfmann.fragmentargs.bundler.ArgsBundler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radek on 12/21/2015.
 */
public class AgentsBundler implements ArgsBundler<List<Agent>> {
    @Override
    public void put(String key, List<Agent> value, Bundle bundle) {
        ArrayList<Agent> vals ;
        if(value instanceof ArrayList) {
            vals = (ArrayList<Agent>) value;
        } else {
            vals = new ArrayList<>(value.size());
            vals.addAll(value);
        }

        bundle.putSerializable(key, vals);
    }

    @Override
    public List<Agent> get(String key, Bundle bundle) {
        return (List<Agent>) bundle.getSerializable(key);
    }
}
