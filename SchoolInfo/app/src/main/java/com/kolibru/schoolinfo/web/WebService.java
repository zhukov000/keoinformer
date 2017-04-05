package com.kolibru.schoolinfo.web;

import com.kolibru.schoolinfo.models.Person;
import com.kolibru.schoolinfo.models.RequestInfo;
import com.kolibru.schoolinfo.models.StudentAttended;
import com.kolibru.schoolinfo.models.StudentExam;
import com.kolibru.schoolinfo.models.StudentHomework;
import com.kolibru.schoolinfo.models.StudentTimeTable;
import com.squareup.okhttp.ResponseBody;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Ссылки на функции API
 */
public interface WebService {

    @FormUrlEncoded
    @POST("parent/register")
    Call<String> registration(
        @Field("phone") String number,
        @Field("sms_code") String sms_code
    );

    @GET("parent/flash-token")
    Call<String> logout(
        @Query("access_token") String access_token
    );

    @GET("parent/children")
    Call<RealmList<Person>> getChildren(
        @Query("access_token") String access_token
    );

    /**
     * Расписание занятий
     * @param access_token
     * @return
     */
    @GET("parent/timetable")
    Call<RealmList<StudentTimeTable>> getTmeTable(
        @Query("access_token") String access_token
    );

    /**
     * Результаты тестирования
     * @param access_token
     * @param date_from
     * @param date_to
     * @return
     */
    @GET("parent/exams")
    Call<RealmList<StudentExam>> getResult(
        @Query("access_token") String access_token,
        @Query("date_from") String date_from,
        @Query("date_to") String date_to
    );

    /**
     * Посещаемость
     * @param access_token
     * @param date_from
     * @param date_to
     * @return
     */
    @GET("parent/attendance")
    Call<RealmList<StudentAttended>> getAttendance(
            @Query("access_token") String access_token,
            @Query("date_from") String date_from,
            @Query("date_to") String date_to
    );

    /**
     * Результаты домашних заданий
     * @param access_token
     * @param date_from
     * @param date_to
     * @return
     */
    @GET("parent/homework")
    Call<RealmList<StudentHomework>> getHomework(
            @Query("access_token") String access_token,
            @Query("date_from") String date_from,
            @Query("date_to") String date_to
    );
}
