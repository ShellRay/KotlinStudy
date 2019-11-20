package com.kotlin.study.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;


import com.kotlin.study.R;
import com.kotlin.study.bean.STRU_CL_RAS_REDPACK_BEGIN;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 红包雨
 */

public class RedPacketRain extends View
{

    public interface OnFinishListener
    {
        public void onRainFinish();
    }

    public static final long SHAKE_TIME       = 1000;
    public static final long PACKET_BORN_TIME = 1000;
    public static final long PACKET_LOSE_TIME = 300;

    private class Position
    {
        private float x;
        private float y;
        private float r;

        public Position(float x, float y, float r)
        {
            this.x = x;
            this.y = y;
            this.r = r;
        }
    }

    private class PlayItem
    {
        private Position position;
        private long     fromtime;
        private long     borntime;
        private long     movetime;
        private boolean  is_click;
        private String   showText;
        private long     fromlose;
        private long     losetime;
        private float    dispareX;
        private float    dispareY;

        public PlayItem(Position position, long fromtime, long borntime, long movetime, boolean is_click)
        {
            this.position = position;
            this.fromtime = fromtime;
            this.borntime = borntime;
            this.movetime = movetime;
            this.is_click = is_click;
        }

        public PlayItem setIs_click(boolean is_click)
        {
            this.is_click = is_click;
            return this;
        }

        public PlayItem setShowText(String showText)
        {
            this.showText = showText;
            return this;
        }

        public PlayItem setFromlose(long fromlose)
        {
            this.fromlose = fromlose;
            return this;
        }

        public PlayItem setLosetime(long losetime)
        {
            this.losetime = losetime;
            return this;
        }

        public PlayItem setDispareX(float dispareX)
        {
            this.dispareX = dispareX;
            return this;
        }

        public PlayItem setDispareY(float dispareY)
        {
            this.dispareY = dispareY;
            return this;
        }

        public boolean isFinished(long time)
        {
            boolean result = false;

            if (is_click)
            {
                long spend = time - fromlose;
                result = spend >= losetime;
            }
            else
            {
                long spend = time - fromtime;
                result = spend >= movetime;
            }

            return result;
        }

        public void drawBorn(Canvas canvas, int screenX, int screenY, float radio)
        {
            float x = peachTree.getIntrinsicWidth();
            float y = peachTree.getIntrinsicHeight();
            float treeHeight = y * screenX / x;
            float tree_width = screenX;

            int red_width = redPacket.getIntrinsicWidth();
            int redHeight = redPacket.getIntrinsicHeight();

            int scaleX = (int) (red_width * radio);
            int scaleY = (int) (redHeight * radio);

            int cx = (int) (tree_width * position.x);
            int cy = (int) (treeHeight * position.y);

            redPacket.setBounds(cx - scaleX / 2, cy - scaleY / 2, cx + scaleX / 2, cy + scaleY / 2);
            int alpha = (int) (255 * radio);

            int savedCount = canvas.save();
            canvas.rotate(position.r, cx, cy);
            redPacket.setAlpha(alpha);
            redPacket.draw(canvas);
            canvas.restoreToCount(savedCount);
        }

        public void drawMove(Canvas canvas, int screenX, int screenY, float radio)
        {
            float x = peachTree.getIntrinsicWidth();
            float y = peachTree.getIntrinsicHeight();

            float treeHeight = y * screenX / x;
            float tree_width = screenX;

            int red_width = redPacket.getIntrinsicWidth();
            int redHeight = redPacket.getIntrinsicHeight();

            float fy = treeHeight * position.y;
            float sy = screenY + redHeight / 2;

            int cx = (int) (tree_width * position.x);
            int cy = (int) (radio * (sy - fy) + fy);

            redPacket.setBounds(cx - red_width / 2, cy - redHeight / 2, cx + red_width / 2, cy + redHeight / 2);
            float rotate = radio < 0.5f ? (0.5f - radio) * position.r / 0.5f : 0;

            int savedCount = canvas.save();
            canvas.rotate(rotate, cx, cy);
            redPacket.setAlpha(255);
            redPacket.draw(canvas);
            canvas.restoreToCount(savedCount);

        }

