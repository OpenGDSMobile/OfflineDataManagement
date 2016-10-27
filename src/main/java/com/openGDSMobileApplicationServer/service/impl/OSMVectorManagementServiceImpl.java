package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.data.mapDownloadVO;
import com.openGDSMobileApplicationServer.service.MapManagementService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016-05-01.
 */
@Service("OSMVector")
public class OSMVectorManagementServiceImpl  implements MapManagementService {

    @Autowired
    OSMManagementDAO osmManDAO;
    @Override
    public JSONObject downloadMap(mapDownloadVO vo) {
        String cmd = "wget -O ",
                baseURL = "http://overpass.osm.rambler.ru/cgi/xapi_meta?*",
                bbox = "[bbox=" + vo.getLongMin() + "," + vo.getLatMin() + "," + vo.getLongMax() + "," + vo.getLatMax() + "]",
                destDir = "",
                fileName = "OSMVector.osm ";

        try {
            DefaultResourceLoader loader = new DefaultResourceLoader();
            SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String folderName = dayFormatter.format(new Date());
            destDir = destDir + loader.getResource("/data/vector/").getFile();
            String osVersion = System.getProperty("os.name");
            System.out.println(osVersion);  ////////////////////////
            DefaultExecutor executor = new DefaultExecutor();
            if (osVersion.contains("Windows")) {
                destDir = destDir + "\\OSMVector" + folderName;
                new File(destDir).mkdir();
                cmd = cmd + destDir + "\\" + fileName + baseURL + bbox;
            } else {
                destDir =  destDir + "/OSMVector" + folderName;
                new File(destDir).mkdir();
                cmd = cmd + destDir + "/" + fileName + baseURL + bbox;
            }

            System.out.println(cmd);        /////////////////////////
            JSONObject jsonObj = null;
            if (osmManDAO.downloadVector(cmd)) {
                /*
                jsonObj = osmManDAO.createInfoFile(vo, folderName);
                osmManDAO.createJsonFilePath(folderName);
                osmManDAO.updateListFile(vo, folderName);
                */
            }
            return jsonObj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
