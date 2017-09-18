/**
 * 自己用的JS
 */

/**
 *  扩展JQuery方法
 * */
$.fn.extend({

    //增加validate方法  返回false表示有错 返回true表示无错
    validate: function (regexString) {
        var hasError = false, elmt = $(this);

        //是容器就遍历子标签验证
        if (elmt.children().length > 0 && !elmt.is("select")) {
            elmt.find("[valitype]").each(function (index, element) {

                //如果该控件不可见,则验证通过
                if (!regexMatch($(element).attr('valitype'), $(element).val()) && $(element).is(":visible")) {
                    $(element).lightHelp("error", true);
                    hasError = true;
                } else
                    $(element).lightHelp(" ", false);
            });
        }
        //是单个元素就直接验证,如果提供了表达式就使用,否则用属性里的
        else {
            if (!regexMatch(regexString ? regexString : elmt.attr('valitype'), elmt.val())) {
                elmt.lightHelp("error", true);
                hasError = true;
            } else
                elmt.lightHelp(" ", false);
        }

        return !hasError;
    },

    //增加eachSelect方法，遍历向父级对象查询符合selector条件的对象
    eachSelect: function (selector) {
        var element = this;
        while (element[0]) {
            if ($(element).parent(selector).length > 0)
                return $(element).parent(selector);
            element = $(element).parent();
        }
        return undefined;
    },

    /**
     * 更新chosen select控件
     * @param json_map 遍历的map
     * @param content_field  map中用于展示的字段
     * @param value_field    map中用于传值的字段
     */
    updateChosen: function (json_map, content_field, value_field, default_value) {
        if (!this.is("select"))
            return;
        var select = this;
        select.removeClass();
        select.children().remove();
        select.next().remove();
        select.append("<option></option>");
        $(json_map).each(function (index, element) {
            var isSelected = default_value == eval("element." + value_field) ? "selected" : "";
            select.append("<option value='" + eval("element." + value_field) + "' " + isSelected + " >" + eval("element." + content_field) + "</option>");
        });
        select.chosen({ no_results_text: "没有查询到", search_contains: true });
    },

    //分页
    paginator: function () {
        if (!this.is("div"))
            return;

        var div = this;

        if ((!div.attr("currentPage") || div.attr("currentPage") < 1) || (!div.attr("totalPages") || div.attr("totalPages") < 1))
            return;

        var onPageClicked = div.attr("onPageClicked") ? eval(div.attr("onPageClicked")) : null;

//            function onPageClicked (e, originalEvent, type, page) {
//                alert("Page item clicked, type: " + type + " page: " + page);
//            }

        var options = {
            currentPage: div.attr("currentPage"),                                         //当前页
            totalPages: div.attr("totalPages"),                                           //总页数
            alignment: div.attr("alignment") ? div.attr("alignment") : "center",        //位置
            numberOfPages: div.attr("numberOfPages") ? div.attr("numberOfPages") : 8,   //显示多少页
            tooltipTitles: function (type, page, current) {
                return;
            },
            shouldShowPage: function (type, page, current) {
                switch (type) {
                    case "first":
                    case "prev":
                    case "next":
                    case "last":
                    default:
                        return true;
                }
            },
            itemTexts: function (type, page, current) {
                switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "尾页";
                    case "page":
                        return page;
                }
            },
            itemContainerClass: function (type, page, current) {
                return (page === current) ? "active" : "pointer-cursor";
            },
            pageUrl: function (type, page, current) {
                return div.attr("pageUrl") && page != current ? div.attr("pageUrl").replace("{page}", page) : null;  //页面跳转URL
            },
            onPageClicked: onPageClicked        //onClick事件
        };
        div.bootstrapPaginator(options);
    },

    //高亮提示
    lightHelp: function (className, showHelp) {
        $(this).eachSelect('.control-group').removeClass("success").removeClass("warning").removeClass("error");
        $(this).eachSelect('.controls').find(".help-inline").css("display", showHelp ? "inline-block" : "none");
        $(this).eachSelect('.control-group').addClass(className ? className : 'error');   //success warning error 有这三种样式
        //event.preventDefault();
    }

});

