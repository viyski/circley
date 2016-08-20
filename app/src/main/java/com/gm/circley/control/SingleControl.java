package com.gm.circley.control;

import android.content.Context;

import com.framework.annotation.AsyncAtomMethod;
import com.framework.base.BaseControl;
import com.framework.proxy.MessageProxy;
import com.gm.circley.util.image.CompressImageHelper;

/**
 * Created by lgm on 2016/8/10.
 */
public class SingleControl extends BaseControl{

    public SingleControl(MessageProxy mMessageCallBack) {
        super(mMessageCallBack);
    }

    @AsyncAtomMethod
    public void getCompressImagePath(Context context,String imagePath){
        String path = CompressImageHelper.compressImageView(context, imagePath);
        mModel.put(1,path);
        sendMessage("getCompressImagePathCallBack");
    }
}
