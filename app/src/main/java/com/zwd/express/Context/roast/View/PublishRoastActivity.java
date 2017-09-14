package com.zwd.express.Context.roast.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zwd.express.Context.invite.View.InviteActivity;
import com.zwd.express.Context.roast.Module.PublishRoastPost;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.adapter.ChooseAdapter;
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
import java.util.List;

import butterknife.BindView;
import retrofit2.Response;
import retrofit2.http.Url;

public class PublishRoastActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.grid)
    RecyclerView grid;
    @BindView(R.id.content)
    EditText content;

    private static final int REQUEST_CAMERA_CODE = 1;
    private static final int REQUEST_PREVIEW_CODE = 2;

    private ArrayList<String> imagePaths = new ArrayList<>();
    private ChooseAdapter adapter;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    private List<String> plist = new ArrayList<>();
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Chat_AddChat.aspx";
    private int id;
    private String Qiniu_token;
    private List<String> qiniu_key=new ArrayList<>();
    private Bundle bundle;
    private boolean flag_qiniu=false;

    @Override
    protected int getContentView() {
        return R.layout.activity_publish_roast;
    }
    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        Qiniu_token = bundle.getString("Qiniu_token");
        initActionBar(toolbar);
        initGrid();

    }
    private void Url_publish(){
        PublishRoastPost post = new PublishRoastPost(id,content.getText().toString()
                ,0,qiniu_key);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.publishRoast(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {

            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                builder = new AlertDialog.Builder(PublishRoastActivity.this);
                View view1 = LayoutInflater.from(PublishRoastActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        finish();

                    }
                });
                TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                txt.setText("发布成功!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onFail(String message) {
                builder = new AlertDialog.Builder(PublishRoastActivity.this);
                View view1 = LayoutInflater.from(PublishRoastActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                txt.setText("发布失败!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
                Log.d("---",message);
            }
        });

    }

    /**
     * 七牛上传
     * @param filePaths
     * @param token
     * @return
     */
    public boolean uploadImageToQiniu(final List<String> filePaths, final String token) {
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
                                    flag_qiniu=true;
                                }else {
                                    flag_qiniu=false;
                                }
                            }else {
                                flag_qiniu=false;
                            }
                        }
                    }, null);
                }
            }
        }).start();

        return flag_qiniu;
    }
    private void initGrid() {
        StaggeredGridLayoutManager layoutManger = new
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager
                .VERTICAL);
        grid.setLayoutManager(layoutManger);
        //GrideAdapter adapter = new GrideAdapter(this,strings);
        // grid.setAdapter(adapter);
        imagePaths.add("000000");
        adapter = new ChooseAdapter(this, imagePaths);
        grid.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter
                .OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int
                    position) {
                String imgs = (String) imagePaths.get(position);
                if ("000000".equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent
                            (PublishRoastActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(20); // 最多选择照片数量，默认为6
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    PhotoPreviewIntent intent = new PhotoPreviewIntent
                            (PublishRoastActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder,
                                           final int position) {
                builder = new AlertDialog.Builder(view.getContext());
                View view1 = LayoutInflater.from(view.getContext()).inflate(R
                        .layout.dialog_takepic, null);
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
                        imagePaths.remove(position);
                        plist.remove(position);
                        adapter.notifyDataSetChanged();
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
        });
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setTitle("发布吐槽");
        toolbar.setBack(R.mipmap.bg_nomal_tool);
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setRightImg(R.mipmap.time_right);
        toolbar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (plist.size()>0){
                    if (uploadImageToQiniu(plist,Qiniu_token)){
                        Url_publish();
                    }
                }else {
                    if(content.getText().toString().length()!=0){
                        Url_publish();
                    }else {
                        ToastUtil.showShort(PublishRoastActivity.this,"请填写完整!");
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        switch (requestCode) {
            case REQUEST_CAMERA_CODE:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> list = data.getStringArrayListExtra
                            (PhotoPickerActivity.EXTRA_RESULT);
                    plist.addAll(list);
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
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (paths.contains("000000")) {
            paths.remove("000000");
        }
        paths.add("000000");
        imagePaths.addAll(paths);
        adapter.notifyDataSetChanged();
        try {
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击键盘以外的区域隐藏键盘
     *
     * @param ev
     * @return
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShoudHideKeyBoard(v, ev)) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShoudHideKeyBoard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right
                    = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() <
                    bottom && event.getY() > top) {
                return false;
            } else {
                return true;
            }
        }
        return false;

    }



}
