var dragBox;
var jsonObj = {};

var dragBoxEndEvent = function(){
    var ext = dragBox.getExtent('EPSG:4326');
    $('#latMin').val(ext[1]);
    $('#latMax').val(ext[3]);
    $('#longMin').val(ext[0]);
    $('#longMax').val(ext[2]);
    jsonObj.latMin = ext[1];
    jsonObj.latMax = ext[3];
    jsonObj.longMin = ext[0];
    jsonObj.longMax = ext[2];
    jsonObj.zoomMin = $('#zoomMin').val();
    jsonObj.zoomMax = $('#zoomMax').val();
}
$(function(){
    var mapView = new ol.View({
        center: [0, 0],
        zoom: 2,
        maxZoom: 17,
        minZoom: 0
    });
    var baseMap = new ol.layer.Tile({
        tile: 'baseMap',
        source: new ol.source.OSM()
    });
    var mapObj = new ol.Map({
        target: 'map',
        view : mapView,
        layers : [baseMap]
    });

    $('#drawToggleBtn').click(function () {
        if ($(this).attr('aria-pressed') == "true") {
            $(this).text("Draw OFF");
            dragBox.drawFlag(false);
        } else {
            $(this).text("Draw ON");
            if (dragBox != null) {
                dragBox.drawFlag(true);
            }
            dragBox = (typeof (dragBox) !== 'undefined') ? dragBox : new RectangularDraw(mapObj);
            dragBox.getInteractionObj().on('boxend', dragBoxEndEvent);
            

        }
    });

    $('#rasterMapTitle').change(function () {
        jsonObj.mapTitle = $(this).val();
    });

    $('#vectorMapTitle').change(function () {
        jsonObj.mapTitle = $(this).val();
    });

    var l = $('#downloadRasterBtn').ladda();
    $('#downloadRasterBtn').click(function () {
        console.log(JSON.stringify(jsonObj));
        if (jsonObj == null) {
            alert('Not Draw Box');
            return -1;
        }
        /*var requestUrl = $("meta[name='ctx']").attr('content') + '/api/downloadOSMRaster';*/
        var requestUrl = '/api/downloadOSMRaster';
        l.ladda('start');
        l.ladda('setProgress', 0.3);
        $.ajax({
            type: 'POST',
            url: requestUrl,
            data: JSON.stringify(jsonObj),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success : function (response) {
                l.ladda('stop');
                jsonObj = {};
                console.log(response);
            },
            error: function (err) {
                l.ladda('stop');
                console.log(err);
            }
        });
    });

    var la = $('#downloadVectorBtn').ladda();
    $('#downloadVectorBtn').click(function () {
        console.log(JSON.stringify(jsonObj));
        if (jsonObj == null) {
            alert('Not Draw Box');
            return -1;
        }
        /*var requestUrl = $("meta[name='ctx']").attr('content') + '/api/downloadOSMVector';*/
        var requestUrl = '/api/downloadOSMVector';
        la.ladda('start');
        la.ladda('setProgress', 0.3);
        $.ajax({
            type: 'POST',
            url: requestUrl,
            data: JSON.stringify(jsonObj),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success : function (response) {
                la.ladda('stop');
                jsonObj = {};
                console.log(response);
            },
            error: function (err) {
                la.ladda('stop');
                console.log(err);
            }
        });
    });
});

