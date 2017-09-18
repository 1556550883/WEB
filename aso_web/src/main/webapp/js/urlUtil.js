
function urlUtils(url) { 
	var resultUrl = null;
    if(url.indexOf('?') != -1){
    resultUrl = url.substr(0,url.indexOf('?') + 1);
    var tmparray = url.substr(url.indexOf('?') + 1,url.length).split('&');
       for(var i = 0;i<tmparray.length;i++)
       {
            var set1 = tmparray[i];
            var parm = set1.substr(0,set1 .indexOf('=')+1);
            var parmValue = set1.substr(set1 .indexOf('=')+1,set1 .length);
            parmValue = toUnicode(parmValue);
            var y = parm + parmValue;
            resultUrl = resultUrl + y + "&";

       }
       resultUrl = resultUrl.substr(0,resultUrl.length-1);
    }
    window.location.href = basePath + resultUrl;
} 
/**
 * 处理url参数并打开
 * @param url
 * @return
 */
function urlUtilsOpen(url) { 
	var resultUrl = null;
    if(url.indexOf('?') != -1){
    resultUrl = url.substr(0,url.indexOf('?') + 1);
    var tmparray = url.substr(url.indexOf('?') + 1,url.length).split('&');
       for(var i = 0;i<tmparray.length;i++)
       {
            var set1 = tmparray[i];
            var parm = set1.substr(0,set1 .indexOf('=')+1);
            var parmValue = set1.substr(set1 .indexOf('=')+1,set1 .length);
            parmValue = toUnicode(parmValue);
            var y = parm + parmValue;
            resultUrl = resultUrl + y + "&";

       }
       resultUrl = resultUrl.substr(0,resultUrl.length-1);
    }
    window.open(basePath + resultUrl);
} 