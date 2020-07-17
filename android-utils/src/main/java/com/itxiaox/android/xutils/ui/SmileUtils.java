package com.itxiaox.android.xutils.ui;

import android.content.Context;
import android.text.Spannable;
import android.text.style.ImageSpan;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ===================================================================================
 *
 * @作者: 张文颖
 *
 * @创建时间: 2014年12月16日 下午2:11:57
 *
 * @描述: 表情解析，仿环信，应用时将类中注释部分打开，替换新的资源文件，可以修改ee_1 = "[weixiao]"中的weixiao为自己所需要的
 *
 * @修改时间:
 *
 * ====================================================================================
 */

public  class SmileUtils {
    public static final String ee_1 = "[weixiao]";
    public static final String ee_2 = "[piezui]";
    public static final String ee_3 = "[se]";
    public static final String ee_4 = "[fadai]";
    public static final String ee_5 = "[deyi]";
    public static final String ee_6 = "[liulei]";
    public static final String ee_7 = "[haixiu]";
    public static final String ee_8 = "[bizui]";
    public static final String ee_9 = "[shui]";
    public static final String ee_10 = "[daku]";
    public static final String ee_11 = "[ganga]";
    public static final String ee_12 = "[fanu]";
    public static final String ee_13 = "[tiaopi]";
    public static final String ee_14 = "[ziya]";
    public static final String ee_15 = "[jingya]";
    public static final String ee_16 = "[nanguo]";
    public static final String ee_17 = "[ku]";
    public static final String ee_18 = "[lenghan]";
    public static final String ee_19 = "[zhuakuang]";
    public static final String ee_20 = "[tu]";
    public static final String ee_21 = "[touxiao]";
    public static final String ee_22 = "[yukuai]";
    public static final String ee_23 = "[baiyan]";
    public static final String ee_24 = "[aoman]";
    public static final String ee_25 = "[jie]";
    public static final String ee_26 = "[kun]";
    public static final String ee_27 = "[jingkong]";
    public static final String ee_28 = "[liuhan]";
    public static final String ee_29 = "[hanxiao]";
    public static final String ee_30 = "[youxian]";
    public static final String ee_31 = "[fendou]";
    public static final String ee_32 = "[zhouma]";
    public static final String ee_33 = "[yiwen]";
    public static final String ee_34 = "[xu]";
    public static final String ee_35 = "[yun]";
    public static final String ee_36 = "[fengle]";
    public static final String ee_37 = "[shuai]";
    public static final String ee_38 = "[kulu]";
    public static final String ee_39 = "[qiaoda]";
    public static final String ee_40 = "[zaijian]";
    public static final String ee_41 = "[cahan]";
    public static final String ee_42 = "[koubi]";
    public static final String ee_43 = "[guzhang]";
    public static final String ee_44 = "[qiudale]";
    public static final String ee_45 = "[huaixiao]";
    public static final String ee_46 = "[zuohengheng]";
    public static final String ee_47 = "[youhengheng]";
    public static final String ee_48 = "[haqian]";
    public static final String ee_49 = "[bishi]";
    public static final String ee_50 = "[weiqu]";
    public static final String ee_51 = "[kuaikule]";
    public static final String ee_52 = "[yinxian]";
    public static final String ee_53 = "[qinqin]";
    public static final String ee_54 = "[xia]";
    public static final String ee_55 = "[kelian]";
    public static final String ee_56 = "[caidao]";
    public static final String ee_57 = "[xigua]";
    public static final String ee_58 = "[pijiu]";
    public static final String ee_59 = "[lanqiu]";
    public static final String ee_60 = "[pingpang]";

    private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();

    private static final Map<Pattern, Integer> emoticons = new HashMap<Pattern, Integer>();

