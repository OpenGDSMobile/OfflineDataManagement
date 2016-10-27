package com.openGDSMobileApplicationServer.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 2016-04-08.
 */
public interface MapDataService {

    JSONObject getMapInfo (String sid) throws IOException;
    JSONArray getMap (String sid) throws IOException;
    JSONArray getMapAllList () throws  IOException;
}
