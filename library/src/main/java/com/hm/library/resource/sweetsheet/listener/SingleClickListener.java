package com.hm.library.resource.sweetsheet.listener;

import android.view.View;

import com.mingle.listener.*;


/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public class SingleClickListener implements View.OnClickListener {

    private View.OnClickListener mListener;

    private com.mingle.listener.SingleClickHelper singleClickhelper =new com.mingle.listener.SingleClickHelper();

    public SingleClickListener(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onClick(View v) {

        if (singleClickhelper.clickEnable()) {
            if(mListener != null) {
                mListener.onClick(v);
            }
        }

    }


}
