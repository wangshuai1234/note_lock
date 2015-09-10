package com.jerry.note.view;
import com.jerry.note.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {

	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private int mScreenWidth;
	private int mMenuRightPadding=50;
	private boolean once;
	private int mMenuWidth;
	private boolean isOpen;
	/**
	 * 未使用自定义控件时使用
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		this(context,attrs,0);
	}
	
	
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.SidlingMenu, defStyle,0);
		int n=a.getIndexCount();
		for(int i=0;i<n;i++)
		{
			int attr=a.getIndex(i);
			switch (attr) {
			case R.styleable.SidlingMenu_rightPadding:
				mMenuRightPadding=a.getDimensionPixelSize(i, (int)TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 50,context.getResources().getDisplayMetrics()));
				break;
			}
		}
		a.recycle();
		WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics=new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth=outMetrics.widthPixels;
		//把dp转换为px
	}
    
	

	public SlidingMenu(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}


	/**
	 * 设置子View的宽和高跟自己的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if(!once)
		{
			mWapper=(LinearLayout)getChildAt(0);
			mMenu=(ViewGroup)mWapper.getChildAt(0);
			mContent=(ViewGroup)mWapper.getChildAt(1);
			mMenuWidth=mMenu.getLayoutParams().width=mScreenWidth-mMenuRightPadding;
			mContent.getLayoutParams().width=mScreenWidth;
			once=true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 通过设置偏移量，将Menu隐藏
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if(changed)
		{
			this.scrollTo(mMenuWidth, 0);
		}
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action=ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			//隐藏在左边的宽度
			int scollX=getScrollX();
			if(scollX>=mMenuWidth/2)
			{
				//隐藏菜单
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen=false;
			}
			else
			{
				//显示菜单
				this.smoothScrollTo(0, 0);
				isOpen=true;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
	/**
	 * 打开菜单
	 */
	public void openMenu()
	{
		if(!isOpen)
		{
			this.smoothScrollTo(0, 0);
			isOpen=true;
		}
	}
	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
		if(isOpen)
		{
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen=false;
		}
	}
	
	/**
	 * 切换菜单
	 */
	public void toggle()
	{
		if(isOpen)
		{
			closeMenu();
		}
		else
		{
			openMenu();
		}
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		float scale=l*1.0f/mMenuWidth;
		float rightScale=0.7f+0.3f*scale;
		float leftScale=1.0f-0.3f*scale;
		float leftAlpha=1.0f-0.4f*scale;
		
		mMenu.setTranslationX(mMenuWidth*scale*0.5f);
		mMenu.setScaleX(leftScale);
		mMenu.setScaleY(leftScale);
		mMenu.setAlpha(leftAlpha);
		mContent.setPivotX(0);
		mContent.setPivotY(mContent.getHeight()/2);
		mContent.setScaleX(rightScale);
		mContent.setScaleY(rightScale);
	}
}
