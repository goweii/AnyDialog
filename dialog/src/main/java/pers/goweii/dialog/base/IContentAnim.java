package pers.goweii.dialog.base;

import android.view.View;

/**
 * @author CuiZhen
 * @date 2018/5/20
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public interface IContentAnim {
    /**
     * 内容进入动画
     *
     * @param content 内容
     * @return 动画时长
     */
    long inAnim(View content);

    /**
     * 内容消失动画
     *
     * @param content 内容
     * @return 动画时长
     */
    long outAnim(View content);
}
