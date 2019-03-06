package com.snsprj.sbsm.json;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

@Slf4j
public class JsonTest {

    @Test
    public void test(){

        try {
            JSONArray jsonArray = new JSONArray();

            JSONObject object = new JSONObject();
            object.put("name","xiaoli");
            object.put("age",27);

            jsonArray.put(object);

            log.info("====>JSONArray to String: {}", jsonArray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
