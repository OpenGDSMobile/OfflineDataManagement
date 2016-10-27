package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.data.mapDownloadVO;
import com.openGDSMobileApplicationServer.service.MapManagementService;
import org.json.JSONObject;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by intruder on 16. 4. 7.
 */
@Service("OSMRaster")
public class OSMRasterManagementServiceImpl implements MapManagementService {

    @Autowired
    OSMManagementDAO osmManDAO;

    public OSMRasterManagementServiceImpl() {

    }
    @Override
    public JSONObject downloadMap(mapDownloadVO vo) {
        String cmd = "perl ",
                lat = " --lat=" + vo.getLatMin() + ":" + vo.getLatMax(),
                lon = " --long=" + vo.getLongMin() + ":" + vo.getLongMax(),
                zoom = " --zoom=" + vo.getZoomMin() + ":" + vo.getZoomMax(),
                destDir = " --destDir ";
        SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String folderName = dayFormatter.format(new Date());
        try {//
            DefaultResourceLoader loader = new DefaultResourceLoader();
            destDir = destDir + loader.getResource("/data/raster/").getFile() + "/OSMRaster" + folderName;
            cmd = cmd + loader.getResource("/data/downloadosmtiles.pl").getFile();
            cmd = cmd + lat + lon + zoom + destDir;
            String osVersion = System.getProperty("os.name");
            System.out.println(osVersion);  ////////////////////////
            if (osVersion.contains("Windows")) {
                cmd = "C:\\Strawberry\\perl\\bin\\" + cmd;
            } else {
                cmd = "/usr/bin/" + cmd;
            }
            System.out.println(cmd);        /////////////////////////
            JSONObject jsonObj = null;
            if (osmManDAO.downloadRaster(cmd)) {
                jsonObj = osmManDAO.createInfoFile(vo, folderName);
                osmManDAO.createJsonFilePath(folderName);
                osmManDAO.updateListFile(vo, folderName);
            }
            return jsonObj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