/**
 * 处理一些顺序加载时就需要调用的匿名函数
 * */
(function ($) {

    //所有的验证提示开始时不显示
    $('.help-inline').css('display', 'none');

    //为所有的包含valitype属性的对象onblur事件增加验证
    $("[valitype]").blur(function () {
        if (!regexMatch($(this).attr('valitype'), $(this).val())) {
            $(this).lightHelp("error", true);
        } else {
            $(this).lightHelp(" ", false);
        }
    });

    //改进一下datatable
    $('.datatable').each(function (index, element) {
        var lengthChange = element.hasAttribute("lengthChange") ? true : false,     //是否显示每页显示数量
            filter = element.hasAttribute("filter") ? true : false,                  //是否显示搜索框
            info = element.hasAttribute("info") ? true : false,                      //是否显示查询信息
            paginate = element.hasAttribute("paginate") ? true : false;             //是否显示分页

        var sDom = "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>";
        if (!lengthChange)
            sDom = sDom.replace("<'span6'l>", "");
        if (!filter)
            sDom = sDom.replace("<'span6'f>", "");
        if (!info)
            sDom = sDom.replace("<'span12'i>", "");
        if (!paginate)
            sDom = sDom.replace("<'span12 center'p>", "");
        sDom = sDom.replace(/<'row-fluid'r?>/g, "");

        $(element).dataTable({
            "sDom": sDom,        //"<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
            "sPaginationType": "bootstrap",
            "bPaginate": paginate,
            "bLengthChange": lengthChange,
            "bFilter": filter,
            "bSort": true,
            "bInfo": info,
            "bAutoWidth": true,
            "oLanguage": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                }
            }
        });

    });

})(jQuery);

/**
 * 处理一些在页面加载完之后才需要执行的函数
 * */
jQuery(function ($) {

    //日历控件中文。搞死要的简体中文版,对比TW版就一个关闭是繁体。。而且关闭还基本是不显示出来的
    $.datepicker.regional['zh-CN'] = {
        closeText: '关闭',
        prevText: '&#x3c;上月',
        nextText: '下月&#x3e;',
        currentText: '今天',
        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
            '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthNamesShort: ['一月', '二月', '三月', '四月', '五月', '六月',
            '七月', '八月', '九月', '十月', '十一月', '十二月'],
        dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
        dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
        weekHeader: '周',
        dateFormat: 'yy-mm-dd',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: true,
        changeYear: true,
        yearSuffix: '年'};
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);

    /*
     $.datepicker.regional['zh-TW'] = {
     closeText: '關閉',
     prevText: '&#x3C;上月',
     nextText: '下月&#x3E;',
     currentText: '今天',
     monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
     '七月', '八月', '九月', '十月', '十一月', '十二月'],
     monthNamesShort: ['一月', '二月', '三月', '四月', '五月', '六月',
     '七月', '八月', '九月', '十月', '十一月', '十二月'],
     dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
     dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
     dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
     weekHeader: '周',
     dateFormat: 'yy-mm-dd',
     firstDay: 1,
     isRTL: false,
     showMonthAfterYear: true,
     yearSuffix: '年'};
     $.datepicker.setDefaults($.datepicker.regional['zh-TW']);
     */

    //时间控件中文
    $.fn.datetimepicker.dates['zh-CN'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
        daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
        daysMin: ["日", "一", "二", "三", "四", "五", "六", "日"],
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        today: "今日"
    };

    //调用分页函数
    $('.paginator').paginator();

    //chosen控件改动一下
    $('[data-rel="chosen"],[rel="chosen"]').chosen({ no_results_text: "没有查询到", search_contains: true });

    //为IE8及以下版本支持placeholder
    var doc=document,inputs=doc.getElementsByTagName('input'),supportPlaceholder = 'placeholder'in doc.createElement('input'), placeholder = function (input) {
        var text = input.getAttribute('placeholder'), defaultValue = input.defaultValue;
        if (defaultValue == '') {
            input.value = text
        }
        input.onfocus = function () {
            if (input.value === text) {
                this.value = ''
            }
        };
        input.onblur = function () {
            if (input.value === '') {
                this.value = text
            }
        }
    };
    if (!supportPlaceholder) {
        for (var i = 0, len = inputs.length; i < len; i++) {
            var input = inputs[i], text = input.getAttribute('placeholder');
            if (input.type === 'text' && text) {
                placeholder(input)
            }
        }
    }
});


