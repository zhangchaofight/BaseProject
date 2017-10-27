package com.example.administrator.baseproject.ThreeTypePullRefresh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.baseproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */

public class OverrideOnTouch extends LinearLayout implements View.OnTouchListener{

    private static final String TAG = OverrideOnTouch.class.getSimpleName();

    /**
     * 五种状态：
     * 1.起始位置：STATUS_START
     * 2.正常滚动：STATUS_NORMAL
     * 3.释放归位：STATUS_HIDE_HEADER
     * 4.释放刷新：STATUS_RELEASE_REFRESH
     * 5.刷新中：STATUS_REFRESHING
     */


    private static final int STATUS_NORMAL = 100;
    private static final int STATUS_PULL_REFRESH = 101;
    private static final int STATUS_RELEASE_REFRESH = 102;
    private static final int STATUS_REFRESHING = 103;

    private int currentStatus = STATUS_NORMAL;

    private final int HEADER_HEIGHT = - (int) getResources().getDimension(R.dimen.hide_header_height);

    private float yDown;

    public OverrideOnTouch(Context context) {
        this(context, null);
    }

    public OverrideOnTouch(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverrideOnTouch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private View mHeader;
    private int mTouchSlop;
    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setOrientation(LinearLayout.VERTICAL);
        addHeader();
        addListView();
    }

    private boolean isLayoutOnce;
    private MarginLayoutParams headerLP;
    ListView mListView;
    //
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //
        if(changed && !isLayoutOnce) {
            headerLP = (MarginLayoutParams) mHeader.getLayoutParams();
            headerLP.topMargin = -HEADER_HEIGHT;
            mHeader.setLayoutParams(headerLP);
            mListView = (ListView) getChildAt(1);
            mListView.setOnTouchListener(this);
            isLayoutOnce = true;
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        Log.d(TAG, "onTouch: EXECUTED");
//        if(isAnimating) {
//            return true;
//        }
        setIsAbleToPull(motionEvent);
        if(!isAbleToPull) {
            return false;
        }
        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float now = motionEvent.getRawY();
                int moved = (int) (now - yDown);
                if(moved < 0 && headerLP.topMargin <= HEADER_HEIGHT) {
                    return false;
                }
                if(moved < mTouchSlop) {
                    return false;
                }
                if(currentStatus != STATUS_REFRESHING) {
                    if(headerLP.topMargin > 0) {
                        currentStatus = STATUS_RELEASE_REFRESH;
                    } else {
                        currentStatus = STATUS_PULL_REFRESH;
                    }
                    headerLP.topMargin = moved - HEADER_HEIGHT;
                    mHeader.setLayoutParams(headerLP);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "onTouch: ACTION UP EXECUTED");
                if(currentStatus == STATUS_RELEASE_REFRESH) {
                    //do refresh task
                    playAnimation(TYPE_REFRESH);
                } else if (currentStatus == STATUS_PULL_REFRESH) {
                    //do hide header task
                    playAnimation(TYPE_HIDE_HEADER);
                }
            break;
        }
        return dealListView();
    }

    private boolean isAbleToPull;
    private void setIsAbleToPull(MotionEvent event) {
        View view = mListView.getChildAt(0);
        if(null != view) {
            int firstVisible = mListView.getFirstVisiblePosition();
            if(firstVisible == 0 && view.getTop() == 0) {
                if (!isAbleToPull) {
                    yDown = event.getRawY();
                }
                isAbleToPull = true;
            } else {
                headerLP.topMargin = -mHeader.getHeight();
                mHeader.setLayoutParams(headerLP);
                isAbleToPull = false;
            }
        } else {
           isAbleToPull = true;
        }
    }

    private boolean isAnimating = false;
    private void setIsAnimating() {
        isAnimating = !isAnimating;
    }

    private void addHeader() {
        mHeader = new View(getContext());
        mHeader.setBackgroundColor(Color.GRAY);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                HEADER_HEIGHT);
        addView(mHeader, params);
    }

    private Context mContext = getContext();
    private void addListView() {
        ListView lv = new ListView(mContext);
        LayoutParams params0 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lv.setLayoutParams(params0);
        List<String> list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            list.add("no: " + i);
        }
        lv.setAdapter(new ArrayAdapter<String>
                (mContext, android.R.layout.simple_expandable_list_item_1, list));
        addView(lv, 1);
    }

    private boolean dealListView() {
        if(currentStatus == STATUS_PULL_REFRESH ||
                    currentStatus == STATUS_RELEASE_REFRESH){
            mListView.setPressed(false);
            mListView.setFocusable(false);
            mListView.setFocusableInTouchMode(false);
            return true;
        }
        return false;
    }


    private static final int TYPE_REFRESH = 1;
    private static final int TYPE_HIDE_HEADER = 0;
    private void playAnimation(int type) {
        Log.d(TAG, "playAnimation: EXECUTED");
        if(type != TYPE_HIDE_HEADER && type != TYPE_REFRESH) {
            return;
        }
        int start = headerLP.topMargin;
        final int end;
        final int status;
        if(type == TYPE_REFRESH) {
            end = 0;
            status = STATUS_REFRESHING;
        } else {
            end = -HEADER_HEIGHT;
            status = STATUS_NORMAL;
        }
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setTarget(mHeader);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i = (int) valueAnimator.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)
                        mHeader.getLayoutParams();
                params.topMargin = i;
                mHeader.setLayoutParams(params);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                setIsAnimating();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                currentStatus = status;
                setIsAnimating();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private void playHideAnimation(){
        ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(1000);
        animator.ofFloat(headerLP.topMargin, -300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i = (int) valueAnimator.getAnimatedValue();
                headerLP.topMargin = i;
                mHeader.setLayoutParams(headerLP);
                if(i == -300) {
                    currentStatus = STATUS_NORMAL;
                }
            }
        });
        animator.start();
    }

    private void playRefreshAnimation(){
        ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(1000);
        animator.ofFloat(headerLP.topMargin, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i = (int) valueAnimator.getAnimatedValue();
                headerLP.topMargin = i;
                mHeader.setLayoutParams(headerLP);
                if(i == 0) {
                    currentStatus = STATUS_REFRESHING;
                }
            }
        });
        animator.start();
    }
}
