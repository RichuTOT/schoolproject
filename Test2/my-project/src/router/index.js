import { createRouter, createWebHistory } from 'vue-router';
import Login from '../components/Login.vue';
import Register from '../components/Register.vue';
import Dashboard from '../views/Dashboard.vue';
import PageOne from '../views/PageOne.vue';
import PageTwo from '../views/PageTwo.vue';
import PageThree from '../views/PageThree.vue';
import Content from '../views/Content.vue'; // 新添加的内容组件

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {path: '/login',name: 'Login',component: Login},
  {path: '/register',name: 'Register',component: Register},
  {path: '/dashboard',name: 'Dashboard',component: Dashboard,
    children: [
      {
        path: 'page-one', 
        name: 'PageOne',
        component: PageOne //个人中心
      },
      {
        path: 'page-two',
        name: 'PageTwo',
        component: PageTwo //搜索社团
      },
      {
        path: 'page-three',
        name: 'PageThree',
        component: PageThree
      },
      {
        path: 'content', // 新添加的内容路径
        name: 'Content',
        component: Content
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
