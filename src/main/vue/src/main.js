import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './mock/index'
import * as echarts from 'echarts';
Vue.prototype.$echarts = echarts


Vue.config.productionTip = false

axios.defaults.baseURL = "http://localhost:8088";
axios.defaults.withCredentials = true

global.Buffer = global.Buffer || require('buffer').Buffer



Vue.use(ElementUI)
new Vue({
  render: h => h(App),
  router
}).$mount('#app')





