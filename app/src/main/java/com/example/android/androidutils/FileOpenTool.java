package com.loyo.library.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * author: Eric_Li
 * date:   On 2018/10/17
 */
public class FileOpenTool {

    private static String getMap(String key) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rar", "application/x-rar-compressed");
        map.put("jpg", "image/jpeg");
        map.put("zip", "application/zip");
        map.put("pdf", "application/pdf");
        map.put("doc", "application/msword");
        map.put("docx", "application/msword");
        map.put("wps", "application/msword");
        map.put("xls", "application/vnd.ms-excel");
        map.put("et", "application/vnd.ms-excel");
        map.put("xlsx", "application/vnd.ms-excel");
        map.put("ppt", "application/vnd.ms-powerpoint");
        map.put("html", "text/html");
        map.put("htm", "text/html");
        map.put("txt", "text/html");
        map.put("mp3", "audio/mpeg");
        map.put("mp4", "video/mp4");
        map.put("3gp", "video/3gpp");
        map.put("wav", "audio/x-wav");
        map.put("avi", "video/x-msvideo");
        map.put("flv", "flv-application/octet-stream");

        map.put("apk",    "application/vnd.android.package-archive");
        map.put("asf",    "video/x-ms-asf");
        map.put("bin",    "application/octet-stream");
        map.put("bmp",      "image/bmp");
        map.put("c",        "text/plain");
        map.put("class",    "application/octet-stream");
        map.put("conf",    "text/plain");
        map.put("cpp",    "text/plain");
        map.put("exe",    "application/octet-stream");
        map.put("gif",    "image/gif");
        map.put("gtar",    "application/x-gtar");
        map.put("gz",        "application/x-gzip");
        map.put("h",        "text/plain");
        map.put("jar",    "application/java-archive");
        map.put("java",    "text/plain");
        map.put("jpeg",    "image/jpeg");
        map.put("js",        "application/x-javascript");
        map.put("log",    "text/plain");
        map.put("m3u",    "audio/x-mpegurl");
        map.put("m4a",    "audio/mp4a-latm");
        map.put("m4b",    "audio/mp4a-latm");
        map.put("m4p",    "audio/mp4a-latm");
        map.put("m4u",    "video/vnd.mpegurl");
        map.put("m4v",    "video/x-m4v");
        map.put("mov",    "video/quicktime");
        map.put("mp2",    "audio/x-mpeg");
        map.put("mpc",    "application/vnd.mpohun.certificate");
        map.put("mpe",    "video/mpeg");
        map.put("mpeg",    "video/mpeg");
        map.put("mpg",    "video/mpeg");
        map.put("mpg4",    "video/mp4");
        map.put("mpga",    "audio/mpeg");
        map.put("msg",    "application/vnd.ms-outlook");
        map.put("ogg",    "audio/ogg");
        map.put("png",    "image/png");
        map.put("pps",    "application/vnd.ms-powerpoint");
        map.put("prop",    "text/plain");
        map.put("rc",        "text/plain");
        map.put("rmvb",    "audio/x-pn-realaudio");
        map.put("rtf",    "application/rtf");
        map.put("sh",        "text/plain");
        map.put("tar",    "application/x-tar");
        map.put("tgz",    "application/x-compressed");
        map.put("wma",    "audio/x-ms-wma");
        map.put("wmv",    "audio/x-ms-wmv");
        map.put("xml",    "text/plain");
        map.put("z",        "application/x-compress");
        map.put("epub",        "application/epub+zip");
        map.put("", "*/*");

        return map.get(key.toLowerCase());
    }

    public static void  openFile(Context context,File file,String fileType){
        fileType = fileType.toLowerCase();
        Uri uri = null;
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                uri = FileProvider.getUriForFile(context, context.getApplicationInfo().packageName + ".provider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            String map = getMap(fileType);
            if (TextUtils.isEmpty(map)){
                map ="*/*";
            }
            intent.setDataAndType(uri, map);
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
