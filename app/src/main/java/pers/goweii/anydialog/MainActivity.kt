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
                contentView(R.layout.dialog_def)
                clickDismiss(R.id.tv_close) {
                    Toast.makeText(this@MainActivity, "点击了关闭", Toast.LENGTH_SHORT).show()
                }
            }.show()
        }
    }
}
