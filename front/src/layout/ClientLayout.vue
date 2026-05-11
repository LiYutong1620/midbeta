<template>
  <div class="min-h-screen bg-slate-50 font-sans text-slate-800">
    <!-- Header -->
    <header class="bg-white border-b border-slate-200 sticky top-0 z-50">
      <div class="max-w-7xl mx-auto px-6 h-16 flex items-center justify-between">
        <div class="flex items-center space-x-10">
          <div class="flex items-center cursor-pointer" @click="router.push('/')">
            <div class="w-8 h-8 bg-indigo-600 rounded-lg flex items-center justify-center mr-3 shadow-lg shadow-indigo-600/20">
              <div class="w-4 h-4 border-2 border-white rotate-45"></div>
            </div>
            <span class="text-xl font-bold text-slate-900 tracking-tight italic">NEWS HUB</span>
          </div>
          <nav class="hidden md:flex items-center space-x-6 text-[11px] font-bold uppercase tracking-widest">
            <router-link to="/" class="transition-colors" :class="route.path === '/' ? 'text-indigo-600 underline underline-offset-8' : 'text-slate-400 hover:text-slate-600'">首页</router-link>
            <router-link to="/admin" class="transition-colors text-slate-400 hover:text-slate-600">管理后台</router-link>
          </nav>
        </div>
        
        <div class="flex items-center space-x-6">
          <template v-if="token">
              <el-dropdown trigger="click">
                <span class="flex items-center cursor-pointer space-x-3 group">
                  <div class="w-8 h-8 rounded-full border border-slate-200 p-0.5 group-hover:border-indigo-400 transition-colors">
                    <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" class="w-full h-full rounded-full object-cover" />
                  </div>
                  <div class="hidden sm:block text-left">
                    <p class="text-xs font-bold text-slate-700 leading-none">{{ nickname }}</p>
                    <p class="text-[10px] text-slate-400 font-bold uppercase mt-1">{{ userStore.isAdmin ? '管理员' : '普通会员' }}</p>
                  </div>
                </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="handleLogout">
                    <span class="text-red-500 font-bold">退出登录</span>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <router-link to="/login">
              <button class="px-6 py-2 bg-indigo-600 text-white rounded-lg text-[11px] font-bold uppercase tracking-widest shadow-lg shadow-indigo-600/20 hover:bg-indigo-700 transition-all">登录</button>
            </router-link>
          </template>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-6 py-10">
      <router-view v-slot="{ Component }">
        <transition name="view-fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>

    <!-- Simple Footer -->
    <footer class="bg-white border-t border-slate-200 py-8">
      <div class="max-w-7xl mx-auto px-6 flex flex-col md:flex-row justify-between items-center text-[10px] font-bold text-slate-400 uppercase tracking-widest">
        <span>&copy; 2026 GEOMETRIC NEWS HUB. ALL RIGHTS RESERVED.</span>
        <div class="flex space-x-6 mt-4 md:mt-0">
          <a href="#" class="hover:text-indigo-600 transition-colors">Privacy Policy</a>
          <a href="#" class="hover:text-indigo-600 transition-colors">Terms of Service</a>
          <a href="#" class="hover:text-indigo-600 transition-colors">Contact Us</a>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from '@/store/user';

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const token = computed(() => userStore.token);
const nickname = computed(() => userStore.nickname);

const handleLogout = () => {
  userStore.logout();
  router.push('/login');
};

</script>

<style scoped>
.view-fade-enter-active,
.view-fade-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}

.view-fade-enter-from {
  opacity: 0;
  transform: translateY(15px);
}

.view-fade-leave-to {
  opacity: 0;
  transform: translateY(-15px);
}
</style>

