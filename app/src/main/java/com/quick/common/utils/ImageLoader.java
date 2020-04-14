package com.quick.common.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
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

    public static void loadCircleImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url))
        {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imageView);
        }
    }
}
