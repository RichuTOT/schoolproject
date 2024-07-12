import { createRouter, createWebHistory } from 'vue-router';
import Login from '../components/Login.vue';
import Register from '../components/Register.vue';
import Dashboard from '../views/Dashboard.vue';
import PageOne from '../views/PageOne.vue';
import PageTwo from '../views/PageTwo.vue';
import PageThree from '../views/PageThree.vue';
import Content from '../views/Content.vue'; // 新添加的内容组件
import PageFour from '../views/PageFour.vue';
import PageFive from '../views/PageFive.vue';
import AdminDashboard from '../views/AdminDashboard.vue';
import ClubApproval from '../views/ClubApproval.vue';
import ActivityApproval from '../views/ActivityApproval.vue';
import Statistics from '../views/Statistics.vue';
import ClubManagement from '../views/ClubManagement.vue';


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
        path: 'page-four',
        name: 'PageFour',
        component: PageFour //社团聊天
      },
      {
        path: 'page-five',
        name: 'PageFive',
        component: PageFive //创建社团
      },

      {
        path: 'content', // 新添加的内容路径
        name: 'Content',
        component: Content
      }
    ]
  },
  {
    path: '/admin-dashboard', name: 'AdminDashboard', component: AdminDashboard,
    children: [
      { path: 'club-approval', name: 'ClubApproval', component: ClubApproval },
      { path: 'activity-approval', name: 'ActivityApproval', component: ActivityApproval },
      { path: 'statistics', name: 'Statistics', component: Statistics },
      { path: 'club-management', name: 'ClubManagement', component: ClubManagement }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
