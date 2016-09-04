package com.hm.library.util

import java.util.regex.Pattern

/**
 * HtmlUtil
 *
 *
 * himi on 2016-07-22 18:06
 * version V1.0
 */


object HtmlUtil {
    private val regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>" // 定义script的正则表达式
    private val regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>" // 定义style的正则表达式
    private val regEx_html = "<[^>]+>" // 定义HTML标签的正则表达式
    private val regEx_space = "\\s*|\t|\r|\n"//定义空格回车换行符

    /**
     * @param htmlStr
     * *
     * @return
     * *  删除Html标签
     */
    fun delHTMLTag(htmlStr: String): String {
        var htmlStr = htmlStr
        val p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE)
        val m_script = p_script.matcher(htmlStr)
        htmlStr = m_script.replaceAll("") // 过滤script标签

        val p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE)
        val m_style = p_style.matcher(htmlStr)
        htmlStr = m_style.replaceAll("") // 过滤style标签

        val p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE)
        val m_html = p_html.matcher(htmlStr)
        htmlStr = m_html.replaceAll("") // 过滤html标签

        val p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE)
        val m_space = p_space.matcher(htmlStr)
        htmlStr = m_space.replaceAll("") // 过滤空格回车标签
        return htmlStr.trim { it <= ' ' } // 返回文本字符串
    }

    fun getTextFromHtml(htmlStr: String): String {
        var htmlStr = htmlStr
        htmlStr = delHTMLTag(htmlStr)
        htmlStr = htmlStr.replace("&nbsp;".toRegex(), "")
        htmlStr = htmlStr.substring(0, htmlStr.indexOf("。") + 1)
        return htmlStr
    }

    /**
     * 删除input字符串中的html格式

     * @param input
     * *
     * @return
     */
    fun splitAndFilterString(input: String?): String {
        if (input == null || input.trim { it <= ' ' } == "") {
            return ""
        }
        // 去掉所有html元素,
        var str = input.replace("<[a-zA-Z]+[1-9]?[^><]*>".toRegex(), "").replace("</[a-zA-Z]+[1-9]?>".toRegex(), "")
        str = str.replace("[(/>)<]".toRegex(), "")
        return str
    }

    /**
     * 删除Html标签
     * @param inputString
     * *
     * @return
     */
    fun removeHtmlTag(inputString: String?): String? {
        if (inputString == null)
            return null
        var htmlStr: String = inputString // 含html标签的字符串
        var textStr = ""
        val p_script: java.util.regex.Pattern
        val m_script: java.util.regex.Matcher
        val p_style: java.util.regex.Pattern
        val m_style: java.util.regex.Matcher
        val p_html: java.util.regex.Pattern
        val m_html: java.util.regex.Matcher
        val p_special: java.util.regex.Pattern
        val m_special: java.util.regex.Matcher
        try {
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            val regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            val regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"
            // 定义HTML标签的正则表达式
            val regEx_html = "<[^>]+>"
            // 定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            val regEx_special = "\\&[a-zA-Z]{1,10};"

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE)
            m_script = p_script.matcher(htmlStr)
            htmlStr = m_script.replaceAll("") // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE)
            m_style = p_style.matcher(htmlStr)
            htmlStr = m_style.replaceAll("") // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE)
            m_html = p_html.matcher(htmlStr)
            htmlStr = m_html.replaceAll("") // 过滤html标签
            p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE)
            m_special = p_special.matcher(htmlStr)
            htmlStr = m_special.replaceAll("") // 过滤特殊标签
            textStr = htmlStr
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return textStr// 返回文本字符串
    }
}