package pers.goweii.anydialog;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import pers.goweii.dialog.anim.AnimHelper;
import pers.goweii.dialog.base.IAnim;
import pers.goweii.dialog.listener.OnDialogClickListener;
import pers.goweii.dialog.surface.AnyDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long ANIM_DURATION = 350;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_show_full).setOnClickListener(this);
        findViewById(R.id.tv_show_top).setOnClickListener(this);
        findViewById(R.id.tv_show_bottom).setOnClickListener(this);
        findViewById(R.id.tv_show_blur_bg).setOnClickListener(this);
        findViewById(R.id.tv_show_dark_bg).setOnClickListener(this);
        findViewById(R.id.tv_show_tran_bg).setOnClickListener(this);
        findViewById(R.id.tv_show_bottom_in).setOnClickListener(this);
        findViewById(R.id.tv_show_bottom_alpha_in).setOnClickListener(this);
        findViewById(R.id.tv_show_top_in).setOnClickListener(this);
        findViewById(R.id.tv_show_top_alpha_in).setOnClickListener(this);
        findViewById(R.id.tv_show_top_bottom).setOnClickListener(this);
        findViewById(R.id.tv_show_bottom_top).setOnClickListener(this);
        findViewById(R.id.tv_show_top_bottom_alpha).setOnClickListener(this);
        findViewById(R.id.tv_show_bottom_top_alpha).setOnClickListener(this);
        findViewById(R.id.tv_show_left_in).setOnClickListener(this);
        findViewById(R.id.tv_show_left_alpha_in).setOnClickListener(this);
        findViewById(R.id.tv_show_right_in).setOnClickListener(this);
        findViewById(R.id.tv_show_right_alpha_in).setOnClickListener(this);
        findViewById(R.id.tv_show_left_right).setOnClickListener(this);
        findViewById(R.id.tv_show_right_left).setOnClickListener(this);
        findViewById(R.id.tv_show_left_right_alpha).setOnClickListener(this);
        findViewById(R.id.tv_show_right_left_alpha).setOnClickListener(this);
        findViewById(R.id.tv_show_reveal).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_show_full:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_1)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .onClickToDismiss(R.id.iv_1)
                        .show();
                break;
            case R.id.tv_show_top:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_3)
                        .dimAmount(0.5f)
                        .gravity(Gravity.TOP)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_bottom:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_3)
                        .dimAmount(0.5f)
                        .gravity(Gravity.BOTTOM)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_blur_bg:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundBlurRadius(20)
                        .backgroundBlurScale(8)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_dark_bg:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_tran_bg:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_bottom_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_bottom_alpha_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_top_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_top_alpha_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_top_bottom:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_bottom_top:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_top_bottom_alpha:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_bottom_top_alpha:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_left_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_left_alpha_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_right_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_right_alpha_in:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_left_right:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_right_left:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_left_right_alpha:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_right_left_alpha:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_reveal:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0.5f)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new IAnim() {
                            @Override
                            public long inAnim(View content) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    AnimHelper.startCircularRevealInAnim(content, content.getMeasuredWidth() / 2, content.getMeasuredHeight() / 2, ANIM_DURATION);
                                }
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    AnimHelper.startCircularRevealOutAnim(content, content.getMeasuredWidth() / 2, content.getMeasuredHeight() / 2, ANIM_DURATION);
                                }
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .onClick(R.id.fl_dialog_yes, new OnDialogClickListener() {
                            @Override
                            public void onClick(AnyDialog anyDialog, View v) {
                                anyDialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }
}
