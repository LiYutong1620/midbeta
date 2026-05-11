import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import request from '@/utils/request';

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '');
  const userInfo = ref<any>({});

  const isLoggedIn = computed(() => !!token.value);
  const nickname = computed(() => userInfo.value.nickname || userInfo.value.nickName ||userInfo.value.username || '用户');
  const isAdmin = computed(() => {
    // 假设 roleId=1 是管理员；如果 User 有 roleCode 字段可改为 userInfo.value.roleCode === 'admin'
    return userInfo.value.roleId == 1;
  });

  const login = async (loginData: { username: string; password: string }) => {
    const res: any = await request.post('/login', loginData);
    const t = res.token || res.data?.token;
    const u = res.user || res.data?.user || {};
    if (t) {
      token.value = t;
      localStorage.setItem('token', t);
    }
    // 保存用户信息
    setUserInfo(u);
    return u;
  };

  const getUserInfo = async () => {
    // 可按需实现，当前已有缓存
  };

  const setUserInfo = (info: any) => {
    userInfo.value = info;
    localStorage.setItem('userInfo', JSON.stringify(info));
  };

  const loadCachedUserInfo = () => {
    try {
      const cached = localStorage.getItem('userInfo');
      if (cached) {
        userInfo.value = JSON.parse(cached);
      }
    } catch (e) {}
  };

  const logout = () => {
    token.value = '';
    userInfo.value = {};
    localStorage.removeItem('token');
    localStorage.removeItem('userInfo');
  };

  loadCachedUserInfo();

  return {
    token,
    userInfo,
    isLoggedIn,
    nickname,
    isAdmin,
    login,
    getUserInfo,
    setUserInfo,
    logout,
  };
});
