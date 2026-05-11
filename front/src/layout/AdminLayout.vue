<template>
  <div class="flex h-screen bg-slate-100 overflow-hidden font-sans text-slate-800">
    <!-- Left Sidebar -->
    <aside class="w-64 bg-slate-900 flex-shrink-0 flex flex-col">
      <div class="h-16 flex items-center px-6 border-b border-slate-800 bg-slate-900">
        <div class="w-8 h-8 bg-indigo-500 rounded-lg flex items-center justify-center mr-3 shadow-lg shadow-indigo-500/20">
          <div class="w-4 h-4 border-2 border-white rotate-45"></div>
        </div>
        <span class="text-white font-bold tracking-tight text-lg">新闻中心 v1.0</span>
      </div>
      
      <nav class="flex-1 py-6 px-4 space-y-1 overflow-y-auto">
        <router-link 
          to="/admin" 
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200"
          :class="route.path === '/admin' ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-900/40' : 'text-slate-400 hover:bg-slate-800'"
        >
          <LayoutDashboard class="w-5 h-5 mr-3" :class="route.path === '/admin' ? 'text-white/80' : 'text-slate-500'" />
          <span class="text-sm font-medium uppercase tracking-wider">控制台</span>
        </router-link>

        <div class="pt-4 pb-2 px-4">
          <span class="text-[10px] font-bold text-slate-600 uppercase tracking-widest">内容管理</span>
        </div>

        <router-link 
          to="/admin/news" 
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200"
          :class="route.path === '/admin/news' ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-900/40' : 'text-slate-400 hover:bg-slate-800'"
        >
          <Newspaper class="w-5 h-5 mr-3" :class="route.path === '/admin/news' ? 'text-white/80' : 'text-slate-500'" />
          <span class="text-sm">新闻资讯</span>
        </router-link>

        <router-link 
          to="/admin/category" 
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200"
          :class="route.path === '/admin/category' ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-900/40' : 'text-slate-400 hover:bg-slate-800'"
        >
          <Layers class="w-5 h-5 mr-3" :class="route.path === '/admin/category' ? 'text-white/80' : 'text-slate-500'" />
          <span class="text-sm">分类管理</span>
        </router-link>

        <router-link 
          to="/admin/tag" 
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200"
          :class="route.path === '/admin/tag' ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-900/40' : 'text-slate-400 hover:bg-slate-800'"
        >
          <Tags class="w-5 h-5 mr-3" :class="route.path === '/admin/tag' ? 'text-white/80' : 'text-slate-500'" />
          <span class="text-sm">标签管理</span>
        </router-link>

        <router-link 
          to="/admin/comments" 
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200"
          :class="route.path === '/admin/comments' ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-900/40' : 'text-slate-400 hover:bg-slate-800'"
        >
          <MessageSquare class="w-5 h-5 mr-3" :class="route.path === '/admin/comments' ? 'text-white/80' : 'text-slate-500'" />
          <span class="text-sm">评论审核</span>
        </router-link>

        <div class="pt-4 pb-2 px-4">
          <span class="text-[10px] font-bold text-slate-600 uppercase tracking-widest">系统设置</span>
        </div>

        <router-link 
          to="/admin/user" 
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200"
          :class="route.path === '/admin/user' ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-900/40' : 'text-slate-400 hover:bg-slate-800'"
        >
          <Users class="w-5 h-5 mr-3" :class="route.path === '/admin/user' ? 'text-white/80' : 'text-slate-500'" />
          <span class="text-sm">用户中心</span>
        </router-link>

        <router-link 
          to="/admin/role" 
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200"
          :class="route.path === '/admin/role' ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-900/40' : 'text-slate-400 hover:bg-slate-800'"
        >
          <User class="w-5 h-5 mr-3" :class="route.path === '/admin/role' ? 'text-white/80' : 'text-slate-500'" />
          <span class="text-sm">角色管理</span>
        </router-link>

        <router-link 
          to="/admin/settings" 
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200"
          :class="route.path === '/admin/settings' ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-900/40' : 'text-slate-400 hover:bg-slate-800'"
        >
          <Settings class="w-5 h-5 mr-3" :class="route.path === '/admin/settings' ? 'text-white/80' : 'text-slate-500'" />
          <span class="text-sm">系统配置</span>
        </router-link>
      </nav>

      <div class="p-6 border-t border-slate-800">
        <div class="bg-slate-800/50 rounded-lg p-3">
          <div class="text-[10px] text-slate-500 uppercase tracking-widest mb-1">接口状态</div>
          <div class="flex items-center text-[11px] text-emerald-400 font-mono">
            <span class="w-2 h-2 bg-emerald-400 rounded-full mr-2 animate-pulse"></span>
            CONNECTED: 8888
          </div>
        </div>
      </div>
    </aside>

    <!-- Main Content Area -->
    <div class="flex-1 flex flex-col min-w-0 overflow-hidden">
      <!-- Header -->
      <header class="h-16 bg-white border-b border-slate-200 flex items-center justify-between px-8 z-10 shrink-0">
        <div class="flex items-center">
          <nav class="flex items-center space-x-2 text-sm text-slate-400 font-medium">
            <span class="hover:text-slate-600 cursor-pointer" @click="router.push('/admin')">后台</span>
            <span class="text-slate-300">/</span>
            <span class="text-slate-800">{{ routeTitle }}</span>
          </nav>
        </div>
        
        <div class="flex items-center space-x-6">
          <div class="flex items-center space-x-3">
            <div class="w-8 h-8 rounded-full bg-slate-100 flex items-center justify-center border border-slate-200 text-slate-600">
              <User class="w-4 h-4" />
            </div>
            <div class="hidden sm:block text-left">
              <p class="text-xs font-bold text-slate-800 leading-none">{{ nickname }}</p>
              <p class="text-[10px] text-slate-400 uppercase font-bold mt-1">管理员</p>
            </div>
          </div>
          <div class="h-4 w-[1px] bg-slate-200"></div>
          <el-dropdown trigger="click">
            <button class="text-xs text-slate-400 font-bold uppercase tracking-widest hover:text-indigo-600 transition-colors focus:outline-none">菜单</button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="router.push('/')">回到首页</el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">
                  <span class="text-red-500 font-bold">安全退出</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>

      <!-- Page Body -->
      <main class="flex-1 overflow-y-auto p-8">
        <router-view v-slot="{ Component }">
          <transition name="page-fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { 
  LayoutDashboard, 
  Newspaper, 
  Users, 
  Settings, 
  List as ListIcon, 
  User,
  MessageSquare,
  Layers,
  Tags
} from 'lucide-vue-next';
import { useUserStore } from '@/store/user';

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

const routeTitle = computed(() => route.meta.title || '系统管理');
const nickname = computed(() => userStore.nickname);

const handleLogout = () => {
  userStore.logout();
  router.push('/login');
};
</script>

<style scoped>
.page-fade-enter-active,
.page-fade-leave-active {
  transition: all 0.25s ease-out;
}

.page-fade-enter-from {
  opacity: 0;
  transform: translateY(10px);
}

.page-fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>

