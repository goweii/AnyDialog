package per.goweii.anydialog.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * @author Cuizhen
 * @date 2018/4/4
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
final class GaussianBlur implements IBlur {
    private static GaussianBlur INSTANCE = null;

    private final RenderScript renderScript;
    private final ScriptIntrinsicBlur gaussianBlur;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private GaussianBlur(Context context) {
        renderScript = RenderScript.create(context.getApplicationContext());
        gaussianBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static GaussianBlur get(Context context){
        if (INSTANCE == null) {
            synchronized (GaussianBlur.class){
                if (INSTANCE == null) {
                    INSTANCE = new GaussianBlur(context);
                }
            }
        }
        return INSTANCE;
    }

    public static void destory(){
        if (INSTANCE != null) {
            // 关闭RenderScript对象
            INSTANCE.renderScript.destroy();
            INSTANCE = null;
        }
    }

    /**
     * 模糊
     * 采用系统自带的RenderScript
     * 输出图与原图参数相同
     *
     * @param originalBitmap 原图
     * @param scaleFactor    缩放因子（>=1）
     * @param radius         模糊半径
     * @return 模糊Bitmap
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public Bitmap blur(@NonNull Bitmap originalBitmap,
                       @FloatRange(from = 0) float radius,
                       @FloatRange(from = 1) float scaleFactor,
                       boolean keepSize,
                       boolean recycleOriginal) {
        if (radius == 0) {
            return originalBitmap;
        }
        if (radius > 25) {
            radius = 25;
            scaleFactor = scaleFactor * (radius / 25);
        }
        if (scaleFactor == 1) {
            return blurIn25(originalBitmap, radius);
        }
        final int width = originalBitmap.getWidth();
        final int height = originalBitmap.getHeight();
        Bitmap input = Bitmap.createScaledBitmap(originalBitmap, (int) (width / scaleFactor), (int) (height / scaleFactor), true);
        if (recycleOriginal) {
            originalBitmap.recycle();
        }
        Bitmap output = blurIn25(input, radius);
        input.recycle();
        if (keepSize) {
            Bitmap outputScaled = Bitmap.createScaledBitmap(output, width, height, true);
            output.recycle();
            output = outputScaled;
        }
        return output;
    }

    /**
     * 高斯模糊
     * 采用系统自带的RenderScript
     * 图像越大耗时越长，测试时1280*680的图片耗时在30~60毫秒
     * 建议在子线程模糊通过Handler回调获取
     *
     * @param originalBitmap 原图
     * @param radius         模糊半径
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Bitmap blurIn25(Bitmap originalBitmap, @FloatRange(fromInclusive = false, from = 0, to = 25) float radius) {
        // 创建输出图片
        Bitmap blurBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), originalBitmap.getConfig());
        // 开辟输入内存
        Allocation allIn = Allocation.createFromBitmap(renderScript, originalBitmap);
        // 开辟输出内存
        Allocation allOut = Allocation.createFromBitmap(renderScript, blurBitmap);
        // 设置模糊半径，范围0f<radius<=25f
        gaussianBlur.setRadius(radius);
        // 设置输入内存
        gaussianBlur.setInput(allIn);
        // 模糊编码，并将内存填入输出内存
        gaussianBlur.forEach(allOut);
        // 将输出内存编码为Bitmap，图片大小必须注意
        allOut.copyTo(blurBitmap);
        return blurBitmap;
    }
}
