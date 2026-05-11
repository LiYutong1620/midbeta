<template>
  <div class="space-y-6">
    <div class="flex justify-between items-center">
      <div>
        <h1 class="text-2xl font-bold text-slate-800">角色管理</h1>
        <p class="text-slate-500 mt-1">管理系统角色及其功能权限</p>
      </div>
      <button 
        @click="handleAdd"
        class="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-lg flex items-center transition-colors shadow-lg shadow-indigo-600/30"
      >
        <ShieldPlus class="w-4 h-4 mr-2" />
        新增角色
      </button>
    </div>

    <div class="bg-white p-4 rounded-xl shadow-sm border border-slate-200">
      <el-form :inline="true" :model="queryParams" class="flex flex-wrap gap-4">
        <el-form-item label="角色名称">
          <el-input v-model="queryParams.roleName" placeholder="请输入角色名称" clearable @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="权限字符">
          <el-input v-model="queryParams.roleCode" placeholder="请输入权限字符" clearable @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">搜索</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
      <el-table v-loading="loading" :data="roleList" style="width: 100%">
        <el-table-column prop="id" label="编号" width="80" />
        <el-table-column prop="roleName" label="角色名称" min-width="150" />
        <el-table-column prop="roleCode" label="权限字符" min-width="150" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <div class="flex space-x-3">
              <button @click="handleEdit(row)" class="text-indigo-600 hover:text-indigo-800 font-medium text-sm">编辑</button>
              <button @click="handleDelete(row)" class="text-rose-600 hover:text-rose-800 font-medium text-sm">删除</button>
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
      :title="form.id ? '编辑角色' : '新增角色'"
      width="500px"
      destroy-on-close
    >
      <el-form :model="form" :rules="rules" ref="roleFormRef" label-width="80px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="权限字符" prop="roleCode">
          <el-input v-model="form.roleCode" placeholder="请输入权限字符" />
          <p class="text-[10px] text-slate-400 mt-1 leading-tight">如：admin, common 等</p>
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
import { ShieldPlus } from 'lucide-vue-next';
import { ElMessage, ElMessageBox } from 'element-plus';
import request from '@/utils/request';

const loading = ref(false);
const total = ref(0);
const roleList = ref([]);
const dialogVisible = ref(false);
const submitLoading = ref(false);
const roleFormRef = ref();

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  roleName: undefined as string | undefined,
  roleCode: undefined as string | undefined,
});

const form = ref<any>({});

const rules = {
  roleName: [{ required: true, message: '角色名称不能为空', trigger: 'blur' }],
  roleCode: [{ required: true, message: '权限字符不能为空', trigger: 'blur' }],
};

const getList = async () => {
  loading.value = true;
  try {
    const res: any = await request.get('/system/role/list', { params: queryParams });
    roleList.value = res.rows || [];
    total.value = res.total || 0;
  } finally {
    loading.value = false;
  }
};

const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
};

const resetQuery = () => {
  queryParams.roleName = undefined;
  queryParams.roleCode = undefined;
  handleQuery();
};

const handleAdd = () => {
  form.value = {
    id: undefined,
    roleName: '',
    roleCode: '',
    remark: ''
  };
  dialogVisible.value = true;
};

const handleEdit = async (row: any) => {
  const res: any = await request.get(`/system/role/${row.id}`);
  form.value = res.data;
  dialogVisible.value = true;
};

const handleDelete = (row: any) => {
  ElMessageBox.confirm(`是否确认删除角色"${row.roleName}"？`).then(async () => {
    await request.delete(`/system/role/${row.id}`);
    getList();
    ElMessage.success('删除成功');
  });
};

const submitForm = async () => {
  roleFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      submitLoading.value = true;
      try {
        if (form.value.id) {
          await request.put('/system/role', form.value);
        } else {
          await request.post('/system/role', form.value);
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
});
</script>
