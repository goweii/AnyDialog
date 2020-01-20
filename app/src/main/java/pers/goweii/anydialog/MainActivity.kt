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
            AnyDialog(this).apply {
                style(AnyDialog.Style.CENTER)
                contentView(R.layout.dialog_def)
                clickDismiss(R.id.tv_close)
            }.show()
        }
        btn_full.setOnClickListener {
            AnyDialog(this).apply {
                style(AnyDialog.Style.CENTER)
                fullscreen(true)
                contentView(R.layout.dialog_full)
                clickDismiss(R.id.tv_close)
                bindData {
                    find<RecyclerView>(R.id.rvh)?.init(true)
                    find<RecyclerView>(R.id.rvv)?.init(false)
                }
            }.show()
        }
        btn_top.setOnClickListener {
            AnyDialog(this).apply {
                style(AnyDialog.Style.TOP)
                contentView(R.layout.dialog_top)
                clickDismiss(R.id.tv_close)
                bindData {
                    find<RecyclerView>(R.id.rvh)?.init(true)
                    find<RecyclerView>(R.id.rvv)?.init(false)
                }
            }.show()
        }
        btn_bottom.setOnClickListener {
            AnyDialog(this).apply {
                fitSystemWindow(true)
                style(AnyDialog.Style.BOTTOM)
                contentView(R.layout.dialog_bottom)
                clickDismiss(R.id.tv_close)
                bindData {
                    find<RecyclerView>(R.id.rvh)?.init(true)
                    find<RecyclerView>(R.id.rvv)?.init(false)
                }
            }.show()
        }
        btn_left.setOnClickListener {
            AnyDialog(this).apply {
                style(AnyDialog.Style.LEFT)
                contentView(R.layout.dialog_left)
                clickDismiss(R.id.tv_close)
                bindData {
                    find<RecyclerView>(R.id.rvh)?.init(true)
                    find<RecyclerView>(R.id.rvv)?.init(false)
                }
            }.show()
        }
        btn_right.setOnClickListener {
            AnyDialog(this).apply {
                style(AnyDialog.Style.RIGHT)
                contentView(R.layout.dialog_left)
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
