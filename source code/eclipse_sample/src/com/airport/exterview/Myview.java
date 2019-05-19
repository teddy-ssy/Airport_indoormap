package com.airport.exterview;

import java.util.ArrayList;
import java.util.List;

import com.airport.R;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.opengl.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Myview extends View {
	public Bitmap bubble, eat, shop, info, enter, zhiji, destion;
	public Bitmap back;
	public List<Bitmap> bitmaplist = new ArrayList<Bitmap>();
	public int bubblex;
	public int bubbley;
	public int bitmapcount;
	int width, height;
	public List<Integer> widthlist = new ArrayList<Integer>();
	public List<Integer> heightlist = new ArrayList<Integer>();
	public float degrees;
	android.graphics.Matrix matrix = new android.graphics.Matrix();

	public Myview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		bubble = BitmapFactory.decodeResource(getResources(),
				R.drawable.transfer_point);
		eat = BitmapFactory.decodeResource(getResources(), R.drawable.eat);
		shop = BitmapFactory.decodeResource(getResources(), R.drawable.shop);
		info = BitmapFactory.decodeResource(getResources(), R.drawable.info);
		enter = BitmapFactory.decodeResource(getResources(), R.drawable.enter);
		zhiji = BitmapFactory.decodeResource(getResources(), R.drawable.zhiji);
		destion = BitmapFactory.decodeResource(getResources(),
				R.drawable.destination);
		bitmaplist.add(bubble);
		bitmaplist.add(eat);
		bitmaplist.add(shop);
		bitmaplist.add(info);
		bitmaplist.add(enter);
		bitmaplist.add(zhiji);
		bitmaplist.add(destion);
		widthlist.add(bubble.getWidth());
		widthlist.add(eat.getWidth());
		widthlist.add(shop.getWidth());
		widthlist.add(info.getWidth());
		widthlist.add(enter.getWidth());
		widthlist.add(zhiji.getWidth());
		widthlist.add(destion.getWidth());
		heightlist.add(bubble.getHeight());
		heightlist.add(eat.getHeight());
		heightlist.add(shop.getHeight());
		heightlist.add(info.getHeight());
		heightlist.add(enter.getHeight());
		heightlist.add(zhiji.getHeight());
		heightlist.add(destion.getHeight());
		back = BitmapFactory.decodeResource(getResources(), R.drawable.back);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		matrix.setRotate(degrees);
		matrix.setTranslate(bubblex, bubbley);
		//System.out.println(bitmapcount);
		// canvas.drawBitmap(back, 0, 0, null);
		if (bitmapcount < 1 || bitmapcount > 7) {
			bitmapcount = 1;
		}
		Bitmap bubble2 = Bitmap.createBitmap(bitmaplist.get(bitmapcount - 1),
				0, 0, widthlist.get(bitmapcount - 1),
				heightlist.get(bitmapcount - 1));
		canvas.drawBitmap(bubble2, matrix, null);
		// canvas.drawBitmap(bubble, bubblex, bubbley,null);
	}

}
