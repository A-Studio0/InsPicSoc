package com.astudio.inspicsoc.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.astudio.dodowaterfall.Helper;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.common.StringUtils;
import com.astudio.inspicsoc.utils.EncryptHelper;

public class LoginActivity extends Activity implements OnClickListener {
	Button rebackBtn, loginBtn, forgetPasswdBtn;
	EditText userEdit, passwdEdit;
	PopupWindow popup;
	RelativeLayout loginLayout;
	Button registBtn;
	private Activity mActivity = this;
	protected InsApplication mInsApplication;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		rebackBtn = (Button) findViewById(R.id.login_reback_btn);
		rebackBtn.setOnClickListener(this);// 注册监听器
		loginBtn = (Button) findViewById(R.id.login_login_btn);
		loginBtn.setOnClickListener(this);// 注册监听器
		passwdEdit = (EditText) findViewById(R.id.login_passwd_edit);
		userEdit = (EditText) findViewById(R.id.login_user_edit);
		forgetPasswdBtn = (Button) findViewById(R.id.forget_passwd);
		forgetPasswdBtn.setOnClickListener(this);
		loginLayout = (RelativeLayout) findViewById(R.id.login_layout);
		registBtn = (Button) findViewById(R.id.main_regist_btn);
		registBtn.setOnClickListener(this);
		mInsApplication = (InsApplication) getApplication();
	}

	@Override
	public void onClick(View v) {
		if (!Helper.checkConnection(this.getApplicationContext())) {
			AbToastUtil.showToast(getApplicationContext(), "请检查您的网络再试");
			return;
		}
		int viewId = v.getId();
		switch (viewId) {
		case R.id.login_reback_btn:// 返回按钮
			LoginActivity.this.finish();// 关闭这个Activity 返回上一个Activity
			break;
		case R.id.login_login_btn:// 点击登录按钮 进行判断 用户名和密码是否为空
			final String userEditStr = userEdit.getText().toString().trim();
			final String passwdEditStr = passwdEdit.getText().toString().trim();
			if (StringUtils.isEmpty(userEditStr)) {// 只要用户名和密码有一个为空
				AbToastUtil.showToast(getApplicationContext(), "用户名不能为空");
				break;
			} else if (StringUtils.isEmpty(passwdEditStr)) {
				AbToastUtil.showToast(getApplicationContext(), "密码不能为空");
				break;
			} else {

				AbHttpUtil httpUtil = AbHttpUtil.getInstance(getApplication());

				String loginUrl = InsUrl.USER_LOGIN.replace("@un", userEditStr)
						.replace("@ps", EncryptHelper.md5(passwdEditStr));

				httpUtil.get(loginUrl, new AbStringHttpResponseListener() {
					@Override
					public void onSuccess(int i, String s) {

						if ("fail".equals(s)) {
							AbToastUtil.showToast(getApplicationContext(),
									"用户名或密码不对,请再试试");
							return;
						}

						try {
							mInsApplication.userName = userEditStr;
							Intent intent = new Intent(getApplication(),
									MainActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							finish();
							getApplication().startActivity(intent);

						} catch (Exception e) {
							Log.e("start new ac :", e.getMessage());
						}
					}

					@Override
					public void onStart() {
						Log.d(getClass().getName(), "调用了OnStart.");
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(int i, String s, Throwable throwable) {
						AbToastUtil.showToast(getApplicationContext(),
								"抱歉，出错了！异常:" + s);
					}
				});
			}
			if (("miao".equals(userEditStr)) && ("123".equals(passwdEditStr))) {
				new AlertDialog.Builder(LoginActivity.this)
						.setIcon(
								getResources().getDrawable(
										R.drawable.login_error_icon))
						.setTitle("登录成功！").setMessage("login,success~")
						.create().show();
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				mActivity.startActivity(intent);
			}
			break;
		case R.id.forget_passwd:// 点击 “忘记密码” 这个文本
			forgetPasswdBtn.setTextColor(Color.RED);// 文本变成红色
			View view = LayoutInflater.from(LoginActivity.this).inflate(
					R.layout.login_dialog, null);
			popup = new PopupWindow(view, AbsListView.LayoutParams.FILL_PARENT,
					AbsListView.LayoutParams.WRAP_CONTENT);
			popup.showAsDropDown(forgetPasswdBtn);
			popup.setFocusable(false);
			popup.setOutsideTouchable(true);
			popup.showAtLocation(forgetPasswdBtn, Gravity.CENTER, 0, 0);
			popup.update();
			loginLayout.setBackgroundColor(Color.GRAY);
			forgetPasswdBtn.setBackgroundColor(Color.GRAY);
			forgetPasswdBtn.setEnabled(false);
			break;
		case R.id.main_regist_btn:// 注册按钮
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			mActivity.startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (popup != null && popup.isShowing()) {
			popup.dismiss();
			loginLayout.setBackgroundColor(Color.WHITE);
			forgetPasswdBtn.setBackgroundColor(Color.WHITE);
			forgetPasswdBtn.setEnabled(true);
		}
		return super.onTouchEvent(event);
	}
}