package com.example.EditSchedule;

import com.example.Alarm.AlarmforSch;
import com.example.DataBase.MyTodoScheduleDB;
import com.example.mainsurface.R;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author LSHM
 *此类功能：封装了日程数据库的删除操作，只要传入context和要删除的id.
 */
public class DialogForDelete {
	private Context context;
	private String id;
	private Dialog dialog;
	private MyTodoScheduleDB helper =null;
	public DialogForDelete(Context context,String id) {
		this.context =context;
		this.id =id;
		helper =new MyTodoScheduleDB(context);
	}
	public void showDialog(){
		//用dialog来实现并建立布局文件
		dialog = new Dialog(context);
		dialog.setTitle("确定删除此日程?");
		dialog.setContentView(R.layout.delete_view_dialog);
		TextView tv =(TextView)dialog.findViewById(R.id.sure_tv);
		tv.setOnClickListener(new MyTvOnClick());
		tv =(TextView)dialog.findViewById(R.id.cancel_tv);
		tv.setOnClickListener(new MyTvOnClick());
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	class MyTvOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.sure_tv:
				SQLiteDatabase db =helper.getWritableDatabase();
				db.delete("Schedule", "id= ?", new String[]{id});
				dialog.dismiss();
				((EditScheduleActivity)context).getFinish("delete");
				//取消提醒闹钟，id作为闹钟请求码，一个闹铃一个请求吗，必须唯一。
				new AlarmforSch(context, Integer.parseInt(id)).cancelAlarm();
				Toast.makeText(context, "删除成功!", Toast.LENGTH_SHORT).show();
				break;
			case R.id.cancel_tv:
				dialog.dismiss();
				//Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
				break;
			}
			
		}
	}
	
}
