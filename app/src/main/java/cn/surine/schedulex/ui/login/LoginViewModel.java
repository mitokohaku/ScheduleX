package cn.surine.schedulex.ui.login;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.annotation.Keep;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cn.surine.schedulex.base.Constants;
import cn.surine.schedulex.base.http.BaseHttpSubscriber;
import cn.surine.schedulex.base.utils.Prefs;
import cn.surine.schedulex.base.utils.SimpleTextWatcher;
import cn.surine.schedulex.base.utils.Strs;
import cn.surine.schedulex.data.entity.VmResultString;

@Keep
public class LoginViewModel extends ViewModel {

    private LoginRepository mLoginRepository;

    private MutableLiveData<String> account = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();


    /**
     * 登录状态
     */
    public MutableLiveData<Integer> loginStatus = new MutableLiveData<>();
    public static final int START_LOGIN = 0;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAIL = 2;


    public TextWatcher accountWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            account.setValue(s.toString());
        }
    };
    public TextWatcher passwordWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            password.setValue(s.toString());
        }
    };


    public LoginViewModel(LoginRepository mLoginRepository) {
        this.mLoginRepository = mLoginRepository;
    }


    /**
     * 登录操作
     */
    public void login() {
        if (TextUtils.isEmpty(account.getValue()) || TextUtils.isEmpty(password.getValue())) {
            return;
        }
        loginStatus.setValue(START_LOGIN);
        mLoginRepository.login(account.getValue(), password.getValue()).subscribe(new BaseHttpSubscriber<VmResultString>() {
            @Override
            public void onSuccess(MutableLiveData<VmResultString> vm) {
                loginStatus.setValue(Strs.equals(Constants.LOGIN_SUCCESS, vm.getValue().result) ? LOGIN_SUCCESS : LOGIN_FAIL);
            }
        });
    }


    /**
     * 保存账号密码
     */
    public void saveAccountAndPassword() {
        //如果支持多种教务的话，可以拼接ID保存，
        Prefs.save(Constants.ACCOUNT, account.getValue());
        Prefs.save(Constants.PASSWORD, account.getValue());
    }
}
