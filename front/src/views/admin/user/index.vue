<template>
  <div class="space-y-6">
    <div class="flex justify-between items-center">
      <div>
        <h1 class="text-2xl font-bold text-slate-800">用户管理</h1>
        <p class="text-slate-500 mt-1">管理后台登录用户及其权限系统</p>
      </div>
      <button 
        @click="handleAdd"
        class="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-lg flex items-center transition-colors shadow-lg shadow-indigo-600/30"
      >
        <UserPlus class="w-4 h-4 mr-2" />
        新增用户
      </button>
    </div>

    <div class="bg-white p-4 rounded-xl shadow-sm border border-slate-200">
      <el-form :inline="true" :model="queryParams" class="flex flex-wrap gap-4">
        <el-form-item label="用户账号">
          <el-input v-model="queryParams.username" placeholder="请输入账号" clearable @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="用户状态" clearable class="w-32">
            <el-option label="正常" :value="1" />
            <el-option label="停用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">搜索</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
      <el-table v-loading="loading" :data="userList" style="width: 100%">
        <el-table-column prop="id" label="编号" width="80" />
        <el-table-column prop="username" label="账号" min-width="120" />
        <el-table-column prop="nickname" label="昵称" min-width="120" />
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-switch
              v-model="row.status"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="lastLoginAt" label="最后登录" width="180" />
        <el-table-column prop="createdAt" label="创建时间" width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <div class="flex space-x-3">
              <button @click="handleEdit(row)" class="text-indigo-600 hover:text-indigo-800 font-medium text-sm">编辑</button>
              <button @click="handleResetPwd(row)" class="text-amber-600 hover:text-amber-700 font-medium text-sm">重置密码</button>
              <template v-if="row.id !== 1">
                <button @click="handleDelete(row)" class="text-rose-600 hover:text-rose-800 font-medium text-sm">删除</button>
              </template>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="p-4 border-t border-slate-100 flex justify-end">
        <el-pagination
          v-model:current-page="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          :total="total"
          layout="total, sizes, prev, pager, next"
          @size-change="getList"
          @current-change="getList"
        />
      </div>
    </div>

    <el-dialog
      v-model="dialogVisible"
      :title="form.id ? '编辑用户' : '新增用户'"
      width="500px"
      destroy-on-close
    >
      <el-form :model="form" :rules="rules" ref="userFormRef" label-width="80px">
        <el-form-item label="登录账号" prop="username">
          <el-input v-model="form.username" placeholder="请输入登录账号" :disabled="!!form.id" />
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="登录密码" prop="passwordHash" v-if="!form.id">
          <el-input v-model="form.passwordHash" placeholder="请输入密码" type="password" show-password />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.roleId" placeholder="请选择角色" class="w-full">
            <el-option
              v-for="item in roleOptions"
              :key="item.id"
              :label="item.roleName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="1">正常</el-radio>
            <el-radio :value="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="flex justify-end space-x-3">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm" :loading="submitLoading">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue';
import { UserPlus } from 'lucide-vue-next';
import { ElMessage, ElMessageBox } from 'element-plus';
import request from '@/utils/request';

const loading = ref(false);
const total = ref(0);
const userList = ref<any[]>([]);
const roleOptions = ref<any[]>([]);
const dialogVisible = ref(false);
const submitLoading = ref(false);
const userFormRef = ref();

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  username: undefined as string | undefined,
  status: undefined as number | undefined,
});

const form = ref<any>({});

const rules = {
  username: [{ required: true, message: '账号不能为空', trigger: 'blur' }],
  nickname: [{ required: true, message: '昵称不能为空', trigger: 'blur' }],
  passwordHash: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
};

const getList = async () => {
  loading.value = true;
  try {
    const res: any = await request.get('/system/user/list', { params: queryParams });
    userList.value = res.rows || [];
    total.value = res.total || 0;
  } finally {
    loading.value = false;
  }
};

const getRoleList = async () => {
  try {
    const res: any = await request.get('/system/role/list', { params: { pageNum: 1, pageSize: 100 } });
    roleOptions.value = res.rows || [];
  } catch (e) {
    console.error('Failed to get roles:', e);
  }
};

const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
};

const resetQuery = () => {
  queryParams.username = undefined;
  queryParams.status = undefined;
  handleQuery();
};

const handleAdd = () => {
  form.value = {
    id: undefined,
    username: '',
    nickname: '',
    passwordHash: '',
    roleId: undefined,
    status: 1,
    remark: ''
  };
  dialogVisible.value = true;
};

const handleEdit = async (row: any) => {
  const res: any = await request.get(`/system/user/${row.id}`);
  form.value = res.data;
  dialogVisible.value = true;
};

const handleStatusChange = async (row: any) => {
  const text = row.status === 1 ? '启用' : '停用';
  try {
    await ElMessageBox.confirm(`确认要"${text}""${row.username}"用户吗？`);
    await request.put('/system/user', { id: row.id, status: row.status });
    ElMessage.success(`${text}成功`);
  } catch {
    row.status = row.status === 0 ? 1 : 0;
  }
};

const handleResetPwd = (row: any) => {
  ElMessageBox.prompt(`请输入"${row.username}"的新密码`, '重置密码', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /^.{5,20}$/,
    inputErrorMessage: '密码长度需在5到20位之间'
  }).then(async ({ value }) => {
    await request.put('/system/user', { id: row.id, passwordHash: value });
    ElMessage.success('密码重置成功');
  });
};

const handleDelete = (row: any) => {
  ElMessageBox.confirm(`是否确认删除用户"${row.username}"？`).then(async () => {
    await request.delete(`/system/user/${row.id}`);
    getList();
    ElMessage.success('删除成功');
  });
};

const submitForm = async () => {
  userFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      submitLoading.value = true;
      try {
        if (form.value.id) {
          await request.put('/system/user', form.value);
        } else {
          await request.post('/system/user', form.value);
        }
        ElMessage.success('操作成功');
        dialogVisible.value = false;
        getList();
      } finally {
        submitLoading.value = false;
      }
    }
  });
};

onMounted(() => {
  getList();
  getRoleList();
});
</script>
