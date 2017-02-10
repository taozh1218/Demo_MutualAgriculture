package com.taozhang.demo_mutualagriculture.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Description:获取图片的工具类（拍照或从相册选择）
 * Created by taozhang on 2016/2/21.
 * Company:Geowind,University of South China.
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/2/21
 */
public class ImageUtil {

    public static final int REQUEST_CODE_FROM_CAMERA = 5001;
    public static final int REQUEST_CODE_FROM_ALBUM = 5002;

    /**
     * 存放拍照图片的uri地址
     */
    public static Uri imageUriFromCamera;

    /**
     * 显示获取照片方式的对话框
     *
     * @param activity
     */
    public static void showImagePickDialog(final Activity activity) {
        String title = "请选择图片获取方式";
        String[] item = new String[]{"拍照", "相册"};
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                pickImageFromCamera(activity);
                                break;
                            case 1:
                                pickImageFromAlbum(activity);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }

    /**
     * 打开相机拍照获取图片
     *
     * @param activity
     */
    private static void pickImageFromCamera(Activity activity) {
        //首先要新建一个uri地址
        imageUriFromCamera = createImageUri(activity);//activity 是context的子类

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//图片拍照抓取
        //如果不设置output ，获取到的是Bitmap，但是是压缩的Bitmap，不符合需求
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);//图片存储地址
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
    }

    /**
     * 创建一条图片的uri，用于保存拍的照片，此时图片内容是空的
     *
     * @param context
     * @return
     */
    private static Uri createImageUri(Context context) {
        String name = "huNongImg" + System.currentTimeMillis();
        //图片信息地址是通过contentProvider提供的，解析使用contentResolver
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, name);//图片名
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpeg");//文件名
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");//文件类型
        System.out.println("MediaStore.Images.Media.EXTERNAL_CONTENT_URI:" + MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "," + contentValues.toString());
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        return uri;
    }


    /**
     * 从相册选取图片
     *
     * @param activity
     */
    public static void pickImageFromAlbum(final Activity activity) {
        //显示意图：指定目标页面是哪个Activity；
        // 隐式则是不指定目标页面，而是设置action动作、type etc.通过这些设置，让系统自动匹配符合的目标页面

        //第一种隐式意图
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);//返回的数据只有一种类型 allow user to select a particular kind of data and return it.
        //因为intent设置的是指定特殊类型，所以接下来需要指定类型，这里是要选择图片类型
        intent.setType("image/*");//匹配所有格式的图片
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM);//跳转到目标页面并返回数据，context是只有startActivity的方法，Activity才有result方法
    }

    /**
     * 从相册选取图片2
     *
     * @param activity
     */
    public static void pickImageFromAlbum2(final Activity activity) {
        //第二种隐式意图
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);//返回的数据有多种，就像是通讯录 pick an item from the data and return.

        //需要指定目标地址,通常使用data指定url地址
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//外部图片储存目录
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM);
    }

    /**
     * 删除图片
     * @param context
     * @param uri
     */
    public static void deleteImageUri(Context context, Uri uri) {
        context.getContentResolver().delete(uri,null,null);
    }
}
