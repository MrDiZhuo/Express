package com.zwd.express.Context.confirmTake.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.zwd.express.Context.confirmTake.Module.ConfirmTakeGet;
import com.zwd.express.Context.confirmTake.Module.ConfirmTakePost;
import com.zwd.express.Context.orderDetail.Module.OrderDetailPost;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;
import com.zwd.express.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class ConfirmTake_BuyActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.take)
    ImageView take;
    @BindView(R.id.edit)
    EditText edit;

    private static final int REQUEST_CAMERA_CODE = 1;
    private static final int REQUEST_PREVIEW_CODE = 2;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_reward)
    TextView txtReward;


    private boolean flag_pic = false;
    private boolean flag_txt = false;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private Bundle bundle;
    private String Qiniu_token;

    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Accept_ReturnDoneAccept.aspx";
    private String url_confirm = "Accept_DoneAccept.aspx";
    private int id;
    private int stu;
    private Intent intent;

    @Override
    protected int getContentView() {
        return R.layout.activity_confirm_take_buy;
    }

    @Override
    protected void initViews() {
        intent = getIntent();
        bundle = intent.getExtras();
        id = bundle.getInt("id");
        stu = bundle.getInt("stu");
        Qiniu_token = bundle.getString("Qiniu_token");
        initActionBar(toolbar);
        Url_confirmdetail();
    }

    private void Url_confirmdetail() {
        OrderDetailPost post = new OrderDetailPost(stu, id);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returnDoneAccept(post, baseUrl, url, new
                CustomCallBack<RemoteDataResult<ConfirmTakeGet>>() {
                    @Override
                    public void onSuccess
                            (Response<RemoteDataResult<ConfirmTakeGet>>
                                     response) {
                        ConfirmTakeGet get = response.body().getData();
                        initView(get);
                    }
                    @Override
                    public void onFail(String message) {
                        Log.d("---fail", message);
                    }
                });
    }

    private void initView(ConfirmTakeGet get) {
        if (get.getHeading() != null && get.getHeading().length() > 0) {
            Glide.with(this).load(get.getHeading()).into(head);
        } else {
            Glide.with(this).load(R.mipmap.pic_head).into(head);
        }
        txtName.setText(get.getName());
        txtPhone.setText(get.getUsernane());
        txtPrice.setText("实际消费:¥" + get.getPrice());
        txtReward.setText("打赏金额:¥" + get.getReward());
    }

    private void Url_confrim(String picurl) {
        ConfirmTakePost post = new ConfirmTakePost(stu, id, Double.valueOf(edit.getText().toString()), picurl);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.doneAccept(post, baseUrl, url_confirm, new
                CustomCallBack<RemoteDataResult>() {
                    @Override
                    public void onSuccess(Response<RemoteDataResult> response) {
                        builder = new AlertDialog.Builder(ConfirmTake_BuyActivity
                                .this);
                        View view1 = LayoutInflater.from(ConfirmTake_BuyActivity
                                .this).inflate(R.layout
                                .dialog_back, null);
                        TextView no = (TextView) view1.findViewById(R.id.no);
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("flag", true);
                                intent.putExtras(bundle);
                                ConfirmTake_BuyActivity.this.setResult(0, intent);
                                ConfirmTake_BuyActivity.this.finish();
                            }
                        });
                        TextView txt = (TextView) view1.findViewById(R.id.txt);
                        txt.setText("确认成功!");
                        builder.setView(view1);
                        dialog = builder.create();
                        dialog.show();
                    }

                    @Override
                    public void onFail(String message) {
                        dialog_();
                        Log.d("---", message);
                    }
                });
    }

    private void dialog_() {
        builder = new AlertDialog.Builder(ConfirmTake_BuyActivity
                .this);
        View view1 = LayoutInflater.from(ConfirmTake_BuyActivity
                .this).inflate(R.layout
                .dialog_back, null);
        TextView no = (TextView) view1.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView txt = (TextView) view1.findViewById(R.id.txt);
        txt.setText("确认失败!");
        builder.setView(view1);
        dialog = builder.create();
        dialog.show();
    }

    @OnLongClick(R.id.pic)
    public boolean onLongClick() {
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
                imagePaths.clear();
                take.setVisibility(View.VISIBLE);
                pic.setVisibility(View.GONE);
                flag_pic = false;
                dialog.dismiss();
            }
        });

        builder.setView(view1);
        dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        return true;
    }

    /**
     * 七牛上传
     *
     * @param filePath
     * @param token
     * @return
     */
    public void uploadImageToQiniu(String filePath, String token) {
        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date());
        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject
                    res) {
                Log.d("error:", info.toString());
                if (key != null && key.length() > 0) {
                    Log.d("-------key", key);
                    Url_confrim("http://othse9pmj.bkt.clouddn.com/"+key);
                } else {
                    dialog_();
                }

            }
        }, null);
    }


    @OnClick({R.id.pic, R.id.take, R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pic:
                PhotoPreviewIntent intent01 = new PhotoPreviewIntent
                        (ConfirmTake_BuyActivity.this);
                intent01.setCurrentItem(0);
                intent01.setPhotoPaths(imagePaths);
                startActivityForResult(intent01, REQUEST_PREVIEW_CODE);
                break;
            case R.id.take:
                PhotoPickerIntent intent02 = new PhotoPickerIntent
                        (ConfirmTake_BuyActivity.this);
                intent02.setSelectModel(SelectModel.MULTI);
                intent02.setShowCarema(true); // 是否显示拍照
                intent02.setMaxTotal(1); // 最多选择照片数量，默认为6
                intent02.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent02, REQUEST_CAMERA_CODE);
                break;
            case R.id.btn:
                if (imagePaths.size()>0&&edit.getText().toString().length()>0){
                    uploadImageToQiniu(imagePaths.get(0), Qiniu_token);
                }else {
                    ToastUtil.showShort(ConfirmTake_BuyActivity.this,"请填写完整!");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        switch (requestCode) {
            case REQUEST_CAMERA_CODE:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> list = data.getStringArrayListExtra
                            (PhotoPickerActivity.EXTRA_RESULT);
                    loadAdpater(list);
                }
                break;
            // 预览
            case REQUEST_PREVIEW_CODE:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> ListExtra = data
                            .getStringArrayListExtra(PhotoPreviewActivity
                                    .EXTRA_RESULT);
                    loadAdpater(ListExtra);
                }
                break;

        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        imagePaths.clear();
        imagePaths.addAll(paths);
        if (imagePaths != null && imagePaths.size() > 0) {
            take.setVisibility(View.GONE);
            pic.setVisibility(View.VISIBLE);
            flag_pic = true;
            Glide.with(this).load(imagePaths.get(0)).into(pic);
        } else {
            take.setVisibility(View.VISIBLE);
            pic.setVisibility(View.GONE);
            flag_pic = false;
        }
        try {
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.setTitle("订单确认");
        toolbar.setBack(R.mipmap.bg_nomal_tool);
    }

}
