<!DOCTYPE html>
<html lang="zh">
<head>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <meta charset="UTF-8">
    <title>信息科技集团-同学信息收集</title>
</head>

<body>
<div class="panel panel-default" style="padding:8px;">
    <!-- Default panel contents -->
    <div class="panel-heading">信息科技集团-同学信息</div>
    <div class="panel-body">
        <p>
            各位同学，大家好，由于校友信息是学校就业处、教务处、档案馆等部门数据整理的校友信息数据，而经过多年的专业整合，数据在统计过程中难免有缺失、重复等情况，根据学校对校友工作的要求，需要重新采集完善的校友信息，麻烦各位填一下表格信息，谢谢！</p>
    </div>
    <div id="stu-table-result">
    </div>
    <script id="stu-table-template" type="application/xml">
        <!-- Table -->
        <table class="table">
            <thead>
                <tr>
                    <th style="min-width:100px;">学号</th>
                    <th style="min-width:60px;">姓名</th>
                    <th style="min-width:50px;">性别</th>
                    <th style="min-width:100px;">联系电话</th>
                    <th style="min-width:100px;">电子邮箱</th>
                    <th>联系地址</th>
                    <th>工作单位</th>
                    <th>工作职位</th>
                    <th style="min-width:50px;">行业</th>
                    <th style="min-width:100px;">QQ</th>
                    <th style="min-width:50px;">学历</th>
                    <th>备注</th>
                </tr>
            </thead>
            <tbody>
                {{ for( var i=0; i< data.length; i++) { }}
                <tr stu-id="{{= data[i].id }}">
                    <td>{{= data[i].stuNo }}</td>
                    <td>{{= data[i].stuName }}</td>
                    <td>{{= data[i].sex }}</td>
                    <td>{{= data[i].phone }}</td>
                    <td>{{= data[i].email }}</td>
                    <td>{{= data[i].address }}</td>
                    <td>{{= data[i].company }}</td>
                    <td>{{= data[i].job }}</td>
                    <td>{{= data[i].industry }}</td>
                    <td>{{= data[i].qq }}</td>
                    <td>{{= data[i].education }}</td>
                    <td>{{= data[i].remark }}</td>
                </tr>
                {{ } }}
            </tbody>
        </table>
    </script>

    <script id="stu-modify-template" type="application/xml">

        <div>
            <form method="post" action="/student/{{=id}}">
                <div class="input-group">
                    <span class="input-group-addon">学号</span>
                    <input type="text" class="form-control" placeholder="学号" name="stuNo" value="{{= stuNo}}">

                    <span class="input-group-addon">姓名</span>
                    <input type="text" class="form-control" placeholder="姓名" name="stuName" value="{{= stuName}}">

                    <span class="input-group-addon">学历</span>
                    <input type="text" class="form-control" placeholder="学历" name="education" value="{{= education}}">
                </div>

                <div class="input-group">

                    <span class="input-group-addon">联系电话</span>
                    <input type="text" class="form-control" placeholder="联系电话" name="phone" value="{{= phone}}">

                    <span class="input-group-addon">联系邮箱</span>
                    <input type="text" class="form-control" placeholder="联系邮箱" name="email" value="{{= email}}">

                </div>

                <div class="input-group">

                    <span class="input-group-addon">QQ</span>
                    <input type="text" class="form-control" placeholder="QQ号" name="qq" value="{{= qq}}">

                    <span class="input-group-addon">联系地址</span>
                    <input type="text" class="form-control" placeholder="联系地址" name="address" value="{{= address}}">

                </div>

                <div class="input-group">

                    <span class="input-group-addon">工作单位</span>
                    <input type="text" class="form-control" placeholder="工作单位" name="company" value="{{= company}}">

                    <span class="input-group-addon">行业</span>
                    <input type="text" class="form-control" placeholder="行业" name="industry" value="{{= industry}}">

                    <span class="input-group-addon">工作职位</span>
                    <input type="text" class="form-control" placeholder="工作职位" name="job" value="{{= job}}">

                </div>
                <div class="input-group">

                    <span class="input-group-addon">备注</span>
                    <input type="text" class="form-control" placeholder="备注" name="remark" value="{{= remark}}">

                </div>
                <button type="submit" class="btn btn-default" aria-label="Left Align">
                    提交
                </button>
            </form>
        </div>

    </script>
</div>

<script src="https://code.jquery.com/jquery-3.2.0.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>

<script src="/ejs.js"></script>

<script type="text/javascript">
    $(function () {
        ejs.open = '{{';
        ejs.close = '}}';
        var tableTemplate = ejs.compile($('#stu-table-template').html());
        var modifyTemplate = ejs.compile($('#stu-modify-template').html());

        $.get('/students?reqdate=' + new Date().getTime(), {}, function (result) {
            var html = tableTemplate({data: result});
            $('#stu-table-result').html(html);
        });

        $(document).on('dblclick', '#stu-table-result tbody tr', function () {
            var stuId = $(this).attr('stu-id');
            var url = 'student/' + stuId + '?reqdate=' + new Date().getTime();
            $.get(url, {}, function (result) {
                var html = modifyTemplate(result);
                $('#stu-table-result').html(html);
            });
        });
    });

</script>
</body>
</html>