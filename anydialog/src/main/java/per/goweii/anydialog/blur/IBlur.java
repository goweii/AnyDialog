package per.goweii.anydialog.blur;

import android.graphics.Bitmap;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/2/13
 */
public interface IBlur {
    /**
     * 高斯模糊图片
     *
     * @param originalBitmap  原图
     * @param radius          模糊半径
     * @param scaleFactor     缩小因子
     * @param keepSize        缩小后是否再次放大为原图尺寸
     * @param recycleOriginal 回收原图
     * @return 模糊图
     */
    Bitmap blur(@NonNull Bitmap originalBitmap,
                @FloatRange(from = 0) float radius,
                @FloatRange(from = 1) float scaleFactor,
                boolean keepSize,
                boolean recycleOriginal);
}
