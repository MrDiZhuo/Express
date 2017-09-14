package com.zwd.express.Context.roastDetail.View;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.roastDetail.Module.CommentGet;
import com.zwd.express.Context.roastDetail.Module.DetailPost;
import com.zwd.express.Context.roastDetail.Module.Newclist;
import com.zwd.express.Context.roastDetail.Module.ReplyPost;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.adapter.CommentAdapter;
import com.zwd.express.adapter.GrideNoneAdapter;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class CommentActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.grid)
    RecyclerView grid;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.btn)
    TextView btn;
    @BindView(R.id.scroll)
    ScrollView scrollView;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.name)
    TextView txt_name;
    @BindView(R.id.context)
    TextView txt_context;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private CommentAdapter adapter;

    private Bundle bundle;
    private Runnable runnable;
    private int distance;
    private String context;
    private String name;
    private int id, userid, targetid;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Chat_ReturnChatcommentDetail.aspx";
    private String url_comment = "Chat_AddChatcommentRelation.aspx";
    private CommentGet commentGet;
    private List<Newclist> newclists;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        context = bundle.getString("context");
        name = bundle.getString("name");
        id = bundle.getInt("chatid");
        userid = bundle.getInt("userid");

        initRefresh();
        initActionBar(toolbar);
        Url_commentDetail();
        edit.addTextChangedListener(new EditChangeListener());

    }

    private void initRefresh() {
        refreshLayout.setColorSchemeResources(R.color.yello);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressViewEndTarget(true, 100);
        refreshLayout.setOnRefreshListener(this);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        Url_commentDetail();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void Url_commentReply() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        ReplyPost post = new ReplyPost(id, userid, edit.getText().toString(),
                date, targetid);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.commentReply(post, baseUrl, url_comment, new
                CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                builder = new AlertDialog.Builder(CommentActivity.this);
                View view1 = LayoutInflater.from(CommentActivity.this)
                        .inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edit.setText("");
                        dialog.dismiss();

                    }
                });
                TextView txt = (TextView) view1.findViewById(R.id.txt);
                txt.setText("回复成功!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onFail(String message) {
                builder = new AlertDialog.Builder(CommentActivity.this);
                View view1 = LayoutInflater.from(CommentActivity.this)
                        .inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TextView txt = (TextView) view1.findViewById(R.id.txt);
                txt.setText("回复失败!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void Url_commentDetail() {
        DetailPost post = new DetailPost(userid,id);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.commentDetail(post, baseUrl, url, new
                CustomCallBack<RemoteDataResult<CommentGet>>() {

                    @Override
                    public void onSuccess(Response<RemoteDataResult<CommentGet>>
                                                  response) {
                        commentGet = response.body().getData();
                        Log.d("----success---", response.body()
                                .getResultMessage());
                        initView();
                    }

                    @Override
                    public void onFail(String message) {
                        Log.d("----message---", message);
                    }
                });
    }

    private void initView() {
        if (commentGet.getPicing() != null && commentGet.getPicing().length()
                > 0) {
            Glide.with(this).load(commentGet.getPicing()).into(head);
        }
        txt_name.setText(commentGet.getName());
        txt_context.setText(commentGet.getComment());
        newclists = commentGet.getNewclist();

        initRecycler();
        initSmooth();
        targetid = commentGet.getUserid();

        Log.d("----id--name--",commentGet.getName()+"  "+commentGet.getUserid());
    }

    private void initSmooth() {

        for (int i = 0; i < newclists.size(); i++) {
            if (newclists.get(i).getComment().equals(context)
                    && newclists.get(i).getUsername().equals(name)) {
                final int finalI = i;
                recycler.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        distance = recycler.getChildAt(finalI).getTop();
                        Log.d("------", distance + "");
                    }
                });
            }
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, distance);
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(runnable, 200);

    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.setBack(R.mipmap.bg_nomal_tool);
        toolbar.setTitle("评论详情");
    }




    class EditChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.length() > 0) {
                btn.setBackgroundResource(R.mipmap.btn_roast);
                btn.setTextColor(getResources().getColor(R.color.white));
                btn.setClickable(true);
            } else {
                btn.setBackgroundResource(R.mipmap.btn_roast_un);
                btn.setTextColor(getResources().getColor(R.color.gray_11));
                btn.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }


    class NoScrollLinearLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = false;

        public NoScrollLinearLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            return isScrollEnabled && super.canScrollVertically();
        }
    }

    @OnClick({R.id.btn,R.id.ll_out})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.btn:
                Url_commentReply();
                targetid = commentGet.getUserid();
                break;
            case R.id.ll_out:
                targetid = commentGet.getUserid();
                break;
        }


    }

    private void initRecycler() {
        NoScrollLinearLayoutManager linearLayoutManager = new
                NoScrollLinearLayoutManager(this);
        linearLayoutManager.setScrollEnabled(false);
        recycler.setLayoutManager(linearLayoutManager);
        adapter = new CommentAdapter(this, newclists);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter
                .OnItemClickListener() {

            @Override
            public void onItemClick(View view, ViewHolder holder, int
                    position) {

                targetid = newclists.get(position).getUserid();

                edit.setFocusable(true);
                edit.setFocusableInTouchMode(true);
                edit.requestFocus();
                InputMethodManager m = (InputMethodManager) edit
                        .getContext().getSystemService(Context
                                .INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager
                        .HIDE_NOT_ALWAYS);



            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int
                    position) {
                return false;
            }
        });

        ////占位子
        ArrayList<Integer> url = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            url.add(R.drawable.draw_white);
        }
        GrideLayoutManger layoutManger = new GrideLayoutManger(3,
                StaggeredGridLayoutManager.VERTICAL);
        grid.setLayoutManager(layoutManger);
        GrideNoneAdapter adapter = new GrideNoneAdapter(this, url);
        grid.setAdapter(adapter);

    }

    class GrideLayoutManger extends StaggeredGridLayoutManager {

        private boolean isScrollEnabled = false;


        public GrideLayoutManger(int spanCount, int orientation) {
            super(spanCount, orientation);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for
            // managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
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
