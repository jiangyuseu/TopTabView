package com.baozou.toptab;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * Created by jiangyu on 2016/2/23.
 */
public class TopTabView extends ViewGroup {

    private LinearLayout topContainer;

    private TopViewAdapter mAdapter;

    private ViewPager mViewPager;

    private View indexView;

    private int currIndex = 0;

    public TopTabView(Context context) {
        this(context, null);
    }

    public TopTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        topContainer = new LinearLayout(context);
        topContainer.setOrientation(LinearLayout.HORIZONTAL);
//        topContainer.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        addView(topContainer);

        indexView = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 4);
        indexView.setLayoutParams(params);
        indexView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        addView(indexView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (topContainer != null && indexView != null && mAdapter != null) {
            int topContainerHeight = MeasureSpec.getSize(heightMeasureSpec);
            int topContainerHeightSpec = MeasureSpec.makeMeasureSpec(topContainerHeight, MeasureSpec.EXACTLY);
            topContainer.measure(widthMeasureSpec, topContainerHeightSpec);

            int screenWidth = MeasureSpec.getSize(widthMeasureSpec);
            int indexWidth = screenWidth / mAdapter.getCount();
            int indexWidthSpec = MeasureSpec.makeMeasureSpec(indexWidth, MeasureSpec.EXACTLY);
            int indexHeightSpec = MeasureSpec.makeMeasureSpec(indexView.getLayoutParams().height, MeasureSpec.EXACTLY);
            indexView.measure(indexWidthSpec, indexHeightSpec);

            int totalHeightSpec = MeasureSpec.makeMeasureSpec(topContainer.getMeasuredHeight() + indexView.getMeasuredHeight(), MeasureSpec.EXACTLY);

            setMeasuredDimension(widthMeasureSpec, totalHeightSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (topContainer != null && indexView != null) {
            int r1 = topContainer.getMeasuredWidth();
            int b1 = topContainer.getMeasuredHeight();
            topContainer.layout(0, 0, r1, b1);

            int indexl1 = 0;
            int indext1 = topContainer.getMeasuredHeight();
            int indexr1 = indexView.getMeasuredWidth();
            int indexb1 = topContainer.getMeasuredHeight() + indexView.getMeasuredHeight();
            indexView.layout(indexl1, indext1, indexr1, indexb1);
        }
    }

    public void setAdapter(TopViewAdapter adapter) {
        this.mAdapter = adapter;
        dataNotifyChanged();
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    private void dataNotifyChanged() {
        topContainer.removeAllViews();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view = mAdapter.getView(i);
            final int position = i;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.weight = 1;
            view.setLayoutParams(params);
            topContainer.addView(view);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.onClick(position);
                    indexAnim(position);
                }
            });
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    indexAnim(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private void indexAnim(int position) {
        if (indexView != null) {
            int offset = indexView.getMeasuredWidth();
            Animation animation = new TranslateAnimation(currIndex * offset, position * offset, 0, 0);
            currIndex = position;
            animation.setFillAfter(true);
            animation.setDuration(200);
            indexView.startAnimation(animation);
        }
    }

    public interface TopViewAdapter {
        View getView(int position);

        int getCount();

        void onClick(int position);

    }
}
