package com.hm.library.util

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import com.hm.library.R
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.LoadedFrom
import com.nostra13.universalimageloader.core.display.BitmapDisplayer
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer
import com.nostra13.universalimageloader.core.imageaware.ImageAware
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware

/**
 * ImageUtil
 *
 *
 * himi on 2015-09-15 14:09
 * version V1.0
 */
object ImageUtil {


    private var mCircleDisplayImageOptions: DisplayImageOptions? = null

    /**
     * @return
     * *
     * @Desc: TODO
     * *
     * @author hrs
     */
    fun SimpleDisplayImageOptions(): DisplayImageOptions {
        val options = DisplayImageOptions.Builder().resetViewBeforeLoading(false).showStubImage(R.drawable.iv_defalut_image) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.iv_defalut_image) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.iv_defalut_image) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(SimpleBitmapDisplayer()) // 正常显示
                .build()
        return options
    }

    /**
     * @return
     * *
     * @Desc: TODO
     * *
     * @author hrs
     */
    fun SimpleDisplayImageOptions(deafultResID: Int): DisplayImageOptions {
        val options = DisplayImageOptions.Builder().resetViewBeforeLoading(false).showStubImage(deafultResID) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(deafultResID) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(deafultResID) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(SimpleBitmapDisplayer()) // 正常显示
                .build()
        return options
    }

    fun RoundedDisplayImageOptions(cornerRadiusPixels: Int): DisplayImageOptions {
        return RoundedDisplayImageOptions(R.drawable.iv_defalut_image, cornerRadiusPixels)
    }

    fun RoundedDisplayImageOptions(deafultResID: Int, cornerRadiusPixels: Int): DisplayImageOptions {
        val options = DisplayImageOptions.Builder().showStubImage(deafultResID) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(deafultResID) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(deafultResID) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(RoundedBitmapDisplayer(cornerRadiusPixels)) // 设置成圆角图片
                .build()
        return options
    }

    fun CircleDisplayImageOptions(defaultResID: Int = R.drawable.iv_defalut_image): DisplayImageOptions {
        if (mCircleDisplayImageOptions == null) {
            mCircleDisplayImageOptions = DisplayImageOptions.Builder().showStubImage(defaultResID) // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(defaultResID) // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(defaultResID) // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                    .displayer(CircleBitmapDisplayer()) // 设置成圆型图片
                    .build()
        }
        return mCircleDisplayImageOptions!!
    }

    fun displayImage(imageUrl: String, imageView: ImageView, deafultResID: Int) {
        displayImage(imageUrl, imageView, SimpleDisplayImageOptions(deafultResID))
    }

    fun displayImage(imageUrl: String, imageView: ImageView, options: DisplayImageOptions? = null) {
        var imageUrl = imageUrl
        var options = options

        // if (imageUrl == null || imageUrl.length() == 0) {
        // ImageLoader.getInstance().displayImage("drawable://" +
        // R.drawable.iv_defalut_image, imageView, options);
        // return;
        // }

//        if (!TextUtils.isEmpty(imageUrl) && !imageUrl.contains("http://tnfs.tngou.net/img"))
//            imageUrl = "http://tnfs.tngou.net/img" + imageUrl

        try {
            if (options == null)
                options = SimpleDisplayImageOptions()

            if (TextUtils.isEmpty(imageUrl) || imageUrl.contains("null")) {
                imageView.setImageDrawable(options.getImageForEmptyUri(imageView.context.resources))
                return
            }


            // if (!imageUrl.startsWith("drawable://") &&
            // !imageUrl.startsWith("file://") &&
            // !imageUrl.startsWith("http://")
            // && !imageUrl.startsWith("content://") &&
            // !imageUrl.startsWith("assets://")) {
            // && !imageUrl.startsWith(HttpServicePath.IMAGE_HOST)) {
            // int index = imageUrl.indexOf("/");
            // if (index > -1) {
            // imageUrl = HttpServicePath.IMAGE_HOST +
            // imageUrl.substring(index
            // + 1, imageUrl.length());
            // }
            // }
            // imageView.setTag(imageUrl);
            // ImageLoader.getInstance().loadImage(imageUrl, new
            // SimpleImageLoadingListener() {
            //
            // @Override
            // common void onLoadingComplete(String imageUrl, View view,
            // Bitmap
            // loadedImage) {
            // super.onLoadingComplete(imageUrl, view, loadedImage);
            // if (imageUrl.equals(imageView.getTag())) {
            // imageView.setImageBitmap(loadedImage);
            // }
            // }
            // });
            ImageLoader.getInstance().displayImage(imageUrl, imageView, options)
            // ImageLoader.getInstance().displayImage(imageUrl, imageView,
            // options);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    internal class CircleBitmapDisplayer constructor(protected val margin: Int = 0) : BitmapDisplayer {

        override fun display(bitmap: Bitmap, imageAware: ImageAware,
                             loadedFrom: LoadedFrom) {
            if (imageAware !is ImageViewAware) {
                throw IllegalArgumentException(
                        "ImageAware should wrap ImageView. ImageViewAware is expected.")
            }

            imageAware.setImageDrawable(CircleDrawable(bitmap, margin))
        }

        internal inner class CircleDrawable constructor(protected var oBitmap: Bitmap// 原图
                                                        , protected val margin: Int = 0) : Drawable() {

            protected val paint: Paint
            protected val bitmapShader: BitmapShader
            protected var radius: Float = 0.toFloat()

            init {
                bitmapShader = BitmapShader(oBitmap, Shader.TileMode.CLAMP,
                        Shader.TileMode.CLAMP)
                paint = Paint()
                paint.isAntiAlias = true
                paint.shader = bitmapShader
            }

            override fun onBoundsChange(bounds: Rect) {
                super.onBoundsChange(bounds)
                computeBitmapShaderSize()
                computeRadius()

            }

            override fun draw(canvas: Canvas) {
                val bounds = bounds// 画一个圆圈
                canvas.drawCircle(bounds.width() / 2f, bounds.height() / 2f,
                        radius, paint)
            }

            override fun getOpacity(): Int {
                return PixelFormat.TRANSLUCENT
            }

            override fun setAlpha(alpha: Int) {
                paint.alpha = alpha
            }

            override fun setColorFilter(cf: ColorFilter?) {
                paint.colorFilter = cf
            }

            /**
             * 计算Bitmap shader 大小
             */
            fun computeBitmapShaderSize() {
                val bounds = bounds ?: return
                // 选择缩放比较多的缩放，这样图片就不会有图片拉伸失衡
                val matrix = Matrix()
                val scaleX = bounds.width() / oBitmap.width.toFloat()
                val scaleY = bounds.height() / oBitmap.height.toFloat()
                val scale = if (scaleX > scaleY) scaleX else scaleY
                matrix.postScale(scale, scale)
                bitmapShader.setLocalMatrix(matrix)
            }

            /**
             * 计算半径的大小
             */
            fun computeRadius() {
                val bounds = bounds
                radius = if (bounds.width() < bounds.height())
                    bounds.width() / 2f - margin
                else
                    bounds.height() / 2f - margin
            }

            val TAG = "CircleDrawable"
        }
    }

}