        public void drawLose(Canvas canvas, int screenX, int screenY, float radio)
        {

            float alpha = (int) (255 - 255 * radio);

            int red_width = redPacket.getIntrinsicWidth();
            int redHeight = redPacket.getIntrinsicHeight();

            int left = (int) (dispareX - red_width / 2);
            int top = (int) (dispareY - redHeight / 2);
            int right = (int) (dispareX + red_width / 2);
            int bottom = (int) (dispareY + redHeight / 2);

            redPacket.setBounds(left, top, right, bottom);
            redPacket.setAlpha((int) alpha);
            redPacket.draw(canvas);

            fontPaint.getTextBounds(showText, 0, showText.length(), fontBound);
            float base_line = dispareY - redHeight / 2 - fontBound.height();
            canvas.drawText(showText, 0, showText.length(), dispareX - fontBound.width() / 2, base_line, fontPaint);
        }

    }

    private class PlayInfo
    {
        private long           starttime;
        private long           shaketime;
        private List<PlayItem> play_list;


        public PlayInfo(long starttime, long shaketime, List<PlayItem> play_list)
        {
            this.starttime = starttime;
            this.shaketime = shaketime;
            this.play_list = play_list;
        }

        public boolean isPlaying()
        {
            return play_list.size() > 0;
        }

        public void onRefresh()
        {
            long time = AnimationUtils.currentAnimationTimeMillis();
            int size = play_list.size();

            for (int i = size - 1; i >= 0; i--)
            {
                PlayItem item = play_list.get(i);

                if (item.isFinished(time))
                {
                    play_list.remove(i);
                }
            }
        }

        public void drawItem(Canvas canvas, int screenX, int screenY)
        {
            long millis = AnimationUtils.currentAnimationTimeMillis();

            for (PlayItem item : play_list)
            {
                if (item.is_click)
                {
                    long spend = millis - item.fromlose;

                    if (spend >= 0 && spend < item.losetime)
                    {
                        float radio = spend / (float) item.losetime;
                        item.drawLose(canvas, screenX, screenY, radio);
                    }
                }
                else
                {
                    long spend = millis - (item.fromtime - item.borntime);

                    if (spend >= 0 && spend < item.borntime)
                    {
                        float radio = spend / (float) item.borntime;
                        item.drawBorn(canvas, screenX, screenY, radio);
                    }

                    spend = millis - item.fromtime;
                    if (spend >= 0 && spend < item.movetime)
                    {
                        float radio = spend / (float) item.movetime;
                        item.drawMove(canvas, screenX, screenY, radio);
                    }
                }
            }
        }

        public void drawTree(Canvas canvas, int screenX, int screenY)
        {

            long passtime = AnimationUtils.currentAnimationTimeMillis() - starttime;

            float x = peachTree.getIntrinsicWidth();
            float y = peachTree.getIntrinsicHeight();
            float treeHeight = y * screenX / x;

            peachTree.setBounds(0, 0, screenX, (int) treeHeight);

            if (passtime < shaketime)
            {
                float rotate = getRotate(passtime, -2, 2, -1, 1, -0.5f, 0.5f);

                int savedCount = canvas.save();
                canvas.rotate(rotate, 0, treeHeight / 3);
                peachTree.draw(canvas);
                canvas.restoreToCount(savedCount);
            }
            else
            {
                peachTree.draw(canvas);
            }
        }

        public float getRotate(long passtime, float... waves)
        {
            float rotate = 0;
            long every = shaketime / waves.length;
            int count = (int) (passtime / every);
            count = Math.min(count, waves.length - 1);

            float high = waves[count];

            float time = passtime - count * every;

            rotate = high * (every - time) / every;

            return rotate;
        }

    }

    private int                       itemCount;
    private Rect                      fontBound;
    private Paint                     fontPaint;
    private PlayInfo                  animation;
    private Drawable                  peachTree;
    private Drawable                  redPacket;
    private OnFinishListener          mListener;
    private STRU_CL_RAS_REDPACK_BEGIN casPacket;

    public RedPacketRain(Context context)
    {
        this(context, null);
    }

