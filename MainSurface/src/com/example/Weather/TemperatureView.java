package com.example.Weather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class TemperatureView extends View {
	private Paint paint_bold = new Paint();
	private Paint paint_date = new Paint();
	private Paint paint_line = new Paint();
	private Path path1 = new Path();
	private Path path2 = new Path();
	// private Context context;
	// private PraseWeatherUtil p;
	private float cellheight;
	private float cellwidth;
	private int[] temphigh;
	private int[] templow;
	private int maxtemp;
	private int mintemp;
	private int LEFT_MARGIN = 15;
	private int TOP_MARGIN = 8;
	private int RADIO = 3;
	private int MARGIN = 25;
	private int BOTTOM_MARGIN = 20;
	//private Calendar calendar = Calendar.getInstance();
	private int CurrentMonth ;

	// private int CurrentWeek = calendar.get(Calendar.DAY_OF_WEEK);

	public TemperatureView(Context context,PraseWeatherUtil p) {
		super(context);
		p.gettemprateInt();
		temphigh = p.getTemphigh();
		templow = p.getTemplow();
		maxtemp = p.getMax_temp();
		mintemp = p.getMin_temp();
		CurrentMonth =Integer.parseInt(p.getFirst_date7().split("月")[1].split("日")[0]);
	}

	public TemperatureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TemperatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private void setBorderPaint() {
		paint_bold.setColor(Color.WHITE);
		paint_bold.setStrokeWidth(1);
		paint_bold.setStyle(Style.STROKE);
	}

	private void setLinePaint(int color) {
		paint_line.setColor(color);
		paint_line.setStrokeWidth(4);
		paint_line.setStyle(Style.STROKE);
	}

	private void setDatePaint() {
		paint_date.setColor(Color.WHITE);
		paint_date.setTextSize(18);
		paint_date.setFakeBoldText(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {        
		float leftin = MARGIN;
		float rightin = this.getMeasuredWidth() - MARGIN;
		float tophalf = 5;
		float bottomin = this.getMeasuredHeight() - BOTTOM_MARGIN;
		cellheight = (bottomin - 5) / 11;
		cellwidth = (rightin - MARGIN) / 3;
		/**
		 * 先画边框
		 */
		setBorderPaint();
		canvas.drawLine(leftin, tophalf, rightin, tophalf, paint_bold);
		canvas.drawLine(rightin, tophalf, rightin, bottomin, paint_bold);
		canvas.drawLine(leftin, tophalf, leftin, bottomin, paint_bold);
		canvas.drawLine(leftin, bottomin, rightin, bottomin, paint_bold);
		/**
		 * 再画内框
		 */
		// 先画横向
		for (int i = 1; i < 11; i++) {
			canvas.drawLine(leftin, tophalf + cellheight * i, rightin, tophalf
					+ cellheight * i, paint_bold);
		}
		// 再画纵向
		//添加日期
		paint_date.setColor(Color.WHITE);
		paint_date.setFakeBoldText(true);
		canvas.drawText(CurrentMonth + "日", leftin - LEFT_MARGIN, bottomin
				+ BOTTOM_MARGIN, paint_date);
		canvas.drawText((CurrentMonth + 3) + "日", rightin - LEFT_MARGIN,
				bottomin + BOTTOM_MARGIN, paint_date);
		for (int j = 1; j < 3; j++) {
			canvas.drawLine(leftin + cellwidth * j, tophalf, leftin + cellwidth
					* j, bottomin, paint_bold);
			canvas.drawText((CurrentMonth + j) + "日", leftin + cellwidth * j
					- LEFT_MARGIN, bottomin + BOTTOM_MARGIN,
					paint_date);
		}
		/**
		 * 绘制趋势线
		 */
		// 先绘制当天最高温度
		setLinePaint(Color.YELLOW);
		setDatePaint();
		for (int i = 0; i < 4; i++) {
			float left = leftin + cellwidth * i;
			float top = gettempY(temphigh[i]);
			if (i != 0) {
				path1.lineTo(left, top);
			} else {
				path1.moveTo(left, top);
			}
			canvas.drawCircle(left, top, RADIO, paint_line);
			canvas.drawText(String.valueOf(temphigh[i]) + "℃", left
					- LEFT_MARGIN, top - TOP_MARGIN, paint_date);
		}
		canvas.drawPath(path1, paint_line);
		// 在绘制当天最低温度
		setLinePaint(Color.BLUE);
		setDatePaint();
		for (int j = 0; j < 4; j++) {
			float left = leftin + cellwidth * j;
			float top = gettempY(templow[j]);
			if (j != 0) {
				path2.lineTo(left, top);
			} else {
				path2.moveTo(left, top);
			}
			canvas.drawCircle(left, top, RADIO, paint_line);
			canvas.drawText(String.valueOf(templow[j]) + "℃", left
					- LEFT_MARGIN, top - TOP_MARGIN, paint_date);
		}
		canvas.drawPath(path2, paint_line);
	}

	private float gettempY(int num) {
		float fenzi = maxtemp - num;
		float fenmu = maxtemp - mintemp;
		float firsttop = cellheight
				+ (fenzi / fenmu)
				* (this.getMeasuredHeight() - BOTTOM_MARGIN - 5 - cellheight * 2);
		return firsttop;
	}

}
