package com.warofoffice.warofoffice.scoreboard.scorecollection;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScoreCollection {
    private static final String FIELD_NAME = "NAME";
    private static final String FIELD_SCORE = "SCORE";
    private static final String FIELD_LEADERNAME = "LEADERNAME";

    public static class Score{
        public String name;
        public int score;
        public String leaderName;

        public Score(String name, int score, String leaderName){
            this.name = name;
            this.score =score;
            this.leaderName =leaderName;
        }
    }

    private ArrayList<Score> scores;

    public ScoreCollection(JSONArray jsonArray){

        scores = new ArrayList<>();

        int count = jsonArray.length();
        JSONObject jsonObject;
        Score score;
        for(int j = 0 ; j < count; j++){
            try {
                jsonObject = jsonArray.getJSONObject(j);
                score = new Score(jsonObject.getString(FIELD_NAME), jsonObject.getInt(FIELD_SCORE), jsonObject.getString(FIELD_LEADERNAME));
                Log.d("test", jsonObject.getString(FIELD_NAME) + " " + jsonObject.getInt(FIELD_SCORE)+ " " + jsonObject.getString(FIELD_LEADERNAME));
                scores.add(score);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(String name, int score, String leaderName){
        int count = this.scores.size();
        int i;
        for(i=0;i<count;i++){
            if(score > this.scores.get(i).score){
                break;
            }
        }
        this.scores.add(i, new Score(name, score, leaderName));
    }

    public Score getScore(int rank){
        if(scores != null && scores.size() > rank)
            return scores.get(rank);
        return null;
    }

    public JSONArray toJSONArray(){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        Score score;

        int count = this.scores.size();
        for(int i=0;i<count;i++){
            try {
                jsonObject = new JSONObject();
                score = this.scores.get(i);
                jsonObject.put(FIELD_NAME, score.name);
                jsonObject.put(FIELD_SCORE, score.score);
                jsonObject.put(FIELD_LEADERNAME, score.leaderName);
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }
}
