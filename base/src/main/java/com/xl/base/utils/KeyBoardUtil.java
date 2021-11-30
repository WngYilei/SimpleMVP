package com.xl.base.utils;

import android.util.Log;

public class KeyBoardUtil {
    KeyBoardUtilCallback callback;
    private String expression = "";
    private boolean end = false;
    private int countOperate = 2;

    public KeyBoardUtil() {
        Log.i( "SSSSSSSSSSSS", "初始化键盘");
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setCallback(KeyBoardUtilCallback callback) {
        this.callback = callback;
    }

    public void inputKeyCode(int code) {
        if (end) {
            expression = "";
            end = false;
        }
        if (code == 4 || code == 111) {//取消
            expression = "";
            countOperate = 0;
            callback.keyBoardListener(expression);
        } else if (code == 67) {//删除
            if (expression.length() > 1) { //算式长度大于1
                expression = expression.substring(0, expression.length() - 1);//退一格
                int i = expression.length() - 1;
                char tmp = expression.charAt(i); //获得最后一个字符
                char tmpFront = tmp;
                for (; i >= 0; i--) { //向前搜索最近的 +-*/和.，并退出
                    tmpFront = expression.charAt(i);
                    if (tmpFront == '.' || tmpFront == '+' || tmpFront == '-' || tmpFront == 'x' || tmpFront == '/') {
                        break;
                    }
                }
                //    Toast.makeText(this, "tmp = "+tmp, Toast.LENGTH_SHORT).show();
                if (tmp >= '0' && tmp <= '9') { //最后一个字符为数字，则识别数赋值为0
                    countOperate = 0;
                } else if (tmp == tmpFront && tmpFront != '.') countOperate = 2; //如果为+-*/，赋值为2
                else if (tmpFront == '.') countOperate = 1; //如果前面有小数点赋值为1
            } else if (expression.length() == 1) {
                expression = "";
            }
            callback.keyBoardListener(expression);
        } else if (code == 158) {//.
            if (expression.equals("") || countOperate == 2) {
                expression += "0" + ".";
                countOperate = 1;  //小数点按过之后赋值为1
            }
            if (countOperate == 0) {
                expression += ".";
                countOperate = 1;
            }
            callback.keyBoardListener(expression);
        } else if (code == 157) {//+
            if (countOperate == 0) {
                expression += "+";
                countOperate = 2;  //  +-*/按过之后赋值为2
            }
            callback.keyBoardListener(expression);
        } else if (code == 66) {//确认
            double result = (double) Math.round(count() * 100) / 100;
            expression = "";
            expression += result;
            end = true; //此次计算结束
            callback.keyBoardEqual(expression);
        } else {
            if (expression.length() >= 1) {
                char tmp1 = expression.charAt(expression.length() - 1);
                if (tmp1 == '0' && expression.length() == 1) {
                    expression = expression.substring(0, expression.length() - 1);
                } else if (tmp1 == '0' && expression.length() > 1) {
                    char tmp2 = expression.charAt(expression.length() - 2);
                    if (tmp2 == '+' || tmp2 == '-' || tmp2 == 'x' || tmp2 == '/') {
                        expression = expression.substring(0, expression.length() - 1);
                    }
                }
            }
            expression += codeToStr(code);
            if (countOperate == 2 || countOperate == 1) countOperate = 0;
            callback.keyBoardListener(expression);
        }
    }

    private String codeToStr(int code) {
        switch (code) {
            case 144:
                return "0";
            case 145:
                return "1";
            case 146:
                return "2";
            case 147:
                return "3";
            case 148:
                return "4";
            case 149:
                return "5";
            case 150:
                return "6";
            case 151:
                return "7";
            case 152:
                return "8";
            case 153:
                return "9";
            default:
                return "";
        }
    }

    //计算逻辑，求expression表达式的值
    private double count() {
        double result = 0;
        double tNum = 1, lowNum = 0.1, num = 0;
        char tmp = 0;
        int operate = 1; //识别+-*/，为+时为正数，为-时为负数，为×时为-2/2,为/时为3/-3;
        boolean point = false;
        for (int i = 0; i < expression.length(); i++) { //遍历表达式
            tmp = expression.charAt(i);
            if (tmp == '.') { //因为可能出现小数，此处进行判断是否有小数出现
                point = true;
                lowNum = 0.1;
            } else if (tmp == '+' || tmp == '-') {
                if (operate != 3 && operate != -3) { //此处判断通用，适用于+-*
                    tNum *= num;
                } else { //计算/
                    tNum /= num;
                }
                if (operate < 0) { //累加入最终的结果
                    result -= tNum;
                } else {
                    result += tNum;
                }
                operate = tmp == '+' ? 1 : -1;
                num = 0;
                tNum = 1;
                point = false;
            } else if (tmp == 'x') {
                if (operate != 3 && operate != -3) {
                    tNum *= num;
                } else {
                    tNum /= num;
                }
                operate = operate < 0 ? -2 : 2;
                point = false;
                num = 0;
            } else if (tmp == '/') {
                if (operate != 3 && operate != -3) {
                    tNum *= num;
                } else {
                    tNum /= num;
                }
                operate = operate < 0 ? -3 : 3;
                point = false;
                num = 0;
            } else {
                //读取expression中的每个数字，doube型
                if (!point) {
                    num = num * 10 + tmp - '0';
                } else {
                    num += (tmp - '0') * lowNum;
                    lowNum *= 0.1;
                }
            }
        }
        //循环遍历结束，计算最后一个运算符后面的数
        if (operate != 3 && operate != -3) {
            tNum *= num;
        } else {
            tNum /= num;
        }
        //    Toast.makeText(this, "tNum = "+tNum, Toast.LENGTH_SHORT).show();
        if (operate < 0) {
            result -= tNum;
        } else {
            result += tNum;
        }
        //返回最后的结果
        return result;
    }

    public interface KeyBoardUtilCallback {
        void keyBoardListener(String s);

        void keyBoardEqual(String s);
    }

}
