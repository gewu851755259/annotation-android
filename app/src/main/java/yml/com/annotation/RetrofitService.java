package yml.com.annotation;

import android.database.Observable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    /**
     * Content-Type:application/x-www-form-urlencoded
     */
    @GET("news/list")
    Call<ResponseBody> searchNews(@Query("start") String start);

    /**
     * 请求体是 From 表单
     * Content-Type:application/x-www-form-urlencoded
     */
    @FormUrlEncoded
    @POST("register.php")
    Observable<ResponseBody> register(@Field("user_account") String account
            , @Field("user_pswd") String pswd
            , @Field("user_pswd_again") String pswd2
            , @Field("user_name") String username);

    @GET("news/{date}/list")
    Call<ResponseBody> dateNews(@Path("date") String date);

}
