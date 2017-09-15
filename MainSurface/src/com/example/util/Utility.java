package com.example.util;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utility {
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		int len = listAdapter.getCount();
		for (int i = 0; i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		// ViewGroup.LayoutParams params = listView.getLayoutParams();
		// params.height = totalHeight + (listView.getDividerHeight() *
		// (listAdapter.getCount() - 1));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				totalHeight + (listView.getDividerHeight() * (len - 1)));
		// lv.setLayoutParams(lp);
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
	/**
	 * private void setListViewHeight(ListView lv) {
		 ListAdapter la = lv.getAdapter();
		 if(null == la) {
			 return;
		 }
		 // calculate height of all items.
		 int h = 0;
		 int cnt = la.getCount();
		 for(int i=0; i<cnt; i++) {
			 View item = la.getView(i, null, lv);
			 item.measure(0, 0);
			 h += item.getMeasuredHeight();
		 }
		 // reset ListView height
		 LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, h + (lv.getDividerHeight() * (cnt - 1)));
		 lv.setLayoutParams(lp);
	}
	 */
}
