import { createApp } from 'vue';
import App from './App.vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import router from './router';
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8088';

export default axios;

const app = createApp(App);
app.use(ElementPlus);
app.use(router);
app.config.globalProperties.$axios = axios;
app.mount('#app');
