import { createRouter, createWebHistory } from 'vue-router';
import Login from '../components/Login.vue';
import Register from '../components/Register.vue';
import Dashboard from '../views/Dashboard.vue'; // 引入新的页面组件

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard // 新的页面路由配置
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
