package pers.goweii.anydialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pers.goweii.dialog.anim.AnimHelper;
import pers.goweii.dialog.base.IContentAnim;
import pers.goweii.dialog.base.IDataBinder;
import pers.goweii.dialog.listener.OnDialogClickListener;
import pers.goweii.dialog.surface.AnyDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvShowFull;
    private TextView mTvShowBluBg;
    private TextView mTvShowDarkBg;
    private TextView mTvShowTranBg;
    private TextView mTvShowBottomIn1;
    private TextView mTvShowBottomIn2;
    private TextView mTvShowTopIn1;
    private TextView mTvShowTopIn2;
    private TextView mTvShowTopBottom;
    private TextView mTvShowBottomTop;
    private TextView mTvShowTopBottomAlpha;
    private TextView mTvShowBottomTopAlpha;
    private TextView mTvShowLeftIn;
    private TextView mTvShowLeftAlphaIn;
    private TextView mTvShowRightIn;
    private TextView mTvShowRightAlphaIn;
    private TextView mTvShowLeftRight;
    private TextView mTvShowRightLeft;
    private TextView mTvShowLeftRightAlpha;
    private TextView mTvShowRightLeftAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        mTvShowFull = findViewById(R.id.tv_show_full);
        mTvShowFull.setOnClickListener(this);
        mTvShowBluBg = findViewById(R.id.tv_show_blur_bg);
        mTvShowBluBg.setOnClickListener(this);
        mTvShowDarkBg = findViewById(R.id.tv_show_dark_bg);
        mTvShowDarkBg.setOnClickListener(this);
        mTvShowTranBg = findViewById(R.id.tv_show_tran_bg);
        mTvShowTranBg.setOnClickListener(this);
        mTvShowBottomIn1 = findViewById(R.id.tv_show_bottom_in);
        mTvShowBottomIn1.setOnClickListener(this);
        mTvShowBottomIn2 = findViewById(R.id.tv_show_bottom_alpha_in);
        mTvShowBottomIn2.setOnClickListener(this);
        mTvShowTopIn1 = findViewById(R.id.tv_show_top_in);
        mTvShowTopIn1.setOnClickListener(this);
        mTvShowTopIn2 = findViewById(R.id.tv_show_top_alpha_in);
        mTvShowTopIn2.setOnClickListener(this);
        mTvShowTopBottom = findViewById(R.id.tv_show_top_bottom);
        mTvShowTopBottom.setOnClickListener(this);
        mTvShowBottomTop = findViewById(R.id.tv_show_bottom_top);
        mTvShowBottomTop.setOnClickListener(this);
        mTvShowTopBottomAlpha = findViewById(R.id.tv_show_top_bottom_alpha);
        mTvShowTopBottomAlpha.setOnClickListener(this);
        mTvShowBottomTopAlpha = findViewById(R.id.tv_show_bottom_top_alpha);
        mTvShowBottomTopAlpha.setOnClickListener(this);
        mTvShowLeftIn = findViewById(R.id.tv_show_left_in);
        mTvShowLeftIn.setOnClickListener(this);
        mTvShowLeftAlphaIn = findViewById(R.id.tv_show_left_alpha_in);
        mTvShowLeftAlphaIn.setOnClickListener(this);
        mTvShowRightIn = findViewById(R.id.tv_show_right_in);
        mTvShowRightIn.setOnClickListener(this);
        mTvShowRightAlphaIn = findViewById(R.id.tv_show_right_alpha_in);
        mTvShowRightAlphaIn.setOnClickListener(this);
        mTvShowLeftRight = findViewById(R.id.tv_show_left_right);
        mTvShowLeftRight.setOnClickListener(this);
        mTvShowRightLeft = findViewById(R.id.tv_show_right_left);
        mTvShowRightLeft.setOnClickListener(this);
        mTvShowLeftRightAlpha = findViewById(R.id.tv_show_left_right_alpha);
        mTvShowLeftRightAlpha.setOnClickListener(this);
        mTvShowRightLeftAlpha = findViewById(R.id.tv_show_right_left_alpha);
        mTvShowRightLeftAlpha.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_show_full:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_1)
                        .backgroundColorInt(0x33000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .bindData(new IDataBinder() {
                            @Override
                            public void bind(AnyDialog anyDialog) {
                                TextView tv_1 = anyDialog.getView(R.id.tv_1);
                                tv_1.setText("这是在bindData（）方法中绑定的数据");
                                ImageView iv_1 = anyDialog.getView(R.id.iv_1);
                                iv_1.setImageResource(R.mipmap.ic_launcher);
                            }
                        })
                        .onClick(R.id.btn_1, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                Toast.makeText(MainActivity.this, "点击了btn_1", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_blur_bg:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundBlur(25)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .bindData(new IDataBinder() {
                            @Override
                            public void bind(AnyDialog anyDialog) {
                                TextView tv_1 = anyDialog.getView(R.id.tv_1);
                                tv_1.setText("这是在bindData（）方法中绑定的数据");
                                ImageView iv_1 = anyDialog.getView(R.id.iv_1);
                                iv_1.setImageResource(R.mipmap.ic_launcher);
                            }
                        })
                        .onClick(R.id.btn_1, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                Toast.makeText(MainActivity.this, "点击了btn_1", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_dark_bg:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .bindData(new IDataBinder() {
                            @Override
                            public void bind(AnyDialog anyDialog) {
                                TextView tv_1 = anyDialog.getView(R.id.tv_1);
                                tv_1.setText("这是在bindData（）方法中绑定的数据");
                                ImageView iv_1 = anyDialog.getView(R.id.iv_1);
                                iv_1.setImageResource(R.mipmap.ic_launcher);
                            }
                        })
                        .onClick(R.id.btn_1, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                Toast.makeText(MainActivity.this, "点击了btn_1", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_tran_bg:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .bindData(new IDataBinder() {
                            @Override
                            public void bind(AnyDialog anyDialog) {
                                TextView tv_1 = anyDialog.getView(R.id.tv_1);
                                tv_1.setText("这是在bindData（）方法中绑定的数据");
                                ImageView iv_1 = anyDialog.getView(R.id.iv_1);
                                iv_1.setImageResource(R.mipmap.ic_launcher);
                            }
                        })
                        .onClick(R.id.btn_1,new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                Toast.makeText(MainActivity.this, "点击了btn_1", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_bottom_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_bottom_alpha_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomAlphaInAnim(content, 150);
                                return 150;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomAlphaOutAnim(content, 150);
                                return 150;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_top_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_top_alpha_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopAlphaInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopAlphaOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_top_bottom:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_bottom_top:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_top_bottom_alpha:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopAlphaInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomAlphaOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_bottom_top_alpha:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomAlphaInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopAlphaOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_left_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_left_alpha_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftAlphaInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftAlphaOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_right_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_right_alpha_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightAlphaInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightAlphaOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_left_right:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_right_left:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_left_right_alpha:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftAlphaInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightAlphaOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_right_left_alpha:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightAlphaInAnim(content, 300);
                                return 300;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftAlphaOutAnim(content, 300);
                                return 300;
                            }
                        })
                        .show();
                break;
        }
    }
}
