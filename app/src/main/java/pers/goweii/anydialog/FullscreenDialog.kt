package pers.goweii.anydialog

import android.content.Context
import per.goweii.anydialog.AnyDialog

class FullscreenDialog(context: Context) : AnyDialog(context) {
    override var fullscreen: Boolean = true
    override var fitSystemBottom: Boolean = true
    override var fitSystemRight: Boolean = true
}