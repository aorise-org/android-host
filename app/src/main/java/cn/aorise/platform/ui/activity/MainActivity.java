/*
 * Copyright (C) 2005-2017 Qihoo 360 Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package cn.aorise.platform.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;

import com.qihoo360.replugin.RePlugin;

import cn.aorise.common.core.config.AoriseConstant;
import cn.aorise.common.core.manager.ActivityManager;
import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.utils.assist.AoriseLog;
import cn.aorise.platform.BuildConfig;
import cn.aorise.platform.R;
import cn.aorise.platform.databinding.ActivityMainBinding;

/**
 * @author RePlugin Team
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final long EXIT_INTERVAL = 2000L;
    private long[] mHints = new long[2];
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        getLoginInfo();
    }

    private void getLoginInfo() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            String account = bundle.getString(AoriseConstant.AccountKey.USER_ACCOUNT);
            String id = bundle.getString(AoriseConstant.AccountKey.USER_ID);
            String sex = bundle.getString(AoriseConstant.AccountKey.USER_SEX);
            AoriseLog.i("Account:" + account + " ;ID:" + id + " ;Sex:" + sex);

            if (BuildConfig.DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("Account:").append(account).append("\r\n").append("ID:").append(id).append("\r\n")
                        .append("Sex:").append(sex);
                showToast(sb.toString());
            }
        }
    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.btnEducation.setOnClickListener(this);
        mBinding.btnHospital.setOnClickListener(this);
        mBinding.btnPetition.setOnClickListener(this);
        mBinding.btnSecurity.setOnClickListener(this);
        mBinding.btnOther.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_education:
                RePlugin.startActivity(MainActivity.this,
                        RePlugin.createIntent("aorise.education", "cn.aorise.sample.ui.activity.MainActivity"));
                break;
            case R.id.btn_hospital:
                //RePlugin.startActivity(MainActivity.this,
                //        RePlugin.createIntent("demo1", "com.qihoo360.replugin.sample.demo1.MainActivity"));
                break;
            case R.id.btn_petition:
                break;
            case R.id.btn_security:
                break;
            case R.id.btn_other:
                RePlugin.startActivity(MainActivity.this,
                        RePlugin.createIntent("aorise.sample", "cn.aorise.sample.ui.activity.MainActivity"));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
            mHints[mHints.length - 1] = SystemClock.uptimeMillis();
            if ((SystemClock.uptimeMillis() - mHints[0]) > EXIT_INTERVAL) {
                showToast(getString(cn.aorise.common.R.string.aorise_label_double_exit));
            } else {
                finish();
                ActivityManager.getInstance().appExit(getApplicationContext());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
