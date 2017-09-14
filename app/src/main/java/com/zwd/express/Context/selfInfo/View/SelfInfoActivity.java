package com.zwd.express.Context.selfInfo.View;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.soundcloud.android.crop.Crop;
import com.zwd.express.Context.editSelf.View.EditSelfInfoActivity;
import com.zwd.express.Context.integrate.View.IntegrateActivity;
import com.zwd.express.Context.selfInfo.Module.EditHeadPost;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Response;

public class SelfInfoActivity extends BaseActivity {
    @BindView(R.id.bg_img)
    ImageView bg_img;
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_username)
    TextView txtUsername;
    @BindView(R.id.txt_credit)
    TextView txtCredit;
    @BindView(R.id.img_credit)
    ImageView imgCredit;
    @BindView(R.id.img_userstatus)
    ImageView imgUserstatus;

    private Bundle bundle;
    private int id;
    private String heading;
    private String Qiniu_token;
    private String username;
    private String name;
    private String sex;
    private int credit;
    private int userstatus;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "User_EditUserHeading.aspx";

    @Override
    protected int getContentView() {
        return R.layout.activity_self_info;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id", id);
        heading = bundle.getString("heading");
        Qiniu_token = bundle.getString("Qiniu_token");
        name = bundle.getString("name");
        username = bundle.getString("username");
        sex = bundle.getString("sex");
        credit = bundle.getInt("credit");
        userstatus = bundle.getInt("userstatus");
        Log.d("----token", Qiniu_token);
        initActionBar(toolbar);
        initTop();

    }

    private String getPath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new
                            String[]{MediaStore.Images.ImageColumns.DATA}, null,
                    null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images
                            .ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 七牛上传
     *
     * @param
     * @param token
     * @return
     */
    public void uploadImageToQiniu(final Uri uri, String token) {
        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        String filePath = getPath(this, uri);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date());
        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject
                    res) {
                Log.d("error:", info.toString());
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                Log.d("-----key", key);
                if (key != null && key.length() > 0) {
                    Log.d("-------key", key);
                    /////存储图片到服务器("http://othse9pmj.bkt.clouddn.com/"+key)
                    url_editHead("http://othse9pmj.bkt.clouddn.com/" + key,
                            uri);
                } else {
                    dialog_();
                }
            }
        }, null);
    }

    private void dialog_() {
        builder = new AlertDialog.Builder(BaseAppManager.getInstance()
                .getForwardActivity());
        View view1 = LayoutInflater.from(BaseAppManager.getInstance()
                .getForwardActivity()).inflate(R.layout
                .dialog_back, null);
        TextView no = (TextView) view1.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView txt = (TextView) view1.findViewById(R.id.txt);
        txt.setText("上传失败!");
        builder.setView(view1);
        dialog = builder.create();
        dialog.show();
    }

    private void url_editHead(final String str_head, final Uri uri) {
        EditHeadPost post = new EditHeadPost(id, str_head);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.editHead(post, baseUrl, url, new
                CustomCallBack<RemoteDataResult>() {
                    @Override
                    public void onSuccess(Response<RemoteDataResult> response) {
                        Glide.with(SelfInfoActivity.this).load(uri).into(head);
                        Glide.with(SelfInfoActivity.this).load(uri)
                                .bitmapTransform
                                        (new BlurTransformation(SelfInfoActivity
                                                .this, 2, 4))
                                .into(new SimpleTarget<GlideDrawable>() {
                                    @Override
                                    public void onResourceReady(GlideDrawable
                                                                        resource,
                                                                GlideAnimation<?
                                                                        super
                                                                        GlideDrawable> glideAnimation) {
                                        bg_img.setBackground(resource);
                                    }
                                });
                    }

                    @Override
                    public void onFail(String message) {
                        dialog_();
                        Log.d("---fail", message);
                    }
                });
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("个人资料");
        toolbar.hideRight();
    }

    private void initTop() {
        if (userstatus == 0) {
            imgUserstatus.setImageResource(R.mipmap.icon_uncertify);
        } else {
            imgUserstatus.setImageResource(R.mipmap.icon_certify);
        }
        txtCredit.setText(String.valueOf(credit));
        if (credit < 60) {
            imgCredit.setImageResource(R.mipmap.icon_fail);
        } else if (credit > 59 && credit < 90) {
            imgCredit.setImageResource(R.mipmap.icon_well);
        } else {
            imgCredit.setImageResource(R.mipmap.icon_excellent);
        }
        if (heading != null && heading.length() > 0) {
            Glide.with(SelfInfoActivity.this).load(heading).into(head);
            Glide.with(SelfInfoActivity.this).load(heading).bitmapTransform
                    (new BlurTransformation(SelfInfoActivity.this, 2, 4))
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource,
                                                    GlideAnimation<? super
                                                            GlideDrawable>
                                                            glideAnimation) {
                            bg_img.setBackground(resource);
                        }
                    });
        } else {
            Glide.with(SelfInfoActivity.this).load(R.mipmap.pic_head).into
                    (head);
            Glide.with(SelfInfoActivity.this).load(R.mipmap.pic_head)
                    .bitmapTransform(new BlurTransformation(SelfInfoActivity
                            .this, 2, 4))
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource,
                                                    GlideAnimation<? super
                                                            GlideDrawable>
                                                            glideAnimation) {
                            bg_img.setBackground(resource);
                        }
                    });
        }
        txtName.setText(name);
        txtUsername.setText(username);
    }

    @OnClick({R.id.edit, R.id.integrate, R.id.head})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.edit:
                bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("name", name);
                bundle.putString("username", username);
                bundle.putString("sex", sex);
                goActivity(EditSelfInfoActivity.class, bundle);
                break;
            case R.id.integrate:
                bundle = new Bundle();
                bundle.putInt("id", id);
                goActivity(IntegrateActivity.class, bundle);
                break;
            case R.id.head:
                Crop.pickImage(this);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        Uri destination = Uri.fromFile(new File(getCacheDir(), date));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            Log.d("----file", uri.toString());
            uploadImageToQiniu(uri, Qiniu_token);

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast
                    .LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
