package pers.goweii.anydialog;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import per.goweii.anydialog.AnimHelper;
import per.goweii.anydialog.AnyDialog;
import per.goweii.anydialog.IAnim;
import per.goweii.anydialog.OnDialogClickListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long ANIM_DURATION = 350;
    private CheckBox cb_inside_status_bar;
    private CheckBox cb_inside_navigation_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        cb_inside_status_bar = findViewById(R.id.cb_inside_status_bar);
        cb_inside_navigation_bar = findViewById(R.id.cb_inside_navigation_bar);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_1)
                        .onClickToDismiss(R.id.iv_1)
                        .show();
                break;
            case R.id.tv_show_top:
                AnyDialog.with(MainActivity.this)
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_3)
                        .gravity(Gravity.TOP)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                               return AnimHelper.createTopInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createTopOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_3)
                        .gravity(Gravity.BOTTOM)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createBottomInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createBottomOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_4)
                        .show();
                break;
            case R.id.tv_show_dark_bg:
                AnyDialog.with(MainActivity.this)
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .dimAmount(0)
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createBottomInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createBottomOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createBottomAlphaInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createBottomAlphaOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createTopInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createTopOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createTopAlphaInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createTopAlphaOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createTopInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createBottomOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createBottomInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createTopOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createTopAlphaInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createBottomAlphaOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createBottomAlphaInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createTopAlphaOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createLeftInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createLeftOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createLeftAlphaInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createLeftAlphaOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createRightInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createRightOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createRightAlphaInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createRightAlphaOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createLeftInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createRightOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createRightInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createLeftOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createLeftAlphaInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createRightAlphaOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                return AnimHelper.createRightAlphaInAnim(content);
                            }

                            @Override
                            public Animator outAnim(View content) {
                                return AnimHelper.createLeftAlphaOutAnim(content);
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
                        .insideStatusBar(cb_inside_status_bar.isChecked())
                        .insideNavigationBar(cb_inside_navigation_bar.isChecked())
                        .contentView(R.layout.dialog_test_2)
                        .contentAnim(new IAnim() {
                            @Override
                            public Animator inAnim(View content) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    return AnimHelper.createCircularRevealInAnim(content, content.getMeasuredWidth() / 2, content.getMeasuredHeight() / 2);
                                }
                                return null;
                            }

                            @Override
                            public Animator outAnim(View content) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                   return AnimHelper.createCircularRevealOutAnim(content, content.getMeasuredWidth() / 2, content.getMeasuredHeight() / 2);
                                }
                                return null;
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
