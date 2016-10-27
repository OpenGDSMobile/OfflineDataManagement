package com.openGDSMobileApplicationServer.api;

import com.openGDSMobileApplicationServer.service.MapDataService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.openGDSMobileApplicationServer.modules.FileModules.deleteDirectory;
import static com.openGDSMobileApplicationServer.modules.FileModules.readFile;

/**
 * Created by intruder on 16. 4. 7.
 */

@RestController
@RequestMapping("/api")
public class RasterRestController {

    @Autowired
    @Qualifier("OSMData")
    MapDataService osmDataService;
    @RequestMapping (value="/Raster/{type}/{sid}", method = {RequestMethod.GET})
    Map<Object, Object> getRaster(@PathVariable String type, @PathVariable String sid){
        Map<Object, Object> resultObj = offlineDownloadOSMMap(type, sid);
        return resultObj;
    }
    @RequestMapping (value="/Raster/{type}/{sid}", method = {RequestMethod.POST})
    Map<Object, Object> postRaster(@PathVariable String type, @PathVariable String sid){
        Map<Object, Object> resultObj = offlineDownloadOSMMap(type, sid);
        return resultObj;
    }

    Map<Object, Object> offlineDownloadOSMMap(String type, String sid) {
        Map<Object, Object> resultObj = new HashMap<>();
        JSONArray jsonArray = null;
        try {
            if (type.equals("OSM")) {
                jsonArray= osmDataService.getMap("OSMRaster" + sid.toString());
            }
            resultObj.put("result", "OK");
            resultObj.put("data", jsonArray.toString());
            return resultObj;
        } catch (Exception e) {
            resultObj.put("result", "ERROR");
            resultObj.put("data", e.getMessage());
            return resultObj;
        }
    }

    @RequestMapping (value="/RasterInfo", method={RequestMethod.GET})
    Map<Object, Object> getAllRaster () {
        Map<Object, Object> resultObj = new HashMap<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = osmDataService.getMapAllList();
            resultObj.put("result", "OK");
            resultObj.put("data", jsonArray.toString());
            return resultObj;
        } catch (Exception e) {
            resultObj.put("result", "ERROR");
            resultObj.put("data", e.getMessage());
            return resultObj;
        }

    }
    @RequestMapping (value="/RasterInfo/{type}/{sid}", method = {RequestMethod.GET})
    Map<Object, Object> getRasterInfo(@PathVariable String type, @PathVariable String sid) {
        Map<Object, Object> resultObj = new HashMap<>();
        JSONObject infoJsonObj = null;
        try {
            if (type.equals("OSM")) {
                infoJsonObj= osmDataService.getMapInfo("OSMRaster" + sid);
                System.out.println("OSMRaster" + sid);
            }
            resultObj.put("result", "OK");
            resultObj.put("data", infoJsonObj.toString());
            return resultObj;
        } catch (Exception e) {
            resultObj.put("result", "ERROR");
            resultObj.put("data", e.getMessage());
            return resultObj;
        }
    }

    @RequestMapping (value="/Raster/{type}/{sid}", method = {RequestMethod.DELETE})
    Map<Object, Object> deleteRaster(@PathVariable String type, @PathVariable String sid) {
        Map<Object, Object> resultObj = new HashMap<>();

        DefaultResourceLoader loader;
        loader = new DefaultResourceLoader();
        try {
            String destDir = loader.getResource("/data/raster/").getFile() + "/OSMRaster" + sid;
            deleteDirectory(new File(destDir));
            JSONArray rasterList = new JSONArray(readFile(loader.getResource("/data/raster/").getFile() + "/rasterList.json"));
            for (int i = 0; i<rasterList.length(); i++) {
                JSONObject tmp = rasterList.getJSONObject(i);
                if (tmp.get("sid").equals(sid)) {
                    rasterList.remove(i);
                    break;
                }
            }

            FileWriter file = new FileWriter(loader.getResource("/data/raster/").getFile() + "/rasterList.json");
            file.write(rasterList.toString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultObj;
    }
}
