package com.airport.help.chatter;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.airport.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

public class ChatEmoticonUtil {

	public static void dealExpression(Context context,
			SpannableString spannableString, Pattern patten, int start)
			throws SecurityException, NoSuchFieldException,
			NumberFormatException, IllegalArgumentException,
			IllegalAccessException {
		Matcher matcher = patten.matcher(spannableString);
		while (matcher.find()) {
			String key = matcher.group();
			if (matcher.start() < start) {
				continue;
			}
			Field field = R.drawable.class.getDeclaredField(key);
			int resId = Integer.parseInt(field.get(null).toString()); // 閫氳繃涓婇潰鍖归厤寰楀埌鐨勫瓧绗︿覆鏉ョ敓鎴愬浘鐗囪祫婧恑d
			if (resId != 0) {
				Bitmap bitmap = BitmapFactory.decodeResource(
						context.getResources(), resId);
				ImageSpan imageSpan = new ImageSpan(bitmap); // 閫氳繃鍥剧墖璧勬簮id鏉ュ緱鍒癰itmap锛岀敤涓�釜ImageSpan鏉ュ寘瑁�
																// int end =
																// matcher.start()
																// +
																// key.length();
																// //璁＄畻璇ュ浘鐗囧悕瀛楃殑闀垮害锛屼篃灏辨槸瑕佹浛鎹㈢殑瀛楃涓茬殑闀垮害
				spannableString.setSpan(imageSpan, matcher.start(), resId,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE); // 灏嗚鍥剧墖鏇挎崲瀛楃涓蹭腑瑙勫畾鐨勪綅缃腑
				if (resId < spannableString.length()) { // 濡傛灉鏁翠釜瀛楃涓茶繕鏈獙璇佸畬锛屽垯缁х画銆傘�
					dealExpression(context, spannableString, patten, resId);
				}
				break;
			}
		}
	}

	/**
	 * 寰楀埌涓�釜SpanableString瀵硅薄锛岄�杩囦紶鍏ョ殑瀛楃涓�骞惰繘琛屾鍒欏垽鏂� * @param context
	 * 
	 * @param str
	 * @return
	 */
	public static SpannableString getExpressionString(Context context,
			String str, String zhengze) {
		SpannableString spannableString = new SpannableString(str);
		Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE); // 閫氳繃浼犲叆鐨勬鍒欒〃杈惧紡鏉ョ敓鎴愪竴涓猵attern
		try {
			dealExpression(context, spannableString, sinaPatten, 0);
		} catch (Exception e) {
			Log.e("dealExpression", e.getMessage());
		}
		return spannableString;
	}

}