package com.example.mr_shareone.permission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    @BindView(R.id.go_test_easypermission)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toast.makeText(getApplicationContext(),checkPermission(getApplicationContext(),"")?"yes":"no",Toast.LENGTH_LONG).show();
        // Here, thisActivity is the current activity
        //这里，thisActivity表示的是当前所在activity
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            //我们需不需要一个解释？？
            //当用户不知道为什么需要获取此权限，所以取消给予权限一次后再次请求到此功能时，我们可以执行这个函数。这个函数会返回true，这里我们就可以向用户解释（dailog等等形式的弹窗）为什么需要这个权限
            //需要注意的是，如果用户上一次取消给予权限并且选择了不再提醒的话，表明用户很清楚这个权限是为什么要获取，并很清楚的表示不给此应用该权限，那么我们就没必要做出解释（这种情形，这个方法返回的是false）
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //向用户展示出解释，然后再次发出请求
                Toast.makeText(getApplicationContext(),"不解释",Toast.LENGTH_LONG).show();

            } else {

                // No explanation needed, we can request the permission.
                //不需要任何解释，直接请求这个权限（这是一个不能被我们重写的对话框）
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                Toast.makeText(getApplicationContext(),"请求权限",Toast.LENGTH_LONG).show();

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                //MY_PERMISSIONS_REQUEST_READ_CONTACTS是自定义的全局变量，用来标识自己发出的权限请求，并在onRequestPermissionsResult中做出相应的回应
            }
        }
    }
    @OnClick(R.id.go_test_easypermission)
    public void goTestEasyPermission(){
        startActivity(new Intent(this,TestEasyPermission.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                //用户选择取消获取权限，那么返回结果就是空的
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //权限获取成功，为所欲为吧！
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(),"获取成功",Toast.LENGTH_LONG).show();

                } else {
                    //权限获取失败，寸步难行啊！
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(),"获取失败",Toast.LENGTH_LONG).show();
                }
            }
            break;
            default:
                break;
            // other 'case' lines to check for other
            // permissions this app might request
            //给其他权限的请求结果作出相应的回应
        }
    }

    public boolean checkPermission(Context context, String permissionid) {
        // Assume thisActivity is the current activity
        permissionid = Manifest.permission.WRITE_CALENDAR; //！！！！测试用句！！！，实际应该由参数来决定
        int permissionCheck = ContextCompat.checkSelfPermission(context, permissionid);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //有当前权限
            return true;
        } else {
            //没有当前权限(permissionCheck == PackageManager.PERMISSION_DENIED)
            return false;
        }
    }
}
