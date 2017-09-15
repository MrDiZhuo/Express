package com.lidong.photopicker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lidong.photopicker.widget.ToolBar;
import com.lidong.photopicker.widget.ViewPagerFixed;

import java.util.ArrayList;

/**
 * Created by foamtrace on 2015/8/25.
 */
public class PhotoPreviewActivity extends AppCompatActivity implements PhotoPagerAdapter.PhotoViewClickListener{

    private ToolBar backActionBar;
    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_CURRENT_ITEM = "extra_current_item";

    /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合  */
    public static final String EXTRA_RESULT = "preview_result";

    /** 预览请求状态码 */
    public static final int REQUEST_PREVIEW = 99;

    private ArrayList<String> paths;
    private ViewPagerFixed mViewPager;
    private PhotoPagerAdapter mPagerAdapter;
    private int currentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_preview);

        initViews();

        paths = new ArrayList<>();
        ArrayList<String> pathArr = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
        if(pathArr != null){
            paths.addAll(pathArr);
        }

        currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);

        mPagerAdapter = new PhotoPagerAdapter(this, paths);
        mPagerAdapter.setPhotoViewClickListener(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.setOffscreenPageLimit(5);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateActionBarTitle();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        updateActionBarTitle();
    }

    private void initViews(){
        mViewPager = (ViewPagerFixed) findViewById(R.id.vp_photos);
        backActionBar = (ToolBar) findViewById(R.id.action_bar_1);
        initActionBar(backActionBar);
    }

    private void initActionBar(ToolBar backActionBar) {
        backActionBar.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_RESULT, paths);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        backActionBar.hidesubTitle();
        backActionBar.setCustomButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int index = mViewPager.getCurrentItem();
                final String deletedPath =  paths.get(index);
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.deleted_a_photo,
                        Snackbar.LENGTH_LONG);
                if(paths.size() <= 1){
                    // 最后一张照片弹出删除提示
                    // show confirm dialog
                    new AlertDialog.Builder(PhotoPreviewActivity.this)
                            .setTitle(R.string.confirm_to_delete)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    paths.remove(index);
                                    onBackPressed();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }else{
                    snackbar.show();
                    paths.remove(index);
                    mPagerAdapter.notifyDataSetChanged();
                }

                snackbar.setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (paths.size() > 0) {
                            paths.add(index, deletedPath);
                        } else {
                            paths.add(deletedPath);
                        }
                        mPagerAdapter.notifyDataSetChanged();
                        mViewPager.setCurrentItem(index, true);
                    }
                });
            }

        });
    }

    @Override
    public void OnPhotoTapListener(View view, float v, float v1) {
        onBackPressed();
    }

    public void updateActionBarTitle() {
        backActionBar.setTitle(getString(R.string.image_index, mViewPager.getCurrentItem() + 1, paths.size()));
    }




}
