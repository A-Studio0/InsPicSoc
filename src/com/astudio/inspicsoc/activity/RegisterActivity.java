package com.astudio.inspicsoc.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.astudio.inspicsoc.R;
/**
 * 此类  是对布局main.xml上 控件的操作
 * @author dl
 *
 */
public class RegisterActivity extends Activity implements OnClickListener{
	private Button registBtn,rebackBtn;
	private Activity mActivity = this;
	private EditText userEdit,passwdEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        registBtn = (Button)findViewById(R.id.main_regist_btn);
        registBtn.setOnClickListener(this);
        
        passwdEdit = (EditText)findViewById(R.id.login_passwd_edit);
        userEdit = (EditText)findViewById(R.id.login_user_edit);
        rebackBtn = (Button)findViewById(R.id.login_reback_btn);
        rebackBtn.setOnClickListener(this);//注册监听器  
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch (viewId) {
			case R.id.login_reback_btn://返回按钮
				RegisterActivity.this.finish();//关闭这个Activity  返回上一个Activity
				break;
		
			case R.id.main_regist_btn://注册按钮
				String userEditStr = userEdit.getText().toString().trim();
				String passwdEditStr = passwdEdit.getText().toString().trim();
				if(("".equals(userEditStr) || null == userEditStr) || 
						("".equals(passwdEditStr) || null == passwdEditStr)){//只要用户名和密码有一个为空
					new AlertDialog.Builder(RegisterActivity.this)
					.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
					.setTitle("注册失败")
					.setMessage("账号或密码不能为空，请输入账号或密码")
					.create().show();
				}
				else{
					new AlertDialog.Builder(RegisterActivity.this)
					.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
					.setTitle("注册成功")
					.setMessage("register success~,登录成功")
					.create().show();
					Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
					mActivity.startActivity(intent);
				}
				break;
		}
		
	}
}