getFaceId(){
    //上传图片到图像识别
    axios.post("http://127.0.0.1:2020/face",
        formdata,{
            headers: {
                "Content-Type": "multipart/form-data"
            }}
    ).then((responds) => {
        this.id = responds.data
        if (responds.data === "Unknown") {
            this.msg = "不在人脸库中"
            this.$message.error("不在人脸库中")

        } else if (responds.data === "no_face") {
            this.msg = "检测不到人脸"
            this.$message.error("检测不到人脸")

        }
    })
}


facePay()
{
    axios.get("student/find_one/" + this.id).then(res=>{
        this.student = res.data
    })

    //通过id识别余额和信息
    axios.get("money/find/" + this.id).then((res)=>{
        this.name = res.data.student.name;
        this.user_money = res.data.money;
        if(this.money > this.user_money){
            this.msg = "余额不足，支付失败!" + "剩余余额" + res.data.money;
        }
        else{
            let param = new URLSearchParams();
            param.append("money",this.money);
            param.append("id",this.id);
            //通过id识别
            axios.post("money/pay",param).then((res)=>{
                if(res.data === 1){
                    this.msg = "本次消费" + this.money + "元" + "剩余余额" + (this.user_money - this.money)

                        this.$message.success(this.name + ",识别成功")
                        this.$message.success(this.msg)

}







//记录账单
                var date = new Date();
                var date = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + "" + date.getHours() + ":" + date.getMinutes()
                let parames = new URLSearchParams();
                parames.append("id",this.id);
                parames.append("money",this.money);
                parames.append("date",date);
                parames.append("content","消费");

                axios.post("/money/cost_stu",parames).then(res=>{
                    console.log(res)
                })

                /*食堂收入，如果今日时间不存在，创建设值为0
     如果今日时间存在，直接进行累加 */
                axios.post("/food/bill_money?money=" + this.money).then(res=>{
                    console.log(res)
                })

                //支付金额清空
                this.money = ''

            }
            else{
                this.msg = "支付失败请重试";
                this.block = this.block + 1;
            }
        })



    }
})
})

},
