package com.zwd.express.Context.identity.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zwd.express.Context.editSelf.View.EditSelfInfoActivity;
import com.zwd.express.Context.identity.Module.IndentityPost;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;
import com.zwd.express.util.isPhoneUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import retrofit2.Response;

public class UnVerifyActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.pic01)
    ImageView pic01;
    @BindView(R.id.take01)
    ImageView take01;
    @BindView(R.id.pic02)
    ImageView pic02;
    @BindView(R.id.take02)
    ImageView take02;
    @BindView(R.id.btn)
    TextView btn;

    private static final int REQUEST_CAMERA_CODE_01 = 1;
    private static final int REQUEST_PREVIEW_CODE_01 = 2;
    private static final int REQUEST_CAMERA_CODE_02 = 3;
    private static final int REQUEST_PREVIEW_CODE_02 = 4;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_number)
    EditText editNumber;

    private boolean flag_pic = false;
    private boolean flag_txt = false;
    private boolean flag_name = false;
    private boolean flag_number = false;
    private boolean flag_picup = false;
    private boolean flag_picdown = false;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> qiniu_key = new ArrayList<>();
    private ArrayList<String> imagePaths01 = new ArrayList<>();
    private ArrayList<String> imagePaths02 = new ArrayList<>();
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    private Bundle bundle;
    private int id;
    private String Qiniu_token;

    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "User_AddUsersIDcard.aspx";

    @Override
    protected int getContentView() {
        return R.layout.activity_un_verifity;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        Qiniu_token = bundle.getString("Qiniu_token");
        initActionBar(toolbar);
        editName.addTextChangedListener(new NameEditChangeListener());
        editNumber.addTextChangedListener(new NumberEditChangeListener());
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setTitle("身份认证");
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.setBack(R.mipmap.bg_nomal_tool);
    }

    class NameEditChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.toString().length() > 0) {
                flag_name = true;
            }else {
                flag_name = false;
            }
            check( flag_name, flag_number,flag_picup, flag_picdown);
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
    class NumberEditChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.toString().length() > 0) {
                flag_number = true;
            }else {
                flag_number = false;
            }
            check( flag_name, flag_number,flag_picup, flag_picdown);
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    }


    private void check(boolean flag_name, boolean flag_number, boolean
            flag_picup,boolean flag_picdown) {
        if (flag_name && flag_number && flag_picup && flag_picdown) {
            btn.setBackgroundResource(R.mipmap.bg_btn_yello);
            btn.setTextColor(getResources().getColor(R.color.white));
            btn.setClickable(true);
        } else {
            btn.setBackgroundResource(R.mipmap.btn_gray);
            btn.setTextColor(getResources().getColor(R.color.gray_12));
            btn.setClickable(false);
        }

    }

    /**
     * 七牛上传
     * @param filePaths
     * @param token
     * @return
     */
    public void uploadImageToQiniu(final List<String> filePaths, final String token) {
        final UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<filePaths.size();i++){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String key = "icon_" +i+"_"+ sdf.format(new Date())+"Express";
                    uploadManager.put(filePaths.get(i), key, token, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            Log.d("error:", info.toString());
                            // info.error中包含了错误信息，可打印调试
                            /////存储图片到服务器("http://opoecp2mn.bkt.clouddn.com/"+key)

                            if(key!=null&&key.length()>0){
                                qiniu_key.add("http://othse9pmj.bkt.clouddn.com/"+key);
                                Log.d("-------key",qiniu_key.size()+"");
                                if (qiniu_key.size()==filePaths.size()){
                                    Url_indetity();
                                }else {
                                    dialog_();
                                }
                            }else {
                                dialog_();
                            }
                        }
                    }, null);
                }
            }
        }).start();

    }
    private void dialog_(){
        builder = new AlertDialog.Builder(UnVerifyActivity.this);
        View view1 = LayoutInflater.from(UnVerifyActivity.this).inflate(R.layout
                .dialog_back, null);
        TextView no = (TextView) view1.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView txt = (TextView) view1.findViewById(R.id.txt);
        txt.setText("认证失败!");
        builder.setView(view1);
        dialog = builder.create();
        dialog.show();
    }

    private void Url_indetity() {
        IndentityPost post = new IndentityPost(id,editName.getText().toString()
                ,editNumber.getText().toString(),qiniu_key.get(0),qiniu_key.get(1));
        RemoteOptionIml remoteOptionIml =new RemoteOptionIml();
        remoteOptionIml.addUsersIDcard(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {

            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                builder = new AlertDialog.Builder(UnVerifyActivity.this);
                View view1 = LayoutInflater.from(UnVerifyActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        dialog.dismiss();
                    }
                });
                TextView txt = (TextView) view1.findViewById(R.id.txt);
                txt.setText("认证成功!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onFail(String message) {
                Log.d("---fail",message);
            }
        });
    }

    @OnLongClick({R.id.pic01, R.id.pic02})
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.pic01:
                builder = new AlertDialog.Builder(this);
                View view1 = LayoutInflater.from(this).inflate(R.layout
                        .dialog_takepic, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                TextView yes = (TextView) view1.findViewById(R.id.yes);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imagePaths.remove(0);
                        take01.setVisibility(View.VISIBLE);
                        pic01.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });

                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                break;
            case R.id.pic02:
                builder = new AlertDialog.Builder(this);
                View view2 = LayoutInflater.from(this).inflate(R.layout
                        .dialog_takepic, null);
                TextView no2 = (TextView) view2.findViewById(R.id.no);
                TextView yes2 = (TextView) view2.findViewById(R.id.yes);
                no2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                yes2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imagePaths.remove(1);
                        take02.setVisibility(View.VISIBLE);
                        pic02.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });

                builder.setView(view2);
                dialog = builder.create();
                dialog.show();
                Window window02 = dialog.getWindow();
                window02.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                break;
        }


        return true;
    }

    @OnClick({R.id.pic01, R.id.take01, R.id.pic02, R.id.take02, R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                uploadImageToQiniu(imagePaths,Qiniu_token);
                break;
            case R.id.pic01:
                PhotoPreviewIntent intent01 = new PhotoPreviewIntent(this);
                intent01.setCurrentItem(0);
                intent01.setPhotoPaths(imagePaths);
                startActivityForResult(intent01, REQUEST_PREVIEW_CODE_01);
                break;
            case R.id.take01:
                PhotoPickerIntent intent03 = new PhotoPickerIntent(this);
                intent03.setSelectModel(SelectModel.MULTI);
                intent03.setShowCarema(true); // 是否显示拍照
                intent03.setMaxTotal(1); // 最多选择照片数量，默认为6
                intent03.setSelectedPaths(imagePaths01); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent03, REQUEST_CAMERA_CODE_01);
                break;
            case R.id.pic02:
                PhotoPreviewIntent intent02 = new PhotoPreviewIntent(this);
                intent02.setCurrentItem(1);
                intent02.setPhotoPaths(imagePaths);
                startActivityForResult(intent02, REQUEST_PREVIEW_CODE_02);
                break;
            case R.id.take02:
                PhotoPickerIntent intent04 = new PhotoPickerIntent(this);
                intent04.setSelectModel(SelectModel.MULTI);
                intent04.setShowCarema(true); // 是否显示拍照
                intent04.setMaxTotal(1); // 最多选择照片数量，默认为6
                intent04.setSelectedPaths(imagePaths02); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent04, REQUEST_CAMERA_CODE_02);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        switch (requestCode) {
            case REQUEST_CAMERA_CODE_01:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> list = data.getStringArrayListExtra
                            (PhotoPickerActivity.EXTRA_RESULT);
                    loadAdpater01(list);
                }
                break;
            case REQUEST_CAMERA_CODE_02:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> list = data.getStringArrayListExtra
                            (PhotoPickerActivity.EXTRA_RESULT);
                    loadAdpater02(list);
                }
                break;
            // 预览
            case REQUEST_PREVIEW_CODE_01:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> ListExtra = data
                            .getStringArrayListExtra(PhotoPreviewActivity
                                    .EXTRA_RESULT);
                    loadAdpater01(ListExtra);
                }
                break;
            // 预览
            case REQUEST_PREVIEW_CODE_02:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> ListExtra = data
                            .getStringArrayListExtra(PhotoPreviewActivity
                                    .EXTRA_RESULT);
                    loadAdpater02(ListExtra);
                }
                break;

        }
    }

    private void loadAdpater01(ArrayList<String> paths) {
        imagePaths01.clear();
        imagePaths01.addAll(paths);
        if (imagePaths01 != null && imagePaths01.size() > 0) {
            take01.setVisibility(View.GONE);
            pic01.setVisibility(View.VISIBLE);
            flag_picup = true;
            Glide.with(this).load(imagePaths01.get(0)).into(pic01);
        } else {
            take01.setVisibility(View.VISIBLE);
            pic01.setVisibility(View.GONE);
            flag_picup = false;
        }
        try {
            JSONArray obj = new JSONArray(imagePaths01);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.add(0, imagePaths01.get(0));
        } else {
            imagePaths.add(imagePaths01.get(0));
        }
        check( flag_name, flag_number,flag_picup, flag_picdown);

    }

    private void loadAdpater02(ArrayList<String> paths) {
        imagePaths02.clear();
        imagePaths02.addAll(paths);
        if (imagePaths02 != null && imagePaths02.size() > 0) {
            take02.setVisibility(View.GONE);
            pic02.setVisibility(View.VISIBLE);
            flag_picdown = true;
            Glide.with(this).load(imagePaths02.get(0)).into(pic02);
        } else {
            take02.setVisibility(View.VISIBLE);
            pic02.setVisibility(View.GONE);
            flag_picdown =false;
        }
        try {
            JSONArray obj = new JSONArray(imagePaths02);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.add(1, imagePaths02.get(0));
        } else {
            imagePaths.add(imagePaths02.get(0));
        }
        check( flag_name, flag_number,flag_picup, flag_picdown);
    }


}