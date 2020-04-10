package com.quick.common.ui.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.quick.common.R;
import com.quick.common.bean.main.ArticleBean;
import com.quick.common.ui.activity.WebActivity;
import com.quick.common.utils.ImageLoader;
import com.quick.core.utils.StringUtils;


/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/12 0012 14:29
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> implements LoadMoreModule {


    public ArticleAdapter() {
        super(R.layout.rv_item_article);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean item) {
        bindArticle(helper.itemView, item);
    }

    public static void bindArticle(View view, ArticleBean item) {
        TextView tv_top = view.findViewById(R.id.tv_top);
        TextView tv_new = view.findViewById(R.id.tv_new);
        TextView tv_author = view.findViewById(R.id.tv_author);
        TextView tv_tag = view.findViewById(R.id.tv_tag);
        TextView tv_time = view.findViewById(R.id.tv_time);
        ImageView iv_img = view.findViewById(R.id.iv_img);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_desc = view.findViewById(R.id.tv_desc);
        TextView tv_chapter_name = view.findViewById(R.id.tv_chapter_name);

        if (item.isTop()) {
            tv_top.setVisibility(View.VISIBLE);
        } else {
            tv_top.setVisibility(View.GONE);
        }
        if (item.isFresh()) {
            tv_new.setVisibility(View.VISIBLE);
        } else {
            tv_new.setVisibility(View.GONE);
        }
        tv_author.setText(item.getAuthor());
        if (item.getTags() != null && item.getTags().size() > 0) {
            tv_tag.setText(item.getTags().get(0).getName());
            tv_tag.setVisibility(View.VISIBLE);
        } else {
            tv_tag.setVisibility(View.GONE);
        }
        tv_time.setText(item.getNiceDate());
        if (!TextUtils.isEmpty(item.getEnvelopePic())) {
            ImageLoader.image(iv_img, item.getEnvelopePic());
            iv_img.setVisibility(View.VISIBLE);
        } else {
            iv_img.setVisibility(View.GONE);
        }
        tv_title.setText(Html.fromHtml(item.getTitle()));
        if (TextUtils.isEmpty(item.getDesc())) {
            tv_desc.setVisibility(View.GONE);
            tv_title.setSingleLine(false);
        } else {
            tv_desc.setVisibility(View.VISIBLE);
            tv_title.setSingleLine(true);
            String desc = Html.fromHtml(item.getDesc()).toString();
            desc = StringUtils.removeAllBank(desc, 2);
            tv_desc.setText(desc);
        }
        tv_chapter_name.setText(Html.fromHtml(formatChapterName(item.getSuperChapterName(), item.getChapterName())));

        tv_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserPageActivity.start(v.getContext(), item.getUserId());
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(v.getContext(), item);
            }
        });
    }

    private static String formatChapterName(String... names) {
        StringBuilder format = new StringBuilder();
        for (String name : names) {
            if (!TextUtils.isEmpty(name)) {
                if (format.length() > 0) {
                    format.append("·");
                }
                format.append(name);
            }
        }
        return format.toString();
    }

}
