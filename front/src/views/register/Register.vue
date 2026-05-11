<template>
  <div class="min-h-screen flex items-center justify-center bg-slate-50 py-12 px-4 sm:px-6 lg:px-8 font-sans">
    <div class="max-w-md w-full space-y-8 bg-white p-10 rounded-2xl border border-slate-200 shadow-xl relative overflow-hidden">
      <!-- Decorative geometric element -->
      <div class="absolute -top-10 -right-10 w-32 h-32 bg-indigo-600/5 rounded-full blur-3xl"></div>
      
      <div class="relative">
        <div class="flex items-center space-x-3 mb-6">
          <div class="w-10 h-10 bg-indigo-600 rounded-xl flex items-center justify-center shadow-lg shadow-indigo-600/20">
            <div class="w-5 h-5 border-2 border-white rotate-45"></div>
          </div>
          <span class="text-xs font-bold text-slate-400 uppercase tracking-widest">创建账户</span>
        </div>
        <h2 class="text-2xl font-bold text-slate-900 tracking-tight">
          新用户注册
        </h2>
        <p class="mt-2 text-sm text-slate-500 font-medium italic">
          加入我们的新闻资讯社区。
        </p>
      </div>

      <el-form
        ref="registerFormRef"
        :model="registerForm"
        :rules="registerRules"
        label-position="top"
        class="mt-8 space-y-4 relative"
      >
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="registerForm.username"
            placeholder="请输入用户名"
            class="geometric-input"
          />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="registerForm.password"
            type="password"
            show-password
            placeholder="请输入密码"
            class="geometric-input"
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="registerForm.confirmPassword"
            type="password"
            show-password
            placeholder="请再次输入密码"
            class="geometric-input"
          />
        </el-form-item>

        <div class="pt-6">
          <button
            :disabled="loading"
            class="w-full h-12 bg-indigo-600 hover:bg-indigo-700 text-white rounded-xl font-bold uppercase tracking-widest shadow-lg shadow-indigo-600/20 transition-all flex items-center justify-center space-x-2 disabled:opacity-50"
            @click="handleRegister"
          >
            <span v-if="!loading">立 即 注 册</span>
            <el-icon v-else class="animate-spin text-white"><Loader2 /></el-icon>
          </button>
        </div>

        <div class="text-center mt-6">
          <router-link
            to="/login"
            class="font-bold text-slate-400 hover:text-indigo-600 text-xs uppercase tracking-widest transition-colors"
          >
            已有账号？返回登录界面
          </router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, type FormInstance } from 'element-plus';
import { Loader2 } from 'lucide-vue-next';
import request from '@/utils/request';

const router = useRouter();
const registerFormRef = ref<FormInstance>();
const loading = ref(false);

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: '',
});

const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== registerForm.password) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};

const registerRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 位', trigger: 'blur' }
  ],
  confirmPassword: [
    { validator: validatePass2, trigger: 'blur' }
  ],
};

const handleRegister = async () => {
  if (!registerFormRef.value) return;
  
  await registerFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      try {
        await request.post('/register', {
          username: registerForm.username,
          passwordHash: registerForm.password
        });
        ElMessage.success('注册成功，请登录');
        router.push('/login');
      } catch (error) {
        console.error('Registration failed:', error);
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
