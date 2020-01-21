package pers.goweii.anydialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import per.goweii.anydialog.AnyDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_def.setOnClickListener {
            AnyDialog(this) {
                style = AnyDialog.Style.CENTER
                contentRes = R.layout.dialog_def
                clickDismiss(R.id.tv_close)
            }.show()
        }
        btn_full.setOnClickListener {
            FullscreenDialog(this).apply {
                contentRes = R.layout.dialog_full
                clickDismiss(R.id.tv_close)
                bindData {
                    find<RecyclerView>(R.id.rvh)?.init(true)
                    find<RecyclerView>(R.id.rvv)?.init(false)
                }
            }.show()
        }
        btn_top.setOnClickListener {
            AnyDialog(this) {
                fitSystemTop = true
                style = AnyDialog.Style.TOP
                contentRes = R.layout.dialog_top
                clickDismiss(R.id.tv_close)
                bindData {
                    find<RecyclerView>(R.id.rvh)?.init(true)
                    find<RecyclerView>(R.id.rvv)?.init(false)
                }
            }.show()
        }
        btn_bottom.setOnClickListener {
            AnyDialog(this) {
                fitSystemBottom = true
                style = AnyDialog.Style.BOTTOM
                contentRes = R.layout.dialog_bottom
                clickDismiss(R.id.tv_close)
                bindData {
                    find<RecyclerView>(R.id.rvh)?.init(true)
                    find<RecyclerView>(R.id.rvv)?.init(false)
                }
            }.show()
        }
        btn_left.setOnClickListener {
            AnyDialog(this) {
                fitSystemBottom = true
                fitSystemRight = true
                style = AnyDialog.Style.LEFT
                contentRes = R.layout.dialog_left
                clickDismiss(R.id.tv_close)
                bindData {
                    find<RecyclerView>(R.id.rvh)?.init(true)
                    find<RecyclerView>(R.id.rvv)?.init(false)
                }
            }.show()
        }
        btn_right.setOnClickListener {
            AnyDialog(this) {
                fitSystemBottom = true
                fitSystemRight = true
                style = AnyDialog.Style.RIGHT
                contentRes = R.layout.dialog_left
                clickDismiss(R.id.tv_close)
                bindData {
                    find<RecyclerView>(R.id.rvh)?.init(true)
                    find<RecyclerView>(R.id.rvv)?.init(false)
                }
            }.show()
        }
    }

    private fun RecyclerView.init(h: Boolean) {
        if (h) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HAdapter()
        } else {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = VAdapter()
        }
    }
}
