package com.walton.hoteltv;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class Utils {

    private Context mContext;

    public Utils(Context context) {
        this.mContext = context;
    }

    public Utils() {

    }

    public static String getTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("HH:mm aa");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String convertMilToDate(String timeInMil) {
        return DateFormat.format("HH:mm aa\ndd/MM/yyyy", Long.parseLong(timeInMil)).toString();
    }

    public static String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
                    null)[0];
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes) {
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }

    /*
    * Open Image file
    * */
    public static void openFile(Context c,String filePath){
//        Uri uri =  Uri.fromFile(new File(filePath));
        Uri uri = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID + ".provider" ,new File(filePath));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String mime;
        if (fileExt(filePath).equalsIgnoreCase("mp4")){
            mime = "video/*";
        }else {
            mime = "image/*";
        }
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (mimeTypeMap.hasExtension(
                mimeTypeMap.getFileExtensionFromUrl(uri.toString())))
            mime = mimeTypeMap.getMimeTypeFromExtension(
                    mimeTypeMap.getFileExtensionFromUrl(uri.toString()));
        intent.setDataAndType(uri,mime);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        c.startActivity(intent);
    }

    /*
     * Open PDF file
     * */
    public static void openPdfFile(Context c, String filePath){
        File file = new File(filePath);
        Uri uri = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID + ".provider" ,file);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        target.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        target.setDataAndType(uri,"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            c.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }

    public static String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();
        }
    }


    static void startMTVPlayer(int mCurrentTvPosition, Context context) {
        Log.d("jdk", "isLauncherJumpToMTvPlayer()===" + mCurrentTvPosition);
        ComponentName componentName = new ComponentName("com.mstar.tv.tvplayer.ui", "com.mstar.tv.tvplayer.ui.RootActivity");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(componentName);
        intent.putExtra("isPowerOn", false);
        intent.putExtra("task_tag", "input_source_changed");
        intent.putExtra("inputSrc", mCurrentTvPosition);
        intent.putExtra("mIsFromHomeTv", true);
        context.startActivity(intent);
        /*if (isAvilible("com.mstar.tv.tvplayer.ui",context)) {
            Log.d("jdk", "isLauncherJumpToMTvPlayer()===" + mCurrentTvPosition);
            ComponentName componentName = new ComponentName("com.mstar.tv.tvplayer.ui", "com.mstar.tv.tvplayer.ui.RootActivity");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setComponent(componentName);
            intent.putExtra("isPowerOn", false);
            intent.putExtra("task_tag", "input_source_changed");
            intent.putExtra("inputSrc", mCurrentTvPosition);
            intent.putExtra("mIsFromHomeTv", true);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, R.string.no_app, Toast.LENGTH_SHORT).show();
        }*/
    }

    public static boolean isAvilible(String packageName, Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }


    public static void openApk(String packageName, String activityName,Context context) {
        //checkApk(packageName);
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(packageName, activityName);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(context, R.string.no_app, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
