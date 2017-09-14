package com.zwd.express.Context.liveRoom.View;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.MicrophoneStreamingSetting;
import com.qiniu.pili.droid.streaming.StreamStatusCallback;
import com.qiniu.pili.droid.streaming.StreamingEnv;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;
import com.zwd.express.Context.liveList.Module.RoomPublishGet;
import com.zwd.express.Context.liveRoom.Module.LiveRoomUpdateGet;
import com.zwd.express.Context.liveRoom.Module.LiveRoomUpdatePost;
import com.zwd.express.Context.liveRoom.Module.RoomPicGet;
import com.zwd.express.Context.liveTake.View.LiveTakeActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.live.CameraPreviewFrameView;
import com.zwd.express.Widget02.live.ChatListView;
import com.zwd.express.Widget02.live.LiveKit;
import com.zwd.express.adapter.ChatListAdapter;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.MessageContent;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveRoom_TakeActivity extends BaseActivity implements
        StreamingStateChangedListener
        , CameraPreviewFrameView.Listener, StreamStatusCallback, Handler
        .Callback {

    @BindView(R.id.cameraPreview_surfaceView)
    CameraPreviewFrameView cameraPreviewFrameView;
    @BindView(R.id.cameraPreview_afl)
    AspectFrameLayout afl;
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.camera)
    ImageView camera;
    @BindView(R.id.chat_listview)
    ChatListView chat_listview;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.num)
    TextView num;

    private MediaStreamingManager streamingManager;
    private StreamingProfile streamingProfile;
    private MicrophoneStreamingSetting mMicrophoneStreamingSetting;
    private CameraStreamingSetting setting;
    private boolean flag_front = false;
    private CameraSwitch cameraSwitch = new CameraSwitch();
    private ChatListAdapter chatListAdapter;
    private Handler handler = new Handler(this);
    private Bundle bundle;
    private int id;
    private int liveId;
    private String livename;
    private String roomId;///直播房间===聊天室ID
    private String publishUrl;
    private AlertDialog dialog, dialog_2;
    private AlertDialog.Builder builder;
    private String baseUrl_snap = "http://139.224.164.183:8009/";
    private String url_snap = "SnapshotStream.aspx";
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Playing_ClosePlaying.aspx";
    private String url_update = "Playing_UpdatePlayingPic.aspx";
    private String picurl = "";
    private boolean flag_out = false;
    private Timer timer;
    @Override
    protected int getContentView() {
        return R.layout.activity_live_room__take;
    }

    @Override
    protected void initViews() {
        StreamingEnv.init(getApplicationContext());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        publishUrl = bundle.getString("publishUrl");
        liveId = bundle.getInt("liveId");
        roomId = bundle.getString("roomId");
        livename = bundle.getString("livename");
        initActionBar(toolbar);
        initCameraPreview();
        initChat();
        timer = new Timer();
        timer.schedule(new updateTask(),0,60000);
    }
    class updateTask extends TimerTask{

        @Override
        public void run() {
            Url_update();
        }
    }

    private void joinChatRoom(final String roomId) {
        LiveKit.joinChatRoom(roomId, 4, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                final InformationNotificationMessage content =
                        InformationNotificationMessage.obtain("来啦");
                LiveKit.sendMessage(content);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Toast.makeText(LiveRoom_TakeActivity.this, "聊天室加入失败! " +
                        "errorCode = " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dialog_fail() {
        builder = new AlertDialog.Builder(BaseAppManager.getInstance()
                .getForwardActivity());
        View view1 = LayoutInflater.from(BaseAppManager.getInstance()
                .getForwardActivity()).inflate(R.layout
                .dialog_back, null);
        TextView no = (TextView) view1.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_2.dismiss();
            }
        });
        TextView txt = (TextView) view1.findViewById(R.id.txt);
        txt.setText("直播间关闭失败!");
        builder.setView(view1);
        dialog_2 = builder.create();
        dialog_2.show();
    }

    ////关闭直播
    private boolean Url_closePlay() {
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        Log.d("----liveId", liveId + "");
        remoteOptionIml.closeLive(liveId, baseUrl, url, new
                CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                Log.d("---success",response.body().getResultCode()+"");
                LiveKit.quitChatRoom(new RongIMClient.OperationCallback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                    }
                });
                flag_out = true;
            }
            @Override
            public void onFail(String message) {
                Log.d("---fail", message);
                flag_out = false;
            }
        });
        return flag_out;
    }

    private void initChat() {
        LiveKit.init(this);
        LiveKit.addEventHandler(handler);
        chatListAdapter = new ChatListAdapter();
        chat_listview.setAdapter(chatListAdapter);
        joinChatRoom(roomId);

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LiveKit.MESSAGE_ARRIVED: {
                MessageContent content = (MessageContent) msg.obj;
                chatListAdapter.addMessage(content);
                break;
            }
            case LiveKit.MESSAGE_SENT: {
                MessageContent content = (MessageContent) msg.obj;
                chatListAdapter.addMessage(content);
                break;
            }
            case LiveKit.MESSAGE_SEND_ERROR: {
                break;
            }
            default:
        }
        chatListAdapter.notifyDataSetChanged();
        return false;
    }


    private void initCameraPreview() {
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL); //  FULL,REAL;
        cameraPreviewFrameView.setListener(this);
        setting = new CameraStreamingSetting();
        streamingProfile = new StreamingProfile();
        try {
            streamingProfile.setVideoQuality(StreamingProfile
                    .VIDEO_QUALITY_MEDIUM2)
                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
                    //                .setPreferredVideoEncodingSize(960, 544)
                    .setEncodingSizeLevel(StreamingProfile
                            .VIDEO_ENCODING_HEIGHT_480)
                    .setEncoderRCMode(StreamingProfile.EncoderRCModes
                            .BITRATE_PRIORITY)
                    //                .setAVProfile(avProfile)
                    .setAdaptiveBitrateEnable(true)
                    .setFpsControllerEnable(true)
                    .setStreamStatusConfig(new StreamingProfile
                            .StreamStatusConfig(3))
                    .setPublishUrl(publishUrl)
                    //                .setEncodingOrientation
                    // (StreamingProfile.ENCODING_ORIENTATION.PORT)
                    .setSendingBufferProfile(new StreamingProfile
                            .SendingBufferProfile(0.2f, 0.8f, 3.0f, 20 * 1000));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
                .setContinuousFocusModeEnabled(true)
                .setCameraPrvSizeLevel(CameraStreamingSetting
                        .PREVIEW_SIZE_LEVEL.MEDIUM)
                .setCameraPrvSizeRatio(CameraStreamingSetting
                        .PREVIEW_SIZE_RATIO.RATIO_16_9);

        streamingManager = new MediaStreamingManager(this, afl,
                cameraPreviewFrameView,
                AVCodecType.HW_VIDEO_WITH_HW_AUDIO_CODEC); // hw codec  //
        // soft codec
        mMicrophoneStreamingSetting = new MicrophoneStreamingSetting();
        mMicrophoneStreamingSetting.setBluetoothSCOEnabled(false);
        streamingManager.prepare(setting, mMicrophoneStreamingSetting, null,
                streamingProfile);
        streamingManager.setStreamingStateListener(this);

    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (Url_closePlay()) {
                    finish();
                } else {
                    dialog_fail();
                }
            }
        });
        toolbar.setTitle("我的直播间");
        toolbar.hideRight();
    }

    @OnClick({R.id.camera, R.id.btn_send, R.id.btn})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.camera:
                camera.removeCallbacks(cameraSwitch);
                camera.postDelayed(cameraSwitch, 100);
                break;
            case R.id.btn_send:
                if (edit.getText().toString().length() > 0) {
                    TextMessage textMsg = TextMessage.obtain(edit.getText()
                            .toString());
                    Log.d("msg", textMsg.getContent());
                    LiveKit.sendMessage(textMsg);
                    edit.setText("");
                }
                break;
            case R.id.btn:
                builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(v.getContext())
                        .inflate(R.layout.dialog_takepic, null);
                TextView txt = (TextView) view.findViewById(R.id.txt);
                txt.setText("确认结束直播?");
                TextView yes = (TextView) view.findViewById(R.id.yes);
                TextView no = (TextView) view.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (Url_closePlay()) {
                            dialog.dismiss();
                            finish();
                            bundle = new Bundle();
                            bundle.putInt("liveId",liveId);
                            bundle.putString("livename",livename);
                            goActivity(LiveTakeActivity.class,bundle);
                        } else {
                            dialog_fail();
                        }
                    }
                });
                builder.setView(view);
                dialog = builder.create();
                dialog.show();
                break;
        }


    }

    ///更新直播信息
    private void Url_update() {
        Url_roomSnap();
        LiveRoomUpdatePost post = new LiveRoomUpdatePost(liveId, picurl);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.updateLive(post, baseUrl, url_update, new
                CustomCallBack<RemoteDataResult<LiveRoomUpdateGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<LiveRoomUpdateGet
                    >> response) {
                LiveRoomUpdateGet get = response.body().getData();
                int cou = get.getCou();
                Log.d("----cou",cou+""+":"+picurl);
                num.setText(String.valueOf(cou));
            }
            @Override
            public void onFail(String message) {

            }
        });
    }
    ///获得直播图片
    private void Url_roomSnap(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.roomSnap(roomId, baseUrl_snap, url_snap, new Callback<RoomPicGet>() {

            @Override
            public void onResponse(Call<RoomPicGet> call,
                                   Response<RoomPicGet> response) {
                RoomPicGet get = response.body();
                picurl = get.getPublishUrl();
                Log.d("----playUrl",picurl);
            }

            @Override
            public void onFailure(Call<RoomPicGet> call, Throwable t) {

            }
        });
    }
    ///转换摄像头
    private class CameraSwitch implements Runnable {

        @Override
        public void run() {
            CameraStreamingSetting.CAMERA_FACING_ID facing_id;
            if (flag_front) {
                facing_id = CameraStreamingSetting.CAMERA_FACING_ID
                        .CAMERA_FACING_BACK;
                flag_front = false;
            } else {
                facing_id = CameraStreamingSetting.CAMERA_FACING_ID
                        .CAMERA_FACING_FRONT;
                flag_front = true;
            }
            streamingManager.switchCamera(facing_id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        streamingManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // You must invoke pause here.
        streamingManager.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        streamingManager.destroy();
    }

    @Override
    public void onStateChanged(StreamingState streamingState, Object o) {
        switch (streamingState) {
            case PREPARING:
                Log.d("-----------IOERROR", "PREPARING");
                break;
            case READY:
                //streamingManager.startStreaming();
                // start streaming when READY
                Log.d("-----------IOERROR", "READY");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (streamingManager != null) {
                            streamingManager.startStreaming();
                        }
                    }
                }).start();
                break;
            case CONNECTING:
                Log.d("-----------IOERROR", "CONNECTING");
                break;
            case STREAMING:
                Log.d("-----------IOERROR", " had been sent");
                // The av packet had been sent.
                break;
            case SHUTDOWN:
                // The streaming had been finished.
                break;
            case IOERROR:
                Log.d("-----------IOERROR", "IOERROR");
                // Network connect error.
                break;
            case SENDING_BUFFER_EMPTY:
                Log.d("-----------IOERROR", "SENDING_BUFFER_EMPTY");
                break;
            case SENDING_BUFFER_FULL:
                Log.d("-----------IOERROR", "SENDING_BUFFER_FULL");
                break;
            case AUDIO_RECORDING_FAIL:
                Log.d("-----------IOERROR", "AUDIO_RECORDING_FAIL");

                // Failed to record audio.
                break;
            case OPEN_CAMERA_FAIL:
                Log.d("-----------IOERROR", "OPEN_CAMERA_FAIL");

                // Failed to open camera.
                break;
            case DISCONNECTED:
                Log.d("-----------IOERROR", "DISCONNECTED");
                // The socket is broken while streaming
                break;
        }
    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onZoomValueChanged(float factor) {
        return false;
    }

    //推流状态信息
    @Override
    public void notifyStreamStatusChanged(final StreamingProfile.StreamStatus
                                                  streamStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("-----sate", "bitrate:" + streamStatus.totalAVBitrate /
                        1024 + " kbps"
                        + "\naudio:" + streamStatus.audioFps + " fps"
                        + "\nvideo:" + streamStatus.videoFps + " fps");
            }
        });
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

    /**
     * 按BACK键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Url_closePlay()) {
                finish();
            } else {
                dialog_fail();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
