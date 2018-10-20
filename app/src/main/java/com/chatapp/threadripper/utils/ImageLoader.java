package com.chatapp.threadripper.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.chatapp.threadripper.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ImageLoader {

    /** Picasso cannot load a null or empty url
     * this is a middleware function for load image safety
     * Use invalid url for a null or empty url
     */
    static String safetyImageURL(String url) {
        if (URLUtil.isValidUrl(url) && url != null
                && !url.contains("default.jpg")) return url;
        return Constants.PLACEHOLDER_IMAGE_URL;
    }

    public static void loadUserAvatar(View view, String url) {
        Picasso.get().load(safetyImageURL(url))
                .placeholder(R.drawable.placeholder_user_avatar)
                .error(R.drawable.placeholder_user_avatar)
                .into((ImageView) view);
    }

    public static void loadImageChatMessage(View view, String url) {
        Picasso.get().load(safetyImageURL(url))
                .placeholder(R.drawable.placeholder_image_chat)
                .error(R.drawable.placeholder_image_chat)
                .fit()
                .centerCrop()
                .into((ImageView) view);
    }
}
