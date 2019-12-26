package com.quick.common.utils;

import android.widget.ImageView;
import com.quick.common.R;
import com.quick.core.glide.GlideHelper;


/**
 * @author CuiZhen
 * @date 2019/5/12
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class ImageLoader {

    public static void image(ImageView imageView, String url){
        GlideHelper.with(imageView.getContext())
                .errorHolder(R.drawable.image_holder)
                .placeHolder(R.drawable.image_holder)
                .cache(true)
                .load(url)
                .into(imageView);
    }

    public static void banner(ImageView imageView, String url){
        GlideHelper.with(imageView.getContext())
                .errorHolder(R.drawable.image_holder)
                .placeHolder(R.drawable.image_holder)
                .cache(true)
                .load(url)
                .into(imageView);
    }

    public static void userIcon(ImageView imageView, String url){
        GlideHelper.with(imageView.getContext())
//                .errorHolder(R.drawable.image_holder)
//                .placeHolder(R.drawable.image_holder)
                .cache(true)
                .load(url)
                .into(imageView);
    }
}
