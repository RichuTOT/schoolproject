import { createApp } from 'vue';
import App from './App.vue';
import ElementPlus from 'element-plus';
import { store } from './store';
import router from './router';
import axios from 'axios';
import 'element-plus/dist/index.css';
import '@fortawesome/fontawesome-free/css/all.css';
import * as icons from '@element-plus/icons-vue';

// 设置 Axios 基础 URL
axios.defaults.baseURL = 'http://localhost:8088';

// 创建 Vue 应用
const app = createApp(App);

// 使用 Element Plus 和 Vue Router
app.use(ElementPlus);
app.use(router);

// 注册 Element Plus 图标
Object.keys(icons).forEach(key => {
  app.component(key, icons[key]);
});

// 将 Axios 和 Vuex 存储注册为全局属性
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$store = store;

// 挂载应用
app.mount('#app');
