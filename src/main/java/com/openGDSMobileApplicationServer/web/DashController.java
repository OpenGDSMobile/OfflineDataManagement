package com.openGDSMobileApplicationServer.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by intruder on 16. 3. 22.
 */

@Controller
@RequestMapping("/")
public class DashController {

    @RequestMapping
    String index() {
        return "index";
    }


    @RequestMapping (value="api/offlineMap")
    String apiOfflineMap() {
        return "OpenAPI_index";
    }
}
