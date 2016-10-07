package com.hm.library.resource.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.library.R;


/**
 * 自定义提示Toast
 *
 * @author hm
 */
public class TipsToast extends Toast {

    public enum TipType {
        Success(R.drawable.tips_success),
        Error(R.drawable.tips_error),
        Smile(R.drawable.tips_smile),
        Warning(R.drawable.tips_warning);

        int value = 0;

        TipType(int value) {    //    必须是private的，否则编译错误
            this.value = value;
        }

        public static TipType valueOf(int value) {    //    手写的从int到enum的转换函数
            if (value == R.drawable.tips_success) {
                return Success;
            }
            if (value == R.drawable.tips_error) {
                return Error;
            }
            if (value == R.drawable.tips_smile) {
                return Smile;
            }
            if (value == R.drawable.tips_warning) {
                return Warning;
            }

            return null;
        }

        public int value() {
            return this.value;
        }
    }


    public TipsToast(Context context) {
        super(context);
    }

    public static TipsToast makeText(Context context, CharSequence text,
                                     int duration) {
        TipsToast result = new TipsToast(context);

        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.view_tips, null);
        TextView tv = (TextView) v.findViewById(R.id.tips_msg);
        tv.setText(text);

        result.setView(v);
        // setGravity方法用于设置位置，此处为垂直居中
        result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        result.setDuration(duration);

        return result;
    }

    public static TipsToast makeText(Context context, int resId, int duration)
            throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId),
                duration);
    }

    public void setIcon(TipType type) {
        int iconResId = type.value();
        setIcon(iconResId);
    }

    public void setIcon(int iconResId) {
        if (getView() == null) {
            return;
            //            throw new RuntimeException(
            //                    "This Toast was not created with Toast.makeText()");
        }
        ImageView iv = (ImageView) getView().findViewById(R.id.tips_icon);
        if (iv == null) {
            return;
            //            throw new RuntimeException(
            //                    "This Toast was not created with Toast.makeText()");
        }
        iv.setImageResource(iconResId);
    }

    @Override
    public void setText(CharSequence s) {
        if (getView() == null) {
            return;
//            throw new RuntimeException(
//                    "This Toast was not created with Toast.makeText()");
        }
        TextView tv = (TextView) getView().findViewById(R.id.tips_msg);
        if (tv == null) {
            return;
//            throw new RuntimeException(
//                    "This Toast was not created with Toast.makeText()");
        }
        tv.setText(s);
    }
}
