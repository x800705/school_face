<template>

   <div>
     <h3>首页</h3>
     <div>欢迎回来,{{admin.name}}</div>
   </div>

</template>

<script>
export default {
  name: "front",
  data(){
    return{
      admin:{

      },
    }

  },
  created(){
    this.admin = JSON.parse(sessionStorage.getItem("admin"))

  }

}
</script>

<style scoped>

</style>
