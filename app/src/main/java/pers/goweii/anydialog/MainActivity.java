package pers.goweii.anydialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import pers.goweii.dialog.anim.AnimHelper;
import pers.goweii.dialog.base.IContentAnim;
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
                        .show();
                break;
            case R.id.tv_show_top:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_3)
                        .dimAmount(0.5f)
                        .gravity(Gravity.TOP)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
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
                        .show();
                break;
            case R.id.tv_show_bottom:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_3)
                        .dimAmount(0.5f)
                        .gravity(Gravity.BOTTOM)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .contentAnim(new IContentAnim() {
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
                        .show();
                break;
            case R.id.tv_show_blur_bg:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundBlur(25)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .show();
                break;
            case R.id.tv_show_dark_bg:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorInt(0x55000000)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
                        .show();
                break;
            case R.id.tv_show_tran_bg:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .touchOutsideCancelable(true)
                        .clickBackCancelable(true)
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
                                AnimHelper.startBottomInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startBottomAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startTopInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startTopAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startTopInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startBottomInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startTopAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startBottomAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startLeftInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startLeftAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startRightInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startRightAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startLeftInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startRightInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startLeftAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
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
                                AnimHelper.startRightAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .show();
                break;
        }
    }
}
