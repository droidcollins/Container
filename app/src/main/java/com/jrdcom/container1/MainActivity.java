package com.jrdcom.container1;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private final String TAG = "Container1";
    private final static int MAX_URLS_CUST_NUMBER = 12;
    private final static int MAX_APPS_SHORTCUTS_NUMBER = 10;

    private GridView mGridView;
    private PackageManager mPackageMgr;
    private List<PackageInfo> mPackageInfoList;

    private String mOperator4Container;//Def_Ideas_For_Operator_Container1;
    private boolean mUseDefaultLink;//Def_Ideas_Use_Default_Link_Container1;
    private boolean mCustAppsShortcuts; //Def_Ideas_Customize_Apps_shortCuts;

    ArrayList<ApplicationInfo> mData = new ArrayList<>();
    SparseArray<ApplicationInfo> mAppInfoDataArray = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGridView = this.findViewById(R.id.gridView);
        mOperator4Container = getResources().getString(R.string.Def_Ideas_For_Operator_Container1);
        mAppInfoDataArray.clear();
        initUrlData(); //URL 12(max)

        mCustAppsShortcuts = getResources().getBoolean(R.bool.Def_Ideas_Customize_Apps_shortCuts);

        int tt = 0;
        for (ApplicationInfo info : mData) {
            Log.e(TAG, "after initUrlData -->" + tt + " -->" + info.getmStrLabel());
        }
        tt = 0;
        if (mCustAppsShortcuts) {
            apkShortcutsCustomize(); //Apps shortcuts 10(max)
            for (ApplicationInfo info : mData) {
                Log.e(TAG, "after apkShortcutsCustomize -->" + tt + " -->" + info.getmStrLabel());
            }
        }


        final int customizedAppsCount = mAppInfoDataArray.size();
        Log.e(TAG, "reserved apps customized count is " + customizedAppsCount);
        for (int i = 0; i < customizedAppsCount; i++) { //URL 12(max) and reserved apps shortcuts 10(max)
            ApplicationInfo appInfo = mAppInfoDataArray.get(i);
            if (null != appInfo) {
                Log.e(TAG, "appinfo " + i + " is " + appInfo.getmStrLabel());
                mData.add(appInfo);
            }
        }
        //initApplicationData();

        mGridView.setAdapter(new AppsAdapter(this, mData));
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            startActivity(mData.get(position).getmIntent());
        } catch (ActivityNotFoundException e) {
            // TODO Auto-generated catch block
            Toast.makeText(this, R.string.activity_not_found,
                    Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Unable to launch. " + " intent=" + mData.get(position).getmIntent(), e);
        } catch (SecurityException e) {
            Toast.makeText(this, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
            Log.e(TAG,
                    "Launcher does not have the permission to launch "
                            + mData.get(position).getmIntent()
                            + ". Make sure to create a MAIN intent-filter for the corresponding activity "
                            + "or use the exported attribute for this activity. "
                            + " intent=" + mData.get(position).getmIntent(), e);
        }
    }


    public void initUrlData() {
        mUseDefaultLink = getResources().getBoolean(R.bool.Def_Ideas_Use_Default_Link_Container1);

        if (mUseDefaultLink) { // use default urls
            String[] mUrlList;
            String[] columbiaUrl = this.getResources().getStringArray(R.array.columbia_url_array);
            String[] mexicoUrl = this.getResources().getStringArray(R.array.mexico_url_array);
            //modified by hz_shan.liao for pr185830 begin
            String[] claroUrl = this.getResources().getStringArray(R.array.claro_url_array);

            Map<String, Drawable> defaultIcons = new ArrayMap<>();
            String[] defaultNames = {
                    "iapps",
                    "ijuegos",
                    "ideasmusik",
                    "ideportes",
                    "imovil",
                    "ipromociones",
                    "ivideo",
                    "msngr",
                    "plugger",
                    "ioperator",
                    "imioperator",
                    "iJuegosHD"
            };

            defaultIcons.put("iapps", getResources().getDrawable(R.drawable.iapps));
            defaultIcons.put("ijuegos", getResources().getDrawable(R.drawable.ijuegos));
            defaultIcons.put("ideasmusik", getResources().getDrawable(R.drawable.ideasmusik));
            defaultIcons.put("ideportes", getResources().getDrawable(R.drawable.ideportes));
            defaultIcons.put("imovil", getResources().getDrawable(R.drawable.imovil));
            defaultIcons.put("ipromociones", getResources().getDrawable(R.drawable.ipromociones));
            defaultIcons.put("ivideo", getResources().getDrawable(R.drawable.ivideo));

            if ("iTelcel".equals(mOperator4Container)) {
                defaultIcons.put("msngr", getResources().getDrawable(R.drawable.imi_telcel));
            } else if ("Comcel".equals(mOperator4Container)) {
                defaultIcons.put("msngr", getResources().getDrawable(R.drawable.imi_comcel));
            } else {
                defaultIcons.put("msngr", getResources().getDrawable(R.drawable.imi_claro));
            }

            defaultIcons.put("plugger", getResources().getDrawable(R.drawable.plugger));
            defaultIcons.put("ioperator", getResources().getDrawable(R.drawable.ioperator));

            if ("Claro".equals(mOperator4Container)) {
                defaultIcons.put("imioperator", getResources().getDrawable(R.drawable.msngrazul_red));
            } else {
                defaultIcons.put("imioperator", getResources().getDrawable(R.drawable.msngrazul_blue));
            }
            defaultIcons.put("iJuegosHD", getResources().getDrawable(R.drawable.ijuegoshd));

            Map<String, String> defaultLabels = new ArrayMap<>();
            defaultLabels.put("iapps", getResources().getString(R.string.iapps));
            defaultLabels.put("ijuegos", getResources().getString(R.string.ijuegos));
            defaultLabels.put("ideasmusik", getResources().getString(R.string.ideasmusik));
            defaultLabels.put("ideportes", getResources().getString(R.string.ideportes));
            defaultLabels.put("imovil", getResources().getString(R.string.imovil));
            defaultLabels.put("ipromociones", getResources().getString(R.string.ipromociones));
            defaultLabels.put("ivideo", getResources().getString(R.string.ivideo));
            defaultLabels.put("msngr", getResources().getString(R.string.msngr));
            defaultLabels.put("plugger", getResources().getString(R.string.plugger));
            defaultLabels.put("ioperator", getResources().getString(R.string.ioperator));
            defaultLabels.put("imioperator", getResources().getString(R.string.imioperator));
            defaultLabels.put("iJuegosHD", getResources().getString(R.string.iJuegosHD));

            if ("iTelcel".equals(mOperator4Container)) {
                mUrlList = mexicoUrl;
            } else if ("Comcel".equals(mOperator4Container)) {
                mUrlList = columbiaUrl;
            } else {
                mUrlList = claroUrl;
            }

            ApplicationInfo appInfo;
            Intent mIntent;
            Uri mUri;
            for (int i = 0; i < MAX_URLS_CUST_NUMBER; i++) {
                appInfo = new ApplicationInfo();
                appInfo.setmStrLabel(defaultLabels.get(defaultNames[i]));
                appInfo.setmBitMapIcon(Utilities.createIconBitmap(
                        defaultIcons.get(defaultNames[i]), this));

                appInfo.setmType(ApplicationInfo.TYPE_URL);
                mIntent = new Intent();
                String mUriString = mUrlList[i];
                if (mUriString == null || mUriString.length() < 2) {
                    continue;
                }
                mUri = Uri.parse(mUriString);
                mIntent.setAction(Intent.ACTION_VIEW);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.setData(mUri);

                appInfo.setmIntent(mIntent);
                mData.add(appInfo);
            }

        } else {
            // get URLs from perso definition
            Log.e(TAG, "get URLs from perso definition");

            //Add by DAYI.XIE afternoon 2017/10/31
            int[] urlOfApps = {R.string.url_of_app1_Container1,
                    R.string.url_of_app2_Container1,
                    R.string.url_of_app3_Container1,
                    R.string.url_of_app4_Container1,
                    R.string.url_of_app5_Container1,

                    R.string.url_of_app6_Container1,
                    R.string.url_of_app7_Container1,
                    R.string.url_of_app8_Container1,
                    R.string.url_of_app9_Container1,
                    R.string.url_of_app10_Container1,
                    R.string.url_of_app11_Container1,
                    R.string.url_of_app12_Container1
            };

//            int[] positionOfApps = {R.integer.position_of_app1_Container1,
//                    R.integer.position_of_app2_Container1,
//                    R.integer.position_of_app3_Container1,
//                    R.integer.position_of_app4_Container1,
//                    R.integer.position_of_app5_Container1,
//                    R.integer.position_of_app6_Container1,
//                    R.integer.position_of_app7_Container1,
//                    R.integer.position_of_app8_Container1,
//                    R.integer.position_of_app9_Container1,
//                    R.integer.position_of_app10_Container1,
//                    R.integer.position_of_app11_Container1,
//                    R.integer.position_of_app12_Container1
//            };

            int[] nameOfApps = {R.string.name_of_app1_Container1,
                    R.string.name_of_app2_Container1,
                    R.string.name_of_app3_Container1,
                    R.string.name_of_app4_Container1,
                    R.string.name_of_app5_Container1,
                    R.string.name_of_app6_Container1,
                    R.string.name_of_app7_Container1,
                    R.string.name_of_app8_Container1,
                    R.string.name_of_app9_Container1,
                    R.string.name_of_app10_Container1,
                    R.string.name_of_app11_Container1,
                    R.string.name_of_app12_Container1
            };

            int[] drawables = {R.drawable.icon_app1,
                    R.drawable.icon_app2,
                    R.drawable.icon_app3,
                    R.drawable.icon_app4,
                    R.drawable.icon_app5,
                    R.drawable.icon_app6,
                    R.drawable.icon_app7,
                    R.drawable.icon_app8,
                    R.drawable.icon_app9,
                    R.drawable.icon_app10,
                    R.drawable.icon_app11,
                    R.drawable.icon_app12
            };

//            Map<String, Drawable> icons = new ArrayMap<>();
//            Map<String, String> urls = new ArrayMap<>();
//            Map<String, String> labels = new ArrayMap<>();
            //Map<String, Integer> positions = new ArrayMap<>();

            int i;
            for (i = 0; i < MAX_URLS_CUST_NUMBER; i++) {
                String tempUrl = null;
                String tempLabel = null;
                //int tempPosition = -1;
                Drawable tempDrw = null;
                try {
                    tempUrl = getResources().getString(urlOfApps[i]);
                    tempLabel = getResources().getString(nameOfApps[i]);
                    //tempPosition = getResources().getInteger(positionOfApps[i]);
                    tempDrw = getResources().getDrawable(drawables[i]);
                } catch (Resources.NotFoundException nfe) {
                    continue;
                }
                if (TextUtils.isEmpty(tempUrl)
                        || TextUtils.isEmpty(tempLabel)
                        || null == tempDrw) {
                    continue;
                }

//                urls.put("app_" + i, tempUrl);
//                labels.put("app_" + i, tempLabel);
//                //positions.put("app_" + i, tempPosition);
//                icons.put("app_" + i, tempDrw);
                ApplicationInfo appInfo;
                Intent intent;
                Uri uri;

                appInfo = new ApplicationInfo();
                appInfo.setmStrLabel(tempLabel);
                appInfo.setmBitMapIcon(Utilities.createIconBitmap(tempDrw, this));
                appInfo.setmType(ApplicationInfo.TYPE_URL);
                intent = new Intent();
                uri = Uri.parse(tempUrl);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(uri);
                appInfo.setmIntent(intent);
                mAppInfoDataArray.put(i, appInfo);
            }

//            ApplicationInfo appInfo;
//            Intent intent;
//            Uri uri;
//
//            for (i = 0; i < MAX_URLS_CUST_NUMBER; i++) {
//                String tempLable = labels.get("app_" + i);
//                Drawable tempDr = icons.get("app_" + i);
//                String tempUrl = urls.get("app_" + i);
//                //Integer tempPosition = positions.get("app_" + i);
//                if (TextUtils.isEmpty(tempUrl)
//                        || TextUtils.isEmpty(tempLable)
//                        || null == tempDr) {
//                    continue;
//                }
//
//                appInfo = new ApplicationInfo();
//                appInfo.setmStrLabel(tempLable);
//                appInfo.setmBitMapIcon(Utilities.createIconBitmap(tempDr, this));
//                appInfo.setmType(ApplicationInfo.TYPE_URL);
//                intent = new Intent();
//                uri = Uri.parse(tempUrl);
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setData(uri);
//                appInfo.setmIntent(intent);
//                mAppInfoDataArray.put(i, appInfo);
//            }

        }
    }

    private void apkShortcutsCustomize() {
        //if (!mUseDefaultLink) {
        int[] reservedApkPackageNames = {
                R.string.reserved1_apk_package_name_Container1,
                R.string.reserved2_apk_package_name_Container1,
                R.string.reserved3_apk_package_name_Container1,
                R.string.reserved4_apk_package_name_Container1,
                R.string.reserved5_apk_package_name_Container1,
                R.string.reserved6_apk_package_name_Container1,
                R.string.reserved7_apk_package_name_Container1,
                R.string.reserved8_apk_package_name_Container1,
                R.string.reserved9_apk_package_name_Container1,
                R.string.reserved10_apk_package_name_Container1
        };
        int[] reserved1ApkClassNames = {
                R.string.reserved1_apk_class_name_Container1,
                R.string.reserved2_apk_class_name_Container1,
                R.string.reserved3_apk_class_name_Container1,
                R.string.reserved4_apk_class_name_Container1,
                R.string.reserved5_apk_class_name_Container1,
                R.string.reserved6_apk_class_name_Container1,
                R.string.reserved7_apk_class_name_Container1,
                R.string.reserved8_apk_class_name_Container1,
                R.string.reserved9_apk_class_name_Container1,
                R.string.reserved10_apk_class_name_Container1
        };
        int[] reservedApkIsCustmizeds = {
                R.bool.reserved1_apk_is_custmized_Container1,
                R.bool.reserved2_apk_is_custmized_Container1,
                R.bool.reserved3_apk_is_custmized_Container1,
                R.bool.reserved4_apk_is_custmized_Container1,
                R.bool.reserved5_apk_is_custmized_Container1,
                R.bool.reserved6_apk_is_custmized_Container1,
                R.bool.reserved7_apk_is_custmized_Container1,
                R.bool.reserved8_apk_is_custmized_Container1,
                R.bool.reserved9_apk_is_custmized_Container1,
                R.bool.reserved10_apk_is_custmized_Container1
        };
//        int[] positionOfReservedApks = {
//                R.integer.position_of_reserved1_apk_Container1,
//                R.integer.position_of_reserved2_apk_Container1,
//                R.integer.position_of_reserved3_apk_Container1,
//                R.integer.position_of_reserved4_apk_Container1,
//                R.integer.position_of_reserved5_apk_Container1,
//                R.integer.position_of_reserved6_apk_Container1,
//                R.integer.position_of_reserved7_apk_Container1,
//                R.integer.position_of_reserved7_apk_Container1,
//                R.integer.position_of_reserved8_apk_Container1,
//                R.integer.position_of_reserved10_apk_Container1
//        };
        mPackageMgr = this.getPackageManager();
        mPackageInfoList = mPackageMgr.getInstalledPackages(0);

        int tmpPositionOfReservedApk = mAppInfoDataArray.size(); // the init position for reservedApps shortcuts.
        Log.e(TAG, "tmpPositionOfReservedApk initial size is " + tmpPositionOfReservedApk);
        int i;
        for (i = 0; i < 10; i++) {
            boolean tmpReservedApkIsCustmized = false;
            String tmpReservedApkPackageName = null;
            String tmpReservedApkClassName = null;

            try {
                tmpReservedApkIsCustmized = getResources().getBoolean(reservedApkIsCustmizeds[i]);
                if (tmpReservedApkIsCustmized) {
                    tmpReservedApkPackageName = getResources().getString(reservedApkPackageNames[i]);
                    tmpReservedApkClassName = getResources().getString(reserved1ApkClassNames[i]);
                    //tmpPositionOfReservedApk = getResources().getInteger(positionOfReservedApks[i]);

                    if (TextUtils.isEmpty(tmpReservedApkPackageName)
                            || TextUtils.isEmpty(tmpReservedApkClassName)
                            ) {
                        continue;
                    }
                } else {
                    continue;
                }
            } catch (Resources.NotFoundException nfe) {
                continue;
            }

            for (PackageInfo info : mPackageInfoList) {
                if (tmpReservedApkPackageName.equals(info.packageName)) {
                    ApplicationInfo appInfo = new ApplicationInfo();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    //
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    ComponentName comp = new ComponentName(tmpReservedApkPackageName, tmpReservedApkClassName);
                    intent.setComponent(comp);
                    appInfo.setmIntent(intent);

                    Drawable reservedAppDrawableIcon = info.applicationInfo.loadIcon(mPackageMgr);
                    if (null == reservedAppDrawableIcon) {
                        continue;
                    }
                    appInfo.setmBitMapIcon(Utilities.createIconBitmap(reservedAppDrawableIcon, this));
                    String strLabel = info.applicationInfo.loadLabel(mPackageMgr).toString().trim();
                    if (TextUtils.isEmpty(strLabel)) {
                        continue;
                    }
                    appInfo.setmStrLabel(strLabel);
                    appInfo.setmType(ApplicationInfo.TYPE_APP_SHORTCUTS);
                    mAppInfoDataArray.put(tmpPositionOfReservedApk, appInfo);
                    tmpPositionOfReservedApk++;
                }
            }
        }//End for Loop 0-10
        //}
    }//End apkShortcutsCustomize
}
