package pers.goweii.anydialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import per.goweii.anydialog.AnyDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_def.setOnClickListener {
            AnyDialog(this).apply {
                style(AnyDialog.Style.CENTER)
                contentView(R.layout.dialog_def)
                clickDismiss(R.id.tv_close)
            }.show()
        }
        btn_top.setOnClickListener {
            AnyDialog(this).apply {
                style(AnyDialog.Style.TOP)
                contentView(R.layout.dialog_def)
                clickDismiss(R.id.tv_close)
            }.show()
        }
        btn_bottom.setOnClickListener {
            AnyDialog(this).apply {
                style(AnyDialog.Style.BOTTOM)
                contentView(R.layout.dialog_def)
                clickDismiss(R.id.tv_close)
            }.show()
        }
        btn_left.setOnClickListener {
            AnyDialog(this).apply {
                style(AnyDialog.Style.LEFT)
                contentView(R.layout.dialog_def)
                clickDismiss(R.id.tv_close)
            }.show()
        }
        btn_right.setOnClickListener {
            AnyDialog(this).apply {
                style(AnyDialog.Style.RIGHT)
                contentView(R.layout.dialog_def)
                clickDismiss(R.id.tv_close)
            }.show()
        }
    }
}
