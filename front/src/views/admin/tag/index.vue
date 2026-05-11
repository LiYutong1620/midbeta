<template>
  <div class="space-y-6">
    <div class="flex justify-between items-center">
      <div>
        <h1 class="text-2xl font-bold text-slate-800">标签管理</h1>
        <p class="text-slate-500 mt-1">管理新闻关联的标签，便于多维度搜索</p>
      </div>
      <button 
        @click="handleAdd"
        class="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-lg flex items-center transition-colors shadow-lg shadow-indigo-600/30"
      >
        <Plus class="w-4 h-4 mr-2" />
        新增标签
      </button>
    </div>

    <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
      <el-table v-loading="loading" :data="tagList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="tagName" label="标签名称">
          <template #default="{ row }">
            <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-indigo-100 text-indigo-800">
              # {{ row.tagName }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="200" />
        <el-table-column label="操作" width="150" align="right">
          <template #default="{ row }">
            <div class="flex justify-end space-x-2">
              <button @click="handleEdit(row)" class="text-indigo-600 hover:text-indigo-800 font-medium text-sm">编辑</button>
              <button @click="handleDelete(row)" class="text-rose-600 hover:text-rose-800 font-medium text-sm">删除</button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- Edit Dialog -->
    <el-dialog
      v-model="dialogVisible"
      :title="form.id ? '编辑标签' : '新增标签'"
      width="400px"
    >
      <el-form :model="form" label-position="top">
        <el-form-item label="标签名称" required>
          <el-input v-model="form.tagName" placeholder="输入标签名，如：科技、民生" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="flex justify-end space-x-3">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm" :loading="submitLoading">提交</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { Plus } from 'lucide-vue-next';
import { ElMessage, ElMessageBox } from 'element-plus';
import request from '@/utils/request';

const loading = ref(false);
const submitLoading = ref(false);
const dialogVisible = ref(false);
const tagList = ref([]);

const form = ref({
  id: null,
  tagName: ''
});

const getList = async () => {
  loading.value = true;
  try {
    const res = await request.get('/system/tag/list');
    tagList.value = res.rows || res.data || [];
  } finally {
    loading.value = false;
  }
};

const handleAdd = () => {
  form.value = { id: null, tagName: '' };
  dialogVisible.value = true;
};

const handleEdit = (row) => {
  form.value = { ...row };
  dialogVisible.value = true;
};

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该标签吗？', '警告', {
    type: 'warning'
  }).then(async () => {
    await request.delete(`/system/tag/${row.id}`);
    ElMessage.success('删除成功');
    getList();
  });
};

const submitForm = async () => {
  if (!form.value.tagName) return ElMessage.error('标签名称不能为空');
  submitLoading.value = true;
  try {
    if (form.value.id) {
      await request.put('/system/tag', form.value);
    } else {
      await request.post('/system/tag', form.value);
    }
    ElMessage.success('操作成功');
    dialogVisible.value = false;
    getList();
  } finally {
    submitLoading.value = false;
  }
};

onMounted(() => {
  getList();
});
</script>
