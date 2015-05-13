package com.astudio.inspicsoc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.astudio.dodowaterfall.Helper;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.common.StringUtils;
import com.astudio.inspicsoc.utils.EncryptHelper;

/**
 * 此类 是对布局main.xml上 控件的操作
 * 
 * @author dl
 *
 */
public class RegisterActivity extends Activity implements OnClickListener {
	private Button registBtn, rebackBtn;
	private Activity mActivity = this;
	private EditText userEdit, passwdEdit, assurepasswdEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		registBtn = (Button) findViewById(R.id.main_regist_btn);
		registBtn.setOnClickListener(this);

		passwdEdit = (EditText) findViewById(R.id.login_passwd_edit);
		userEdit = (EditText) findViewById(R.id.login_user_edit);
		rebackBtn = (Button) findViewById(R.id.login_reback_btn);
		assurepasswdEdit = (EditText) findViewById(R.id.login_assurepasswd_edit);
		rebackBtn.setOnClickListener(this);// 注册监听器
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (!Helper.checkConnection(this.getApplicationContext())) {
			AbToastUtil.showToast(getApplicationContext(), "请检查您的网络再试");
			return;
		}
		int viewId = v.getId();
		switch (viewId) {
		case R.id.login_reback_btn:// 返回按钮
			RegisterActivity.this.finish();// 关闭这个Activity 返回上一个Activity
			break;

		case R.id.main_regist_btn:// 注册按钮
			String userEditStr = userEdit.getText().toString().trim();
			String passwdEditStr = passwdEdit.getText().toString().trim();
			String assPass = assurepasswdEdit.getText().toString().trim();
			if (StringUtils.isEmpty(userEditStr)) {// 只要用户名和密码有一个为空
				AbToastUtil.showToast(this, "用户名不能为空");
				break;
			} else if (StringUtils.isEmpty(passwdEditStr)) {
				AbToastUtil.showToast(this, "密码不能为空");
				break;
			} else if (StringUtils.isEmpty(assPass)) {
				AbToastUtil.showToast(this, "请确认密码");
				break;
			} else if (StringUtils.isEquals(passwdEditStr, assPass)) {

				AbHttpUtil httpUtil = AbHttpUtil.getInstance(getApplication());

				String loginUrl = InsUrl.USER_REGISTER.replace("@un",
						userEditStr).replace("@ps",
						EncryptHelper.md5(passwdEditStr));

				httpUtil.get(loginUrl, new AbStringHttpResponseListener() {
					@Override
					public void onSuccess(int i, String s) {

						if ("fail".equals(s)) {
							AbToastUtil.showToast(getApplicationContext(),
									"注册失败");
							return;
						}

						AbToastUtil.showToast(getApplicationContext(), "注册成功");
						try {
							Intent intent = new Intent(getApplication(),
									LoginActivity.class);
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
								"抱歉，出错了！异常:" + "用户已存在");
					}
				});
			}
			break;
		}

	}
}