<template>
    <div>
        <h1>消费记录</h1>
      <router-link to="index">返回</router-link>
 <div>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="content"
        label="内容"
        width="180">
      </el-table-column>
      <el-table-column
        prop="money"
        label="金额"
        width="180">
      </el-table-column>
      <el-table-column
        prop="date"
        label="时间">
      </el-table-column>
    </el-table>

       <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="current_page"
      :page-sizes="[1,2,5,10]"
      :page-size="page_size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>



</div>
      
    </div>


    


    
</template>

  <script>
  import axios from 'axios';
    export default {
      
      data() {
        return {
          id:"",
          tableData: [],
          current_page:1,
          total:40, 
          page_size:5
        }
      },

     
      created(){  // 创建函数
        this['id'] = sessionStorage.getItem("id");
          
    axios.get("/money/count_list?id=" + this.id).then((res)=>{
      this.total = res.data;
    })
    
    axios.get("/money/list",{
         params:{
           num : this['page_size'],
           page : this['current_page'],
           id : this['id']
           
         }
       }).then((responds) =>{
          this.tableData = responds.data;
          this.total = responds.data.length;
       })

      },


      methods: {
      handleSizeChange(val) { // 分页变化
         this['page_size'] = val

            axios.get("/student/find_list",{
         params:{
           num : this['page_size'],
           page : this['current_page'],
           id : this['id']
           
         }
       }).then((responds) =>{
          this.tableData = responds.data;
       })

       
      },


      handleCurrentChange(val) { // 页数变化
        console.log(val)
       this['current_page'] = val

       
          axios.get("/student/find_list",{
         params:{
           num : this['page_size'],
           page : this['current_page'],
           id : this['id']
           
         }
       }).then((responds) =>{
          this.tableData = responds.data;
       })


     
    },

    }

    }


    
  </script>