    public RedPacketRain(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public RedPacketRain(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        redPacket = getDrawable(R.mipmap.red_packet);
        peachTree = getDrawable(R.mipmap.peach_tree);

        float fontsize = getResources().getDimension(R.dimen.redPacakgeFont);
        fontPaint = new Paint();
        fontPaint.setTypeface(Typeface.DEFAULT_BOLD);
        fontPaint.setTextSize(fontsize);
        fontPaint.setColor(Color.parseColor("#fed701"));

        fontBound = new Rect();
    }


    public int getClickCount()
    {
        return itemCount;
    }

    public Drawable getDrawable(int id)
    {
        if (Build.VERSION.SDK_INT >= 21)
        {
            return getContext().getDrawable(id);
        }
        else
        {
            return getContext().getResources().getDrawable(id);
        }
    }

    public STRU_CL_RAS_REDPACK_BEGIN getCasPacket()
    {
        return casPacket;
    }

    public void setCasPacket(STRU_CL_RAS_REDPACK_BEGIN casPacket)
    {
        this.casPacket = casPacket;
    }

    public void setOnFinishListener(OnFinishListener listener)
    {
        this.mListener = listener;
    }

    public void playRainAnimation(int redCount, int showSize, long duration)
    {

        itemCount = 0;

        int pictures = redCount / showSize;

        if (redCount % showSize != 0)
        {
            pictures++;
        }

        long movetime = (duration - SHAKE_TIME) / pictures;
        long interval = movetime / showSize;

        long currtime = AnimationUtils.currentAnimationTimeMillis();
        List<PlayItem> play_list = new ArrayList<>();
        List<Position> list = new ArrayList<>();
        List<Position> save = new ArrayList<>();

        list.add(new Position(0.10f, 0.55f, 0));
        list.add(new Position(0.18f, 0.85f, 0));
        list.add(new Position(0.30f, 0.90f, -15));
        list.add(new Position(0.40f, 0.65f, 15));
        list.add(new Position(0.53f, 0.95f, -10));
        list.add(new Position(0.70f, 0.55f, 15));
        list.add(new Position(0.80f, 0.75f, 0));
        list.add(new Position(0.93f, 0.95f, 0));

        Random rd = new Random();

        for (int i = 0; i < redCount; i++)
        {
            if (list.isEmpty())
            {
                list.addAll(save);
                save.clear();
            }

            int size = list.size();
            int index = rd.nextInt(size);
            Position position = list.remove(index);
            save.add(position);

            long fromtime = currtime + SHAKE_TIME + movetime * i / showSize + interval * i % showSize;
            PlayItem item = new PlayItem(position, fromtime, PACKET_BORN_TIME, movetime, false);
            play_list.add(item);
        }

        animation = new PlayInfo(currtime, SHAKE_TIME, play_list);
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if (animation != null)
        {
            int screenX = getMeasuredWidth();
            int screenY = getMeasuredHeight();
            animation.drawItem(canvas, screenX, screenY);
            animation.drawTree(canvas, screenX, screenY);
        }

    }

    @Override
    public void computeScroll()
    {
        if (animation != null)
        {
            if (animation.isPlaying())
            {
                animation.onRefresh();
                invalidate();
            }
            else
            {
                animation = null;

                if (mListener != null)
                {
                    mListener.onRainFinish();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if(!isEnabled()){
            return false;
        }
        if (animation == null)
        {
            return false;
        }

        int action = MotionEventCompat.getActionMasked(ev);

        if (action == MotionEvent.ACTION_DOWN)
        {
            getParent().requestDisallowInterceptTouchEvent(true);
            handleClick(ev.getX(), ev.getY());
        }

        return true;
    }

    private void handleClick(float x, float y)
    {

        long time = AnimationUtils.currentAnimationTimeMillis();

        float red_width = redPacket.getIntrinsicWidth();
        float redHeight = redPacket.getIntrinsicHeight();

        float screenX = getMeasuredWidth();
        float screenY = getMeasuredHeight();

        float px = peachTree.getIntrinsicWidth();
        float py = peachTree.getIntrinsicHeight();

        float treeHeight = py * screenX / px;
        float tree_width = screenX;

        List<PlayItem> playlist = animation.play_list;
        int size = playlist.size();

        for (int i = size - 1; i >= 0; i--)
        {
            PlayItem item = playlist.get(i);

            if (item.is_click)
            {
                continue;
            }

            long spend = time - item.fromtime;

            if (spend > 0 && spend < item.movetime)
            {
                float radio = spend / (float) item.movetime;
                Position pos = item.position;

                float sy = screenY + redHeight / 2;
                float cx = tree_width * pos.x;
                float cy = treeHeight * pos.y;

                float ty = radio * (sy - cy) + cy;

                if (x >= cx - red_width / 2 && y >= ty - redHeight / 2 && x <= cx + red_width / 2 && y <= ty + redHeight / 2)
                {
                    itemCount++;
                    long millis = AnimationUtils.currentAnimationTimeMillis();
                    // 设置红包的消失参数
                    item.setShowText("X" + itemCount).setFromlose(millis).setLosetime(PACKET_LOSE_TIME).setDispareX(cx).setDispareY(ty).setIs_click(true);

                    callOnClick();
                }
            }

        }
    }

}