    static {
        //
        // addPattern(emoticons, ee_1, R.drawable.weixiao);
        // addPattern(emoticons, ee_2, R.drawable.piezui);
        // addPattern(emoticons, ee_3, R.drawable.se);
        // addPattern(emoticons, ee_4, R.drawable.fadai);
        // addPattern(emoticons, ee_5, R.drawable.deyi);
        // addPattern(emoticons, ee_6, R.drawable.liulei);
        // addPattern(emoticons, ee_7, R.drawable.haixiu);
        // addPattern(emoticons, ee_8, R.drawable.bizui);
        // addPattern(emoticons, ee_9, R.drawable.shui);
        // addPattern(emoticons, ee_10, R.drawable.daku);
        // addPattern(emoticons, ee_11, R.drawable.ganga);
        // addPattern(emoticons, ee_12, R.drawable.fanu);
        // addPattern(emoticons, ee_13, R.drawable.tiaopi);
        // addPattern(emoticons, ee_14, R.drawable.ziya);
        // addPattern(emoticons, ee_15, R.drawable.jingya);
        // addPattern(emoticons, ee_16, R.drawable.nanguo);
        // addPattern(emoticons, ee_17, R.drawable.ku);
        // addPattern(emoticons, ee_18, R.drawable.lenghan);
        // addPattern(emoticons, ee_19, R.drawable.zhuakuang);
        // addPattern(emoticons, ee_20, R.drawable.tu);
        // addPattern(emoticons, ee_21, R.drawable.touxiao);
        // addPattern(emoticons, ee_22, R.drawable.yukuai);
        // addPattern(emoticons, ee_23, R.drawable.baiyan);
        // addPattern(emoticons, ee_24, R.drawable.aoman);
        // addPattern(emoticons, ee_25, R.drawable.jie);
        // addPattern(emoticons, ee_26, R.drawable.kun);
        // addPattern(emoticons, ee_27, R.drawable.jingkong);
        // addPattern(emoticons, ee_28, R.drawable.liuhan);
        // addPattern(emoticons, ee_29, R.drawable.hanxiao);
        // addPattern(emoticons, ee_30, R.drawable.youxian);
        // addPattern(emoticons, ee_31, R.drawable.fendou);
        // addPattern(emoticons, ee_32, R.drawable.zhouma);
        // addPattern(emoticons, ee_33, R.drawable.yiwen);
        // addPattern(emoticons, ee_34, R.drawable.xu);
        // addPattern(emoticons, ee_35, R.drawable.yun);
        // addPattern(emoticons, ee_36, R.drawable.fengle);
        // addPattern(emoticons, ee_37, R.drawable.shuai);
        // addPattern(emoticons, ee_38, R.drawable.kulu);
        // addPattern(emoticons, ee_39, R.drawable.qiaoda);
        // addPattern(emoticons, ee_40, R.drawable.zaijian);
        // addPattern(emoticons, ee_41, R.drawable.cahan);
        // addPattern(emoticons, ee_42, R.drawable.koubi);
        // addPattern(emoticons, ee_43, R.drawable.guzhang);
        // addPattern(emoticons, ee_44, R.drawable.qiudale);
        // addPattern(emoticons, ee_45, R.drawable.huaixiao);
        // addPattern(emoticons, ee_46, R.drawable.zuohengheng);
        // addPattern(emoticons, ee_47, R.drawable.youhengheng);
        // addPattern(emoticons, ee_48, R.drawable.haqian);
        // addPattern(emoticons, ee_49, R.drawable.bishi);
        // addPattern(emoticons, ee_50, R.drawable.weiqu);
        // addPattern(emoticons, ee_51, R.drawable.kuaikule);
        // addPattern(emoticons, ee_52, R.drawable.yinxian);
        // addPattern(emoticons, ee_53, R.drawable.qinqin);
        // addPattern(emoticons, ee_54, R.drawable.xia);
        // addPattern(emoticons, ee_55, R.drawable.kelian);
        // addPattern(emoticons, ee_56, R.drawable.caidao);
        // addPattern(emoticons, ee_57, R.drawable.xigua);
        // addPattern(emoticons, ee_58, R.drawable.pijiu);
        // addPattern(emoticons, ee_59, R.drawable.lanqiu);
        // addPattern(emoticons, ee_60, R.drawable.pingpang);
    }

    private static void addPattern(Map<Pattern, Integer> map, String smile, int resource) {
        map.put(Pattern.compile(Pattern.quote(smile)), resource);
    }

    /**
     * replace existing spannable with smiles
     *
     * @param context
     * @param spannable
     * @return
     */
    public static boolean addSmiles(Context context, Spannable spannable) {
        boolean hasChanges = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start() && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    spannable.setSpan(new ImageSpan(context, entry.getValue()), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return hasChanges;
    }

    public static Spannable getSmiledText(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }

    public static boolean containsKey(String key) {
        boolean b = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(key);
            if (matcher.find()) {
                b = true;
                break;
            }
        }

        return b;
    }

}