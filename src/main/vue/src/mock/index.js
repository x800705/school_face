import Mock from 'mockjs'

Mock.mock("student/login",{
    "code":200,
    "msg":"登录成功",
    "data|10":[{
        "img":"@image('200x100','#ffcc33','#FFF','png','happy')",
        "name":"@cname",
        "money|1-100":1,
        "time":"@datetime"
    }]
})

Mock.mock("food/menu",
    //返回10条随机数据
    Mock.mock({
        "data|10":[
            {
                "id|1-10000":1,
                "imgSrc":"@image('200x100','#ffcc33','#FFF','png','happy')",
                "name":"@cname",//随即中文名
                "money|1-100":1

            }
        ]
    })
)

Mock.mock("food/del/",{
    "data":200
})

Mock.mock("food/bill",{
    "data|10":[{
        "id|1-10000":1,
        "content":"@cname",
        "money|1-100":1,
        "state":"待支付",}]


})


Mock.mock("food/money",{
    "data|10":[{
        "date":"@date",
        "money|1-1000":0}]


})

Mock.mock("food/rank",{
    "data|10":[{
        "name":"@cname",
        "num|1-10":0}]


})

