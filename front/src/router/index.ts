import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import { useUserStore } from '@/store/user'; 
import { ElMessage } from 'element-plus';
const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/register/Register.vue'),
    meta: { title: '注册' }
  },
  {
    path: '/',
    component: () => import('@/layout/ClientLayout.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import('@/views/home/Home.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'news/:id',
        name: 'NewsDetail',
        component: () => import('@/views/client/NewsDetail.vue'),
        meta: { title: '新闻详情' }
      }
    ]
  },
  {
    path: '/admin',
    component: () => import('@/layout/AdminLayout.vue'),
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('@/views/admin/Dashboard.vue'),
        meta: { title: '控制台' }
      },
      {
        path: 'news',
        name: 'NewsManagement',
        component: () => import('@/views/admin/news/index.vue'),
        meta: { title: '新闻管理' }
      },
      {
        path: 'category',
        name: 'CategoryManagement',
        component: () => import('@/views/admin/category/index.vue'),
        meta: { title: '分类管理' }
      },
      {
        path: 'tag',
        name: 'TagManagement',
        component: () => import('@/views/admin/tag/index.vue'),
        meta: { title: '标签管理' }
      },
      {
        path: 'comments',
        name: 'CommentManagement',
        component: () => import('@/views/admin/comments/index.vue'),
        meta: { title: '评论管理' }
      },
      {
        path: 'user',
        name: 'UserManagement',
        component: () => import('@/views/admin/user/index.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'role',
        name: 'RoleManagement',
        component: () => import('@/views/admin/role/index.vue'),
        meta: { title: '角色管理' }
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

// 路由守卫 (简单实现)
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  // 放行登录/注册
  if (to.path === '/login' || to.path === '/register') {
    return next();
  }
  // 未登录跳转登录页
  if (!token) {
    return next('/login');
  }
  // 登录后访问 /admin 需验证管理员身份
  if (to.path.startsWith('/admin')) {
    const userStore = useUserStore();
    if (!userStore.isAdmin) {
      ElMessage.error('无权访问后台');
      return next('/');
    }
  }

  next();
});

export default router;
