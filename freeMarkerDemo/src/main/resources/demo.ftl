<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<#--我是注释我不做输出 -->
${name},你好，${message}
<#assign linkman="露娜"/>
${linkman}
<#assign man={"name":"张良","age":25} />
${man.name}
${man.age}
<#include "head.ftl"/>
<#if name="官婉儿">成功
<#else>失败</#if>
<#list list as li >
   ${li_index} ${li}<br>
</#list>
总共${list?size}条记录
<#assign person="{'name':'lzl','age':'25'}"/>
<#assign data=person?eval/>
${data.name}
${data.age}

${today?date}
${today?time}
${today?datetime}
${today?string("yyyy-MM-dd HH:mm:ss")}
${num?c}
<#if aaa??>llll
${aaa}</#if>
${ssa!"sss"}
<#if (d>=25) >
    sssa</#if>
</body>
</html>