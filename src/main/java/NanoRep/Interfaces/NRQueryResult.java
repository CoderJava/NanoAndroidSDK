package NanoRep.Interfaces;

import java.util.ArrayList;

import NanoRep.Chnneling.NRChanneling;

/**
 * Created by nissimpardo on 07/06/16.
 */
public interface NRQueryResult {
    String getId();
    String getTitle();
    void setBody(String body);
    String getLikes();
    String getBody();
    ArrayList<NRChanneling> getChanneling();
}
