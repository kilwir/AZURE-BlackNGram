package com.mopidev.blackngram.Listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * Bad Boys Team
 * Created by remyjallan on 10/12/2015.
 */
public interface OnItemClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view,int position);
    void onShareItem(View view, int position);
    void onLikeItem(View view, int position);
}
