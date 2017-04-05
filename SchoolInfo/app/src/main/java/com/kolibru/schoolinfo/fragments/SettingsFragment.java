package com.kolibru.schoolinfo.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.system.ErrnoException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.activities.MainActivity;
import com.kolibru.schoolinfo.databinding.FragAuthBinding;
import com.kolibru.schoolinfo.models.Person;
import com.kolibru.schoolinfo.models.StudentAttended;
import com.kolibru.schoolinfo.models.StudentExam;
import com.kolibru.schoolinfo.models.StudentHomework;
import com.kolibru.schoolinfo.models.StudentTimeTable;
import com.kolibru.schoolinfo.utils.AppSetting;
import com.kolibru.schoolinfo.web.WebFunctionService;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.TELEPHONY_SERVICE;


public class SettingsFragment extends BaseFragment {

    private FragAuthBinding itemBinding;
    private Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm=Realm.getDefaultInstance();
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // авторизация
        View view = inflater.inflate(R.layout.frag_auth, container, false);
        itemBinding = DataBindingUtil.bind(view);
        // сохранен ли токен
        if(WebFunctionService.getTOKEN()!=null) {
            // токен сохранен - пользователь зарегистрирован в приложении
            itemBinding.logout.setVisibility(View.VISIBLE);
            itemBinding.llh.setVisibility(View.GONE);
            itemBinding.codeBlock.setVisibility(View.GONE);
            itemBinding.get.setVisibility(View.GONE);
            if(appSetting.haveKey(AppSetting.SETTING_FIO)){

                itemBinding.info.setVisibility(View.VISIBLE);
                itemBinding.info.setText(getText(R.string.message_welcome) + ", "+appSetting.getString(AppSetting.SETTING_FIO));
            }
            itemBinding.logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webFunctionService.logout(new Callback<String>() {

                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                WebFunctionService.setTOKEN(null);
                                appSetting.removeKey(AppSetting.SETTING_TOKEN);
                                appSetting.removeKey(AppSetting.SETTING_FIO);
                                appSetting.removeKey(AppSetting.SETTING_PHONE);
                                appSetting.removeKey(AppSetting.SETTING_SMS);
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.where(Person.class).findAll().deleteAllFromRealm();
                                    }
                                });
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.where(StudentAttended.class).findAll().deleteAllFromRealm();
                                    }
                                });
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.where(StudentHomework.class).findAll().deleteAllFromRealm();
                                    }
                                });
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.where(StudentExam.class).findAll().deleteAllFromRealm();
                                    }
                                });
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.where(StudentTimeTable.class).findAll().deleteAllFromRealm();
                                    }
                                });
                                ((MainActivity)getActivity()).reload();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            String string=t.getMessage();
                        }
                    });
                }
            });

        }
        else{
            itemBinding.codeBlock.setVisibility(View.GONE);
            itemBinding.logout.setVisibility(View.GONE);
            itemBinding.get.setVisibility(View.VISIBLE);
            try {
                // получить номер телефона с сим-карты
                TelephonyManager tm = (TelephonyManager) getContext().getSystemService(TELEPHONY_SERVICE);
                // и установить его в поле
                itemBinding.phone.setText(tm.getLine1Number());
            }
            catch (Exception e)
            {
                Log.d("PHONE" , e.getMessage());
            }

            itemBinding.get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemBinding.phone.getText().length()>0) {
                        appSetting.saveSharedPreferences(AppSetting.SETTING_PHONE,itemBinding.phone.getText().toString());
                        webFunctionService.registration(itemBinding.phone.getText().toString(),null, new Callback<String>() {

                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                try {
                                    String string=response.body();
                                    itemBinding.info.setText(string);
                                    JSONObject jsonObject=new JSONObject(string);
                                    if(jsonObject.has("sms_code")){
                                        appSetting.saveSharedPreferences(AppSetting.SETTING_FIO,jsonObject.getString("fullname"));
                                        appSetting.saveSharedPreferences(AppSetting.SETTING_SMS,jsonObject.getString("sms_code"));
                                        itemBinding.codeBlock.setVisibility(View.VISIBLE);
                                        itemBinding.get.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                String string=t.getMessage();
                            }
                        });
                    }
                    else {
                        Snackbar.make(itemBinding.getRoot(), getString(R.string.not_fill_fields), Snackbar.LENGTH_LONG).show();
                    }

                }
            });
            itemBinding.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        String code=null;
                        if(itemBinding.codeBlock.getVisibility()==View.VISIBLE){
                            if(itemBinding.code.getText().length()>0
                                    && appSetting.haveKey(AppSetting.SETTING_SMS)
                                    && appSetting.getString(AppSetting.SETTING_SMS).equals(itemBinding.code.getText().toString())){
                                code=itemBinding.code.getText().toString();
                            }
                            else {
                                Snackbar.make(itemBinding.getRoot(), getString(R.string.incorrect_code), Snackbar.LENGTH_LONG).show();
                                return;
                            }
                        }
                        else
                            return;
                        webFunctionService.registration(itemBinding.phone.getText().toString(),code, new Callback<String>() {

                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                try {
                                    String string=response.body();
                                    itemBinding.info.setText(string);
                                    JSONObject jsonObject=new JSONObject(string);
                                    if(jsonObject.has("access_token")){
                                        WebFunctionService.setTOKEN(jsonObject.getString("access_token"));
                                        appSetting.saveSharedPreferences(AppSetting.SETTING_TOKEN, WebFunctionService.getTOKEN());
                                        appSetting.saveSharedPreferences(AppSetting.SETTING_FIO,jsonObject.getString("fullname"));
                                        Toast.makeText(getActivity(), getText(R.string.message_welcome) + ", "+jsonObject.getString("fullname"),Toast.LENGTH_LONG).show();
                                        webFunctionService.getChildren(new Callback<RealmList<Person>>() {
                                            @Override
                                            public void onResponse(Call<RealmList<Person>> call, final Response<RealmList<Person>> response) {
                                                try {
                                                    realm.executeTransaction(new Realm.Transaction() {
                                                        @Override
                                                        public void execute(Realm realm) {
                                                            realm.copyToRealmOrUpdate(response.body());
                                                        }
                                                    });

                                                }
                                                catch (Exception e){
                                                }
                                                ((MainActivity) getActivity()).reload();
                                            }

                                            @Override
                                            public void onFailure(Call<RealmList<Person>> call, Throwable t) {

                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                String string=t.getMessage();
                            }
                        });
                }
            });
        }
        return view;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {menu.clear();
        inflater.inflate(R.menu.main_empty, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }
}
