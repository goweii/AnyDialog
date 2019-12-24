package pers.goweii.anydialog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import per.goweii.anydialog.AnyDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_def.setOnClickListener {
            AnyDialog(this).apply {
                contentView(R.layout.dialog_def)
            }.show()
        }
    }
}
