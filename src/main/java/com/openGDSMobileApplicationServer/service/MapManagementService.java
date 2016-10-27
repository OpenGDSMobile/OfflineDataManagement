package com.openGDSMobileApplicationServer.service;

import com.openGDSMobileApplicationServer.data.mapDownloadVO;
import org.json.JSONObject;

/**
 * Created by intruder on 16. 4. 7.
 */
public interface MapManagementService {
    JSONObject downloadMap(mapDownloadVO vo);
}
