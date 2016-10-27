package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.data.mapDownloadVO;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Repository;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

import static com.openGDSMobileApplicationServer.modules.FileModules.encodeToString;
import static com.openGDSMobileApplicationServer.modules.FileModules.readFile;

/**
 * Created by intruder on 16. 4. 7.
 */

@Repository
public class OSMManagementDAO {
    String imgFilePath;
    JSONObject pathJsonObj;
    JSONArray pathList;
    DefaultResourceLoader loader;
    String osVersion;

    OSMManagementDAO () {
        imgFilePath = "";
        pathJsonObj = new JSONObject();
        pathList = new JSONArray();
        loader = new DefaultResourceLoader();
        osVersion = System.getProperty("os.name");
    }
    public Boolean downloadRaster (String cmd) throws IOException {
        DefaultExecutor executor = new DefaultExecutor();
        CommandLine cmdLine = CommandLine.parse(cmd);
        executor.execute(cmdLine);
        return true;
    }
    public Boolean downloadVector (String cmd) throws IOException {
        DefaultExecutor executor = new DefaultExecutor();
        CommandLine cmdLine = CommandLine.parse(cmd);
        executor.execute(cmdLine);
        return true;
    }


    public JSONObject createInfoFile (mapDownloadVO vo, String folderName) throws IOException {
        JSONObject jsonObj = new JSONObject();
        String destDir = loader.getResource("/data/raster/").getFile() + "/OSMRaster" + folderName;
        destDir = destDir + "/mapInfo.json";
        jsonObj.put("mapTitle", vo.getMapTitle());
        jsonObj.put("sid", folderName);
        jsonObj.put("latMin", vo.getLatMin());
        jsonObj.put("latMax", vo.getLatMax());
        jsonObj.put("longMin", vo.getLongMin());
        jsonObj.put("longMax", vo.getLongMax());
        jsonObj.put("zoomMin", vo.getZoomMin());
        jsonObj.put("zoomMax", vo.getZoomMax());
        FileWriter file = new FileWriter(destDir);
        file.write(jsonObj.toString());
        file.flush();
        file.close();
        return jsonObj;
    }
    public Boolean updateListFile (mapDownloadVO vo, String folderName) throws IOException {
        JSONObject jsonObj = new JSONObject();
        String destDir = loader.getResource("/data/raster/").getFile() + "/rasterList.json";
        jsonObj.put("mapTitle", vo.getMapTitle());
        jsonObj.put("sid", folderName);
        jsonObj.put("latMin", vo.getLatMin());
        jsonObj.put("latMax", vo.getLatMax());
        jsonObj.put("longMin", vo.getLongMin());
        jsonObj.put("longMax", vo.getLongMax());
        jsonObj.put("zoomMin", vo.getZoomMin());
        jsonObj.put("zoomMax", vo.getZoomMax());
        JSONArray listJsonObj = new JSONArray(readFile(destDir));
        listJsonObj.put(jsonObj);
        FileWriter file = new FileWriter(destDir);
        file.write(listJsonObj.toString());
        file.flush();
        file.close();
        return true;
    }

    public String createJsonFilePath (String folderName) throws IOException {
        String destDir = loader.getResource("/data/raster/").getFile() + "/OSMRaster" + folderName;
        subDirList(destDir);
        FileWriter file = new FileWriter(destDir + "/mapData.json");
        file.write(pathList.toString());
        file.flush();
        file.close();
        return null;
    }

    public void subDirList(String source) throws IOException {
        File dir = new File(source);
        File[] fileList = dir.listFiles();
        for(int i = 0 ; i < fileList.length ; i++){
            File file = fileList[i];
            if(file.isFile()){
                imgFilePath = file.getCanonicalPath().toString();
                String fileCheck = imgFilePath.substring(imgFilePath.lastIndexOf(".") + 1, imgFilePath.length());
                String removePath = loader.getResource("/data/raster/").getFile().toString();
                imgFilePath = imgFilePath.replace(removePath, "");
                if (osVersion.contains("Windows")) {
                    imgFilePath = imgFilePath.replace("\\", "/");
                    System.out.println(imgFilePath);
                }
                if (fileCheck.equals("png")) {
                    BufferedImage img = ImageIO.read(new File(file.getCanonicalPath().toString()));
                    JSONObject content = new JSONObject();
                    content.put("path", imgFilePath);
                    content.put("base64", encodeToString(img, "png"));
                    pathList.put(content);
                }
                imgFilePath =  "";
            }else if(file.isDirectory()){
                subDirList(file.getCanonicalPath().toString());
            }
        }
    }

}
