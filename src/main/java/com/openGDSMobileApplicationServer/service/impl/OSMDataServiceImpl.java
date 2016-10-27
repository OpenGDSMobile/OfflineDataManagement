package com.openGDSMobileApplicationServer.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.openGDSMobileApplicationServer.service.MapDataService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static com.openGDSMobileApplicationServer.modules.FileModules.readFile;

/**
 * Created by Administrator on 2016-04-08.
 */
@Service("OSMData")
public class OSMDataServiceImpl implements MapDataService {
    DefaultResourceLoader loader;
    OSMDataServiceImpl () {
        loader = new DefaultResourceLoader();
    }

    @Override
    public JSONObject getMapInfo(String sid) throws IOException {
        String jsonFile = loader.getResource("/data/raster/").getFile() + "/" + sid + "/mapInfo.json";
        System.out.println(jsonFile);
        return new JSONObject(readFile(jsonFile));
    }

    @Override
    public JSONArray getMap(String sid) throws IOException {
        String jsonFile = loader.getResource("/data/raster/").getFile() + "/" + sid + "/mapData.json";
        System.out.println(jsonFile);
        JSONArray jsonData = new JSONArray(readFile(jsonFile));
        return jsonData;
    }

    @Override
    public JSONArray getMapAllList() throws IOException {
        String jsonFile = loader.getResource("/data/raster/").getFile() + "/rasterList.json";
        return new JSONArray(readFile(jsonFile));
    }

}
