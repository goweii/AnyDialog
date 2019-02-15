package per.goweii.anydialog;

import android.view.View;

/**
 * @author CuiZhen
 * @date 2018/5/20
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public interface IAnim {
    /**
     * 进入动画
     *
     * @param target 背景
     * @return 动画时长
     */
    long inAnim(View target);

    /**
     * 消失动画
     *
     * @param target 背景
     * @return 动画时长
     */
    long outAnim(View target);
}
