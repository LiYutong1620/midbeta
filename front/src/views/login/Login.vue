<template>
  <div class="min-h-screen flex items-center justify-center bg-slate-50 py-12 px-4 sm:px-6 lg:px-8 font-sans">
    <div class="max-w-md w-full space-y-8 bg-white p-10 rounded-2xl border border-slate-200 shadow-xl relative overflow-hidden">
      <!-- Decorative geometric element -->
      <div class="absolute -top-10 -right-10 w-32 h-32 bg-indigo-600/5 rounded-full blur-3xl"></div>
      
      <div class="relative">
        <div class="w-12 h-12 bg-indigo-600 rounded-xl flex items-center justify-center mb-6 shadow-lg shadow-indigo-600/20">
          <div class="w-6 h-6 border-2 border-white rotate-45"></div>
        </div>
        <h2 class="text-3xl font-bold text-slate-900 tracking-tight">
          欢迎回来
        </h2>
        <p class="mt-2 text-sm text-slate-400 font-medium uppercase tracking-wider">
          登录您的账户
        </p>
      </div>

      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        label-position="top"
        class="mt-8 space-y-5 relative"
        @keyup.enter="handleLogin"
      >
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            class="geometric-input"
          />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            show-password
            placeholder="请输入密码"
            class="geometric-input"
          />
        </el-form-item>

        <div class="flex items-center justify-between">
          <el-checkbox v-model="rememberMe" label="记住我" />
          <router-link
            to="/register"
            class="font-bold text-indigo-600 hover:text-indigo-700 text-xs uppercase tracking-widest transition-colors"
          >
            立即注册
          </router-link>
        </div>

        <div class="pt-2">
          <button
            :disabled="loading"
            class="w-full h-12 bg-indigo-600 hover:bg-indigo-700 text-white rounded-xl font-bold uppercase tracking-widest shadow-lg shadow-indigo-600/20 transition-all disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center space-x-2"
            @click="handleLogin"
          >
            <span v-if="!loading">登 录</span>
            <el-icon v-else class="animate-spin"><Loader2 /></el-icon>
            <ArrowRight v-if="!loading" class="w-4 h-4 ml-2" />
          </button>
        </div>
      </el-form>
      
      <div class="text-center mt-6">
        <p class="text-[10px] text-slate-400 font-bold uppercase tracking-widest">
          &copy; 2026 新闻资讯发布与推荐系统
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, type FormInstance } from 'element-plus';
import { ArrowRight, Loader2 } from 'lucide-vue-next';
import { useUserStore } from '@/store/user';

const router = useRouter();
const userStore = useUserStore();
const loginFormRef = ref<FormInstance>();
const loading = ref(false);
const rememberMe = ref(false);

const loginForm = reactive({
  username: '',
  password: '',
});

const loginRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
};

const handleLogin = async () => {
  if (!loginFormRef.value) return;
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      try {
        // 这里调用 userStore.login，内部会存储 token 和 userInfo
        await userStore.login(loginForm);
        ElMessage.success('登录成功');
        
        // 根据角色跳转
        if (userStore.isAdmin) {
          router.push('/admin');
        } else {
          router.push('/');
        }
      } catch (error) {
        console.error('Login failed:', error);
      } finally {
        loading.value = false;
      }
    }
  });
};
</script>

<style>
.geometric-input .el-input__wrapper {
  background-color: #f8fafc !important;
  border: 1px solid #e2e8f0 !important;
  box-shadow: none !important;
  border-radius: 10px !important;
  padding: 8px 12px !important;
}

.geometric-input .el-input__wrapper.is-focus {
  border-color: #4f46e5 !important;
  background-color: #fff !important;
}

.el-form-item__label {
  font-size: 10px !important;
  font-weight: 700 !important;
  text-transform: uppercase !important;
  letter-spacing: 0.1em !important;
  color: #64748b !important;
  margin-bottom: 4px !important;
}
</style>
