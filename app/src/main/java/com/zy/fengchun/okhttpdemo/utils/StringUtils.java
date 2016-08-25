package com.zy.fengchun.okhttpdemo.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class StringUtils {
    /**
     * @Description: 判断String是否为空, true代表空，false代表非空 @param string @return boolean @throws
     */
    public static boolean isNullOrEmpty(String string) {
        if (string == null) {
            return true;
        } else if ("".equalsIgnoreCase(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * @param mobileNumber 手机号码
     * @return
     * @description 手机号码验证
     * @date 2015年8月17日
     */
    public static boolean isMobileNumber(String mobileNumber) {
        return Pattern.compile("^1\\d{10}$").matcher(mobileNumber).matches();
    }

    /**
     * @param zipCode 邮政编码
     * @return
     * @description 邮编格式验证
     * @date 2015年8月17日
     */
    public static boolean isZipCode(String zipCode) {
        Pattern p = Pattern.compile("^\\d{6}$");
        Matcher m = p.matcher(zipCode);
        return m.matches();
    }

    /**
     * @param str RMB字符串
     * @return 如:0.01
     * @description RMB格式化 ,保留两位小数
     */
    public static String formatMoney(String str) {
        try {
            Double value = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(value);
        } catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * @param d RMB
     * @return 如:0.01
     * @description RMB格式化 ,保留两位小数
     */
    public static String formatMoney(double d) {
        return formatMoney(String.valueOf(d));
    }

    /**
     * 检查是否是正确的email
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 去除字符串里的空格
     *
     * @param str
     * @return
     */
    public static String removeAllSpace(String str) {
        String tmpstr = str.replace(" ", "");
        return tmpstr;
    }

    /**
     * 校验身份证
     *
     * @param certiCode 待校验身份证
     * @param certiCode
     * @return
     */
    public static int checkCertiCode(String certiCode) {
        try {
            if (certiCode == null || certiCode.length() != 18) {
                return 1;
            }

            String s1;
            String s2;
            String s3;
            String s4;

            if (certiCode.length() == 18) {
                if (!checkFigure(certiCode.substring(0, 17))) {
                    return 5;
                }

                s1 = certiCode.substring(6, 10);
                s2 = certiCode.substring(10, 12);
                s3 = certiCode.substring(12, 14);

                if (!checkDate(s1, s2, s3)) {
                    return 2;
                }

                if (!checkIDParityBit(certiCode)) {
                    return 3;
                }
            }
        } catch (Exception exception) {
            return 4;
        }
        return 0;
    }

    /**
     * 检查字符串是否全为数字
     *
     * @param certiCode
     * @return
     */
    private static boolean checkFigure(String certiCode) {
        try {
            Long.parseLong(certiCode);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 检查日期格式
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static boolean checkDate(String year, String month, String day) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        try {
            String s3 = year + month + day;
            simpledateformat.setLenient(false);
            simpledateformat.parse(s3);
        } catch (java.text.ParseException parseexception) {
            return false;
        }
        return true;
    }

    /**
     * 检查校验位
     *
     * @param certiCode
     * @return
     */
    private static boolean checkIDParityBit(String certiCode) {
        boolean flag = false;
        if (certiCode == null || "".equals(certiCode)) {
            return false;
        }
        int[] ai = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        if (certiCode.length() == 18) {
            int i = 0;
            for (int k = 0; k < 18; k++) {
                char c = certiCode.charAt(k);
                int j;
                if (c == 'X' || c == 'x') {
                    j = 10;
                } else if (c <= '9' && c >= '0') {
                    j = c - 48;
                } else {
                    return flag;
                }
                i += j * ai[k];
            }

            if (i % 11 == 1) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 判断身份证号码是否有效
     *
     * @param idCard
     * @return
     */
    public static boolean validateIdCard(String idCard) {
        if (checkCertiCode(idCard) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取身份证中出生日期
     *
     * @param
     * @return String
     * @description
     * @date 2015-3-3
     * @Exception
     */
    public static String getBirthInfo(String certiCode) {
        StringBuffer result = new StringBuffer();
        if (certiCode.length() == 15) {
            result.append("19" + certiCode.substring(6, 8))
                    .append("-")
                    .append("certiCode.substring(8, 10)")
                    .append("-")
                    .append(certiCode.substring(10, 12));
        } else if (certiCode.length() == 18) {
            result.append(certiCode.substring(6, 10))
                    .append("-")
                    .append(certiCode.substring(10, 12))
                    .append("-")
                    .append(certiCode.substring(12, 14));
        }
        return result.toString();
    }

    /**
     * 获取性别
     *
     * @param idCard
     * @return
     */
    public static int getIdCardSex(String idCard) {
        if (idCard.length() != 18) {
            return 3;
        }
        if (Integer.valueOf(idCard.substring(16, 17)) % 2 == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 隐藏证件号
     *
     * @param cardNum 证件号
     * @param type    1：身份证
     *                2：护照
     *                3：军官证
     *                4：驾驶证
     *                5：台胞证
     *                6：港澳通行证
     *                7：台湾通行证
     *                8：士官证
     *                9：手机号
     * @return
     */
    // 0：身份证 1：护照 2：学生证 3：军人证 4：驾驶证 5：回乡证 6：台胞证 7：港澳通行证 8：台湾通行证 9：士兵证 10
    public static String hideIdCardNum(String cardNum, int type) {
        String result = "";
        if (isNullOrEmpty(cardNum)) {
            return result;
        }
        switch (type) {
            // 身份证
            case 0:
                if (cardNum.length() < 18) {
                    return cardNum;
                }
                else {
                    result = cardNum.substring(0, 4) + "************" + cardNum.substring(cardNum.length() -3, cardNum.length());
                }
                break;

            // 护照
            case 1:
                if (cardNum.length() < 6) {
                    return cardNum;
                }
                else {
                    result = cardNum.substring(0, cardNum.length() - 5) + "****" + cardNum.substring(cardNum.length() - 1, cardNum.length());
                }
                break;

            // 军官证
            case 3:
                if (cardNum.length() < 5) {
                    return cardNum;
                }
                else {
                    result = cardNum.substring(0, cardNum.length() - 3) + "***" + cardNum.substring(cardNum.length() - 1, cardNum.length());
                }
                break;

            // 驾驶证
            case 4:
                if (cardNum.length() < 8) {
                    return cardNum;
                }
                else {
                    result = cardNum.substring(0, 4) + "************" + cardNum.substring(cardNum.length() - 3, cardNum.length());
                }
                break;

            // 台胞证
            case 5:
                if (cardNum.length() < 6) {
                    return cardNum;
                }
                else {
                    result = cardNum.substring(0, cardNum.length() - 5) + "****" + cardNum.substring(cardNum.length() - 1, cardNum.length());
                }
                break;

            // 港澳通行证
            case 7:
                if (cardNum.length() < 6) {
                    return cardNum;
                }
                else {
                    result = cardNum.substring(0, cardNum.length() - 5) + "****" + cardNum.substring(cardNum.length() - 1, cardNum.length());
                }
                break;

            // 台湾通行证
            case 8:
                if (cardNum.length() < 5) {
                    return cardNum;
                }
                else {
                    result = cardNum.substring(0, cardNum.length() - 3) + "***" + cardNum.substring(cardNum.length() - 1, cardNum.length());
                }
                break;

            // 士官证
            case 9:
                if (cardNum.length() < 6) {
                    return cardNum;
                }
                else {
                    result = cardNum.substring(0, cardNum.length() - 5) + "****" + cardNum.substring(cardNum.length() - 1, cardNum.length());
                }
                break;

            // 手机号
            case -1:
                if (cardNum.length() < 6) {
                    return cardNum;
                }
                else {
                    result = cardNum.substring(0, cardNum.length() - 5) + "****" + cardNum.substring(cardNum.length() - 1, cardNum.length());
                }
                break;
        }
        return result;
    }
}
