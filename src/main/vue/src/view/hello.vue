<template>
  <Happy></Happy>
</template>

<script>
import Happy from "../components/happy.vue"
export default {
  name: "hello",
  components:{
    Happy
  },
  methods:{
    yes(){
      alert("好快乐")
    },

  }
}
</script>

<style scoped>

</style>