/**
 * 下面是零散的方法可供调用
 * **/

// 判断一些常用正则
function regexMatch(enumStr, input) {
    var regexEnum = {
        require: /.+/,    //非空
        phone: /^1[3|4|5|8][0-9]\d{4,8}$/,     //手机
        landline: /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,    //座机  没看,纯拷老项目
        date: /^(\d{1,4})(-|\/)(\d{2})(-|\/)(\d{2})$/,      //日期   格式类似:2013-01-01 or 2013/01/01 or 2013-01/01  网上拷的
        //同时包含手机和电话的验证
        phoneAnddianhua: /^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/,
        Email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,     //email
        equal: enumStr.match(/equal:([^,]+)/) ? enumStr.match(/equal:([^,]+)/)[1] : null,        //判断相等  传的值是控件id
        Currency: /^\d+(\.\d+)?$/,     //货币
        path: /^[a-zA-Z]:(([a-zA-Z]*)||([a-zA-Z]*\\))*$/,   //磁盘路径
        length: enumStr.match(/length:(\d+)/) ? enumStr.match(/length:(\d+)/)[1] : null
    };

    if (regexEnum.equal)
        enumStr = enumStr.replace(/equal([^,]+)/, "equal");
    if (regexEnum.length)
        enumStr = enumStr.replace(/length([^,]+)/, "length");

    var enumSplit = enumStr.split(',');

    var hasError = false;

    for (var i = 0; i < enumSplit.length; i++) {
        var regex = eval('regexEnum.' + enumSplit[i]);

        if (!regex)
            return false;

        switch (enumSplit[i]) {
            case "equal":
                $(regex.split(";")).each(function (i, args) {
                    if (args && $("#" + args).val() != input)
                        hasError = true;
                });
                break;
            case "length":
                if (input.length > regex)
                    hasError = true;
                break;
            default :
                if (!regex.test(input))
                    hasError = true;
                break;
        }
    }
    return !hasError;
}

/**
 * 遍历向父级对象查询符合selector条件的对象
 * @param element
 * @param selector
 * @returns {*|jQuery}
 */
function eachSelect(element, selector) {
    while (element[0]) {
        if ($(element).parent(selector).length > 0)
            return $(element).parent(selector);
        element = $(element).parent();
    }
    return undefined;
}

/**
 * 更新chosen select控件
 * @param select对象
 * @param json_map
 * @param content_field
 * @param value_field
 */
function updateChosen(select, json_map, content_field, value_field, default_value) {
    $(select).removeClass();
    $(select).children().remove();
    $(select).next().remove();
    $(select).append("<option></option>");
    $(json_map).each(function (index, element) {
        var isSelected = default_value == eval("element." + value_field) ? "selected" : "";
        $(select).append("<option value='" + eval("element." + value_field) + "' " + isSelected + ">" + eval("element." + content_field) + "</option>");
    });
    $(select).chosen({ no_results_text: "没有查询到", search_contains: true });
}

//改动了一下ajax2里的方法
function ajax(v_url, data, func, dataType) {
//    var url = v_url,
//        data = data,
//        func = func,
//        dataType = dataType;
    var v_response;
    $.ajax({
        async: false, //同步请求
        url: v_url, //请求地址
        //contentType:"application/x-www-form-urlencoded;charset=GBK",
        data: data, //参数
        cache: false, //设置为 false 将不会从浏览器缓存中加载请求信息
        type: "POST",
        dataType: dataType == null ? "text" : dataType,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("MyJqueryAjax Request Error!");
        },
        success: func ? func : function (req) {
            v_response = req;
        }
    });
    return v_response;
};

