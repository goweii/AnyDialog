package pers.goweii.dialog.base;

import android.view.View;

/**
 * @author CuiZhen
 * @date 2018/5/20
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public interface IBackgroundAnim {
    /**
     * 背景进入动画
     *
     * @param background 背景
     * @return 动画时长
     */
    long inAnim(View background);

    /**
     * 背景消失动画
     *
     * @param background 背景
     * @return 动画时长
     */
    long outAnim(View background);
}
