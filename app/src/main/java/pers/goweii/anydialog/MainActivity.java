package pers.goweii.anydialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pers.goweii.dialog.surface.AnyDialog;
import pers.goweii.dialog.base.IDataBinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvShowFull;
    private TextView mTvShowBluBg;
    private TextView mTvShowDarkBg;
    private TextView mTvShowTranBg;
    
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
                        .onClick(R.id.btn_1, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "点击了btn_1", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_blur_bg:
                AnyDialog.with(MainActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundBlur(20)
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
                        .onClick(R.id.btn_1, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
                        .onClick(R.id.btn_1, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
                        .onClick(R.id.btn_1, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "点击了btn_1", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
        }
    }
}
