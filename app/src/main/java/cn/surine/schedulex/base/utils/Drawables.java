package cn.surine.schedulex.base.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

/**
 * Intro：创建drawable
 *
 * @author sunliwei
 * @date 2020-01-29 17:24
 */
public class Drawables {

    /**
     * 创建一个常规的shape形状
     *
     * @param color       填充颜色
     * @param corner      圆角
     * @param strokeWidth 描边宽度
     * @param strokeColor 描边颜色
     */
    public static Drawable getDrawable(int color, int corner, int strokeWidth, int strokeColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(corner);
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        return gradientDrawable;
    }


    /**
     * 创建一个渐变shape形状
     * @param orientation 方向
     * @param colors 颜色值
     * @param strokeColor 描边颜色
     * @param corner 圆角
     * @param strokeWidth 描边宽度
     */
    public static Drawable getDrawable(GradientDrawable.Orientation orientation,int[] colors, int corner, int strokeWidth, int strokeColor){
        GradientDrawable gradientDrawable = new GradientDrawable(orientation,colors);
        gradientDrawable.setCornerRadius(corner);
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        return gradientDrawable;
    }
}
