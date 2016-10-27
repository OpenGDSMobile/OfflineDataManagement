package com.openGDSMobileApplicationServer.api;

import com.openGDSMobileApplicationServer.data.mapDownloadVO;
import com.openGDSMobileApplicationServer.service.MapManagementService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by intruder on 16. 3. 19.
 */

@RestController
@RequestMapping("/api")
public class DownloadRestController {
    @Autowired
    @Qualifier("OSMRaster")
    MapManagementService osmRManagement;

    @Autowired
    @Qualifier("OSMVector")
    MapManagementService osmVManagement;

    // latMin, latMax, longMin, longMax, zoomMin, zoomMax
    @RequestMapping (value="/downloadOSMRaster", method = {RequestMethod.POST},
            headers="Content-Type=application/json")
    Map<Object, Object> postInterfaceGetOSMRaster(@RequestBody mapDownloadVO obj) {
        System.out.println(obj.toString());/////////
        return downloadOSMMap(obj);
    }
    @RequestMapping (value="/downloadOSMRaster", method = {RequestMethod.GET})
    Map<Object, Object> getInterfaceGetOSMRaster(mapDownloadVO obj) {
        System.out.println(obj.toString());/////////
        return downloadOSMMap(obj);
    }

    @RequestMapping (value="downloadOSMVector", method = {RequestMethod.POST},
            headers="Content-Type=application/json")
    Map<Object, Object> postInterfaceGetOSMVector(@RequestBody mapDownloadVO obj ) {
        osmVManagement.downloadMap(obj);
        return null;
    }

    Map<Object, Object> downloadOSMMap(mapDownloadVO obj) {
        Map<Object, Object> resultObj = new HashMap<>();
        try {
            resultObj.put("result", "OK");
            resultObj.put("data", osmRManagement.downloadMap(obj).toString());
            return resultObj;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            resultObj.put("result", "ERROR");
            resultObj.put("data", e.getMessage());
            return resultObj;
        }
    }

}
