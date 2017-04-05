package com.kolibru.schoolinfo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.Response;


/**
 * Created by Andrej_Maligin on 27.09.2015.
 */
public class ConstClass {

    public static int DEVICE_HEIGHT = 480;
    public static int DEVICE_WIDTH = 800;

    public static String GetDateFromWeb(String str)
    {
        String date = str;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            date = ConstClass.DateTimeFormat( format.parse(str) );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String DateTimeFormat(Date date)
    {
        Locale russia = new Locale("ru","RU","RU");
        Calendar cal = Calendar.getInstance(russia);
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.DEFAULT, russia);
        return df.format(date);
    }

    public static String[] week={
            "",
            "Понедельник",
        "Вторник",
        "Среда",
        "Четверг",
        "Пятница",
        "Суббота",
        "Воскресение",
    };
    public static String getDayOfWeek(int w){
        try {
            return week[w];
        }
        catch (Exception e){
            return "";
        }
    }


    public static  enum TypeUser {
        COURIER(1),
        CLIENT(2);
        private final int value;

        private TypeUser(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }



    public static int getWidth() {
        return DEVICE_WIDTH;
    }

    public static int getMax() {
        if (DEVICE_HEIGHT > DEVICE_WIDTH)
            return DEVICE_HEIGHT;
        else
            return DEVICE_WIDTH;
    }

    public static String getFormatDuration(String duration_s) {
        int duration = 0;
        try {
            duration = Integer.parseInt(duration_s);
        } catch (Exception e) {
        }
        try {
            return Integer.toString(duration);
        } catch (Exception e) {
            return "1";
        }
    }

    public static String getFormatDuration(int duration) {
        try {
            return Integer.toString(duration) ;
        } catch (Exception e) {
            return "1";
        }
    }


    public static String getDistance(String dist_s) {
        int dist = 0;
        try {
            dist = Integer.parseInt(dist_s);
        } catch (Exception e) {
        }
        try {
            if (dist > 999)
                return Float.toString((float) dist / 1000) + " км";
            else
                return Integer.toString(dist) + " м";
        } catch (Exception e) {
            return "1";
        }
    }

    public static String getDistance(int dist) {
        try {
            if (dist > 999)
                return Float.toString((float) dist / 1000) + " км";
            else
                return Integer.toString(dist) + " м";
        } catch (Exception e) {
            return "1";
        }
    }

    /**
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * @param bitmap
     * @return
     */
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap image_min = Bitmap.createScaledBitmap(bitmap, Math.round((float) bitmap.getWidth() / (float) Math.min(bitmap.getWidth(), bitmap.getHeight()) * (float) 1024),
                Math.round((float) bitmap.getHeight() / (float) Math.min(bitmap.getWidth(), bitmap.getHeight()) * (float) 1024), false);

        image_min.compress(Bitmap.CompressFormat.JPEG, 35, baos);
        String temp = null;
        try {
            byte[] b = baos.toByteArray();

            temp = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 15, baos);
            byte[] b = baos.toByteArray();
            temp = Base64.encodeToString(b, Base64.DEFAULT);
        }
        return temp;
    }

    /**
     * @param bitmap
     * @return
     */
    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap image_min = Bitmap.createScaledBitmap(bitmap, Math.round((float) bitmap.getWidth() / (float) Math.min(bitmap.getWidth(), bitmap.getHeight()) * (float) 1024),
                Math.round((float) bitmap.getHeight() / (float) Math.min(bitmap.getWidth(), bitmap.getHeight()) * (float) 1024), false);

        image_min.compress(Bitmap.CompressFormat.JPEG, 35, baos);

        return baos.toByteArray();
    }
    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return bitmap (from given string)
     */
    public static Bitmap byteToBitmap(byte[] encodeByte) {
        try {
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            return null;
        }
    }
    public static int getColor(Context context, int id){
        if (Build.VERSION.SDK_INT >=23) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public final static boolean isValidPhone(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.PHONE.matcher(target).matches();
        }
    }


    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getPrice(int value){
        try{
            return String.valueOf(value)+" руб";

        }
        catch (Exception e){
            return "Отсутствует";
        }
    }
}
